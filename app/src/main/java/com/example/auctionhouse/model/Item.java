package com.example.auctionhouse.model;

public class Item {

    private String name;
    private String expirationDate;
    private int startPrice;
    private int currentBid;
    private String seller;
    private String highestBidder;

    public Item() {}

    public Item(String name, String expirationDate, int startPrice, int currentBid, String seller, String highestBidder) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.startPrice = startPrice;
        this.currentBid = currentBid;
        this.seller = seller;
        this.highestBidder = highestBidder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public int getStartPrice() { return startPrice; }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getHighestBidder() { return highestBidder; }

    public void setHighestBidder() { this.highestBidder = highestBidder; }
}
