package com.example.hoangtruc.shopapp.data.db.model;

import android.widget.TextView;

import java.util.List;

public class Electronic {
    private List<Brand> mBrandList;
    private List<Product> mProductList;
    private String mTitleTop;
    private String mTitleBot;
    private boolean isCheckTable;
    private String[] imageSaleList;

    public String[] getImageSaleList() {
        return imageSaleList;
    }

    public void setImageSaleList(String[] imageSaleList) {
        this.imageSaleList = imageSaleList;
    }

    public boolean isCheckTable() {
        return isCheckTable;
    }

    public void setCheckTable(boolean checkTable) {
        isCheckTable = checkTable;
    }

    public String getTitleTop() {
        return mTitleTop;
    }

    public void setTitleTop(String titleTop) {
        mTitleTop = titleTop;
    }

    public String getTitleBot() {
        return mTitleBot;
    }

    public void setTitleBot(String titleBot) {
        mTitleBot = titleBot;
    }

    public List<Brand> getBrandList() {
        return mBrandList;
    }

    public void setBrandList(List<Brand> brandList) {
        mBrandList = brandList;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }
}
