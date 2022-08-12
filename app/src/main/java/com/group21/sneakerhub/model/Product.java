package com.group21.sneakerhub.model;

import java.text.DecimalFormat;
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

    public Product() {}

    public Product(long id, long categoryId, double price, String color, List<Integer> availableSizes, double rating, int numberOfUsersRated, boolean isFavourite, List<String> imageUrls, String description, List<String> features) {
        this.id = id;
        this.name = name;
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

    public boolean changeIsFavourite () {
        // toggle isFavourite and return the new value
        this.isFavourite = !this.isFavourite;
        return this.isFavourite;
    }


}
