package com.example.hoangtruc.shopapp.data.db.model;

public class Review {
    private String idFeedBack,nameDevice,review,title,date;
    private int  idProduct,rating;

    public String getIdFeedBack() {
        return idFeedBack;
    }

    public void setIdFeedBack(String idFeedBack) {
        this.idFeedBack = idFeedBack;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
