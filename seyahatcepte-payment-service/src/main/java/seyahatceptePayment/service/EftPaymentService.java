package seyahatceptePayment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seyahatceptePayment.model.EftPayment;
import seyahatceptePayment.repository.EftPaymentRepository;

@Service
public class EftPaymentService {

	@Autowired EftPaymentRepository eftPaymentRepository;
	
	public void eftPayment(EftPayment eftPayment) {
		
		eftPaymentRepository.save(eftPayment);
	}
	
	public List<EftPayment> getAll() {
		
		return eftPaymentRepository.findAll();
	}
}
