package com.amazonaws.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

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
