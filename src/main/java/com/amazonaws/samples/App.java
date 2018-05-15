package com.amazonaws.samples;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.util.ComprehendUtil;

public class App 
{
    public static void main( String[] args )
    {

        String text = "It is raining today in Seattle so rester à la maison.";
        String text2 = " Je reste à la maison aujourd’hui.";
        String text3 = "私は毎日日本語の勉強している。難しいです。";

        ComprehendUtil.detectDominantSentiment(text);
        ComprehendUtil.detectDominantSentiment(text2);
        ComprehendUtil.detectDominantSentiment(text3);
        
        System.out.println( "Done" );
    }
}
