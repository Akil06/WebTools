package com.ordermanage.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.ordermanage.exception.OrderException;
import com.ordermanage.exception.QuoteException;
import com.ordermanage.pojo.Order;
import com.ordermanage.pojo.Quote;

public class QuoteDAO extends DAO{

	public Quote create(Quote quote)
            throws QuoteException {
        try {
            begin();            
            getSession().save(quote);     
            commit();
            return quote;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create product", e);
            throw new QuoteException("Exception while creating quote: " + e.getMessage());
        }
    }
	
	
	
	
public void updateQuantity(long orderId,long productId,int quantity )

	 throws QuoteException {
	        try {
	            begin();            
	        Query q= getSession().createQuery("from Quote where orderID=:orderid and productID=:productid ");
	        q.setLong("orderid", orderId);
	        q.setLong("productid", productId);
	        
	        List<Quote> quote = q.list();
	        
	        for(Quote qt: quote) {
	        	qt.setQuantity(quantity);
	        	qt.setStatus("Submitted");
	            getSession().update(qt);
	        	commit();
	        }
	        
	        } catch (HibernateException e) {
	            rollback();
	            //throw new AdException("Could not create product", e);
	            throw new QuoteException("Exception while creating quote: " + e.getMessage());
	        }
}
	public void updateQuoteAmount(long orderId,long productId,int quantity,int amount) throws QuoteException {
        try {
            begin();            
        Query q= getSession().createQuery("from Quote where orderID=:orderid and productID=:productid ");
        q.setLong("orderid", orderId);
        q.setLong("productid", productId);
        
        
        List<Quote> quote = q.list();
        
        for(Quote qt: quote) {
        	
        	qt.setStatus("Reviewed");
        	qt.setAmountPayable(amount);
        	qt.setQuantityAvailable(quantity);
            getSession().update(qt);
        	commit();
        }
        
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create product", e);
            throw new QuoteException("Exception while creating quote: " + e.getMessage());
        }
		
	}
}
