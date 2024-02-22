package com.lcwd.electronic.store.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {
	
	@Id
	private String productId;
	
	private String title;
	
	@Column(length = 10000)
	private String discription;
	
	private int price;
	private int discountedPrice;
	private int quantity;
	private Date addedDate;
	private boolean live;
	private boolean stock;
	
	public Product() {
		super();
	}
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", title=" + title + ", discription=" + discription + ", price="
				+ price + ", discountedPrice=" + discountedPrice + ", quantity=" + quantity + ", addedDate=" + addedDate
				+ ", live=" + live + ", stock=" + stock + "]";
	}

	public Product(String productId, String title, String discription, int price, int discountedPrice, int quantity,
			Date addedDate, boolean live, boolean stock) {
		super();
		this.productId = productId;
		this.title = title;
		this.discription = discription;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.quantity = quantity;
		this.addedDate = addedDate;
		this.live = live;
		this.stock = stock;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date date) {
		this.addedDate = date;
	}
	public boolean isLive() {
		return live; 
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isStock() {
		return stock;
	}
	public void setStock(boolean stock) {
		this.stock = stock;
	}	
}





