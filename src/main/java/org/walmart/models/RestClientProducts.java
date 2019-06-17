package org.walmart.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Model class is used as a rest template to fetch the data from the source url with a page number and number of products
 * @param products It is the List of Product
 * @param totalProducts It is the total number of the products which are available to be fetched
 * @param pageNumber It represents the page number
 * @param pageSize It represents total products on that page
 * @param statusCode It represents status code for the request
 *
 */

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
