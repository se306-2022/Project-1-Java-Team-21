package com.group21.sneakerhub.model;

public abstract class Entity {
    protected String name;
    protected int Id;

    public Entity(String name, int Id) {
        this.name = name;
        this.Id = Id;
    }


    protected String GetName() {
        return this.name;
    }

    protected int GetId(){
        return this.Id;
    }
}
