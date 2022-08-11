package com.group21.sneakerhub.model;

public abstract class Entity {
    protected long id;
    protected String name;

    public long getId(){
        return id;
    };

    public String getName(){
        return name;
    }
}
