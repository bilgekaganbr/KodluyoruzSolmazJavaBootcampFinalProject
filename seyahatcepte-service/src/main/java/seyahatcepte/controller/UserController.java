package seyahatcepte.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import seyahatcepte.request.CardPaymentRequest;
import seyahatcepte.request.EftPaymentRequest;
import seyahatcepte.request.UserRequest;
import seyahatcepte.response.UserResponse;
import seyahatcepte.service.UserService;

@RestController
@RequestMapping
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//create admin user and admin/user roles every time the application runs
	@PostConstruct
    public void initRoleAndUser() {
        
		userService.initRoleAndUser();
    }
	
	//register(create) user
	@PostMapping(value = "/register")
	public UserResponse create(@RequestBody UserRequest userRequest) {
		
		return userService.create(userRequest);
	}
	
	//get all users(only admin)
	@GetMapping(value = "/admin/users/all")
	@PreAuthorize("hasRole('Admin')")
	public List<UserResponse> getAll() {
		
		return userService.getAll();
	}
	
	//get user by username(admin only)
	@GetMapping(value = "admin/users/{userName}")
	@PreAuthorize("hasRole('Admin')")
	public UserResponse getByUserName(@PathVariable String userName) {
		
		return userService.getByUserName(userName);
	}

	//user buys bus ticket with credit card
	@PutMapping(value ="/bus/services/buy/creditCard/{id}")
	@PreAuthorize("hasRole('User')")
	public UserResponse buyBusTicket(@RequestBody CardPaymentRequest cardPaymentRequest, @PathVariable Integer id) {
		
		return userService.buyBusTicket(cardPaymentRequest, id);
	}
	
	//user buys aeroplane tickets with credit card
	@PutMapping(value = "/aeroplane/services/buy/creditCard/{id}")
	@PreAuthorize("hasRole('User')")
	public UserResponse buyAeroplaneTicket(@RequestBody CardPaymentRequest cardPaymentRequest, @PathVariable Integer id) {
		
		return userService.buyAeroplaneTicket(cardPaymentRequest, id);
	}
	
	//user buys bus ticket with eft
	@PutMapping(value = "/bus/services/buy/EFT/{id}")
	@PreAuthorize("hasRole('User')")
	public UserResponse buyBusTicket(@RequestBody EftPaymentRequest eftPaymentRequest, @PathVariable Integer id) {
		
		return userService.buyBusTicket(eftPaymentRequest, id);
	}
	
	//user buys aeroplane ticket with eft
	@PutMapping(value = "/aeroplane/services/buy/EFT/{id}")
	@PreAuthorize("hasRole('User')")
	public UserResponse buyAeroplaneTicket(@RequestBody EftPaymentRequest eftPaymentRequest, @PathVariable Integer id) {
		
		return userService.buyAeroplaneTicket(eftPaymentRequest, id);
	}

}
