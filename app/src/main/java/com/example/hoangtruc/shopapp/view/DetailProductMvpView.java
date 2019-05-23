package com.example.hoangtruc.shopapp.view;

import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.db.model.Review;

import java.util.List;

public interface DetailProductMvpView {
    void displayDetailProduct(Product product);
    void displaySliderProduct(String [] thumbnail);
    void displayListFeedback(List<Review> reviewList);
     void updateCartCount();
    void updateButtonCart(Product product);
}
