package com.lcwd.electronic.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Object object;

	//handler resource not found exception//
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		System.out.println("==> global exception handler invoked !!");
		
		ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
		apiResponseMessage.setMessage(ex.getMessage());
		apiResponseMessage.setStatus(HttpStatus.NOT_FOUND); 
		apiResponseMessage.setSuccess(true);
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage, HttpStatus.NOT_FOUND);
	}
	
	//method argument not valid exception//
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String, Object> response = new HashMap<>();
		allErrors.stream().forEach(ObjectError -> {
			String msg = ObjectError.getDefaultMessage();
			String field = ((FieldError)ObjectError).getField();
			response.put(field, msg);
		});
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	//Handle bad api requesst
	@ExceptionHandler(BadApiRequest.class) 
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex)
	{
		System.out.println("==> bad api request in global exception got invoked !!");
		
		ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
		apiResponseMessage.setMessage(ex.getMessage());
		apiResponseMessage.setStatus(HttpStatus.BAD_REQUEST); 
		apiResponseMessage.setSuccess(false);
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage, HttpStatus.BAD_REQUEST);
	}
}








