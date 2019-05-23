package com.example.hoangtruc.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoangtruc.shopapp.OnItemCLickListener;
import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.Brand;
import com.example.hoangtruc.shopapp.data.db.model.Electronic;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.view.main.EqualSpacingItemDecoration;
import com.example.hoangtruc.shopapp.view.main.MoreBrandsActivity;
import com.example.hoangtruc.shopapp.view.main.SpanningGridLayoutManager;
import com.example.hoangtruc.shopapp.view.main.fragments.ElectronicSaleFragment;
import com.itsronald.widget.ViewPagerIndicator;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ElectronicAdapter extends RecyclerView.Adapter<ElectronicAdapter.ViewHolderElectronic> {
    private Context mContext;
    private List<Electronic> mElectronicList;
    private int currentPage = 0;

    public ElectronicAdapter(Context context, List<Electronic> electronicList) {
        mContext = context;
        mElectronicList = electronicList;
    }

    @NonNull
    @Override
    public ElectronicAdapter.ViewHolderElectronic onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_electronic, parent, false);
        ViewHolderElectronic viewHolderElectronic = new ViewHolderElectronic(view);
        return viewHolderElectronic;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderElectronic holder, int position) {
        final Electronic electronic = mElectronicList.get(position);
        holder.mTextTitleTop.setText(electronic.getTitleTop());
        holder.mTextTitleBot.setText(electronic.getTitleBot());
        //Set Image
        final Context context=holder.mView.getContext();
        if (position == 0) {
            //SET VIEW PAGER FOR SLIDE SALE IMAGE
            String[] imageList = electronic.getImageSaleList();
            List<Fragment> fragmentList = new ArrayList<>();
            for (int i = 0; i < imageList.length; i++) {
                ElectronicSaleFragment electronicSaleFragment =
                        (ElectronicSaleFragment) ElectronicSaleFragment.newInstance(Constant.SERVER + imageList[i]);
                fragmentList.add(electronicSaleFragment);
            }
            ViewPagerSaleAdapter viewPagerSaleAdapter = new ViewPagerSaleAdapter(((AppCompatActivity) mContext).getSupportFragmentManager(), fragmentList);

            holder.mViewPagerSale.setAdapter(viewPagerSaleAdapter);
            holder.mViewPagerSale.setPageTransformer(true, new DepthTransformation());
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (currentPage == electronic.getImageSaleList().length) {
                        currentPage = 0;
                    }
                    holder.mViewPagerSale.setCurrentItem(currentPage++, true);
                }
            };
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, 3000, 3000);
            holder.mViewPagerIndicator.setViewPager(holder.mViewPagerSale);
            viewPagerSaleAdapter.notifyDataSetChanged();
        } else {
            holder.mFrameLayout.setVisibility(View.GONE);

        }

        //Top Brands
        TopBrandAdapter topBrandAdapter = new TopBrandAdapter(mContext, electronic.getBrandList(), electronic.isCheckTable());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3, GridLayout.HORIZONTAL, false);
        holder.mRecyclerViewTopBrand.setLayoutManager(layoutManager);
        holder.mRecyclerViewTopBrand.setAdapter(topBrandAdapter);
        topBrandAdapter.notifyDataSetChanged();

        //Top Devices
        TopDeviceAdapter topDeviceAdapter = new TopDeviceAdapter(mContext, electronic.getProductList(), R.layout.custom_item_top_devices);
        RecyclerView.LayoutManager managerTopDevice = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.mRecyclerViewTopDevices.setLayoutManager(managerTopDevice);
        holder.mRecyclerViewTopDevices.setAdapter(topDeviceAdapter);

       // Button More
        final List<Brand> brandList=electronic.getBrandList();
        holder.mButtonMoreBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMoreBrands=new Intent(context , MoreBrandsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("listBrands", (ArrayList<? extends Parcelable>) brandList);
                // if true is Brand
                bundle.putBoolean("isCheckTable",true);
                iMoreBrands.putExtras(bundle);
                context.startActivity(iMoreBrands);
            }
        });
        final List<Product> productList=electronic.getProductList();
        holder.mButtonMoreDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMoreProduct=new Intent(context , MoreBrandsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("listProduct", (ArrayList<? extends Parcelable>) productList);
                // if true is Brand
                bundle.putBoolean("isCheckTable",false);
                iMoreProduct.putExtras(bundle);
                context.startActivity(iMoreProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mElectronicList.size();
    }

    public class ViewHolderElectronic extends RecyclerView.ViewHolder {
        private ViewPager mViewPagerSale;
        private InkPageIndicator mViewPagerIndicator;
        RecyclerView mRecyclerViewTopBrand, mRecyclerViewTopDevices;
        TextView mTextTitleTop, mTextTitleBot;
        FrameLayout mFrameLayout;
        Button mButtonMoreBrands, mButtonMoreDevices;
        OnItemCLickListener mOnItemCLickListener;
        View mView;

        public ViewHolderElectronic(View itemView) {
            super(itemView);
            mView=itemView;
            mViewPagerSale = itemView.findViewById(R.id.viewPagerSale);
            mViewPagerIndicator = itemView.findViewById(R.id.indicator);
            mFrameLayout = itemView.findViewById(R.id.frameViewPager);
            mRecyclerViewTopBrand = itemView.findViewById(R.id.recyclerViewTopBrand);
            mRecyclerViewTopDevices = itemView.findViewById(R.id.recyclerViewTopDevice);
            mTextTitleTop = itemView.findViewById(R.id.textTitleTop);
            mTextTitleBot = itemView.findViewById(R.id.textTitleBot);
            mButtonMoreBrands = itemView.findViewById(R.id.buttonMoreTopBrands);
            mButtonMoreDevices = itemView.findViewById(R.id.buttonMoreTopDevices);

        }

    }
}
