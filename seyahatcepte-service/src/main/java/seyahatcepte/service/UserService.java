package seyahatcepte.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import seyahatcepte.exception.AeroplaneServiceNotFoundException;
import seyahatcepte.exception.BusServiceNotFoundException;
import seyahatcepte.exception.SeyahatCepteException;
import seyahatcepte.exception.UserNotFoundException;
import seyahatcepte.client.CardPayment;
import seyahatcepte.client.CardPaymentClient;
import seyahatcepte.client.EftPayment;
import seyahatcepte.client.EftPaymentClient;
import seyahatcepte.controller.UserController;
import seyahatcepte.converter.TicketConverter;
import seyahatcepte.converter.UserConverter;
import seyahatcepte.model.Aeroplane;
import seyahatcepte.model.Bus;
import seyahatcepte.model.Role;
import seyahatcepte.model.Ticket;
import seyahatcepte.model.User;
import seyahatcepte.model.enums.UserType;
import seyahatcepte.model.enums.VehicleType;
import seyahatcepte.queue.SeyahatCepteEmailQueue;
import seyahatcepte.queue.SeyahatCepteSmsQueue;
import seyahatcepte.repository.AeroplaneRepository;
import seyahatcepte.repository.BusRepository;
import seyahatcepte.repository.RoleRepository;
import seyahatcepte.repository.TicketRepository;
import seyahatcepte.repository.UserRepository;
import seyahatcepte.request.CardPaymentRequest;
import seyahatcepte.request.EftPaymentRequest;
import seyahatcepte.request.UserRequest;
import seyahatcepte.response.TicketResponse;
import seyahatcepte.response.UserResponse;

@Service
public class UserService {

	private static final int MAX_INDIVIDUAL_TICKET_SIZE_PER_SERVICE = 5;
	private static final int MAX_CORPORATE_TICKET_SIZE_PER_SERVICE = 20;
	private static final int MAX_INDIVIDUAL_ALLOWED_MALE_NUMBER = 2;
	public List<Ticket> tickets = new ArrayList<>();

	Logger logger = Logger.getLogger(UserController.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BusRepository busRepository;

	@Autowired
	AeroplaneRepository aeroplaneRepository;

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private TicketConverter ticketConverter;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private SeyahatCepteEmailQueue seyahatcepteEmailQueue;
	
	@Autowired
	private SeyahatCepteSmsQueue seyahatcepteSmsQueue;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CardPaymentClient cardPaymentClient;

	@Autowired
	private EftPaymentClient eftPaymentClient;

	@Autowired
	private TicketService ticketService;

	public UserResponse create(UserRequest userRequest) {

		User createdUser = userConverter.convert(userRequest);

		Role role = roleRepository.findById("User").get();

		// set 'User' role to user
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		createdUser.setRoles(roles);

		// encode user password
		createdUser.setPassword(getEncodedPassword(createdUser.getPassword()));

		userRepository.save(createdUser);

		logger.log(Level.INFO, "[createUser] - user created: {0}", createdUser.getUserName());

		// send notification to user's email
		rabbitTemplate.convertAndSend(seyahatcepteEmailQueue.getQueueName(), userRequest.getEmail());

		return userConverter.convert(createdUser);
	}

	// create admin user and admin/user roles every time the application runs
	public void initRoleAndUser() {

		Role adminRole = new Role();
		adminRole.setName("Admin");
		adminRole.setDescription("Admin role");

		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("User");
		userRole.setDescription("Default role for newly created record");

		roleRepository.save(userRole);

		User adminUser = new User();
		adminUser.setUserName("admin123");
		adminUser.setPassword(getEncodedPassword("admin@pass"));
		adminUser.setName("admin");
		adminUser.setSurname("admin");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setCreateDate(LocalDateTime.now());
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRoles(adminRoles);

		userRepository.save(adminUser);
	}

	// get all users
	public List<UserResponse> getAll() {

		logger.log(Level.INFO, "[getAllUsers] - all users are found");

		return userConverter.convert(userRepository.findAll());
	}

	// get user by username
	public UserResponse getByUserName(String userName) {

		logger.log(Level.INFO, "[getUserByUserName] - user is found: {0}", userName);

		return getAll().stream().filter(user -> userName.equals(user.getUserName())).findAny().get();
	}

	// user buys bus ticket with credit card
	public UserResponse buyBusTicket(CardPaymentRequest cardPaymentRequest, Integer id) {

		// find desired bus by id
		Bus foundBus = busRepository.findById(id)
				.orElseThrow(() -> new BusServiceNotFoundException("Bus service not found!"));
		// find the user who sent the request
		User foundUser = userRepository.findById(cardPaymentRequest.getUserName())
				.orElseThrow(() -> new UserNotFoundException("User not found!"));

		// check bus capacity
		validateBusCapacity(cardPaymentRequest, foundBus);

		// check user's ticket list
		validateTicketSize(foundUser, id);

		if ((UserType.INDIVIDUAL.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
				+ cardPaymentRequest.getCustomerGenders().length() <= MAX_INDIVIDUAL_TICKET_SIZE_PER_SERVICE)
				|| (UserType.CORPORATE.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
						+ cardPaymentRequest.getCustomerGenders().length() <= MAX_CORPORATE_TICKET_SIZE_PER_SERVICE)) {

			// If user meets the above conditions create new credit card payment
			CardPayment cardPayment = new CardPayment(cardPaymentRequest.getUserName(), cardPaymentRequest.getCardNo(),
					cardPaymentRequest.getCustomerGenders());
			// check genders in the request
			validateCustomerGenderNumber(foundUser, cardPaymentRequest);
			// save the payment
			cardPaymentClient.cardPayment(cardPayment);

			logger.log(Level.INFO, "[saveCardPayment] - payment is saved: {0}", cardPayment.getUserName());

			// create number of tickets that specified by user
			for (int i = 0; i < cardPaymentRequest.getCustomerGenders().length(); i++) {

				Ticket ticket = new Ticket(foundBus.getBeginning(), foundBus.getDestination(), foundBus.getMonth(),
						foundBus.getDay(), foundBus.getHour(), VehicleType.BUS, foundBus.getId(), foundUser,
						foundBus.getPrice());

				// save tickets
				ticketRepository.save(ticket);

				logger.log(Level.INFO, "[saveTickets] - ticket is saved: {0}", ticket.getId());
				
				//convert ticket for rabbitmq
				TicketResponse ticketResponse = ticketConverter.convert(ticket);
				
				//send ticket info
				rabbitTemplate.convertAndSend(seyahatcepteSmsQueue.getQueueName(), ticketResponse);
				
				logger.log(Level.INFO, "[ticketInfo] - ticketInfo: {0}, sent to: {1}",
						new Object[] {ticketResponse, seyahatcepteSmsQueue.getQueueName()});

				// add tickets to user's ticket list
				foundUser.getTicketList().add(ticket);

			}

		} else {

			// if the user does not meet conditions throw an exception
			logger.log(Level.WARNING, "[ticketListSize] - user exceeds the number of tickets allowed: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException(
					"The operation cannot continue because the user exceeds " + "the maximum number of tickets!");
		}

		// update user
		User updatedUser = userRepository.save(foundUser);

		logger.log(Level.INFO, "[updateUser] - user is updated: {0}", updatedUser.getUserName());

		return userConverter.convert(updatedUser);

	}

	// user buys aeroplane ticket with credit card
	public UserResponse buyAeroplaneTicket(CardPaymentRequest cardPaymentRequest, Integer id) {

		// find desired aeroplane by id
		Aeroplane foundAeroplane = aeroplaneRepository.findById(id)
				.orElseThrow(() -> new AeroplaneServiceNotFoundException("Aeroplane service not found!"));
		// find the user who sent the request
		User foundUser = userRepository.findById(cardPaymentRequest.getUserName())
				.orElseThrow(() -> new UserNotFoundException("User not found!"));

		// check aeroplane capacity
		validateAeroplaneCapacity(cardPaymentRequest, foundAeroplane);

		// check user's ticket list
		validateTicketSize(foundUser, id);

		if ((UserType.INDIVIDUAL.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
				+ cardPaymentRequest.getCustomerGenders().length() <= MAX_INDIVIDUAL_TICKET_SIZE_PER_SERVICE)
				|| (UserType.CORPORATE.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
						+ cardPaymentRequest.getCustomerGenders().length() <= MAX_CORPORATE_TICKET_SIZE_PER_SERVICE)) {

			// If user meets the above conditions create new credit card payment
			CardPayment cardPayment = new CardPayment(cardPaymentRequest.getUserName(), cardPaymentRequest.getCardNo(),
					cardPaymentRequest.getCustomerGenders());
			// check genders in the request
			validateCustomerGenderNumber(foundUser, cardPaymentRequest);
			// save the payment
			cardPaymentClient.cardPayment(cardPayment);

			logger.log(Level.INFO, "[saveCardPayment] - payment is saved: {0}", cardPayment.getUserName());

			// create number of tickets that specified by user
			for (int i = 0; i < cardPaymentRequest.getCustomerGenders().length(); i++) {

				Ticket ticket = new Ticket(foundAeroplane.getBeginning(), foundAeroplane.getDestination(),
						foundAeroplane.getMonth(), foundAeroplane.getDay(), foundAeroplane.getHour(),
						VehicleType.AEROPLANE, foundAeroplane.getId(), foundUser, foundAeroplane.getPrice());

				// save tickets
				ticketRepository.save(ticket);

				logger.log(Level.INFO, "[saveTickets] - ticket is saved: {0}", ticket.getId());
				
				//convert ticket for rabbitmq
				TicketResponse ticketResponse = ticketConverter.convert(ticket);
				
				//send ticket info
				rabbitTemplate.convertAndSend(seyahatcepteSmsQueue.getQueueName(), ticketResponse);
				
				logger.log(Level.INFO, "[ticketInfo] - ticketInfo: {0}, sent to: {1}",
						new Object[] {ticketResponse, seyahatcepteSmsQueue.getQueueName()});

				// add tickets to user's ticket list
				foundUser.getTicketList().add(ticket);
			}
		} else {

			// if the user does not meet conditions throw an exception
			logger.log(Level.WARNING, "[ticketListSize] - user exceeds the number of tickets allowed: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException(
					"The operation cannot continue because the user exceeds " + "the maximum number of tickets!");
		}

		// update user
		User updatedUser = userRepository.save(foundUser);

		logger.log(Level.INFO, "[updateUser] - user is updated: {0}", updatedUser.getUserName());

		return userConverter.convert(updatedUser);
	}

	// user buys bus ticket with eft
	public UserResponse buyBusTicket(EftPaymentRequest eftPaymentRequest, Integer id) {

		// find desired bus by id
		Bus foundBus = busRepository.findById(id)
				.orElseThrow(() -> new BusServiceNotFoundException("Bus service not found!"));
		// find the user who sent the request
		User foundUser = userRepository.findById(eftPaymentRequest.getUserName())
				.orElseThrow(() -> new UserNotFoundException("User not found!"));

		// validateBusCapacity
		validateBusCapacity(eftPaymentRequest, foundBus);

		// check user's ticket list
		validateTicketSize(foundUser, id);

		if ((UserType.INDIVIDUAL.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
				+ eftPaymentRequest.getCustomerGenders().length() <= MAX_INDIVIDUAL_TICKET_SIZE_PER_SERVICE)
				|| (UserType.CORPORATE.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
						+ eftPaymentRequest.getCustomerGenders().length() <= MAX_CORPORATE_TICKET_SIZE_PER_SERVICE)) {

			// If user meets the above conditions create new eft payment
			EftPayment eftPayment = new EftPayment(eftPaymentRequest.getUserName(), eftPaymentRequest.getSenderIban(),
					eftPaymentRequest.getReceiverIban(), eftPaymentRequest.getCustomerGenders());
			// check genders in the request
			validateCustomerGenderNumber(foundUser, eftPaymentRequest);
			// save the payment
			eftPaymentClient.eftPayment(eftPayment);

			logger.log(Level.INFO, "[saveEftPayment] - payment is saved: {0}", eftPayment.getUserName());

			// create number of tickets that specified by user
			for (int i = 0; i < eftPaymentRequest.getCustomerGenders().length(); i++) {

				Ticket ticket = new Ticket(foundBus.getBeginning(), foundBus.getDestination(), foundBus.getMonth(),
						foundBus.getDay(), foundBus.getHour(), VehicleType.BUS, foundBus.getId(), foundUser,
						foundBus.getPrice());

				// save tickets
				ticketRepository.save(ticket);

				logger.log(Level.INFO, "[saveTickets] - ticket is saved: {0}", ticket.getId());
				
				//convert ticket for rabbitmq
				TicketResponse ticketResponse = ticketConverter.convert(ticket);
				
				//send ticket info
				rabbitTemplate.convertAndSend(seyahatcepteSmsQueue.getQueueName(), ticketResponse);
				
				logger.log(Level.INFO, "[ticketInfo] - ticketInfo: {0}, sent to: {1}",
						new Object[] {ticketResponse, seyahatcepteSmsQueue.getQueueName()});

				// add tickets to user's ticket list
				foundUser.getTicketList().add(ticket);
			}
		} else {

			// if the user does not meet conditions throw an exception
			logger.log(Level.WARNING, "[ticketListSize] - user exceeds the number of tickets allowed: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException(
					"The operation cannot continue because the user exceeds " + "the maximum number of tickets!");
		}

		// update user
		User updatedUser = userRepository.save(foundUser);

		logger.log(Level.INFO, "[updateUser] - user is updated: {0}", updatedUser.getUserName());

		return userConverter.convert(updatedUser);
	}

	// user buys aeroplane ticket with eft
	public UserResponse buyAeroplaneTicket(EftPaymentRequest eftPaymentRequest, Integer id) {

		// find aeroplane by id
		Aeroplane foundAeroplane = aeroplaneRepository.findById(id)
				.orElseThrow(() -> new AeroplaneServiceNotFoundException("Aeroplane service not found!"));
		// find the user who sent the request
		User foundUser = userRepository.findById(eftPaymentRequest.getUserName())
				.orElseThrow(() -> new UserNotFoundException("User not found!"));

		// validate aeroplane capacity
		validateAeroplaneCapacity(eftPaymentRequest, foundAeroplane);

		// check user's ticket list
		validateTicketSize(foundUser, id);

		if ((UserType.INDIVIDUAL.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
				+ eftPaymentRequest.getCustomerGenders().length() <= MAX_INDIVIDUAL_TICKET_SIZE_PER_SERVICE)
				|| (UserType.CORPORATE.equals(foundUser.getType()) && validateTicketSize(foundUser, id)
						+ eftPaymentRequest.getCustomerGenders().length() <= MAX_CORPORATE_TICKET_SIZE_PER_SERVICE)) {

			// If user meets the above conditions cre)ate new eft payment
			EftPayment eftPayment = new EftPayment(eftPaymentRequest.getUserName(), eftPaymentRequest.getSenderIban(),
					eftPaymentRequest.getReceiverIban(), eftPaymentRequest.getCustomerGenders());
			// check genders in the request
			validateCustomerGenderNumber(foundUser, eftPaymentRequest);
			// save the payment
			eftPaymentClient.eftPayment(eftPayment);

			logger.log(Level.INFO, "[saveEftPayment] - payment is saved: {0}", eftPayment.getUserName());

			// create number of tickets that specified by user
			for (int i = 0; i < eftPaymentRequest.getCustomerGenders().length(); i++) {

				Ticket ticket = new Ticket(foundAeroplane.getBeginning(), foundAeroplane.getDestination(),
						foundAeroplane.getMonth(), foundAeroplane.getDay(), foundAeroplane.getHour(),
						VehicleType.AEROPLANE, foundAeroplane.getId(), foundUser, foundAeroplane.getPrice());

				// save tickets
				ticketRepository.save(ticket);

				logger.log(Level.INFO, "[saveTickets] - ticket is saved: {0}", ticket.getId());
				
				//convert ticket for rabbitmq
				TicketResponse ticketResponse = ticketConverter.convert(ticket);
				
				//send ticket info
				rabbitTemplate.convertAndSend(seyahatcepteSmsQueue.getQueueName(), ticketResponse);
				
				logger.log(Level.INFO, "[ticketInfo] - ticketInfo: {0}, sent to: {1}",
						new Object[] {ticketResponse, seyahatcepteSmsQueue.getQueueName()});

				// add tickets to user's ticket list
				foundUser.getTicketList().add(ticket);
			}
		} else {

			// if the user does not meet conditions throw an exception
			logger.log(Level.WARNING, "[ticketListSize] - user exceeds the number of tickets allowed: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException(
					"The operation cannot continue because the user exceeds " + "the maximum number of tickets!");
		}

		// update user
		User updatedUser = userRepository.save(foundUser);

		logger.log(Level.INFO, "[updateUser] - user is updated: {0}", updatedUser.getUserName());

		return userConverter.convert(updatedUser);

	}

	public String getEncodedPassword(String password) {

		return passwordEncoder.encode(password);
	}

	private Long validateTicketSize(User foundUser, Integer serviceId) {

		// find the number of all tickets for the same service in the user's ticket list
		long number = foundUser.getTicketList().stream().filter(ticket -> serviceId.equals(ticket.getServiceId()))
				.count();

		if (UserType.INDIVIDUAL.equals(foundUser.getType()) && MAX_INDIVIDUAL_TICKET_SIZE_PER_SERVICE == number) {

			// if the number is greater than 5 for individual users, throw an exception
			logger.log(Level.WARNING,
					"[validateTicketSize] - individual user cannot buy more than five tickets for one service!: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException("Individual user cannot buy more than 5 tickets for one service");
		}

		else if (UserType.CORPORATE.equals(foundUser.getType()) && MAX_CORPORATE_TICKET_SIZE_PER_SERVICE == number) {

			// if the number is greater than 20 for corporate users, throw an exception
			logger.log(Level.WARNING,
					"[validateTicketSize] - corporate user cannot buy more than twenty tickets for one service: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException("Corporate user cannot buy more than 20 tickets for one service!");
		}

		else {

			return number;
		}
	}

	// validate the number of male customer for one order which will pay by credit
	// card
	private void validateCustomerGenderNumber(User foundUser, CardPaymentRequest cardPaymentRequest) {

		// find the number of tickets to be issued for males
		long number = cardPaymentRequest.getCustomerGenders().chars().filter(it -> it == 'M').count();

		if (UserType.INDIVIDUAL.equals(foundUser.getType()) && number > MAX_INDIVIDUAL_ALLOWED_MALE_NUMBER) {

			// if the number is greater than 2, throw an exception
			logger.log(Level.WARNING,
					"[validateCustomerGenderNumber] - individual user cannot buy tickets for more than "
							+ "two male for a single service: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException(
					"Individual user cannot buy tickets for more than 2 males for one service!");
		}
	}

	// validate the number of male customer for one order which will pay by eft
	private void validateCustomerGenderNumber(User foundUser, EftPaymentRequest eftPaymentRequest) {

		// find the number of tickets to be issued for males
		long number = eftPaymentRequest.getCustomerGenders().chars().filter(it -> it == 'M').count();

		if (UserType.INDIVIDUAL.equals(foundUser.getType()) && number > MAX_INDIVIDUAL_ALLOWED_MALE_NUMBER) {

			// if the number is greater than 2, throw an exception
			logger.log(Level.WARNING,
					"[validateCustomerGenderNumber] - individual user cannot buy tickets for more than "
							+ "two male for a single service: {0}",
					foundUser.getUserName());

			throw new SeyahatCepteException(
					"Individual user cannot buy tickets for more than 2 males for one service!");
		}
	}

	// validate bus capacity for credit card payment
	private void validateBusCapacity(CardPaymentRequest cardPaymentRequest, Bus foundBus) {

		int capacity = foundBus.getCapacity();
		long sold = ticketService.getTotalNumberTicketSoldByServiceId(foundBus.getId());
		int request = cardPaymentRequest.getCustomerGenders().length();

		if (capacity - (sold + request) < 0) {

			// if the bus is out of capacity, throw an exception
			throw new SeyahatCepteException("The service is out of capacity!");
		}
	}

	// validate aeroplane capacity for credit card payment
	private void validateAeroplaneCapacity(CardPaymentRequest cardPaymentRequest, Aeroplane foundAeroplane) {

		int capacity = foundAeroplane.getCapacity();
		long sold = ticketService.getTotalNumberTicketSoldByServiceId(foundAeroplane.getId());
		int request = cardPaymentRequest.getCustomerGenders().length();

		if (capacity - (sold + request) < 0) {

			// if the aeroplane is out of capacity, throw an exception
			throw new SeyahatCepteException("The service is out of capacity!");
		}
	}

	// validate bus capacity for eft payment
	private void validateBusCapacity(EftPaymentRequest eftPaymentRequest, Bus foundBus) {

		int capacity = foundBus.getCapacity();
		long sold = ticketService.getTotalNumberTicketSoldByServiceId(foundBus.getId());
		int request = eftPaymentRequest.getCustomerGenders().length();

		if (capacity - (sold + request) < 0) {

			// if the bus is out of capacity, throw an exception
			throw new SeyahatCepteException("The service is out of capacity!");
		}
	}

	private void validateAeroplaneCapacity(EftPaymentRequest eftPaymentRequest, Aeroplane foundAeroplane) {

		int capacity = foundAeroplane.getCapacity();
		long sold = ticketService.getTotalNumberTicketSoldByServiceId(foundAeroplane.getId());
		int request = eftPaymentRequest.getCustomerGenders().length();

		if (capacity - (sold + request) < 0) {

			// if the aeroplane is out of capacity, throw an exception
			throw new SeyahatCepteException("The service is out of capacity!");
		}
	}

}
