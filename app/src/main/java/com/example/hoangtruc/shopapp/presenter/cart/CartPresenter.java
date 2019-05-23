package com.example.hoangtruc.shopapp.presenter.cart;

import android.content.Context;

import com.example.hoangtruc.shopapp.data.db.DatabaseHelper;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.view.cart.CartMvpView;

import java.util.List;

public class CartPresenter implements CartMvpPresenter {

    private CartMvpView mCartMvpView;
    private Context mContext;

    public CartPresenter(CartMvpView cartMvpView,Context context) {
        mCartMvpView = cartMvpView;
        mContext=context;
    }

    @Override
    public void getListProductsOfCart() {
        DatabaseHelper databaseHelper=new DatabaseHelper(mContext);
        List<Product> productList=databaseHelper.getAllProduct();
        mCartMvpView.displayProductsOfCart(productList);
        int mTong=0;
        for (Product product:productList){
            mTong+=product.getTotalPrice();
        }
        int totalItem=productList.size();
        mCartMvpView.displayTotalPrice(mTong,totalItem);
    }
}
