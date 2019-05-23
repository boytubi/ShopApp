package com.example.hoangtruc.shopapp.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.adapter.TopBrandAdapter;
import com.example.hoangtruc.shopapp.adapter.TopDeviceAdapter;
import com.example.hoangtruc.shopapp.data.db.model.Brand;
import com.example.hoangtruc.shopapp.data.db.model.Product;

import java.util.List;

public class MoreBrandsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mManager;
    private TopBrandAdapter mTopBrandAdapter;
    private TopDeviceAdapter mTopDeviceAdapter;
    private List<Brand> mBrandList;
    private List<Product> mProductList;
    private boolean isCheckTable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list_products);
        initializeViews();
        Bundle bundle=getIntent().getExtras();
        mBrandList=bundle.getParcelableArrayList("listBrands");
        mProductList=bundle.getParcelableArrayList("listProduct");
        isCheckTable=bundle.getBoolean("isCheckTable");
        if (isCheckTable) {
            displayListBrands(mBrandList);
        }else {
            displayListProducts(mProductList);
        }
    }
    private void initializeViews() {
        mRecyclerView = findViewById(R.id.recyclerViewListProductsOfCategory);

    }
    private void displayListBrands(List<Brand> brands){
        mTopBrandAdapter=new TopBrandAdapter(this,brands,false);
        mManager= new GridLayoutManager(getApplicationContext(),2, GridLayout.VERTICAL,false);
        mRecyclerView.setAdapter(mTopBrandAdapter);
        mRecyclerView.setLayoutManager(mManager);

    }
    private void displayListProducts(List<Product> products){
        mTopDeviceAdapter=new TopDeviceAdapter(this,products, R.layout.custom_item_top_devices);
        mManager= new GridLayoutManager(getApplicationContext(),2, GridLayout.VERTICAL,false);
        mRecyclerView.setAdapter(mTopDeviceAdapter);
        mRecyclerView.setLayoutManager(mManager);

    }



}
