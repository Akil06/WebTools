package com.ordermanage.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ordermanage.pojo.User;

public class UserValidator implements Validator {

	public boolean supports(Class aClass) {
		return aClass.equals(User.class);
	}

	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.user", "First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.user", "Last Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.invalid.user", "User Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.email","Email Required");
		
		// check if user exists
	
		Pattern p = Pattern.compile("[^\\w\\s]");
		Matcher matchFirst = p.matcher(user.getFirstName());
        if(matchFirst.find()){
            errors.rejectValue("firstName", "error.invalid.name", "Enter alphanumeric characters only");
        }
        Matcher matchLast = p.matcher(user.getLastName());
        if(matchLast.find()){
            errors.rejectValue("lastName", "error.invalid.name", "Enter alphanumeric characters only");
        }
        Matcher matchusername = p.matcher(user.getUsername());
        if(matchusername.find()){
            errors.rejectValue("username", "error.invalid.name", "Enter alphanumeric characters only");
        }
        Matcher matchpassword = p.matcher(user.getPassword());
        if(matchpassword.find()){
            errors.rejectValue("password", "error.invalid.name", "Enter alphanumeric characters only");
        }
        Matcher matcheemail = p.matcher(user.getEmail());
        if(matcheemail.find()){
            errors.rejectValue("email", "error.invalid.name", "Enter alphanumeric characters only");
        }
	}
}