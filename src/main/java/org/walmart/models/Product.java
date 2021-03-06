package org.walmart.models;

import javax.persistence.*;

/**
 * Model class for Product class which represents each Product
 *
 * @param productId It represents ID of a particular product
 * @param productName It represents the name of the product
 * @param shortDescription It represents a short description of the product
 * @param longDescription It represent a long description of the product
 * @param price It represents string value of the price
 * @param productImage It represents image for the product which has to be appended to "https://mobile-tha-server.firebaseapp.com" for fetching the image
 * @param priceFloat It represents the float value of the price
 * @param reviewRating It represents review rating for the product
 * @param reviewCount It represents total number of the reviews for the product
 * @param inStock It represents whether the product is in stock or not
 *
 */

@Entity
@Table(name = "products_master",
        indexes = {
            @Index(name = "INDEX_PRODUCT_NAME", columnList = "productName"),
            @Index(name = "INDEX_PRODUCT_SHORT_DESC", columnList = "shortDescription"),
            @Index(name = "INDEX_PRODUCT_LONG_DESC", columnList = "longDescription")
        })
public class Product {

    public Product(){}

    @Id
    private String productId;
    private String productName;

    @Column(length = 1000)
    private String shortDescription;

    @Column(length = 5000)
    private String longDescription;


    private String price;
    private String productImage;

    private Float priceFloat;

    public Float getPriceFloat() {
        return priceFloat;
    }

    public void setPriceFloat(Float priceFloat) {
        this.priceFloat = priceFloat;
    }

    private Double reviewRating;
    private int reviewCount;
    private boolean inStock;

    public Product(String productId, String productName, String shortDescription, String longDescription, String price, String productImage, Double reviewRating, int reviewCount, boolean inStock) {
        this.productId = productId;
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
        this.productImage = productImage;
        this.reviewRating = reviewRating;
        this.reviewCount = reviewCount;
        this.inStock = inStock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}

