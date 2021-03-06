package com.example.hoangtruc.shopapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerSaleAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> mFragmentList;
    public ViewPagerSaleAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        mFragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
