package com.amazonaws.logic;

import java.util.List;

import com.amazonaws.dto.Product;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentItemResult;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentResult;
import com.amazonaws.services.comprehend.model.SentimentScore;
import com.amazonaws.util.ComprehendUtil;
import com.amazonaws.util.TranslateUtil;

public class SentimentLogic {
	
	public static void analyzeSentimentResult(Product product) {
		SentimentScore score = new SentimentScore();
		score.setPositive(0.0f);
		score.setNegative(0.0f);
		score.setMixed(0.0f);
		score.setNeutral(0.0f);
		if(product != null) {
			BatchDetectSentimentResult resultList = product.getReviewsList();
			if(resultList != null) {
				List<BatchDetectSentimentItemResult> sentimentItemList = resultList.getResultList();
				for(int i=0; i<sentimentItemList.size(); i++) {
					BatchDetectSentimentItemResult result = sentimentItemList.get(i);
					System.out.println("sentiment: " + result.getSentiment());
					switch(result.getSentiment()) {
						case "POSITIVE":
							score.setPositive(score.getPositive() + 1);
							break;
						case "NEGATIVE":
							score.setNegative(score.getNegative() + 1);
							break;
						case "MIXED":
							score.setMixed(score.getMixed() + 1);
							break;
						case "NEUTRAL":
							score.setNeutral(score.getNeutral() + 1);
							break;
						
					}
				}
				
				product.setAveSentimentScore(score);
				System.out.println("Number of sentiments: " + sentimentItemList.size());
				System.out.println("Average score: " + score);
			}
			
		}
	}
	
	public static String translateNonEnglishInput(String text) {
		String englishText = text;
		String language = ComprehendUtil.detectDominantLanguage(text);
		
		if(!language.equals("en")) {
			 englishText = TranslateUtil.translateText(text, language);
        }
		return englishText;
	}
	
}
