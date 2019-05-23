package com.example.hoangtruc.shopapp.view.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.adapter.ElectronicAdapter;

import com.example.hoangtruc.shopapp.data.db.model.Brand;
import com.example.hoangtruc.shopapp.data.db.model.Electronic;
import com.example.hoangtruc.shopapp.presenter.electronic.ElectronicPresenter;
import com.example.hoangtruc.shopapp.view.main.ElectronicMvpView;
import com.itsronald.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class ElectronicFragment extends Fragment implements ElectronicMvpView{
    private RecyclerView mRecyclerView;
    private List<Electronic> mElectronicList;
    private ElectronicPresenter mElectronicPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_electronic,container,false);
        mRecyclerView=view.findViewById(R.id.recyclerViewElectronic);

        mElectronicList=new ArrayList<>();
        mElectronicPresenter=new ElectronicPresenter(this);
        mElectronicPresenter.getListElectronic();

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    //Display Electronic
    @Override
    public void displayElectronic(List<Electronic> electronicList) {
        mElectronicList=electronicList;
        ElectronicAdapter  electronicAdapter=new ElectronicAdapter(getContext(),mElectronicList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(electronicAdapter);
        electronicAdapter.notifyDataSetChanged();
    }
}
