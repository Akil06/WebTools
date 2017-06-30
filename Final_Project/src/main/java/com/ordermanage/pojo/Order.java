package com.ordermanage.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="order_table")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="OrderId", unique=true, nullable=false)
    private long orderId;	
	
	
	@ManyToOne
	@JoinColumn(name="`Id`")
	private User user;
	/*
	@Autowired
	@ManyToOne(fetch = FetchType.EAGER, mappedBy = "user")*/
	// @ManyToOne(targetEntity=User.class)
	// private long Id;
	
	/*
	@ManyToMany
	@JoinTable(
			  name="Quote", 
			  joinColumns={@JoinColumn(name="orderID", nullable=false, updatable=false)},
			  inverseJoinColumns={@JoinColumn(name="productID")}
			) */
	// private Set<Product> products;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="po.order", cascade=CascadeType.ALL)
	private Set <Quote> quote = new HashSet<Quote>();
	
	@Transient
	int postedBy;
	
	public Order(){
	
		//products=new HashSet<Product>();
	}
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	/*public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}*/
	
	public int getPostedBy() {
		return postedBy;
	}

	
	public Set<Quote> getQuote() {
		return quote;
	}

	public void setQuote(Set<Quote> quote) {
		this.quote = quote;
	}

	public void setPostedBy(int postedBy) {
		this.postedBy = postedBy;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
