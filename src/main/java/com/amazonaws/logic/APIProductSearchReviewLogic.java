package com.amazonaws.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.amazonaws.dto.ProductReview;
import com.amazonaws.dto.ProductRecommendation;
import com.amazonaws.util.ComprehendUtil;

public class APIProductSearchReviewLogic {
	
	static Connection conn = null;
	static PreparedStatement prepStatement = null;
	
	public static List<ProductRecommendation> searchReviews(
			String searchText) {		
    	
		connectDB();
		
		ProductReview productReview = new ProductReview(searchText);
//    	ComprehendUtil.detectDominantSentiment(productReview, searchText);
		ComprehendUtil.detectKeyPhrase(productReview, searchText);
		ComprehendUtil.detectEntity(productReview, searchText);
//	    SentimentLogic.analyzeSentimentResult(productReview);
//	    System.out.println("------- PRODUCT -------");
//	    System.out.println("Product Name: " + productName);
//	    System.out.println("Product Description: " + description);
//	    System.out.println("Product Price: " + price);
//	    System.out.println("---------------------------");
//    	System.out.println("Review: " + productReview.getCustomerReview());
//        System.out.println("Rating: " + productReview.getScore());
//        System.out.println("Sentiment: " + productReview.getSentiment());
        //System.out.println("Key Phrases: " + productReview.getKeyPhrases().toString());
//        System.out.println("Entities: " + productReview.getEntityMap().toString());
//    	System.out.println("---------------------------");

		String keyPhraseFilter = "";
//    	for(int i=0; i < productReview.getKeyPhrases().size(); i++) {
//    		keyPhraseFilter = keyPhraseFilter + '"'+productReview.getKeyPhrases().get(i).toString()+'"';
//    		if(i+1 < productReview.getKeyPhrases().size()) {
//    			keyPhraseFilter = keyPhraseFilter +",";
//    		}
//    	}
    	if (productReview.getKeyPhrases().size() == 0) keyPhraseFilter = "\"\"";
		int x = 1; 
		 for(Map.Entry<String, Integer> entry: productReview.getKeyPhrases().entrySet()) {
			 keyPhraseFilter = keyPhraseFilter + '"'+entry.getKey()+'"';
	    		if(x+1 <= productReview.getKeyPhrases().size()) {
	    			keyPhraseFilter = keyPhraseFilter +",";
	    			x++;
	    		}
		 }
		 
    	
    	String entityFilter = "";
    	if (productReview.getEntityMap().size() == 0) entityFilter = "\"\"";
    	for(int i=0; i < productReview.getEntityMap().size(); i++) {
    		entityFilter = entityFilter + productReview.getEntityMap().get(i).toString();
    		if(i+1 <= productReview.getEntityMap().size()) {
    			entityFilter = entityFilter + ",";
    		}
    	}
		
		return getDataFromDB(keyPhraseFilter, entityFilter);
    	
    }
	
	
	private static List<ProductRecommendation> getDataFromDB(String keyPhrases, String entities) {
		 
		try {
			// MySQL Select Query Tutorial
			//product_name, price, description, review, avg(key_phrase_score) 
			String getQueryStatement = "select product_name, price, rating, description, review, avg(key_phrase_score) avg_key_score "
					+ " from reviews where lower(key_phrase) in ("+keyPhrases+") "
							+ "or lower(entity) in ("+entities+") and sentiment = 'POSITIVE' "
									+ "group by product_name, price, description, review order by 1;";
 
			prepStatement = conn.prepareStatement(getQueryStatement);
 
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepStatement.executeQuery();
 
			// Let's iterate through the java ResultSet
			List<ProductRecommendation> recommendations = new ArrayList<ProductRecommendation>();
			String previousProductName = "";
			while (rs.next()) {
				ProductRecommendation pr;
				if(!previousProductName.equals(rs.getString("product_name"))) {
					pr = new ProductRecommendation();
					pr.setProductName(rs.getString("product_name"));
					pr.setDescription(rs.getString("description"));
					pr.setPrice(rs.getInt("price"));
//					pr.setRating(rs.getInt("rating"));
					pr.getReview().add(rs.getString("review"));
					recommendations.add(pr);
				} else {
					pr = recommendations.get(recommendations.size()-1);
					pr.getReview().add(rs.getString("review"));
				}
				System.out.println("Name: " + rs.getString("product_name"));
				System.out.println("Description: " + rs.getString("description"));
				System.out.println("Price: " + rs.getInt("price"));
				System.out.println("Rating: " + rs.getInt("rating"));
				System.out.println("Review: " + rs.getString("review"));
			}

			return recommendations;
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
		return null;
 
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
