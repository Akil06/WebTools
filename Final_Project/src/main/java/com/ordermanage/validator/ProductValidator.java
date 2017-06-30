package com.ordermanage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ordermanage.dao.ProductDAO;

import com.ordermanage.pojo.Product;


@Component
public class ProductValidator implements Validator {
	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;
	public boolean supports(Class aClass) {
		return aClass.equals(Product.class);
	}

	public void validate(Object obj, Errors errors) {
		Product newProduct = (Product) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productDescription", "error.invalid.product", "Product Description Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.invalid.product", "Product name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "modelNo", "error.invalid.product", "Product modelno Required");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.invalid.category", "Product Description Required");

	
		
		if (errors.hasErrors()) {
            return;//Skip the rest of the validation rules
        }
		;
		if(newProduct.getCategories().size()==0){
			errors.rejectValue("categories", "required.Category");
		   }
	}
}
