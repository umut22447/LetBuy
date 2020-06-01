package com.example.myapplication5;

import android.widget.ImageView;

public class Item {
    private int imageResourceId;
    private String name, seller, type, description;
    private double price;
    private int itemId;

    public Item(int itemId,String seller, String name, String type, String description, double price, int imageResourceId){
        this.itemId = itemId;
        this.seller = seller;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }


    @Override
    public String toString(){
        return "Item Name : " + this.name + "\nPrice : " + this.price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
