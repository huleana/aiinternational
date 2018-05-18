package com.amazonaws.logic;

import java.text.DecimalFormat;
import java.util.List;

import com.amazonaws.dto.Product;
import com.amazonaws.dto.ProductReview;
import com.amazonaws.util.ComprehendUtil;
import com.amazonaws.util.FileLogic;

public class ProductReviewsAnalysisLogic {
	
	//TODO Remove this class for Tests.
	public static void analyzeReviews() {
		final String filename = "How to Win Friends & Influence People";
		List<String> reviews = FileLogic.readFromFile(filename);
		Product product = new Product(filename);
		
	    for(int i=0; i<reviews.size(); i++) {
	    	ProductReview productReview = new ProductReview(reviews.get(i));
	    	ComprehendUtil.detectDominantSentiment(productReview, reviews.get(i));
			ComprehendUtil.detectKeyPhrase(productReview, reviews.get(i));
			ComprehendUtil.detectEntity(productReview, reviews.get(i));
		    SentimentLogic.analyzeSentimentResult(productReview);
		    product.getReviews().add(productReview);
	    }
	    
	    // Set the overall rating of the product based on each review's score
	    List<ProductReview> productReviews = product.getReviews();
	    int totalScore = 0;
	    for(int i=0; i<productReviews.size(); i++) {
	    	totalScore += productReviews.get(i).getScore();
	    }
	    DecimalFormat df = new DecimalFormat("0.00");
	    product.setAverageScore(df.format((double)totalScore/productReviews.size()));
	    
	    System.out.println("------- PRODUCT -------");
	    System.out.println("Product Name: " + product.getProductName());
	    System.out.println("Product Rating: " + product.getAverageScore());
	    for(int i =0; i < productReviews.size(); i++) {
	    	System.out.println("---------------------------");
	    	System.out.println("Review: " + productReviews.get(i).getCustomerReview());
	        System.out.println("Rating: " + productReviews.get(i).getScore());
	        System.out.println("Sentiment: " + productReviews.get(i).getSentiment());
	        System.out.println("Sentiment Score: " + productReviews.get(i).getSentimentScore());
	        System.out.println("Key Phrases: " + productReviews.get(i).getKeyPhrases().toString());
	        System.out.println("Entities: " + productReviews.get(i).getEntityMap().toString());
	    	System.out.println("---------------------------");
	    }
	    
	}
}
