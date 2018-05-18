package com.amazonaws.samples;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.dto.Product;
import com.amazonaws.dto.ProductRecommendation;
import com.amazonaws.logic.APIProductReviewsAnalysisLogic;
import com.amazonaws.logic.APIProductSearchReviewLogic;

@RestController
public class AppController {
	
	 @RequestMapping("/review")
	    public String reviewSubmit(
	    		@RequestParam("name") String productName,
	    		@RequestParam("description") String description,
	    		@RequestParam("price") int price,
	    		@RequestParam("review") String reviewText) {
		 	APIProductReviewsAnalysisLogic.analyzeReviews(productName, price, description, reviewText);
	        System.out.println( "Done" );
	        return "Review submitted: " + reviewText;
	    }
	 
	 @RequestMapping("/search")
	    public List<ProductRecommendation> index(@RequestParam("searchText") String searchText) {
		 	List<ProductRecommendation> recommendations = Collections.emptyList();
		 	APIProductSearchReviewLogic.searchReviews(searchText);
	        return recommendations;
	    }
}
