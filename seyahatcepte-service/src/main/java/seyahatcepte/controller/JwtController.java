package seyahatcepte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import seyahatcepte.request.JwtRequest;
import seyahatcepte.response.JwtResponse;
import seyahatcepte.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {
	
	@Autowired
	private JwtService jwtService;
	
	//create JwtToken
	@PostMapping(value = "/authenticate")
	public JwtResponse create(@RequestBody JwtRequest jwtRequest) throws Exception {
		
		return jwtService.create(jwtRequest);
	}
}
