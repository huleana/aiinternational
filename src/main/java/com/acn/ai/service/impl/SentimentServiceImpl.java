package com.acn.ai.service.impl;

import com.acn.ai.service.dto.ProductReview;
import com.acn.ai.utils.ComprehendUtil;
import com.acn.ai.utils.TranslateUtil;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.GetIntentRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetIntentResult;
import org.springframework.stereotype.Service;

@Service
public class SentimentServiceImpl {
	
	public static void analyzeSentimentResult(ProductReview productReview) {
			int score = 0;
			DetectSentimentResult sentimentResult = productReview.getSentimentResult();
			switch(productReview.getSentiment()) {
				case "POSITIVE":
					if(sentimentResult.getSentimentScore().getPositive() >= 90) {
						score = 5;
					} else {
						score = 4;
					}
					break;
				case "NEGATIVE":
					score = 1;
					break;
				case "MIXED":
					score = 2;
					break;
				case "NEUTRAL":
					score = 3;
					break;
				
			}
				productReview.setScore(score);
				System.out.println("Score: " + productReview.getScore());
	}
	
	public static String translateNonEnglishInput(String text) {
		String englishText = text;
		String language = ComprehendUtil.detectDominantLanguage(text);
		
		if(!language.equals("en")) {
			 englishText = TranslateUtil.translateText(text, language);
        }
		return englishText;
	}
	
	public static void getIntent(String text) {
		AmazonLexModelBuilding client = AmazonLexModelBuildingClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		GetIntentRequest request = new GetIntentRequest().withName("DocOrderPizza").withVersion("$LATEST");
		GetIntentResult response = client.getIntent(request);
		System.out.println("lex response: " + response);
	}
	
}
