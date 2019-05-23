package com.example.hoangtruc.shopapp.view.search;

import com.example.hoangtruc.shopapp.data.db.model.Product;

import java.util.List;

public interface SearchMvpView {
    void displayResult(List<Product> products);
}
