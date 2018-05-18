package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.dto.AnalyzeRequest;
import com.amazonaws.dto.AnalyzeResponse;
import com.amazonaws.dto.ProductRecommendation;
import com.amazonaws.logic.APIProductSearchReviewLogic;

@CrossOrigin
@RestController
public class AnalyzeController {

    @RequestMapping(value = "/analyze", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public ResponseEntity<AnalyzeResponse> analyze(@Valid @RequestBody AnalyzeRequest request) throws Exception {
        Logger.getLogger("API").info("request: " + request.getCondition());
        AnalyzeResponse response = new AnalyzeResponse();

        List<ProductRecommendation> productList = APIProductSearchReviewLogic.searchReviews(request.getCondition());
        if (productList == null || productList.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setProductList(new ArrayList<>());
        } else {
            response.setProductList(productList);
            response.setStatus(HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
