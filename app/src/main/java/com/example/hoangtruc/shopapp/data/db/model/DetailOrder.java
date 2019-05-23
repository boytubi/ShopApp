package com.example.hoangtruc.shopapp.data.db.model;

public class DetailOrder {
    private int mIdOrder;
    private int mIdProduct;
    private int mQuantity;

    public int getIdOrder() {
        return mIdOrder;
    }

    public void setIdOrder(int idOrder) {
        mIdOrder = idOrder;
    }

    public int getIdProduct() {
        return mIdProduct;
    }

    public void setIdProduct(int idProduct) {
        mIdProduct = idProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }
}
