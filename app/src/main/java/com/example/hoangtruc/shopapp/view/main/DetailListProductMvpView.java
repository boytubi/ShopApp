package com.example.hoangtruc.shopapp.view.main;

import com.example.hoangtruc.shopapp.data.db.model.Product;

import java.util.List;

public interface DetailListProductMvpView {
    void displayListProductsOfBrand(List<Product> products);
}
