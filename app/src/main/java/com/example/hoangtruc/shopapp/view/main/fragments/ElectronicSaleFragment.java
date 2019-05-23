package com.example.hoangtruc.shopapp.view.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hoangtruc.shopapp.R;
import com.squareup.picasso.Picasso;

public class ElectronicSaleFragment extends Fragment {
       private ImageView mImageViewSale ;
       private static final String IMAGE_URL="image_url";
    public static Fragment newInstance(String imageUrl){
        ElectronicSaleFragment electronicSaleFragment=new ElectronicSaleFragment();
        Bundle bundle=new Bundle();
        bundle.putString(IMAGE_URL,imageUrl);
        electronicSaleFragment.setArguments(bundle);
        return electronicSaleFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.slide_sale,container,false);
       mImageViewSale=view.findViewById(R.id.imageViewSaleElectronic);
       if (getArguments().containsKey(IMAGE_URL)){
           String imageUrl=getArguments().getString(IMAGE_URL);
           Picasso.get()
                   .load(imageUrl)
                   .into(mImageViewSale);
       }
       return view;
    }
}
