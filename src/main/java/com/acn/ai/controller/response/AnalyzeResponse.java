package com.acn.ai.controller.response;

import com.acn.ai.service.dto.ProductRecommendation;
import org.springframework.http.HttpStatus;

import java.util.List;

public class AnalyzeResponse {
    private HttpStatus status;
    private List<ProductRecommendation> productList;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<ProductRecommendation> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductRecommendation> productList) {
        this.productList = productList;
    }
}
