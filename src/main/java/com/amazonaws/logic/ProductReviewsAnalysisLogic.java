package com.amazonaws.logic;

import java.util.List;

import com.amazonaws.dto.CustomerRequest;
import com.amazonaws.dto.Product;
import com.amazonaws.util.ComprehendUtil;
import com.amazonaws.util.FileLogic;

public class ProductReviewsAnalysisLogic {
	
	public static void analyzeReviews() {
		final String filename = "reviews";
		List<String> reviews = FileLogic.readFromFile(filename);
		Product product = new Product(filename);
		
	    for(int i=0; i<reviews.size(); i++) {
	    	String result = SentimentLogic.translateNonEnglishInput(reviews.get(i));
	    	ComprehendUtil.detectDominantSentiment(reviews.get(i));
			ComprehendUtil.detectKeyPhrase(reviews.get(i));
	    }
	    SentimentLogic.analyzeSentimentResult(product);
	}
}
