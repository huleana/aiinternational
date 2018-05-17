package com.amazonaws.logic;

import java.util.List;

import com.amazonaws.dto.CustomerRequest;
import com.amazonaws.dto.ProductReview;
import com.amazonaws.util.ComprehendUtil;
import com.amazonaws.util.FileLogic;

public class ProductReviewsAnalysisLogic {
	
	public static void analyzeReviews() {
		final String filename = "reviews";
		List<String> reviews = FileLogic.readFromFile(filename);
		ProductReview product = new ProductReview(filename);
		
	    for(int i=0; i<reviews.size(); i++) {
	    	String result = SentimentLogic.translateNonEnglishInput(reviews.get(i));
	    	ComprehendUtil.detectDominantSentiment(product, reviews.get(i));
			ComprehendUtil.detectKeyPhrase(product, reviews.get(i));
			ComprehendUtil.detectEntity(product, reviews.get(i));
	    }
	    SentimentLogic.analyzeSentimentResult(product);
	}
}
