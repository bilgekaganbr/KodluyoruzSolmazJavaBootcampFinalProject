package seyahatceptePayment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import seyahatceptePayment.model.EftPayment;
import seyahatceptePayment.service.EftPaymentService;

@RestController
@RequestMapping
public class EftPaymentController {

	@Autowired
	private EftPaymentService eftPaymentService;
	
	@PostMapping(value = "/payment/EFT")
	public EftPayment eftPayment(@RequestBody EftPayment eftPayment) {
		
		eftPaymentService.eftPayment(eftPayment);
		return eftPayment;
	}
	
	@GetMapping(value = "/payment/EFT/all")
	public List<EftPayment> getAll() {
		
		return eftPaymentService.getAll();
	}
}
