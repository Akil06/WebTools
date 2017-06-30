package com.ordermanage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.ordermanage.dao.UserDAO;
import com.ordermanage.exception.UserException;
import com.ordermanage.pojo.User;
import com.ordermanage.validator.UserValidator;
import org.json.simple.JSONObject;;

@Controller
//@RequestMapping("/user/*")
public class UserController {
	// annotations go here

	@Autowired // if there is just one bean of this type, spring provides IoC
				// automatically
	UserDAO userDao;

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	protected String goToUserHome(HttpServletRequest request) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		User uS = (User) session.getAttribute("user");
		
		User userSupport = userDao.getSupport(uS.getUsername(), uS.getPassword());
		if ((userSupport == null)) {
			return "home";

		} else {
			return "supportHome";
		}

	}
	
	@RequestMapping(value = "/register/add", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.print("registerUser");

		return new ModelAndView("register", "user", new User());

	}
	

	@RequestMapping(value = "/register/login", method = RequestMethod.POST)
	protected String loginUser(HttpServletRequest request) throws Exception {



		try {
			// add
			HttpSession session = (HttpSession) request.getSession();
			System.out.println("loginUser");

			User u = userDao.get(request.getParameter("username"), request.getParameter("password"));

			if (u == null) {
				System.out.println("UserName/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return "error";
			}

			session.setAttribute("user", u);
			User userSupport = userDao.getSupport(request.getParameter("username"), request.getParameter("password"));
			if ((userSupport == null)) {
				session.setAttribute("userType", "customer");
				return "home";
				
			} else {
				session.setAttribute("userType", "support");
				return "supportHome";
				
			}
		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
		//	session.setAttribute("errorMessage", "error while login");
			return "error";
		}

	}

	

	@RequestMapping(value = "/register/add", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request, @ModelAttribute("user") User user,
			BindingResult result) throws Exception {

		// add
		validator.validate(user, result);

		if (result.hasErrors()) {
			return new ModelAndView("register", "user", user);
		}

		try {
			// add
			System.out.println("register New User");

			User u = userDao.register(user);

			request.getSession().setAttribute("user", u);

			return new ModelAndView("home", "user", u);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	protected void logoutUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("http://localhost:8080/orderManage/index.jsp");
		// return ("index");
	}
			
}
