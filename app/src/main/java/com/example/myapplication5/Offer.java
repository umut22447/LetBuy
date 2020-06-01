package com.example.myapplication5;

public class Offer {
    private String seller,buyer;
    private int sellerItemId, buyerItemId;
    private double amount;

    public Offer(String seller, String buyer, int sellerItemId,int buyerItemId, double amount){
        this.seller = seller;
        this.buyer = buyer;
        this.sellerItemId = sellerItemId;
        this.buyerItemId = buyerItemId;
        setAmount(amount);
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount < 0){
            this.amount = 0;
        }
        else{
            this.amount = amount;
        }
    }

    public int getSellerItemId() {
        return sellerItemId;
    }

    public void setSellerItemId(int sellerItemId) {
        this.sellerItemId = sellerItemId;
    }

    public int getBuyerItemId() {
        return buyerItemId;
    }

    public void setBuyerItemId(int buyerItemId) {
        this.buyerItemId = buyerItemId;
    }

    @Override
    public String toString(){
        return "Offer from " + this.buyer + "  to you.";
    }



}
