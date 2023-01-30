package seyahatcepte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import seyahatcepte.model.Bus;
import seyahatcepte.service.BusService;

@RestController
@RequestMapping
public class BusController {
	
	@Autowired
	private BusService busService;
	
	//create bus service(only admin)
	@PostMapping(value = "/admin/bus/services/create")
	@PreAuthorize("hasRole('Admin')")
	public Bus create(@RequestBody Bus bus) {
		
		busService.create(bus);
		return bus;
	}
	
	//delete bus service(only admin)
	@DeleteMapping(value = "/admin/bus/services/delete/{id}")
	@PreAuthorize("hasRole('Admin')")
	public boolean delete(@PathVariable Integer id) {
		
		busService.delete(id);
		return true;
	}
	
	//get all bus services
	@GetMapping(value = "/bus/services") 
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Bus> getAll() {
		
		return busService.getAll();
	}
	
	//get all bus services by beginning
	@GetMapping(value = "/bus/services/beginning/{beginning}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Bus> getAllByBeginning(@PathVariable String beginning) {
		
		return busService.getAllByBeginning(beginning);
	}
	
	//get all bus services by destination
	@GetMapping(value = "/bus/services/destination/{destination}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Bus> getAllByDestination(@PathVariable String destination) {
		
		return busService.getAllByDestination(destination);
	}
	
	//get all bus services by month
	@GetMapping(value = "/bus/services/month/{month}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Bus> getAllByMonth(@PathVariable String month) {
		
		return busService.getAllByMonth(month);
	}
	
	//get all bus services by month and day
	@GetMapping(value = "/bus/services/month/day/{month}/{day}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Bus> getAllByMonthAndDay(@PathVariable String month, @PathVariable String day) {
		
		return busService.getAllByMonthAndDay(month, day);
	}
	
	//get all bus services by month, day, and hour
	@GetMapping(value = "/bus/services/month/day/hour/{month}/{day}/{hour}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Bus> getAllByMonthAndDayAndHour(@PathVariable String month, @PathVariable String day, @PathVariable String hour) {
		
		return busService.getAllByMonthAndDayAndHour(month, day, hour);
	}
 
}
