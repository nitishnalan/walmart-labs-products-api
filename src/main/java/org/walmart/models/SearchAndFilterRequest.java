package org.walmart.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SearchAndFilterRequest {

    private String search;

    @Min(message="Minimum price can be 0", value=0)
    private float minPrice;

    @Min(value=0)
    private float maxPrice;

    @Min(message = "Minimum review rating can be 0", value = 0)
    @Max(message= "Maximum review rating can be 5", value = 5)
    private int minReviewRating;

    @Min(message = "Minimum review rating can be 0", value = 0)
    @Max(message= "Maximum review rating can be 5", value = 5)
    private int maxReviewRating;

    @Min(message= "Minimum review rating can be 0", value = 0)
    private int minReviewCount;

    @Min(message= "Minimum review rating can be 0", value = 0)
    private int maxReviewCount;

    private boolean inStock;

    public SearchAndFilterRequest(String search, @Min(message = "Minimum price can be 0", value = 0) float minPrice, @Min(value = 0) float maxPrice, @Min(message = "Minimum review rating can be 0", value = 0) @Max(message = "Maximum review rating can be 5", value = 5) int minReviewRating, @Min(message = "Minimum review rating can be 0", value = 0) @Max(message = "Maximum review rating can be 5", value = 5) int maxReviewRating, @Min(message = "Minimum review rating can be 0", value = 0) int minReviewCount, @Min(message = "Minimum review rating can be 0", value = 0) int maxReviewCount, boolean inStock) {
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

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinReviewRating() {
        return minReviewRating;
    }

    public void setMinReviewRating(int minReviewRating) {
        this.minReviewRating = minReviewRating;
    }

    public int getMaxReviewRating() {
        return maxReviewRating;
    }

    public void setMaxReviewRating(int maxReviewRating) {
        this.maxReviewRating = maxReviewRating;
    }

    public int getMinReviewCount() {
        return minReviewCount;
    }

    public void setMinReviewCount(int minReviewCount) {
        this.minReviewCount = minReviewCount;
    }

    public int getMaxReviewCount() {
        return maxReviewCount;
    }

    public void setMaxReviewCount(int maxReviewCount) {
        this.maxReviewCount = maxReviewCount;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
