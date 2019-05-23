package com.example.hoangtruc.shopapp.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.adapter.TopDeviceAdapter;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.network.ILoadMore;
import com.example.hoangtruc.shopapp.data.network.LoadMoreScroll;
import com.example.hoangtruc.shopapp.presenter.main.detaillist.DetailPresenter;

import java.util.List;

public class DetailListProductActivity extends AppCompatActivity implements DetailListProductMvpView, View.OnClickListener,ILoadMore {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mManager;
    private TopDeviceAdapter mTopDeviceAdapter;
    private DetailPresenter mDetailPresenter;
    private int mId;
    private boolean isCheck;
    private boolean isCheckSwapped = true;
    private Button mButtonSwap;
    private List<Product> mProductList;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list_products);

        Intent iResult = this.getIntent();
        mId = iResult.getIntExtra("ID", 0);
        isCheck = iResult.getBooleanExtra("Check", false);
        initializeViews();

        mDetailPresenter = new DetailPresenter(this);
        mDetailPresenter.getListProductsOfBrand(mId, isCheck);
        mButtonSwap.setOnClickListener(this);
    }

    private void initializeViews() {
        mRecyclerView = findViewById(R.id.recyclerViewListProductsOfCategory);
        mButtonSwap = findViewById(R.id.buttonSwap);
        mProgressBar=findViewById(R.id.progress_bar_loadmore);
    }

    @Override
    public void displayListProductsOfBrand(List<Product> products) {
        mProductList = products;

        if (!isCheckSwapped) {
            mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mTopDeviceAdapter = new TopDeviceAdapter(this, mProductList, R.layout.custom_horizontal_item_list);

        }else {
            mManager = new GridLayoutManager(this, 2);
            mTopDeviceAdapter = new TopDeviceAdapter(this, mProductList, R.layout.custom_item_top_devices);

        }

        mRecyclerView.setAdapter(mTopDeviceAdapter);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addOnScrollListener(new LoadMoreScroll(mManager,this));
        mTopDeviceAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.buttonSwap:
                isCheckSwapped = !isCheckSwapped;
                mDetailPresenter.getListProductsOfBrand(mId, isCheck);
                break;

        }
    }

    @Override
    public void onLoadMore(int total) {
        List<Product>  productList=mDetailPresenter.getListProductsLoadMore(mId, isCheck,total,mProgressBar);
        mProductList.addAll(productList);
        mTopDeviceAdapter.notifyDataSetChanged();

    }
}
