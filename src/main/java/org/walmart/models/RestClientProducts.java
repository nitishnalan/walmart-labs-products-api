package org.walmart.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestClientProducts {

    public RestClientProducts() {
    }

    private List<Product> products;
    private int totalProducts;
    private int pageNumber;
    private int pageSize;
    private int statusCode;

    public RestClientProducts(List<Product> products, int totalProducts, int pageNumber, int pageSize, int statusCode) {
        this.products = products;
        this.totalProducts = totalProducts;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.statusCode = statusCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
