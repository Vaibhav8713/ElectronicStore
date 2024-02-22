package com.lcwd.electronic.store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext; 

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String>{
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		System.out.println("==> custom annotation logic <==");
		//logic 
		if(value.isBlank()) return false;
		return true; 
	}
}
