package com.example.hoangtruc.shopapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hoangtruc.shopapp.view.main.fragments.BeautyFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.BrandFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.ElectronicFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.FashionFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.HigtlightFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.HomeandLifeFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.MotherandKidFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.SaleEventFragment;
import com.example.hoangtruc.shopapp.view.main.fragments.SportsandEnterainment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList=new ArrayList<>();
    private final List<String> mTitle=new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        mFragmentList.add(new ElectronicFragment());

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
