package com.ordermanage.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ordermanage.pojo.Category;
import com.ordermanage.pojo.Order;
import com.ordermanage.pojo.OrderQuantity;
import com.ordermanage.pojo.User;
import com.ordermanage.dao.UserDAO;

import com.ordermanage.dao.CategoryDAO;
import com.ordermanage.dao.OrderDAO;
import com.ordermanage.dao.ProductDAO;
import com.ordermanage.dao.QuoteDAO;
import com.ordermanage.exception.ProductException;
import com.ordermanage.pojo.Product;
import com.ordermanage.validator.ProductValidator;

@Controller
// @RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;
	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	@Autowired
	@Qualifier("orderDao")
	OrderDAO orderDao;
	@Autowired
	@Qualifier("quoteDao")
	QuoteDAO quoteDao;
	@Autowired
	@Qualifier("productValidator")
	ProductValidator productValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
	}
	@Autowired
	ServletContext servletContext;
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	protected ModelAndView addProduct(@ModelAttribute("product") Product product, BindingResult result,HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String userType= (String)session.getAttribute("userType");
		
		if (userType=="customer"){
			//System.out.println("customer-1");
			return new ModelAndView ("error");
		}
		System.out.print("add Product");

		productValidator.validate(product, result);

		if (result.hasErrors()) {

			return new ModelAndView("productAdd", "product", product);
		}
		try {
			User u = userDao.get(product.getPostedBy());
			product.setUser(u);
			String filename=product.getName()+product.getProductId();
			product.setFilename(filename);
				if (product.getFilename().trim() != "" || product.getFilename() != null) {
					File directory;
					String check = File.separator; // Checking if system is linux
													// based or windows based by
													// checking seprator used.
					String path = null;
					if (check.equalsIgnoreCase("\\")) {
						path = servletContext.getRealPath("").replace("build\\", ""); // gives real path as Lab9/build/web/
																					  // so we need to replace build in the path
																							}

					if (check.equalsIgnoreCase("/")) {
						path = servletContext.getRealPath("").replace("build/", "");
						path += "/"; // Adding trailing slash for Mac systems.
					}
					directory = new File(path + "\\" + product.getFilename());
					boolean temp = directory.exists();
					if (!temp) {
						temp = directory.mkdir();
					}
					if (temp) {
						// We need to transfer to a file
						CommonsMultipartFile photoInMemory = product.getPhoto();

						String fileName = photoInMemory.getOriginalFilename();
						// could generate file names as well

						File localFile = new File(directory.getPath(), fileName);

						// move the file from memory to the file

						photoInMemory.transferTo(localFile);
						product.setFilename(localFile.getPath());
						System.out.println("File is stored at" + localFile.getPath());
						System.out.print("registerNewUser");
						product = productDao.create(product);
			for (Category c : product.getCategories()) {
				c = categoryDao.get(c.getdescription());
				c.getProducts().add(product);
				categoryDao.update(c);
			}
			} else {
				System.out.println("Failed to create directory!");
			}
			
			}
			return new ModelAndView("productCreated", "product", product);
		}

		catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public ModelAndView addProduct(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String userType= (String)session.getAttribute("userType");
		
		if (userType=="customer"){
			//System.out.println("customer-1");
			return new ModelAndView ("error");
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("categories", categoryDao.list());
		mv.addObject("product", new Product());
		mv.setViewName("productAdd");
		return mv;

	}

	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	public ModelAndView viewProduct(HttpServletRequest request) throws Exception {

		try {
			List<Product> products = productDao.list();

			return new ModelAndView("productList", "products", products);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while showing product list");
		}
	}

	@RequestMapping(value = "/product/info/{productId}", method = RequestMethod.GET)
	public ModelAndView viewProductInfo(@PathVariable("productId")String productId,HttpServletRequest request) throws Exception {

		try {
			List<Product> products = productDao.getProductInfo(Long.parseLong(productId));
			Product inf = new Product();
			for (Product p: products){
				
				inf.setName(p.getName());
				inf.setProductId(p.getProductId());
				inf.setProductDescription(p.getProductDescription());
				inf.setModelNo(p.getModelNo());
				inf.setFilename(p.getFilename());
				inf.setStatus(p.getStatus());
				inf.setInStock(p.getInStock());
				
			}
			
			return new ModelAndView("productInfo", "products", inf);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while showing product list");
		}
	}
	
	@RequestMapping(value = "/product/update/{productId}", method = RequestMethod.GET)
	public ModelAndView updateProductInfo(@PathVariable("productId")String productId,HttpServletRequest request) throws Exception {

		try {
			Product product = productDao.get(Long.parseLong(productId));
			//Product inf = new Product();
			String inStock = request.getParameter("inStock");
			String availability= request.getParameter("status");
			product.setInStock(Integer.parseInt(inStock));
			product.setStatus(availability);
			productDao.update(product);
			return new ModelAndView("productCreated","product",product);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while showing product list");
		}
	}
	
	
	@RequestMapping(value = "/product/search", method = RequestMethod.GET)
	public ModelAndView searchProductPage(HttpServletRequest request) throws Exception {

		return new ModelAndView("searchProduct");

	}

	@RequestMapping(value = "/product/search", method = RequestMethod.POST)
	public ModelAndView searchProduct(HttpServletRequest request) throws Exception {
		String searchBy = request.getParameter("searchby");
		String keyword = request.getParameter("keyword");
		HttpSession session = request.getSession();
		session.setAttribute("searchBy", searchBy);
		try {
			if (searchBy.equals("category")) {
				
				List<Object> pcList = (List<Object>) productDao.searchByCategory(searchBy, keyword);
				int count = pcList.size();
				// session.setAttribute("size", count);
				System.out.println("size search Product==" + count);
				List<Product> searchedProductsCat = new ArrayList<Product>();

				Iterator itr = pcList.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					Product oq = new Product();

					oq.setName(((String) obj[0]));
					oq.setProductDescription((String) obj[1]);

					oq.setModelNo((String) obj[2]);
					// oq.setCategories((Integer)obj[4]);
					searchedProductsCat.add(oq);
					session.setAttribute("searchedProductsCat", searchedProductsCat);
				}
				
				return new ModelAndView("productSearchResults", "searchedProductsCat", searchedProductsCat);
			} else {
				List<Product> searchedProducts = (List<Product>) productDao.search(searchBy, keyword);
				
				session.setAttribute("searchedProducts", searchedProducts);
				return new ModelAndView("productSearchResults", "searchedProducts", searchedProducts);
			}

		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	
}
