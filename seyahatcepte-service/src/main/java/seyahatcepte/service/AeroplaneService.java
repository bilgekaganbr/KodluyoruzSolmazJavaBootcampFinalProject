package seyahatcepte.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seyahatcepte.controller.AeroplaneController;
import seyahatcepte.exception.AeroplaneServiceNotFoundException;
import seyahatcepte.model.Aeroplane;
import seyahatcepte.repository.AeroplaneRepository;

@Service
public class AeroplaneService {

	Logger logger = Logger.getLogger(AeroplaneController.class.getName());

	@Autowired
	private AeroplaneRepository aeroplaneRepository;

	// create aeroplane service
	public void create(Aeroplane aeroplane) {

		logger.log(Level.INFO, "[createAeroplaneService] - aeroplane service created: {0}", aeroplane.getId());

		aeroplaneRepository.save(aeroplane);
	}

	// delete aeroplane service
	public void delete(Integer id) {

		Aeroplane aeropane = aeroplaneRepository.findById(id)
				.orElseThrow(() -> new AeroplaneServiceNotFoundException("Aeroplane service not found!"));

		logger.log(Level.INFO, "[deleteAeroplaneService] - aeroplane service deleted: {0}", id);

		aeroplaneRepository.delete(aeropane);
	}

	// get all aeroplane services
	public List<Aeroplane> getAll() {

		logger.log(Level.INFO, "[findAllAeroplaneServices] - all aeroplane services are found");

		return aeroplaneRepository.findAll();
	}

	// get all aeroplane services by beginning
	public List<Aeroplane> getAllByBeginning(String beginning) {

		logger.log(Level.INFO,
				"[findAllAeroplaneServicesByBeginning] - all aeroplane services are found by beginning: {0}",
				beginning);

		return getAll().stream().filter(aeroplane -> aeroplane.getBeginning().equals(beginning)).toList();
	}

	// get all aeroplane services by destination
	public List<Aeroplane> getAllByDestination(String destination) {

		logger.log(Level.INFO,
				"[findAllAeroplaneServicesByDestination] - all aeroplane services are found by destination: {0}",
				destination);

		return getAll().stream().filter(aeroplane -> aeroplane.getDestination().equals(destination)).toList();
	}

	// get all aeroplane services by month
	public List<Aeroplane> getAllByMonth(String month) {

		logger.log(Level.INFO, "[findAllAeroplaneServicesByMonth] - all aeroplane services are found by month: {0}",
				month);

		return getAll().stream().filter(aeroplane -> aeroplane.getMonth().equals(month)).toList();
	}

	// get all aeroplane services by month and day
	public List<Aeroplane> getAllByMonthAndDay(String month, String day) {

		logger.log(Level.INFO,
				"[findAllAeroplaneServicesByMonthAndDay] - all aeroplane services are found by month: {0} and day: {1}",
				new Object[] { month, day });

		return getAll().stream().filter(aeroplane -> aeroplane.getMonth().equals(month))
				.filter(aeroplane -> aeroplane.getDay().equals(day)).toList();
	}

	// get all aeroplane services by month, day, and hour
	public List<Aeroplane> getAllByMonthAndDayAndHour(String month, String day, String hour) {

		logger.log(Level.INFO,
				"findAeroplaneServicesByMonthAndDayAndHour] - all aeroplaneServices are found by month: {0}, day: {1}, and hour: {2}",
				new Object[] { month, day, hour });

		return getAll().stream().filter(aeroplane -> aeroplane.getMonth().equals(month))
				.filter(aeroplane -> aeroplane.getDay().equals(day))
				.filter(aeroplane -> aeroplane.getHour().equals(hour)).toList();
	}

}
