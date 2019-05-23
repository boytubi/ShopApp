package com.example.hoangtruc.shopapp.view.cart;

import com.example.hoangtruc.shopapp.data.db.model.Product;

import java.util.List;

public interface CartMvpView  {
    void displayProductsOfCart(List<Product> products);
    void displayTotalPrice(int tong,int totalItem);
    void setTotalChange(int newValue);
}
