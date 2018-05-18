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
		ComprehendUtil.detectKeyPhrase(productReview, searchText);
		ComprehendUtil.detectEntity(productReview, searchText);

		String keyPhraseFilter = "";

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

    	//TODO Fix this for loop to get the Entities
    	//    	for(int i=0; i < productReview.getEntityMap().size(); i++) {
    	//    		entityFilter = entityFilter + productReview.getEntityMap().get(i).toString();
    	//    		if(i+1 <= productReview.getEntityMap().size()) {
    	//    			entityFilter = entityFilter + ",";
    	//    		}
    	//    	}
		
		return getDataFromDB(keyPhraseFilter, entityFilter);
    	
    }
	
	private static List<ProductRecommendation> getDataFromDB(String keyPhrases, String entities) {
		 
		try {
			//TODO Remove native query and preparedStament. Use JPA instead.
			String getQueryStatement = "select product_name, price, rating, description, review, avg(key_phrase_score) avg_key_score "
					+ " from reviews where lower(key_phrase) in ("+keyPhrases+") "
							+ "or lower(entity) in ("+entities+") and sentiment = 'POSITIVE' "
									+ "group by product_name, price, description, review order by 1;";
 
			prepStatement = conn.prepareStatement(getQueryStatement);

			ResultSet rs = prepStatement.executeQuery();
 
			List<ProductRecommendation> recommendations = new ArrayList<ProductRecommendation>();
			String previousProductName = "";
			int id = 1;
			while (rs.next()) {
				ProductRecommendation pr;
				if(!previousProductName.equals(rs.getString("product_name"))) {
					pr = new ProductRecommendation();
					pr.setProductId(id+"");
					pr.setProductName(rs.getString("product_name"));
					pr.setDescription(rs.getString("description"));
					pr.setPrice(rs.getInt("price"));
					pr.setRating(rs.getInt("rating"));
					pr.setReview(new ArrayList<String>());
					pr.getReview().add(rs.getString("review"));
					previousProductName = rs.getString("product_name");
					id++;
					recommendations.add(pr);
				} else {
					pr = recommendations.get(recommendations.size()-1);
					pr.getReview().add(rs.getString("review"));
				}
			}
			return recommendations;
		} catch (
		SQLException e) {
			e.printStackTrace();
		}
		return null;
 
	}
	
	private static List<ProductRecommendation> getDataFromDBAll(String keyPhrases, String entities) {
		 
		try {
			//TODO Same as TODO above
			String getQueryStatement = "select product_name, price, rating, description, review, avg(key_phrase_score) avg_key_score "
					+ " from reviews where sentiment = 'POSITIVE' "
									+ "group by product_name, price, description, review order by 1;";
 
			prepStatement = conn.prepareStatement(getQueryStatement);
 
			ResultSet rs = prepStatement.executeQuery();
 
			List<ProductRecommendation> recommendations = new ArrayList<ProductRecommendation>();
			String previousProductName = "";
			int id = 1;
			while (rs.next()) {
				ProductRecommendation pr;
				if(!previousProductName.equals(rs.getString("product_name"))) {
					//TODO Use the sentimentScore and keyPhrasesScore to identify the recommendations ordered by relevance instead of pick/show all
					pr = new ProductRecommendation();
					pr.setProductId(id+"");
					pr.setProductName(rs.getString("product_name"));
					pr.setDescription(rs.getString("description"));
					pr.setPrice(rs.getInt("price"));
					pr.setRating(rs.getInt("rating"));
					pr.setReview(new ArrayList<String>());
					pr.getReview().add(rs.getString("review"));
					previousProductName = rs.getString("product_name");
					id++;
					recommendations.add(pr);
				} else {
					pr = recommendations.get(recommendations.size()-1);
					pr.getReview().add(rs.getString("review"));
				}
			}
			return recommendations;
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
		return null;
 
	}
		
	private static void connectDB() {
		//TODO same as TODO above (JPA)
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
	
	public static List<ProductRecommendation> searchReviewsAll(
			String searchText) {		
    	
		connectDB();
		
		ProductReview productReview = new ProductReview(searchText);
		ComprehendUtil.detectKeyPhrase(productReview, searchText);
		ComprehendUtil.detectEntity(productReview, searchText);
		String keyPhraseFilter = "";
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
		
		return getDataFromDBAll(keyPhraseFilter, entityFilter);
    	
    }
}
