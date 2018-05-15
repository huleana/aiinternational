package com.amazonaws.util;

import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.DominantLanguage;

public class ComprehendUtil {
	
	private static AmazonComprehend comprehendClient =
            AmazonComprehendClientBuilder.standard()
                                         .build();
	
	public static DetectSentimentResult detectDominantSentiment(String text) {
		DetectSentimentResult detectSentimentResult = null;
		// Get dominant language
		String language = ComprehendUtil.detectDominantLanguage(text);
		String englishText = text;
		
		if(!language.equals("en")) {
			 englishText = TranslateUtil.translateText(text, language);
        }
		
		if(!englishText.isEmpty()) {
			// Call detectSentiment API
	        System.out.println("Calling DetectSentiment");
	        DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(englishText)
	                                                                                    .withLanguageCode("en");
	        detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
	        System.out.println(detectSentimentResult);
	        System.out.println("End of DetectSentiment\n");
		} else {
			System.out.println("This language is not supported.");
		}
        
        return detectSentimentResult;
	}
	
	// Checks dominant language of a text
	// Text needs to be atleast 20 chars
	public static String detectDominantLanguage(String text) {
		String dominantLanguage = null;
		
		// Call detectDominantLanguage API
        System.out.println("Calling DetectDominantLanguage");
        DetectDominantLanguageRequest detectDominantLanguageRequest = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult detectDominantLanguageResult = comprehendClient.detectDominantLanguage(detectDominantLanguageRequest);
        System.out.println(detectDominantLanguageResult);
        System.out.println("End of DetectDominantLanguage\n");
        
       List<DominantLanguage> languages =  detectDominantLanguageResult.getLanguages();
       double score = 0;
       if(languages != null) {
    	   Iterator<DominantLanguage> iterator = languages.iterator();
    	   while(iterator.hasNext()) {
    		   DominantLanguage lang = iterator.next();
    		   if (lang.getScore() > score) {
    			   score = lang.getScore();
    			   dominantLanguage = lang.getLanguageCode();
    		   }
    	   }
       }
		return dominantLanguage;
	}
}
