package com.amazonaws.logic;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amazonaws.dto.Product;
import com.amazonaws.dto.ProductReview;
import com.amazonaws.util.ComprehendUtil;
import com.amazonaws.util.FileLogic;

public class UserSearchLogic {

	public static void analyzeSearch() {
		final String filename = "viber";
		List<String> reviews = FileLogic.readFromFile(filename);
		Product product = new Product(filename);
		
		ProductReview productReview = new ProductReview(reviews.get(0));
		ComprehendUtil.detectKeyPhrase(productReview, reviews.get(0));
		ComprehendUtil.detectEntity(productReview, reviews.get(0));
	    
	    System.out.println("------- USER SEARCH -------");   
    	System.out.println("---------------------------");
        System.out.println("Key Phrases: " + productReview.getKeyPhrases().toString());
        System.out.println("Entities: " + productReview.getEntityMap().toString());
    	System.out.println("---------------------------");
	    
    	Map<String, Integer> phraseMap = productReview.getKeyPhrases();
    	Iterator<String> phraseIterator = phraseMap.keySet().iterator();
    	while(phraseIterator.hasNext()) {
    		String phrase = phraseIterator.next();
    		List<String> response = DatamuseQueryLogic.findSimilar(phrase);
        	System.out.println("Find similar words for "+ phrase + ": \n" + response.toString());
    	}
	    
	    
	}
}
