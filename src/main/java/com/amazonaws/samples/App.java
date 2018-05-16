package com.amazonaws.samples;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.dto.Product;
import com.amazonaws.logic.SentimentLogic;
import com.amazonaws.util.ComprehendUtil;

public class App 
{
    public static void main( String[] args )
    {
    	final String filename = "viber";
    	List<String> reviews = readFromFile(filename);
    	Product product = new Product(filename);
    	List<String> resultList = new ArrayList<String>();
    	
    	if(reviews.size() > 1) {
    		
    	}
        for(int i=0; i<reviews.size(); i++) {
        	String result = SentimentLogic.translateNonEnglishInput(reviews.get(i));
        	resultList.add(result);
        }
        product.setReviewsList(ComprehendUtil.detectBatchDominantSentiment(resultList));
        SentimentLogic.analyzeSentimentResult(product);
        
        System.out.println( "Done" );
    }
    
    private static List<String> readFromFile(String filename) {
    	List<String> reviews = new ArrayList<String>();
    	String line = "";
    	try {
    		FileReader fileReader = new FileReader(filename+".txt");
    		BufferedReader bufferedReader = new BufferedReader(fileReader);
    		while((line = bufferedReader.readLine()) != null) {
    			reviews.add(line);
    		}
    		bufferedReader.close();
    	} catch(FileNotFoundException fnfException) {
    		System.out.println("Unable to open file: " + filename);
    	} catch(IOException ioException) {
    		System.out.println("Error reading file: " + filename + "\nError message: " + ioException.getMessage());
    	}
    	return reviews;
    }
}
