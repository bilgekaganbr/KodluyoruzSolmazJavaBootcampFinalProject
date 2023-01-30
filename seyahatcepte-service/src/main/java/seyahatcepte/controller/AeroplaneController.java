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

import seyahatcepte.model.Aeroplane;
import seyahatcepte.service.AeroplaneService;

@RestController
@RequestMapping
public class AeroplaneController {
	
	@Autowired
	private AeroplaneService aeroplaneService;
	
	//create aeroplane service(only admin)
	@PostMapping(value = "/admin/aeroplane/services/create")
	@PreAuthorize("hasRole('Admin')")
	public Aeroplane create(@RequestBody Aeroplane aeroplane) {
		
		aeroplaneService.create(aeroplane);
		return aeroplane;
	}
	
	//delete aeroplane service by id(only admin)
	@DeleteMapping(value = "/admin/aeroplane/services/delete/{id}")
	@PreAuthorize("hasRole('Admin')")
	public boolean delete(@PathVariable Integer id) {
		
		aeroplaneService.delete(id);
		return true;
	}
	
	//get all aeroplane services
	@GetMapping(value = "/aeroplane/services")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Aeroplane> getAll() {
		
		return aeroplaneService.getAll();
	}
	
	//get all aeroplane services by beginning
	@GetMapping(value = "/aeroplane/services/beginning/{beginning}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Aeroplane> getAllByBeginning(@PathVariable String beginning) {
		
		return aeroplaneService.getAllByBeginning(beginning);
	}
	
	//get all aeroplane services by destination
	@GetMapping(value = "/aeroplane/services/destination/{destination}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Aeroplane> getAllByDestination(@PathVariable String destination) {
		
		return aeroplaneService.getAllByDestination(destination);
	}
	
	//get all aeroplane services by month
	@GetMapping(value = "/aeroplane/services/month/{month}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Aeroplane> getAllByMonth(@PathVariable String month) {
		
		return aeroplaneService.getAllByMonth(month);
	}
	
	//get all aeroplane services by month and day
	@GetMapping(value = "aeroplane/services/month/day/{month}/{day}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Aeroplane> getAllByMonthAndDay(@PathVariable String month, @PathVariable String day) {
		
		return aeroplaneService.getAllByMonthAndDay(month, day);
	}
	
	//get all aeroplane services by month, day, and hour
	@GetMapping(value = "aeroplane/services/month/day/hour/{month}/{day}/{hour}")
	@PreAuthorize("hasAnyRole('Admin', 'User')")
	public List<Aeroplane> getAllByMonthAndDayAndHour(@PathVariable String month, @PathVariable String day, 
			@PathVariable String hour) {
		
		return aeroplaneService.getAllByMonthAndDayAndHour(month, day, hour);
	}

}
