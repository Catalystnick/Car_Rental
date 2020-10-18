package com.geekdroids.carrental;

public class Cars {
    private String Model;
    private String Owner;
    private String ID;
    private String Colour;
    private String Seats;



    public Cars(){

    }

    public Cars(String model, String owner, String ID, String colour, String seats) {
        Model = model;
        Owner = owner;
        this.ID = ID;
        Colour = colour;
        Seats = seats;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        Seats = seats;
    }

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
