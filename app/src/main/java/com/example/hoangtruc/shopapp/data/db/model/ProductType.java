package com.example.hoangtruc.shopapp.data.db.model;

import java.util.List;

public class ProductType {
    private int mProductTypeId, mProductTypeIdParent;
    private String mNameType;
    private List<ProductType> mListChildType;

    public ProductType() {
    }

    public int getProductTypeId() {
        return mProductTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        mProductTypeId = productTypeId;
    }

    public int getProductTypeIdParent() {
        return mProductTypeIdParent;
    }

    public void setProductTypeIdParent(int productTypeIdParent) {
        mProductTypeIdParent = productTypeIdParent;
    }

    public String getNameType() {
        return mNameType;
    }

    public void setNameType(String nameType) {
        mNameType = nameType;
    }

    public List<ProductType> getListChildType() {
        return mListChildType;
    }

    public void setListChildType(List<ProductType> listChildType) {
        mListChildType = listChildType;
    }
}
