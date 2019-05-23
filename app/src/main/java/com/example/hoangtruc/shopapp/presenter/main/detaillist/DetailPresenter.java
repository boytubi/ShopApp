package com.example.hoangtruc.shopapp.presenter.main.detaillist;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.network.DetailListResponseData;
import com.example.hoangtruc.shopapp.view.main.DetailListProductMvpView;

import java.util.ArrayList;
import java.util.List;

public class DetailPresenter implements DetailMvpPresenter {
    private DetailListProductMvpView mDetailListProductMvpView;
    private DetailListResponseData mDetailListResponseData;

    public DetailPresenter(DetailListProductMvpView detailListProductMvpView) {
        mDetailListProductMvpView = detailListProductMvpView;
        mDetailListResponseData=new DetailListResponseData();
    }

    @Override
    public void getListProductsOfBrand(int id,boolean isCheck) {
        List<Product> productList= new ArrayList<>();

        if (isCheck){
            productList=mDetailListResponseData.getListProductsOfCategory(id,"getListProductsOfBrand","LISTPRODUCTS",0);

        }else {
            productList=mDetailListResponseData.getListProductsOfCategory(id,"getListProductsOfCategory","LISTPRODUCTS",0);

        }
        Log.d("checkSize", String.valueOf(productList.size()));
        mDetailListProductMvpView.displayListProductsOfBrand(productList);
    }
    public List<Product> getListProductsLoadMore(int id, boolean isCheck, int offset, ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        List <Product> productList=new ArrayList<>();
        if (isCheck){
            productList=mDetailListResponseData.getListProductsOfCategory(id,"getListProductsOfBrand","LISTPRODUCTS",offset);

        }else {
            productList=mDetailListResponseData.getListProductsOfCategory(id,"getListProductsOfCategory","LISTPRODUCTS",offset);

        }
        if (productList.size()<=0){
            progressBar.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.VISIBLE);
        }
        return productList;
    }
}
