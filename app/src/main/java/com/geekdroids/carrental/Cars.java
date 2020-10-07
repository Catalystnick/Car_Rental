package com.geekdroids.carrental;

public class Cars {
    private String Model;
    private String Owner;
    private String ID;



    public Cars(){

    }

    public Cars(String model, String owner, String ID) {
        Model = model;
        Owner = owner;
        this.ID = ID;
    }//constructor

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    //helping class
}
