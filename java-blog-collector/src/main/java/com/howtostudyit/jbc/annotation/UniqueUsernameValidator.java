package com.howtostudyit.jbc.annotation;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.howtostudyit.jbc.repository.UserRepository;

public class UniqueUsernameValidator implements
		ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		// Preventing NullPointerException (InitDbService: @PostConstruct)
		if (this.userRepository == null) {
			return true;
		}
		return userRepository.findByName(username) == null;
	}
}
