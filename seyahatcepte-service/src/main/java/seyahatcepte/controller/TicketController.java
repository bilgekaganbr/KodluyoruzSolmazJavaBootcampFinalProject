package seyahatcepte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import seyahatcepte.model.Ticket;
import seyahatcepte.service.TicketService;

@RestController
@RequestMapping
public class TicketController {
	
	@Autowired
	private TicketService ticketService;

	//get all tickets(only admin)
	@GetMapping(value = "admin/tickets/all")
	@PreAuthorize("hasRole('Admin')")
	public List<Ticket> getAll() {
		
		return ticketService.getAll();
	}
	
	//get all tickets by username
	@GetMapping(value = "/{userName}/tickets")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Ticket> getAllByUserName(@PathVariable String userName) {
		
		return ticketService.getAllByUserName(userName);
	}
	
	//get all bus tickets(only admin)
	@GetMapping(value = "/admin/tickets/bus")
	@PreAuthorize("hasRole('Admin')")
	public List<Ticket> getAllBus() {
		
		return ticketService.getAllBus();
	}
	
	//get all aeroplane tickets(only admin)
	@GetMapping(value = "/admin/tickets/aeroplane")
	@PreAuthorize("hasRole('Admin')")
	public List<Ticket> getAllAeroplane() {
		
		return ticketService.getAllAeroplane();
	}
	
	//get the number of tickets sold for a particular service(only admin)
	@GetMapping(value = "/admin/tickets/sold/{id}")
	@PreAuthorize("hasRole('Admin')")
	public Long getTotalNumberTicketSoldByServiceId(@PathVariable Integer id) {
		
		return ticketService.getTotalNumberTicketSoldByServiceId(id);
	}
	
	//get the number of all tickets sold(only admin)
	@GetMapping(value = "/admin/tickets/sold/all")
	@PreAuthorize("hasRole('Admin')")
	public Integer getTotalNumberTicketSold() {
		
		return ticketService.getTotalNumberTicketSold();
	}
	
	//get the total income for a particular service(only admin)
	@GetMapping(value = "/admin/tickets/income/{id}")
	@PreAuthorize("hasRole('Admin')")
	public Integer getTotalIncomeByServiceId(@PathVariable Integer id) {
		
		return ticketService.getTotalIncomeByServiceId(id);
	}
	
	//get the total income(only admin)
	@GetMapping(value = "/admin/tickets/income/total")
	@PreAuthorize("hasRole('Admin')")
	public Integer getTotalIncome() {
		
		return ticketService.getTotalIncome();
	}
}
