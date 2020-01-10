package com.example.auctionhouse.model;

public class Item {

    private String name;
    private int expiration_date;
    private int start_price;
    private int current_bid;
    private String seller;

    public Item() {}

    public Item(String name, int expiration_date, int start_price, int current_bid, String seller) {
        this.name = name;
        this.expiration_date = expiration_date;
        this.start_price = start_price;
        this.current_bid = current_bid;
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpirationDate() {
        return expiration_date;
    }

    public void setExpirationDate(int expirationDate) {
        this.expiration_date = expirationDate;
    }

    public int getStartPrice() {
        return start_price;
    }

    public void setStartPrice(int startPrice) {
        this.start_price = startPrice;
    }

    public int getCurrentBid() {
        return current_bid;
    }

    public void setCurrentBid(int currentBid) {
        this.current_bid = currentBid;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
