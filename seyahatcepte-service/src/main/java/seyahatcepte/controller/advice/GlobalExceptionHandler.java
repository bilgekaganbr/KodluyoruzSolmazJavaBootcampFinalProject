package seyahatcepte.controller.advice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import seyahatcepte.exception.AeroplaneServiceNotFoundException;
import seyahatcepte.exception.BusServiceNotFoundException;
import seyahatcepte.exception.ExceptionResponse;
import seyahatcepte.exception.RoleNotFoundException;
import seyahatcepte.exception.SeyahatCepteException;
import seyahatcepte.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/*@Autowired
	private MessageSource messageSource;*/

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handle(UserNotFoundException exception) {
		
		return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(SeyahatCepteException.class)
	public ResponseEntity<ExceptionResponse> handle(SeyahatCepteException exception) {
		
		return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(BusServiceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handle(BusServiceNotFoundException exception) {
		
		return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(AeroplaneServiceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handle(AeroplaneServiceNotFoundException exception) {
		
		return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handle(RoleNotFoundException exception) {
		
		return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
}
