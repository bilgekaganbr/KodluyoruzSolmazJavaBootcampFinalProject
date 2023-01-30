package seyahatcepte.converter;

import org.springframework.stereotype.Component;

import seyahatcepte.model.Ticket;
import seyahatcepte.response.TicketResponse;

@Component
public class TicketConverter {

	public TicketResponse convert(Ticket ticket) {
		
		TicketResponse ticketResponse = new TicketResponse();
		
		ticketResponse.setId(ticket.getId());
		ticketResponse.setBeginning(ticket.getBeginning());
		ticketResponse.setDestination(ticket.getDestination());
		ticketResponse.setMonth(ticket.getMonth());
		ticketResponse.setDay(ticket.getDay());
		ticketResponse.setHour(ticket.getDay());
		ticketResponse.setType(ticket.getType());
		ticketResponse.setServiceId(ticket.getServiceId());
		
		return ticketResponse;
	}
}
