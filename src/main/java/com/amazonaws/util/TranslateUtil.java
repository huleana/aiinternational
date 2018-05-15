package com.amazonaws.util;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateAsyncClient;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.amazonaws.services.translate.model.UnsupportedLanguagePairException;

public class TranslateUtil {
	private static AmazonTranslate translate = AmazonTranslateClientBuilder.defaultClient();
	
	public static String translateText(String text, String language) {
		String translation = "";
		TranslateTextRequest request = new TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode(language)
                .withTargetLanguageCode("en");
        try {
        	TranslateTextResult result  = translate.translateText(request);
        	translation = result.getTranslatedText();
        } catch(UnsupportedLanguagePairException exception) {
        	System.out.println(exception.getErrorMessage());
        }
        System.out.println(translation);   
        return translation;
	}
}


