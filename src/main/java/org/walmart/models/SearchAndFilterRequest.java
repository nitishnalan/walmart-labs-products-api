package org.walmart.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Model class for Get Request parameter
 * @param search It represents the search keyword
 * @param minPrice It represents the minimum price of the product
 * @param maxPrice It represents the maximum price of the product
 * @param minReviewRating It represents the minimum review rating of the product
 * @param maxReviewRating It represents the maximum review rating of the product
 * @param minReviewCount It represents the minimum review rating count of the product
 * @param maxReviewCount It represent the maximum review rating count of the product
 * @param inStock It represents whether the product is in stock or not
 */

public class SearchAndFilterRequest {
    public SearchAndFilterRequest() {
    }

    private String search;

    @Min(message="Minimum price can only be greater than or equal to 0", value=0)
    private Float minPrice;

    @Min(value=0)
    private Float maxPrice;

    @Min(message = "Minimum review rating can only be greater than or equal to 0", value = 0)
    @Max(message= "Maximum review rating can be 5", value = 5)
    private Double minReviewRating;

    @Min(message = "Minimum review rating can only be greater than or equal to 0", value = 0)
    @Max(message= "Maximum review rating can only be 5", value = 5)
    private Double maxReviewRating;

    @Min(message= "Minimum review count can start with 0", value = 0)
    private Integer minReviewCount;

    @Min(message= "Maximum Review Count can start with 0", value = 0)
    private Integer maxReviewCount;

    private Boolean inStock;

    public SearchAndFilterRequest(String search, @Min(message = "Minimum price can be 0", value = 0) Float minPrice, @Min(value = 0) Float maxPrice, @Min(message = "Minimum review rating can be 0", value = 0) @Max(message = "Maximum review rating can be 5", value = 5) Double minReviewRating, @Min(message = "Minimum review rating can be 0", value = 0) @Max(message = "Maximum review rating can be 5", value = 5) Double maxReviewRating, @Min(message = "Minimum review rating can be 0", value = 0) Integer minReviewCount, @Min(message = "Minimum review rating can be 0", value = 0) Integer maxReviewCount, Boolean inStock) {
        this.search = search;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minReviewRating = minReviewRating;
        this.maxReviewRating = maxReviewRating;
        this.minReviewCount = minReviewCount;
        this.maxReviewCount = maxReviewCount;
        this.inStock = inStock;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinReviewRating() {
        return minReviewRating;
    }

    public void setMinReviewRating(Double minReviewRating) {
        this.minReviewRating = minReviewRating;
    }

    public Double getMaxReviewRating() {
        return maxReviewRating;
    }

    public void setMaxReviewRating(Double maxReviewRating) {
        this.maxReviewRating = maxReviewRating;
    }

    public Integer getMinReviewCount() {
        return minReviewCount;
    }

    public void setMinReviewCount(Integer minReviewCount) {
        this.minReviewCount = minReviewCount;
    }

    public Integer getMaxReviewCount() {
        return maxReviewCount;
    }

    public void setMaxReviewCount(Integer maxReviewCount) {
        this.maxReviewCount = maxReviewCount;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "SearchAndFilterRequest{" +
                "search='" + search + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", minReviewRating=" + minReviewRating +
                ", maxReviewRating=" + maxReviewRating +
                ", minReviewCount=" + minReviewCount +
                ", maxReviewCount=" + maxReviewCount +
                ", inStock=" + inStock +
                '}';
    }
}
