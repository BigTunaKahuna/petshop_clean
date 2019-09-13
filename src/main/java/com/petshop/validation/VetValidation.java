package com.petshop.validation;


import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.petshop.dto.VetDTO;
import com.petshop.models.Vet;

@Configuration
public class VetValidation implements Validator{
	public boolean supports(Class clazz) {
		return VetDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		ValidationUtils.rejectIfEmpty(errors, "yearsOfExperience", "yearsOfExperience.empty");
		ValidationUtils.rejectIfEmpty(errors, "age", "age.empty");
		VetDTO vet = (VetDTO) target;
		
//		if(vet.getAge()<20)
//			errors.rejectValue("age", "too.small");
	}
}
