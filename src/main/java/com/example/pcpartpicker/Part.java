package com.example.pcpartpicker;

public class Part {
    private String name;
    private double price;
    private String type; // For CPU/Motherboard
    private int requiredPsu; // For GPU
    private int wattage; // For PSU

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) { this.price = price; }

    public String getType() {
        return type;
    }
    public void setType(String type) { this.type = type; }

    public int getRequiredPsu() {
        return requiredPsu;
    }
    public void setRequiredPsu(int requiredPsu) { this.requiredPsu = requiredPsu; }

    public int getWattage() {
        return wattage;
    }
    public void setWattage(int wattage) { this.wattage = wattage; }
}