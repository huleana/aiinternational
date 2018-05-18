package com.acn.ai.controller.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AnalyzeResponse {
    private HttpStatus status;
    private List<Product> productList;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
