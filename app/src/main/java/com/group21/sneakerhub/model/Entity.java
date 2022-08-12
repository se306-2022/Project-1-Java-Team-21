package com.group21.sneakerhub.model;

public abstract class Entity {

    public String name;
    public long id;

    public String GetName() {
        return this.name;
    }

    public long GetId(){
        return this.id;
    }
}
