package com.example.hoangtruc.shopapp.data.network.electronic;

import android.util.Log;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.Brand;
import com.example.hoangtruc.shopapp.data.db.model.Electronic;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ElectronicResponsData {
    public List<Brand> getListBrand (String methodName,String nameJsonArray){
        List<Brand> brandList=new ArrayList<>();
        String url= Constant.SERVER_NAME;
        String dataJson="";
        List<HashMap<String,String>> attrs=new ArrayList<>();

        HashMap<String,String> hsMethodName= new HashMap<>();
        hsMethodName.put("methodName",methodName);
        attrs.add(hsMethodName);
        LoadJson loadJson=new LoadJson(url,attrs);
        loadJson.execute();

        try {
            dataJson=loadJson.get();
            JSONObject jsonObject=new JSONObject(dataJson);
            JSONArray jsonArray=jsonObject.getJSONArray(nameJsonArray);
            int count =jsonArray.length();
            for (int i=0;i<count;i++){
                Brand brand=new Brand();
                JSONObject object=jsonArray.getJSONObject(i);
                brand.setID(object.getInt("MA"));
                brand.setNameBrand(object.getString("TEN"));
                brand.setImage(object.getString("HINH"));

                brandList.add(brand);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return brandList;
    }
    public List<Product> getListProducts (String methodName,String nameJsonArray){
        List<Product> productList=new ArrayList<>();
        String url=Constant.SERVER_NAME;
        String dataJson="";

        List<HashMap<String,String>> attrs=new ArrayList<>();
        HashMap<String,String> hsMethodName=new HashMap<>();
        hsMethodName.put("methodName",methodName);
        attrs.add(hsMethodName);

        LoadJson loadJson=new LoadJson(url,attrs);
        loadJson.execute();

        try {
            dataJson=loadJson.get();
            JSONObject jsonObject=new JSONObject(dataJson);
            JSONArray jsonArray=jsonObject.getJSONArray(nameJsonArray);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                Product product=new Product();
                product.setID(object.getInt("MA"));
                product.setNameProduct(object.getString("TEN"));
                product.setPrice(object.getInt("GIATIEN"));
                product.setImage(object.getString("HINH"));
                productList.add(product);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productList;
    }
    public String getImageSale(){
        String imageList="";
        String url= Constant.SERVER_NAME;
        String dataJson="";
        List<HashMap<String,String>> hashMaps=new ArrayList<>();
        HashMap<String, String> hsMethod = new HashMap<>();
        hsMethod.put("methodName", "getImageSale");
        hashMaps.add(hsMethod);
        LoadJson loadJson=new LoadJson(url,hashMaps);
        loadJson.execute();


        try {
            dataJson=loadJson.get();
            JSONObject  object=new JSONObject(dataJson);
            JSONArray jsonArray=object.getJSONArray("ListImageSale");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object1=jsonArray.getJSONObject(i);
                imageList=object1.getString("ImageSale");
                Log.d("imageList",imageList);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  imageList;
    }
}
