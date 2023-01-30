package seyahatcepte.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SeyahatCepteSmsQueue {

	private String queueName = "seyahatcepte.notification.sms";
	private String exchange = "seyahatcepte.notification.sms";

	@Bean
	public Queue smsQueue() {

		return new Queue(queueName, false);
	}

	@Bean
	public DirectExchange smsExchange() {

		return new DirectExchange(exchange);
	}

	@Bean
	public Binding binding(Queue smsQueue, DirectExchange smsExchange) {

		return BindingBuilder.bind(smsQueue).to(smsExchange).with("");
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	
	
}
