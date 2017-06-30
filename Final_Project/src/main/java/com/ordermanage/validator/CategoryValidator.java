package com.ordermanage.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ordermanage.dao.CategoryDAO;
import com.ordermanage.exception.CategoryException;
import com.ordermanage.pojo.Category;
import com.ordermanage.pojo.User;

@Component
public class CategoryValidator implements Validator {

	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDao;
	
	public boolean supports(Class aClass) {
		return Category.class.equals(aClass);
	}

	public void validate(Object obj, Errors errors) {
		Category newCategory = (Category) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.invalid.category", "Category Description Required");
		

		if (errors.hasErrors()) {
            return;//Skip the rest of the validation rules
        }
		Pattern p = Pattern.compile("[^\\w\\s]");
		Matcher matchFirst = p.matcher(newCategory.getdescription());
        if(matchFirst.find()){
            errors.rejectValue("description", "error.invalid.description", "Enter alphanumeric characters only");
        } 
	
	}
}
