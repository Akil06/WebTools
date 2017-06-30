package com.ordermanage.pojo;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;




@Entity
@Table(name="Category")


public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="categoryID", unique=true, nullable=false)
    private long categoryId;
	
	
	@Column(name="description", unique=true, nullable=false)
	private String description;
	@ManyToMany
	@JoinTable(
			  name="category_product_table", 
			  joinColumns={@JoinColumn(name="categoryID", nullable=false, updatable=false)},
			  inverseJoinColumns={@JoinColumn(name="productID")}
			)
	private Set<Product> products;
	 public Category(String description) {
	        this.description = description;
	        products = new HashSet<Product>();
	    }

	    public Category() {
	    }

		public long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(long categoryId) {
			this.categoryId = categoryId;
		}

		public String getdescription() {
			return description;
		}

		public void setdescription(String description) {
			this.description = description;
		}

		public Set<Product> getProducts() {
			return products;
		}

		public void setProducts(Set<Product> products) {
			this.products = products;
		}

		@Override 
		public String toString(){
			return description;
		}
	
	
}
