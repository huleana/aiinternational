package com.amazonaws.dto;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.comprehend.model.DetectSentimentResult;

public class ProductReview {
	private String id;
	private String customerReview;
	private DetectSentimentResult sentimentResult;
	private String sentiment;
	private int sentimentScore;
	private int score;
	private Map<String, Integer> keyPhrases;
	private Map<String, String> entityMap;
	
	public ProductReview(String customerReview) {
		this.customerReview = customerReview;
		this.keyPhrases = new HashMap<String, Integer>();
		this.entityMap = new HashMap<String, String>();
	}
	
	public ProductReview() {
		this.keyPhrases = new HashMap<String, Integer>();
		this.entityMap = new HashMap<String, String>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerReview() {
		return customerReview;
	}
	public void setCustomerReview(String customerReview) {
		this.customerReview = customerReview;
	}
	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Map<String, String> getEntityMap() {
		return entityMap;
	}
	public void setEntityMap(Map<String, String> entityMap) {
		this.entityMap = entityMap;
	}
	public int getSentimentScore() {
		return sentimentScore;
	}
	public void setSentimentScore(int sentimentScore) {
		this.sentimentScore = sentimentScore;
	}
	public void setKeyPhrases(Map<String, Integer> keyPhrases) {
		this.keyPhrases = keyPhrases;
	}
	public Map<String, Integer> getKeyPhrases() {
		return this.keyPhrases;
	}
	public DetectSentimentResult getSentimentResult() {
		return sentimentResult;
	}
	public void setSentimentResult(DetectSentimentResult sentimentResult) {
		this.sentimentResult = sentimentResult;
	}
}
