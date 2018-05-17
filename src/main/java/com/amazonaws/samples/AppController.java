package com.amazonaws.samples;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.dto.Product;
import com.amazonaws.logic.ProductReviewsAnalysisLogic;

@RestController
public class AppController {
	
	 @RequestMapping("/review/{reviewText}")
	    public String reviewSubmit(@PathVariable String reviewText) {
		 	ProductReviewsAnalysisLogic.analyzeReviews();
	        System.out.println( "Done" );
	        return "Review submitted: " + reviewText;
	    }
	 
	 @RequestMapping("/search/{searchText}")
	    public List<Product> index(@PathVariable String searchText) {
		 	List<Product> recommendations = Collections.emptyList();
		 	///Recommendation method
	        return recommendations;
	    }
}
