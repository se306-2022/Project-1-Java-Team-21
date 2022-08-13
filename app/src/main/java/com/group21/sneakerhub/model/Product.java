package com.group21.sneakerhub.model;

import java.util.List;

public class Product extends Entity {

    private long categoryId;
    private double price;
    private String color;
    private List<Integer> availableSizes;
    private double rating;
    private int numberOfUsersRated;
    private boolean isFavourite;
    private List<String> imageUrls;
    private String description;
    private List<String> features;
    private boolean isFirst;
    private String imageURL; // only for test

    public Product() {}

    public Product(String name, long id, long categoryId, String imageURL, double price, String color, List<Integer> availableSizes, double rating, int numberOfUsersRated, boolean isFavourite, List<String> imageUrls, String description, List<String> features, boolean isFirst) {
        this.name = name;
        this.id = id;
        this.imageURL = imageURL; // just for test
        this.categoryId = categoryId;
        this.price = price;
        this.color = color;
        this.availableSizes = availableSizes;
        this.rating = rating;
        this.numberOfUsersRated = numberOfUsersRated;
        this.isFavourite = isFavourite;
        this.imageUrls = imageUrls;
        this.description = description;
        this.features = features;
        this.isFirst = isFirst;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public List<Integer> getAvailableSizes() {
        return availableSizes;
    }

    public double getRating() {
        // gets the rating to the nearest 0.5
        return Math.round(rating * 2) / 2.0;
    }

    /**
     * Creating this getter just for testing list view,delete later
     * @return
     */
    public String getImageURL(){
        return this.imageURL;
    }

    public int getNumberOfUsersRated() {
        return numberOfUsersRated;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void changeRating (double rating) {
        this.rating = ((this.rating * numberOfUsersRated) + rating) / numberOfUsersRated + 1;
        this.numberOfUsersRated++;
    }

    public void changeIsFavourite () {
        // toggle isFavourite and return the new value
        this.isFavourite = !this.isFavourite;
    }

    public boolean getIsFirst() {
        return isFirst;
    }
}
