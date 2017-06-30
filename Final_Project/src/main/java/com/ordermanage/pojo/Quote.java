package com.ordermanage.pojo;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="Quote")
@AssociationOverrides({
	@AssociationOverride(name = "po.order",
		joinColumns = @JoinColumn(name = "orderID")),
	@AssociationOverride(name = "po.product",
		joinColumns = @JoinColumn(name = "productID")) })
public class Quote {
	
	@EmbeddedId	
	private ProductOrder po;
	
	public Quote() {
		 po = new ProductOrder();
		
	}
	@Transient
	private Order order;
	@Transient
	private Product product;
	
	@Column(name="status")
	private String status;
	
	@Column(name="quantityAvailable")
	private int quantityAvailable; 
	
	@Column(name="amountPayable")
	private int amountPayable;
	
	public ProductOrder getPo() {
		return po;
	}

	public void setPo(ProductOrder po) {
		this.po = po;
	}
	
	public Order getOrder() {
		return getPo().getOrder();
	}

	public void setOrder(Order order) {
		getPo().setOrder(order);
	}

	
	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	
	public int getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(int amountPayable) {
		this.amountPayable = amountPayable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product getProduct() {
		return getPo().getProduct();
	}
	
	public void setProduct(Product product) {
		getPo().setProduct(product);
	}
	
	//private ProductOrder productOrder;
	
	@Column(name="Quantity")
	private int quantity;

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
