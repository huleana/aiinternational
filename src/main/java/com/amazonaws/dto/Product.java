package com.amazonaws.dto;

import com.amazonaws.services.comprehend.model.BatchDetectSentimentResult;
import com.amazonaws.services.comprehend.model.SentimentScore;

public class Product {
	private String id;
	private String name;
	private BatchDetectSentimentResult reviewsList;
	private SentimentScore aveSentimentScore;
	
	public Product(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BatchDetectSentimentResult getReviewsList() {
		return reviewsList;
	}
	public void setReviewsList(BatchDetectSentimentResult reviewsList) {
		this.reviewsList = reviewsList;
	}
	public SentimentScore getAveSentimentScore() {
		return aveSentimentScore;
	}

	public void setAveSentimentScore(SentimentScore aveSentimentScore) {
		this.aveSentimentScore = aveSentimentScore;
	}
}
