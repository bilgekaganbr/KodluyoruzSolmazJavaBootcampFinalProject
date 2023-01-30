package seyahatceptePayment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seyahatceptePayment.model.CardPayment;
import seyahatceptePayment.repository.CardPaymentRepository;

@Service
public class CardPaymentService {
	
	@Autowired
	CardPaymentRepository cardPaymentRepository;

	public void cardPayment(CardPayment cardPayment) {
		
		cardPaymentRepository.save(cardPayment);
	}
	
	public List<CardPayment> getAll() {
		
		return cardPaymentRepository.findAll();
	}
}
