package com.example.hoangtruc.shopapp.view.main;

import com.example.hoangtruc.shopapp.data.db.model.ProductType;

import java.util.List;

public interface MenuMvpView {
    void displayListMenu(List<ProductType> productTypes);

}
