package com.example.hoangtruc.shopapp.view;

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

public class SliderProductFragment extends Fragment {
    private ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider_product, container, false);
        Bundle bundle = getArguments();
        String urlImage = bundle.getString("thumbnail");
        mImageView = view.findViewById(R.id.imageSliderProduct);
        Picasso.get()
                .load(urlImage)
                .into(mImageView);
        return view;
    }
}
