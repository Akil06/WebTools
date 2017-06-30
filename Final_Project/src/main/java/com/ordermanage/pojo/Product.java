package com.ordermanage.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;




@Entity
@Table(name="Product")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="productID", unique=true, nullable=false)
    private long productId;	
	
	@Column(name="name")
    private String name;
	
	@Column(name="modelno")
    private String modelNo;
	
	@Column(name="productDescription")
	private String productDescription;
	
	@Column(name="status")
	private String status;
	
	@Column(name="inStock")
	private int inStock;
	
	@Column(name="filename")
	private String filename;
	@Transient
	private CommonsMultipartFile photo;  
	@ManyToOne
	private User user;
	
	@ManyToMany(mappedBy="products")
	private Set<Category> categories;
	
	/*@ManyToMany(mappedBy="products")
	private Set<Order> orders;*/
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="po.product")
	private Set<Quote> quote;
	
	@Transient
	int postedBy;
	
	public Product(String name, String modelNo,String productDescription, User user, Category catergory) {
        this.name = name;
        this.modelNo = modelNo;
        this.productDescription=productDescription;
        this.user = user;      
        //this.categories.add(catergory);
        categories = new HashSet<Category>();
    }
	
	public Product() {
    }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	
	public CommonsMultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(CommonsMultipartFile photo) {
		this.photo = photo;
	}

	public void setProductDescription(String productDescription){
		this.productDescription=productDescription;
	}
	public String getProductDescription(){
		return productDescription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(int postedBy) {
		this.postedBy = postedBy;
	}
	
	
	
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	/*
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	} */
	
	
	
	@Override 
	public String toString(){
		return name;
	}

	public Set<Quote> getQuote() {
		return quote;
	}

	public void setQuote(Set<Quote> quote) {
		this.quote = quote;
	}
	
}
