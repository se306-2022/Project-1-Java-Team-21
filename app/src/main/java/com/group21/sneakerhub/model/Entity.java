package com.group21.sneakerhub.model;

public abstract class Entity {

    public String name;
    public long id;

    public String getName() {
        return this.name;
    }

    public long getId(){
        return this.id;
    }
}
