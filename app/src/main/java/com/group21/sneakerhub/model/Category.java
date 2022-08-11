package com.group21.sneakerhub.model;

import java.net.URI;

public class Category {

    public String uri;
    public String colour;
    public String layout;
    public int id;
    public String name;

    public Category(){

    }

    public Category(String name, int id, String URI, String colour, String layout) {
        this.name = name;
        this.id = id;
        this.uri = uri;
        this.colour = colour;
        this.layout = layout;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String GetURI() {
        return this.uri;
    }

    public String GetColour(){
        return this.colour;
    }

    public String GetLayout(){
        return this.layout;
    }

    public String GetName(){
        return this.name;
    }

    public int GetId(){
        return this.id;
    }
}
