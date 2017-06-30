package com.ordermanage.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.ordermanage.exception.OrderException;
import com.ordermanage.pojo.Order;
import com.ordermanage.pojo.Quote;

public class OrderDAO extends DAO{
	public Order create(Order order)
            throws OrderException {
        try {
            begin();            
            getSession().save(order);     
            commit();
            return order;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create product", e);
            throw new OrderException("Exception while creating order: " + e.getMessage());
        }
    }
	
	public List<Quote> getQuote(long orderid)  throws OrderException {
        try {
            begin();            
        Query q=    getSession().createQuery("from quote where orderID=:orderid");
        q.setLong("orderid", orderid);
        List<Quote> quote = q.list();
        return quote;
        }
	  
	catch (HibernateException e) {
         rollback();
         //throw new AdException("Could not create product", e);
         throw new OrderException("Exception while creating order: " + e.getMessage());
     }
}
	
	public List<Object> getOrders(long personID)  throws OrderException {
        try {
            begin();            
            String hql="select o.orderId,q.po.product.productId,q.po.product.name,q.po.product.productDescription,q.po.product.modelNo,q.quantity,q.status,o.user.username,o.user.personID,q.quantityAvailable,q.amountPayable from Order o inner join o.quote q where q.status<>:status and o.user.personID=:userid";
        Query q=  getSession().createQuery(hql);
        
        q.setLong("userid", personID);
        q.setString("status", "InCart");
        
        q.setComment("hql=="+hql);
        System.out.println("userid = "+hql);
        List<Object> quote = (List<Object>) q.list();
        commit();
        return quote;
        }
	  
	catch (HibernateException e) {
         rollback();
         //throw new AdException("Could not create product", e);
         throw new OrderException("Exception while creating order: " + e.getMessage());
     }
}
	
	public List<Object> getAllOrders()  throws OrderException {
        try {
            begin();            
            String hql="select o.orderId,q.po.product.productId,q.po.product.name,q.po.product.productDescription,q.po.product.modelNo,q.quantity,q.status,o.user.username,o.user.personID,q.quantityAvailable,q.amountPayable from Order o inner join o.quote q where q.status=:status";
        Query q=  getSession().createQuery(hql);
        
        // q.setLong("userid", personID);
        q.setString("status", "Submitted");
        q.setComment("hql=="+hql);
        System.out.println("userid = "+hql);
        List<Object> quote = (List<Object>) q.list();
        commit();
        return quote;
        }
	  
	catch (HibernateException e) {
         rollback();
         //throw new AdException("Could not create product", e);
         throw new OrderException("Exception while creating order: " + e.getMessage());
     }
}
	public List<Object> getOrdersbyOrderId(long orderId)  throws OrderException {
        try {
            begin();            
            String hql="select o.orderId,q.po.product.productId,q.po.product.name,q.po.product.productDescription,q.po.product.modelNo,q.quantity,q.status,o.user.username,o.user.personID,q.quantityAvailable,q.amountPayable from Order o inner join o.quote q where o.orderId=:orderId";
        Query q=  getSession().createQuery(hql);
        
        // q.setLong("userid", personID);
        q.setLong("orderId", orderId);
        q.setComment("hql=="+hql);
        System.out.println("userid = "+hql);
        List<Object> orderList = (List<Object>) q.list();
        commit();
        return orderList;
        }
	  
	catch (HibernateException e) {
         rollback();
         //throw new AdException("Could not create product", e);
         throw new OrderException("Exception while creating order: " + e.getMessage());
     }
}

	public List<Object> getOrdersinCart(long personID)  throws OrderException {
        try {
            begin();            
            String hql="select o.orderId,q.po.product.productId,q.po.product.name,q.po.product.productDescription,q.po.product.modelNo,q.quantity,q.status,o.user.username,o.user.personID,q.quantityAvailable,q.amountPayable from Order o inner join o.quote q where q.status=:status and o.user.personID=:userid";
        Query q=  getSession().createQuery(hql);
        
        q.setLong("userid", personID);
        q.setString("status", "InCart");
        q.setComment("hql=="+hql);
        System.out.println("userid = "+hql);
        List<Object> quote = (List<Object>) q.list();
        commit();
        return quote;
        }
	  
	catch (HibernateException e) {
         rollback();
         //throw new AdException("Could not create product", e);
         throw new OrderException("Exception while creating order: " + e.getMessage());
     }
}
	
	
}
