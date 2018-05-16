package com.amazonaws.util;

import java.util.Iterator;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentRequest;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentResult;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.DominantLanguage;

public class ComprehendUtil {
	
	private static AmazonComprehend comprehendClient =
            AmazonComprehendClientBuilder.standard().withRegion(Regions.US_EAST_1)
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
	        DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(englishText)
	                                                                                    .withLanguageCode("en");
	        detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
	        System.out.println("Text: " + englishText);
	        System.out.println("Language: " + language + ", Result: " + detectSentimentResult.getSentiment());
		} else {
			System.out.println("This language is not supported.");
		}
        
        return detectSentimentResult;
	}
	
	public static BatchDetectSentimentResult detectBatchDominantSentiment(List<String> text) {
		BatchDetectSentimentResult detectSentimentResult = null;
		// Call detectSentiment API
        BatchDetectSentimentRequest detectSentimentRequest = new BatchDetectSentimentRequest().withTextList(text)
                                                                                    .withLanguageCode("en");
        
        detectSentimentResult = comprehendClient.batchDetectSentiment(detectSentimentRequest);
        System.out.println("Result: " + detectSentimentResult);
        return detectSentimentResult;
	}
	
	// Checks dominant language of a text
	// Text needs to be atleast 20 chars
	public static String detectDominantLanguage(String text) {
		String dominantLanguage = null;
		
		// Call detectDominantLanguage API
        DetectDominantLanguageRequest detectDominantLanguageRequest = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult detectDominantLanguageResult = comprehendClient.detectDominantLanguage(detectDominantLanguageRequest);
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
