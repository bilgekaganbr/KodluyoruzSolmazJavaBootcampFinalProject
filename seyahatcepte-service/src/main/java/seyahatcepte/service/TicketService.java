package seyahatcepte.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seyahatcepte.controller.TicketController;
import seyahatcepte.model.Ticket;
import seyahatcepte.model.enums.VehicleType;
import seyahatcepte.repository.TicketRepository;

@Service
public class TicketService {

	Logger logger = Logger.getLogger(TicketController.class.getName());

	@Autowired
	private TicketRepository ticketRepository;

	// get all tickets
	public List<Ticket> getAll() {

		logger.log(Level.INFO, "[getAllTickets] - all tickets are found");

		return ticketRepository.findAll();
	}

	// get all tickets by user name
	public List<Ticket> getAllByUserName(String userName) {

		logger.log(Level.INFO, "[getAllTicketsByUserName] - all tickets are found by username: {0}", userName);

		return getAll().stream().filter(ticket -> userName.equals(ticket.getUser().getUserName())).toList();
	}

	// get all bus tickets
	public List<Ticket> getAllBus() {

		logger.log(Level.INFO, "[getAllBusTickets] - all bus tickets are found");

		return getAll().stream().filter(ticket -> VehicleType.BUS.equals(ticket.getType())).toList();
	}

	// get all aeroplane tickets
	public List<Ticket> getAllAeroplane() {

		logger.log(Level.INFO, "[getAllAeroplaneTickets] - all aeroplane tickets are found");

		return getAll().stream().filter(ticket -> VehicleType.AEROPLANE.equals(ticket.getType())).toList();
	}

	// get the number of tickets sold for a particular service
	public Long getTotalNumberTicketSoldByServiceId(Integer id) {

		logger.log(Level.INFO,
				"[getTotalNumberTicketSoldByServiceId] - total number of ticket sold of service is calculated: ", id);

		return getAll().stream().filter(ticket -> id.equals(ticket.getServiceId())).count();
	}

	// get the number of all tickets sold
	public Integer getTotalNumberTicketSold() {

		logger.log(Level.INFO, "[getTotalNumberTicketSold] - total number of ticket sold calculated");

		return getAll().size();
	}

	// get the total income for a particular service
	public Integer getTotalIncomeByServiceId(Integer id) {

		List<Ticket> services = getAll().stream().filter(ticket -> id.equals(ticket.getServiceId())).toList();

		logger.log(Level.INFO, "[getTotalIncomeByServiceId] - total income of service is calculated: {0}", id);

		return services.size() * services.get(0).getCost();
	}

	// get the total income
	public Integer getTotalIncome() {

		int total = 0;

		for (int i = 0; i < getAll().size(); i++) {

			total += getAll().get(i).getCost();
		}

		logger.log(Level.INFO, "[getTotalIncome] - total income is calculated");

		return total;
	}
}
