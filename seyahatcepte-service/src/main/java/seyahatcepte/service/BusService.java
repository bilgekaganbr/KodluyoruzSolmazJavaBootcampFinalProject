package seyahatcepte.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seyahatcepte.controller.BusController;
import seyahatcepte.exception.BusServiceNotFoundException;
import seyahatcepte.model.Bus;
import seyahatcepte.repository.BusRepository;

@Service
public class BusService {

	Logger logger = Logger.getLogger(BusController.class.getName());

	@Autowired
	private BusRepository busRepository;

	// create bus service
	public void create(Bus bus) {

		logger.log(Level.INFO, "[createBusService] - bus service created: {0}", bus.getId());

		busRepository.save(bus);
	}

	// delete bus service
	public void delete(Integer id) {

		logger.log(Level.INFO, "[deleteBusService] - bus service deleted: {0}", id);

		Bus bus = busRepository.findById(id).orElseThrow(() -> new BusServiceNotFoundException("Bus service not found!"));
		busRepository.delete(bus);
	}

	// get all bus services
	public List<Bus> getAll() {

		logger.log(Level.INFO, "[findAllBusServices] - all bus services are found");

		return busRepository.findAll();
	}

	// get all bus services by beginning
	public List<Bus> getAllByBeginning(String beginning) {

		logger.log(Level.INFO, "[findAllBusServicesByBeginning] - all bus services are found by beginning: {0}",
				beginning);

		return getAll().stream().filter(bus -> bus.getBeginning().equals(beginning)).toList();
	}

	// get all bus services by destination
	public List<Bus> getAllByDestination(String destination) {

		logger.log(Level.INFO, "[findAllBusServicesByDestination] - all bus services are found by destination: {0}",
				destination);

		return getAll().stream().filter(bus -> bus.getDestination().equals(destination)).toList();
	}

	// get all bus services by month
	public List<Bus> getAllByMonth(String month) {

		logger.log(Level.INFO, "[findAllBusServicesByMonth] - all bus services are found by month: {0}", month);

		return getAll().stream().filter(bus -> bus.getMonth().equals(month)).toList();
	}

	// get all bus services by month and day
	public List<Bus> getAllByMonthAndDay(String month, String day) {

		logger.log(Level.INFO,
				"[findAllBusServicesByMonthAndDay] - all bus services are found by month: {0} and day: {1}",
				new Object[] { month, day });

		return getAll().stream().filter(bus -> bus.getMonth().equals(month)).filter(bus -> bus.getDay().equals(day))
				.toList();
	}

	// get all bus services by month, day, and hour
	public List<Bus> getAllByMonthAndDayAndHour(String month, String day, String hour) {

		logger.log(Level.INFO,
				"findBusServicesByMonthAndDayAndHour] - all bus services are found by month: {0}, day: {1}, and hour: {2}",
				new Object[] { month, day, hour });

		return getAll().stream().filter(bus -> bus.getMonth().equals(month)).filter(bus -> bus.getDay().equals(day))
				.filter(bus -> bus.getHour().equals(hour)).toList();

	}

}
