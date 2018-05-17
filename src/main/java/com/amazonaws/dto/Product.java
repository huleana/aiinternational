package com.amazonaws.dto;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private String productName;
	private String price;
	private String description;
	private String averageScore;
	private List<ProductReview> reviews;
	
	public Product() {
		this.reviews = new ArrayList<ProductReview>();
	}
	
	public Product(String productName) {
		this.productName = productName;
		this.reviews = new ArrayList<ProductReview>();
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ProductReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<ProductReview> reviews) {
		this.reviews = reviews;
	}
	public String getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
	}
	
}
