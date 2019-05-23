package com.example.hoangtruc.shopapp.presenter.main.menu;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.DatabaseHelper;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.db.model.ProductType;
import com.example.hoangtruc.shopapp.data.network.model.FacebookLogin;
import com.example.hoangtruc.shopapp.data.network.model.ProductTypeTask;
import com.example.hoangtruc.shopapp.network.LoadJson;
import com.example.hoangtruc.shopapp.view.main.MenuMvpView;
import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuPresenter implements MenuMvpPresenter {
    MenuMvpView mMenuView;
    private DatabaseHelper mDatabaseHelper;
    public MenuPresenter(MenuMvpView mMenuView) {
        this.mMenuView = mMenuView;
    }

    @Override
    public void getListMenu() {

        String dataJSON;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        List<ProductType> productTypeList = new ArrayList<>();
        // GET
//        LoadJson downloadJSON=new LoadJson(url_localhost);

        //POST
        HashMap<String,String> hsHam = new HashMap<>();
        hsHam.put("ham","getListMenu");

        HashMap<String, String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha", "0");
        attrs.add(hsHam);
        attrs.add(hsMaLoaiCha);

        LoadJson downloadJSON = new LoadJson(Constant.SERVER_NAME, attrs);
        downloadJSON.execute();

        try {
            dataJSON = downloadJSON.get();
            ProductTypeTask productTypeTask = new ProductTypeTask();

            if (dataJSON != null) {
                productTypeList = productTypeTask.parseJSONProductType(dataJSON);
                mMenuView.displayListMenu(productTypeList);
            } else {
                Log.d("Error", "ERROR JSON : " + dataJSON);
            }
            Log.d("test", "Size of array : " + productTypeList.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccessToken getAccessTokenFacebook() {
        FacebookLogin facebookLogin = new FacebookLogin();
        AccessToken accessToken = facebookLogin.getAccessToken();
        return accessToken;
    }


}
