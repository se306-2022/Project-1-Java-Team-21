package com.group21.sneakerhub.model;

import java.util.List;

/**
 * Parent product class
 */

public class Product extends Entity {

    // Variables
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

    // Constructors
    public Product() {}

    public Product(String name, long id, long categoryId, double price, String color, List<Integer> availableSizes, double rating, int numberOfUsersRated, boolean isFavourite, List<String> imageUrls, String description, List<String> features, boolean isFirst) {
        this.name = name;
        this.id = id;
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

    // Getters and setters

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
        return this.rating;
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
        this.rating = ((this.rating * numberOfUsersRated) + rating) / (numberOfUsersRated + 1);
        this.numberOfUsersRated++;
    }

    public boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public void toggleIsFavourite(){
        this.isFavourite = !this.isFavourite;
    }

}
