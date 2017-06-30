package com.ordermanage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ordermanage.dao.OrderDAO;
import com.ordermanage.dao.UserDAO;
import com.ordermanage.dao.ProductDAO;
import com.ordermanage.dao.QuoteDAO;
import com.ordermanage.exception.OrderException;
import com.ordermanage.exception.ProductException;
import com.ordermanage.exception.QuoteException;
import com.ordermanage.exception.UserException;
import com.ordermanage.pojo.Category;
import com.ordermanage.pojo.Order;
import com.ordermanage.pojo.OrderQuantity;
import com.ordermanage.pojo.Product;
import com.ordermanage.pojo.Quote;
import com.ordermanage.pojo.User;

@Controller
//@RequestMapping("/order/*")
public class OrderController {

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("orderDao")
	OrderDAO orderDao;

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;

	@Autowired
	@Qualifier("quoteDao")
	QuoteDAO quoteDao;

	@RequestMapping(value = "/order/add", method = RequestMethod.GET)
	protected ModelAndView addOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.print("add Order");
		HttpSession session = request.getSession();
		// return new ModelAndView("productAdd", "product", product);
		try {
			Order order = new Order();

			String names[] = request.getParameterValues("checkedProduct");
			String quantity[] = request.getParameterValues("quantity");
			Set<Product> orderedProducts = new HashSet<Product>();
			if (names==null){
			// response.sendRedirect("productSearchResults.jsp");
				
				session.setAttribute("errormissing", "Select to add to Cart");
				String searchBy = (String) session.getAttribute("searchBy");
				 if (searchBy.equals("category")) {
			
					 List<Product> searchedProductsCat = (List<Product>) session.getAttribute("searchedProductsCat");
					 return new ModelAndView("productSearchResults", "searchedProductsCat", searchedProductsCat);
				 }
				 else {
					 List<Product> searchedProducts = (List<Product>) session.getAttribute("searchedProducts");
					 return new ModelAndView("productSearchResults", "searchedProducts", searchedProducts);
				 }
			}
			
			
			for (String productName : names) {
				Product p = productDao.get(productName);
				Quote quote = new Quote();
				quote.setOrder(order);
				quote.setProduct(p);
				quote.setQuantity(0);
				quote.setStatus("InCart");
				orderedProducts.add(p);
				// p.getQuote().add(quote);

				User user = (User) session.getAttribute("user");
				order.setPostedBy((int) user.getPersonID());
				order.setUser(user);

				User u = userDao.get(order.getPostedBy());
				order.getQuote().add(quote);
				session.setAttribute("products", orderedProducts);
				session.setAttribute("order", order);

				order = orderDao.create(order);
				quote = quoteDao.create(quote);
			}
			/*
			 * for(Product p: order.get){ p = productDao.get(p.getName());
			 * p.getOrders().add(order); productDao.update(p); }
			 */

			return new ModelAndView("orderAdd", "order", order);
		}

		catch (QuoteException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/order/cart", method = RequestMethod.GET)
	protected ModelAndView cartOrder(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		long userid = (Long) session.getAttribute("userid");
		System.out.println("userid = " + userid);

		// Set<Product> order edProducts=
		// (Set<Product>)session.getAttribute("products");
		List<Object> quotes = (List<Object>) orderDao.getOrdersinCart(userid);
		int count = quotes.size();
		session.setAttribute("size", count);
		System.out.println("size==" + count);
		List<OrderQuantity> oqListCart = new ArrayList<OrderQuantity>();

		Iterator itr = quotes.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			OrderQuantity oq = new OrderQuantity();
			oq.setOrderId((Long) obj[0]);
			oq.setProductId((Long) obj[1]);
			oq.setProductDescription((String) obj[3]);
			oq.setName((String) obj[2]);
			oq.setModelNo((String) obj[4]);
			oq.setQuantity((Integer) obj[5]);
			oq.setStatus((String) obj[6]);
			oq.setUsername((String) obj[7]);
			oq.setPersonID((Long)obj[8]);
			oq.setQuantityAvailable((Integer)obj[9]);
			oq.setAmountPayable((Integer)obj[10]);
		
			oqListCart.add(oq);
		}
		session.setAttribute("oqListCart", oqListCart);
		return new ModelAndView("orderCart", "oqListCart", oqListCart);
	}

	@RequestMapping(value = "/order/place", method = RequestMethod.GET)
	protected ModelAndView placeOrder(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String quantity[] = request.getParameterValues("quantity");
		Order order = (Order) session.getAttribute("order");
	List<OrderQuantity> oqListCart = (List<OrderQuantity>) session.getAttribute("oqListCart");
		try {
			int count = 0;
			int no = 0;
			for (OrderQuantity p : oqListCart) {

				quoteDao.updateQuantity(p.getOrderId(), p.getProductId(), Integer.parseInt(quantity[count]));
				count++;
				no++;
			}

			return new ModelAndView("orderCreated", "no", no);
		} catch (QuoteException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/order/view", method = RequestMethod.GET)
	protected ModelAndView viewOrder(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		long userid = (Long) session.getAttribute("userid");
		System.out.println("userid = " + userid);

		// Set<Product> order edProducts=
		// (Set<Product>)session.getAttribute("products");
		List<Object> quotes = (List<Object>) orderDao.getOrders(userid);
		int count = quotes.size();
		session.setAttribute("size", count);
		System.out.println("size==" + count);
		List<OrderQuantity> oqList = new ArrayList<OrderQuantity>();

		Iterator itr = quotes.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			OrderQuantity oq = new OrderQuantity();

			oq.setOrderId((Long) obj[0]);
			oq.setProductId((Long) obj[1]);
			oq.setProductDescription((String) obj[3]);
			oq.setName((String) obj[2]);
			oq.setModelNo((String) obj[4]);
			oq.setQuantity((Integer) obj[5]);
			oq.setStatus((String) obj[6]);
			oq.setUsername((String) obj[7]);
			oq.setPersonID((Long)obj[8]);
			oq.setQuantityAvailable((Integer)obj[9]);
			oq.setAmountPayable((Integer)obj[10]);
		
			oqList.add(oq);
		}

		return new ModelAndView("orderView", "oqList", oqList);

	}
	
		
	
	

	@RequestMapping(value = "/order/viewAll", method = RequestMethod.GET)
	protected ModelAndView viewAllOrder(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		long userid = (Long) session.getAttribute("userid");
		System.out.println("userid = " + userid);

		// Set<Product> order edProducts=
		// (Set<Product>)session.getAttribute("products");
		List<Object> quotes = (List<Object>) orderDao.getAllOrders();
		int count = quotes.size();
		session.setAttribute("size", count);
		System.out.println("size==" + count);
		List<OrderQuantity> oqList = new ArrayList<OrderQuantity>();

		Iterator itr = quotes.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			OrderQuantity oq = new OrderQuantity();
			oq.setOrderId((Long) obj[0]);
			oq.setProductId((Long) obj[1]);
			oq.setProductDescription((String) obj[3]);
			oq.setName((String) obj[2]);
			oq.setModelNo((String) obj[4]);
			oq.setQuantity((Integer) obj[5]);
			oq.setStatus((String) obj[6]);
			oq.setUsername((String) obj[7]);
			oq.setPersonID((Long)obj[8]);
			oq.setQuantityAvailable((Integer)obj[9]);
			oq.setAmountPayable((Integer)obj[10]);
			oqList.add(oq);
		}

		return new ModelAndView("orderView", "oqList", oqList);

	}
		
	@RequestMapping(value = "/order/updateStatus/{orderId}", method = RequestMethod.GET)
	public String updateOrderStatus(@PathVariable("orderId") String orderId, HttpServletRequest request)
			throws Exception {
		// String orderidSelected= request.getParameter("orderId");
		Long orderiduse = Long.parseLong(orderId);

		List<Object> quotes = (List<Object>) orderDao.getOrdersbyOrderId(orderiduse);
		List<OrderQuantity> updateOrderList = new ArrayList<OrderQuantity>();

		Iterator itr = quotes.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			OrderQuantity oq = new OrderQuantity();
			oq.setOrderId((Long) obj[0]);
			oq.setProductId((Long) obj[1]);
			oq.setProductDescription((String) obj[2]);
			oq.setName((String) obj[3]);
			oq.setModelNo((String) obj[4]);
			oq.setQuantity((Integer) obj[5]);
			oq.setStatus((String) obj[6]);
			oq.setUsername((String) obj[7]);
			oq.setPersonID((Long)obj[8]);
			System.out.println("oq.setPersonID"+oq.getPersonID());	
			updateOrderList.add(oq);
		}
		
		HttpSession sessionOrd = request.getSession();
		sessionOrd.setAttribute("updateOrderList", updateOrderList);
		return ("orderUpdateStatus");

	}
	@RequestMapping(value="/user/info",method=RequestMethod.GET)
	public void userInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		// JSONObject obj = new JSONObject();
		try {
			
			String useridReq = request.getParameter("userid");
			System.out.println("in ajax"+useridReq);
			 User user = userDao.get(Long.parseLong(useridReq));
			 String ret = "First Name :"+user.getFirstName() + "\n"+ "Last Name :"+user.getLastName() + "" + "Email :"+user.getEmail();
			 response.getWriter().print(ret);
			//obj.put("First Name ", user.getFirstName());
		//	obj.put("Last Name ", user.getLastName());
			//obj.put("Email ", user.getEmail());
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	
	
	@RequestMapping(value = "/order/updateOrder", method = RequestMethod.POST)
	public String updateOrder(
			HttpServletRequest request) throws Exception {
		// String orderidSelected= request.getParameter("orderId");
		Long orderiduse = Long.parseLong(request.getParameter("orderid"));
		Long prouctiduse = Long.parseLong(request.getParameter("productid"));
		HttpSession session = request.getSession();
		String quantity =(String) request.getParameter("quantityAvailable");
		String amount = (String) request.getParameter("amountPayable");

		quoteDao.updateQuoteAmount(orderiduse, prouctiduse, Integer.parseInt(quantity), Integer.parseInt(amount));
		return ("orderUpdated");
	}

}
