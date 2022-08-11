package com.group21.sneakerhub.model;

public abstract class Entity {
    public String name;
    public int id;

    public String GetName() {
        return this.name;
    }

    public int GetId(){
        return this.id;
    }
}
