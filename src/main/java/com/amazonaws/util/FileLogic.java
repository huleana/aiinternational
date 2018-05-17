package com.amazonaws.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLogic {

	public static List<String> readFromFile(String filename) {
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
