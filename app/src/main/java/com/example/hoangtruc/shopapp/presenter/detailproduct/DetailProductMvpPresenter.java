package com.example.hoangtruc.shopapp.presenter.detailproduct;

import android.content.Context;

import com.example.hoangtruc.shopapp.data.db.model.Product;

public interface DetailProductMvpPresenter {
    void detailProduct(int  idProduct);
    void listFeedback(int idProduct,int offset);
    void addCart( Product product );
    void removeCart(Product product );
    boolean checkCart(int id );
    int getCartCount( Context context);

}
