package com.ordermanage.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.ordermanage.exception.CategoryException;
import com.ordermanage.exception.ProductException;
import com.ordermanage.pojo.Category;
import com.ordermanage.pojo.Product;;

public class ProductDAO extends DAO{
	public Product create(Product product)
            throws ProductException {
        try {
            begin();            
            getSession().save(product);     
            commit();
            return product;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create product", e);
            throw new ProductException("Exception while creating advert: " + e.getMessage());
        }
    }

    public Product get(String name) throws ProductException {
        try {
            begin();
            Query q=getSession().createQuery("from Product where name= :name");
            q.setString("name",name);
            Product product=(Product)q.uniqueResult();
            commit();
            return product;
        } catch (HibernateException e) {
            rollback();
            throw new ProductException("Could not obtain the named category " + name + " " + e.getMessage());
        }
    }

    public Product get(Long productId) throws ProductException {
        try {
            begin();
            Query q=getSession().createQuery("from Product where productId= :productId");
            q.setLong("productId",productId);
            Product product=(Product)q.uniqueResult();
            commit();
            return product;
        } catch (HibernateException e) {
            rollback();
            throw new ProductException("Could not obtain the named category " + productId + " " + e.getMessage());
        }
    }
    
	
    public void delete(Product product)
            throws ProductException {
        try {
            begin();
            getSession().delete(product);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new ProductException("Could not delete advert", e);
        }
    }
    public void update(Product product) throws CategoryException {
        try {
            begin();
            getSession().update(product);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new CategoryException("Could not save the category", e);
        }
    }
    public List<Product> list() throws ProductException{
    	
    	try {
            begin();
            Query q = getSession().createQuery("from Product");
            List<Product> products = q.list();
            commit();
            return products;
        } catch (HibernateException e) {
            rollback();
            throw new ProductException("Could not delete product", e);
        }
    	
    }
    
public List<Product> getProductInfo(long productId) throws ProductException{
    	
    	try {
            begin();
//            Query q = getSession().createQuery("from Product where productId=:productId");
            Criteria ct= getSession().createCriteria(Product.class);
            ct.add(Restrictions.eq("productId",productId));
            //q.setLong("productId", productId);
            List<Product> products = ct.list();
            commit();
            return products;
        } catch (HibernateException e) {
            rollback();
            throw new ProductException("Could not delete product", e);
        }
    	
    }
    




    
    
    public List<Product> search(String searchBy,String keyword) throws ProductException {
    	try {
            begin();
            
            String query_main = "from Product ";
            String query_Cat="select p.name,p.productDescription,p.modelNo,c.description from Product p";
            String query_append = "";
            if (searchBy .equals("name")) {
                query_append = "where name= :keyword and status=:status";
            }
            if (searchBy .equals("productDescription")) {
                query_append = "where productDescription= :keyword and status=:status";
            }
            if (searchBy .equals("modelNo")) {
                query_append = "where modelNo= :keyword";
            }
            
            if (searchBy .equals("category")) {
                query_append = "inner join p.categories c where c.description = :keyword and status=:status";
            }
            String query;
            if (searchBy .equals("category")) {
            	query = query_main + query_Cat;
            }
            
            else {
            	query = query_main + query_append;
            }
            
            Query q = getSession().createQuery(query);
            q.setString("keyword",keyword);
            q.setString("status", "Available");
            if (searchBy .equals("category")) {
            	List<Product> products = q.list();
                commit();
                return products;
            }
            else {
            	List<Product> products = q.list();
                commit();
                return products;
            }

        } catch (HibernateException e) {
            rollback();
            throw new ProductException("Could not search product", e);
        }
    }
    public List<Object> searchByCategory(String searchBy,String keyword) throws ProductException {
    	 String query_Cat="select p.name, p.productDescription, p.modelNo from Category c";
    	 String query_append = " inner join c.products p where c.description = :keyword and p.status=:status";
    	String query = query_Cat+query_append;
    	 Query q = getSession().createQuery(query);
    	 q.setString("keyword", keyword);
    	 q.setString("status", "Available");
    	 try {
    		 begin();
    	 List<Object> products = q.list();
    	 
         commit();
         return products;
    	 }
    	 catch (HibernateException e) {
             rollback();
             throw new ProductException("Could not search product", e);
         }
    }
    
}
