package com.example.hoangtruc.shopapp.presenter.electronic;

import com.example.hoangtruc.shopapp.data.db.model.Brand;
import com.example.hoangtruc.shopapp.data.db.model.Electronic;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.network.electronic.ElectronicResponsData;
import com.example.hoangtruc.shopapp.view.main.ElectronicMvpView;

import java.util.ArrayList;
import java.util.List;

public class ElectronicPresenter implements ElectronicMvpPresenter {
    private ElectronicMvpView mElectronicMvpView;
    private ElectronicResponsData mElectronicResponsData;

    public ElectronicPresenter(ElectronicMvpView electronicMvpView) {
        mElectronicMvpView = electronicMvpView;
        mElectronicResponsData=new ElectronicResponsData();
    }

    @Override
    public void getListElectronic() {
        List<Electronic> electronicList=new ArrayList<>();

        Electronic electronic=new Electronic();
        List<Brand> brandList=mElectronicResponsData.getListBrand("getListThuongHieu","DANHSACHTHUONGHIEU");
        List<Product> productList=mElectronicResponsData.getListProducts("getListTopDevices","TOPDEVICES");
        String data=mElectronicResponsData.getImageSale();
        String[] imageSaleList=data.split(",");
        electronic.setImageSaleList(imageSaleList);
        electronic.setBrandList(brandList);
        electronic.setProductList(productList);
        electronic.setTitleTop("Top Brands");
        electronic.setTitleBot("Top Devices");
        electronic.setCheckTable(true);
        electronicList.add(electronic);

        Electronic electronicAccessories=new Electronic();
        List<Brand> accessoriesList=mElectronicResponsData.getListBrand("getListItem","LISTAccessories");
        List<Product> accessoriesTopList=mElectronicResponsData.getListProducts("getListTopItem","TOPAccessories");
        electronicAccessories.setBrandList(accessoriesList);
        electronicAccessories.setProductList(accessoriesTopList);
        electronicAccessories.setTitleTop("Accessories");
        electronicAccessories.setTitleBot("Top Accessories");
        electronicAccessories.setCheckTable(false);
        electronicList.add(electronicAccessories);


        Electronic electronicAudio=new Electronic();
        List<Brand> topList=mElectronicResponsData.getListBrand("getListTopTVAndAudio","TopAudio");
        List<Product> audioList=mElectronicResponsData.getListProducts("getListTVAndAudio","ListAudio");
        electronicAudio.setProductList(audioList);
        electronicAudio.setBrandList(topList);
        electronicAudio.setTitleTop("Top");
        electronicAudio.setTitleBot("TV ,Audio & Theater");
        electronicAudio.setCheckTable(false);
        electronicList.add(electronicAudio);

        mElectronicMvpView.displayElectronic(electronicList);
    }

    @Override
    public void getListImageSale() {


    }


}
