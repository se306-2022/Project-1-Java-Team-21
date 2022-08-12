package com.group21.sneakerhub.model;

import com.google.firebase.firestore.Exclude;

public class Category extends Entity {

    public String imageURI;
    public String colour;
    public String layout;

    /**
     * No argument, empty constructor as required by firestone
     */
    public Category(){
    }

    public Category(String name, long id, String imageURI, String colour, String layout) {
        this.name = name;
        this.id = id;
        this.imageURI = imageURI;
        this.colour = colour;
        this.layout = layout;
    }

    public String GetURI() {
        return this.imageURI;
    }

    public String GetColour(){
        return this.colour;
    }

    public String getLayoutInformation() {
        return this.layout;
    }
}
