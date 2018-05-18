package com.acn.ai.controller;

import com.acn.ai.controller.request.AnalyzeRequest;
import com.acn.ai.controller.response.AnalyzeResponse;
import com.acn.ai.controller.response.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

        // TODO: call real service layer logic
        List<Product> productList = this.mockProductList();
        if (productList == null || productList.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setProductList(new ArrayList<>());
        } else {
            response.setProductList(productList);
            response.setStatus(HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<Product> mockProductList() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1001");
        product.setProductName("bike");
        product.setPrice(1000);
        product.setRating(5);
        product.setDescription("test description");
        product.setReview("asdfdasfasf");
        productList.add(product);
        return productList;
    }

}
