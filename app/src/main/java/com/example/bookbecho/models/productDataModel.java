package com.example.bookbecho.models;

public class productDataModel {

    String title , description , price , photo , user , sold;

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public productDataModel(String title, String description, String price, String photo, String user) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.user = user;
        this.sold = sold;
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
    //    String productTitle , productDescription;
//    String productPrice;
//    String productImageUrl;
//
//    public String getProductImageUrl() {
//        return productImageUrl;
//    }
//
//    public void setProductImageUrl(String productImageUrl) {
//        this.productImageUrl = productImageUrl;
//    }
//
////    public int getProductImage() {
////        return productImage;
////    }
////
////    public void setProductImage(int productImage) {
////        this.productImage = productImage;
////    }
//
//    public String getProductTitle() {
//        return productTitle;
//    }
//
//    public void setProductTitle(String productTitle) {
//        this.productTitle = productTitle;
//    }
//
//    public String getProductDescription() {
//        return productDescription;
//    }
//
//    public void setProductDescription(String productDescription) {
//        this.productDescription = productDescription;
//    }
//
//    public String getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(String productPrice) {
//        this.productPrice = productPrice;
//    }
//
//
//    public productDataModel( String productTitle, String productDescription, String productPrice , String productImageUrl ) {
////        this.productImage = productImage;
//        this.productTitle = productTitle;
//        this.productDescription = productDescription;
//        this.productPrice = productPrice;
//        this.productImageUrl = productImageUrl;
//    }
}
