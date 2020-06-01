package com.example.myapplication5;

public class UserInventoryItem {
    private String itemName, oldOwner, newOwner;

    public UserInventoryItem(String itemName, String oldOwner, String newOwner) {
        this.itemName = itemName;
        this.oldOwner = oldOwner;
        this.newOwner = newOwner;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOldOwner() {
        return oldOwner;
    }

    public void setOldOwner(String oldOwner) {
        this.oldOwner = oldOwner;
    }

    public String getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(String newOwner) {
        this.newOwner = newOwner;
    }

    @Override
    public String toString(){
        return "Item Name : " + itemName + "\nOld Owner : " + oldOwner + "\nNew Owner : You";
    }
}
