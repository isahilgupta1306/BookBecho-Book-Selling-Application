package com.example.bookbecho.models;

public class productDataModel {

    String title;
    String description;
    String price;
    String photo;
    String user; //this denotes the user uid of the person who posted the product
    String sold;

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    String prodID;

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public productDataModel(String title, String description, String price, String photo, String user , String prodID) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.user = user;
        this.sold = sold;
        this.prodID = prodID;
    }
    public productDataModel(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
