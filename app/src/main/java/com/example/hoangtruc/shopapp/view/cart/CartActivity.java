package com.example.hoangtruc.shopapp.view.cart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.adapter.CartAdapter;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.presenter.cart.CartMvpPresenter;
import com.example.hoangtruc.shopapp.presenter.cart.CartPresenter;
import com.example.hoangtruc.shopapp.view.order.OrderActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartMvpView,View.OnClickListener {
    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;

    private CartPresenter mCartPresenter;
    private Button btnCheckPromo;
    private EditText edCheckPromo;
    private LinearLayout mLinearLayout3;
    private Button mBtnPayNow;
    private TextView mTitleTotalPrice, mTitleDiscount;
    private Typeface mLight, mRegular;
    private TextView mTotalPrice, mDiscount;
    private NumberFormat mNumberFormat;
    private int mTong=0,mTotalItems=0;
    private boolean flagDiscount=false;
    private  double tongDiscount;
    private TextView mTextViewTotalItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cart);
        mCartPresenter = new CartPresenter(this, getApplicationContext());
        mLight = Typeface.createFromAsset(getAssets(), "fonts/MLight.ttf");
        mRegular = Typeface.createFromAsset(getAssets(), "fonts/MRegular.ttf");
        mNumberFormat=new DecimalFormat("###,###");
        initializeView();

        mCartPresenter.getListProductsOfCart();
        manipulateViews();

        mTotalPrice.setText(mNumberFormat.format(mTong)+" VND");
        btnCheckPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheckPromo();
            }
        });
    }

    private void initializeView() {
        mRecyclerView = findViewById(R.id.recyclerViewCart2);
        btnCheckPromo = findViewById(R.id.buttonCheckPromo);
        edCheckPromo = findViewById(R.id.edCheckCode);
        mLinearLayout3 = findViewById(R.id.linearLayout3);
        mBtnPayNow = findViewById(R.id.btnPayNow);
        mTitleTotalPrice = findViewById(R.id.titleTotalPrice);
        mTitleDiscount = findViewById(R.id.titleDiscount);
        mTotalPrice=findViewById(R.id.TextViewTotalPrice);
        mDiscount=findViewById(R.id.textViewDiscount);
        mTextViewTotalItem=findViewById(R.id.textViewTotalItem);
    }

    private void manipulateViews() {
        mTitleTotalPrice.setTypeface(mLight);
        mTitleDiscount.setTypeface(mLight);
        mTotalPrice.setTypeface(mRegular);
        mDiscount.setTypeface(mRegular);
        mBtnPayNow.setTypeface(mLight);
        btnCheckPromo.setTypeface(mLight);
        mDiscount.setText("0%");
        mBtnPayNow.setOnClickListener(this);
        mTextViewTotalItem.setText(String.valueOf(mTotalItems)+" Items");
    }

    private void doCheckPromo() {
        if (edCheckPromo.getText().toString().equals("TRUC")) {
            mLinearLayout3.animate()
                    .alpha(0)
                    .translationY(-200).setDuration(800).setStartDelay(100).start();
            btnCheckPromo.animate()
                    .alpha(0)
                    .translationY(-200).setDuration(800).setStartDelay(100).start();

            mDiscount.setText("20%");
            mDiscount.setTextColor(Color.parseColor("#70BDC6"));
            mBtnPayNow.animate().translationY(-100).setDuration(800).setStartDelay(300).start();

            tongDiscount=mTong-mTong*0.2;
            mTotalPrice.setText(mNumberFormat.format(tongDiscount)+" VND");
            flagDiscount=true;

        } else {

            Toast.makeText(getApplicationContext(), "Coupon is Wrong", Toast.LENGTH_SHORT).show();
            flagDiscount=false;
        }

    }

    @Override
    public void displayProductsOfCart(List<Product> products) {

        mCartAdapter = new CartAdapter(products, this,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL
                , false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mCartAdapter);
        mCartAdapter.notifyDataSetChanged();


    }

    @Override
    public void displayTotalPrice( int tong,int totalItems) {
        mTong=tong;
        mTotalItems=totalItems;
    }

    @Override
    public void setTotalChange(int newValue) {
        if (flagDiscount){
            double priceDiscount=newValue-newValue*0.2;
             tongDiscount=tongDiscount+priceDiscount;
            Log.d("tongDiscount",String.valueOf(priceDiscount));
            mTotalPrice.setText(mNumberFormat.format(tongDiscount)+" VND");
        }else {

            mTong=mTong+newValue;
            Log.d("Tong : ",String.valueOf(mTong));
            Log.d("tong ",String.valueOf(mTong));
            mTotalPrice.setText(mNumberFormat.format(mTong)+" VND");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPayNow:
                Intent iOrder=new Intent(CartActivity.this, OrderActivity.class);
                startActivity(iOrder);
                break;
        }
    }
}
