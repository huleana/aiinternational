package com.acn.ai.controller;

import com.acn.ai.controller.request.AnalyzeRequest;
import com.acn.ai.controller.response.AnalyzeResponse;
import com.acn.ai.service.dto.ProductRecommendation;
import com.acn.ai.service.impl.APIProductSearchReviewLogic;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
