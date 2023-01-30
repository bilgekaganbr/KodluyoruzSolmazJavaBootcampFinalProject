package seyahatcepteNotification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import seyahatcepteNotification.response.TicketResponse;

@Component
public class NotificationListener {
	
	@RabbitListener(queues = "seyahatcepte.notification.email")
	public void emailListener(String email) {
		
		System.out.println("Thank you for creating your account! :" + email);
	}
	
	@RabbitListener(queues = "seyahatcepte.notification.sms") 
	public void smsListener(TicketResponse ticketResponse) {
		
		System.out.println("ticket info: " + ticketResponse);
	}

}
