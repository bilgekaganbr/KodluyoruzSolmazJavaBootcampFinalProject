package seyahatceptePayment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import seyahatceptePayment.model.CardPayment;
import seyahatceptePayment.service.CardPaymentService;

@RestController
@RequestMapping
public class CardPaymentController {

	@Autowired
	private CardPaymentService cardPaymentService;
	
	@PostMapping(value = "/payment/creditCard")
	public CardPayment cardPayment(@RequestBody CardPayment cardPayment) {
		
		cardPaymentService.cardPayment(cardPayment);
		return cardPayment;
	}
	
	@GetMapping(value = "/payment/creditCard/all") 
	public List<CardPayment> getAll() {
		
		return cardPaymentService.getAll();
	}
}
