package com.amazonaws.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatamuseQueryLogic {

    public static List<String> findSimilar(String text) {
    	List<String> resultList = new ArrayList<String>();
        String value = text.replaceAll(" ", "+");
        String dataResponse = getJSON("http://api.datamuse.com/words?rd="+value);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode fullDocument;
		try {
			fullDocument = mapper.readTree(dataResponse);
	        if(fullDocument != null) {
		        for (JsonNode node: fullDocument) {
		        	resultList.add(node.get("word").asText(""));
		        }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;
    }
    
    private static String getJSON(String url) {
        URL datamuse;
        URLConnection dc;
        StringBuilder s = null;
        try {
            datamuse = new URL(url);
            dc = datamuse.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream(), "UTF-8"));
            String inputLine;
            s = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                s.append(inputLine);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s != null ? s.toString() : null;
    }
}
