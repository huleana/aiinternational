package com.acn.ai.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.acn.ai.service.dto.ProductReview;
import com.acn.ai.utils.ComprehendUtil;

public class APIProductReviewsAnalysisLogic {
	
	static Connection conn = null;
	static PreparedStatement prepStatement = null;
	
	public static void analyzeReviews(
			String productName, int price, String description, String review) {		
    	
		connectDB();
		
		ProductReview productReview = new ProductReview(review);
    	ComprehendUtil.detectDominantSentiment(productReview, review);
		ComprehendUtil.detectKeyPhrase(productReview, review);
		ComprehendUtil.detectEntity(productReview, review);
	    SentimentLogic.analyzeSentimentResult(productReview);
//	    System.out.println("------- PRODUCT -------");
//	    System.out.println("Product Name: " + productName);
//	    System.out.println("Product Description: " + description);
//	    System.out.println("Product Price: " + price);
//	    System.out.println("---------------------------");
//    	System.out.println("Review: " + productReview.getCustomerReview());
//        System.out.println("Rating: " + productReview.getScore());
//        System.out.println("Sentiment: " + productReview.getSentiment());
        

	    for(Map.Entry<String, Integer> entry: productReview.getKeyPhrases().entrySet()) {
    		addDataToDB(
    				productName, price, description, 0,  
    				productReview.getSentiment(), productReview.getSentimentScore(),  
    				entry.getKey(),
    				entry.getValue(),
    				productReview.getEntityMap().toString(), review);		
    	}
//    	for(int i=0; i < productReview.getEntityMap().size(); i++) {
//    		addDataToDB(
//    				productName, price, description, 0,  
//    				productReview.getSentiment(), productReview.getSentimentScore(),  
//    				"",
//    				0,
//    				productReview.getEntityMap().get(i).toString(), review);		
//    	}
    	
    }
	
	public static void addDataToDB(
			String productName,
			int price,
			String description,
			int rating,
			String sentiment,
			double sentimentScore,
			String keyPhrase,
			double keyPhraseScore,
			String entity,
			String review) {
		 
		try {
			String insertQueryStatement = "INSERT  INTO  "
					+ "reviews  "
					+ "(product_name, price, description, rating, "
					+ "sentiment,sentiment_score, key_phrase, key_phrase_score, entity, review) "
					+ "VALUES"
					+ " (?,?,?,?,?,?,?,?,?,?)";
 
			prepStatement = (PreparedStatement) conn.prepareStatement(insertQueryStatement);
			prepStatement.setString(1, productName);
			prepStatement.setInt(2, price);
			prepStatement.setString(3, description);
			prepStatement.setInt(4, 1);
			prepStatement.setString(5, sentiment);
			prepStatement.setDouble(6, sentimentScore);
			prepStatement.setString(7, keyPhrase);
			prepStatement.setDouble(8, keyPhraseScore);
			prepStatement.setString(9, entity);
			prepStatement.setString(10, review);
			
 
			// execute insert SQL statement
			prepStatement.executeUpdate();
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.print("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			System.out.print("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
		}
 
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ai_hackaton", "root", "root");
			if (conn != null) {
				System.out.print("Connection Successful! Enjoy. Now it's time to push data");
			} else {
				System.out.print("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.print("MySQL Connection Failed!");
			e.printStackTrace();
		}
	}
}
