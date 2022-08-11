package com.group21.sneakerhub.model;

public abstract class Category extends Entity {
    private String imageURI;
    private String colour;

    public String getImageURI() {
        return imageURI;
    }

    public String getColour() {
        return colour;
    }
}
