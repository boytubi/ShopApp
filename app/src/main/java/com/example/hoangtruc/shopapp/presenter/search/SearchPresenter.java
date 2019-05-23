package com.example.hoangtruc.shopapp.presenter.search;

import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.network.SearchResponseData;
import com.example.hoangtruc.shopapp.view.search.SearchMvpView;

import java.util.List;

public class SearchPresenter implements SearchMvpPresenter{
    private List<Product> mProductList;
    private SearchMvpView mSearchMvpView;
    private SearchResponseData mSearchResponseData;

    public SearchPresenter(SearchMvpView searchMvpView) {
        mSearchMvpView = searchMvpView;
        mSearchResponseData = new SearchResponseData();
    }

    @Override
    public void getListResult(String nameProduct) {
        mProductList=mSearchResponseData.getListProductResult(nameProduct,"getSearchList","ResultSearch");
        mSearchMvpView.displayResult(mProductList);
    }
}
