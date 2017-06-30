package com.ordermanage.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ordermanage.pojo.Category;
import com.ordermanage.exception.CategoryException;
import com.ordermanage.dao.CategoryDAO;

import com.ordermanage.validator.CategoryValidator;

@Controller
@RequestMapping("/category/*")
public class CategoryController {
	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDao;

	@Autowired
	@Qualifier("categoryValidator")
	CategoryValidator categoryValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(categoryValidator);
	}


	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	protected ModelAndView addCategory (@ModelAttribute("category") Category category, BindingResult result,HttpServletRequest request) throws Exception {
		System.out.print("add Category");
		HttpSession session = request.getSession();
		String userType= (String)session.getAttribute("userType");
		
		if (userType=="customer"){
			//System.out.println("customer-1");
			return new ModelAndView ("error");
		}
		
		categoryValidator.validate(category, result);
		
		if (result.hasErrors()) {
		
		return new ModelAndView("categoryAdd", "category", category);
		}
		try {				
			category = categoryDao.create(category.getdescription());
		} catch (CategoryException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
		return new ModelAndView("categoryCreated", "category", category);
	}
	
	@RequestMapping(value="/category/add", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request) throws Exception {		
		HttpSession session = request.getSession();
		String userType= (String)session.getAttribute("userType");
		
		if (userType=="customer"){
			//System.out.println("customer-1");
			return new ModelAndView ("error");
		}
		return new ModelAndView("categoryAdd", "category", new Category());
	}
	
	
}
