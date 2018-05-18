package com.acn.ai.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.acn.ai.service.dto.Product;
import com.acn.ai.service.dto.ProductReview;
import com.acn.ai.utils.ComprehendUtil;
import com.acn.ai.utils.FileLogic;

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
