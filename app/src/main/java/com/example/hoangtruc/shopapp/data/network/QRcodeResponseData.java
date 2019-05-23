package com.example.hoangtruc.shopapp.data.network;

import android.util.Log;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class QRcodeResponseData {
    public int getIDProductByQR(String QRCode){
        String url= Constant.SERVER_NAME;
        int idProduct = 0;
        String dataJson="";
        List<HashMap<String,String>> hashMaps=new ArrayList<>();
        HashMap<String,String> hsMethodName=new HashMap<>();
        hsMethodName.put("methodName","getProductByQRcode");
        HashMap<String,String> hsQRcode=new HashMap<>();
        hsQRcode.put("qrcode",QRCode);
        hashMaps.add(hsMethodName);
        hashMaps.add(hsQRcode);
        LoadJson loadJson=new LoadJson(url,hashMaps);
        loadJson.execute();

        try {
            dataJson=loadJson.get();
            JSONObject jsonObject=new JSONObject(dataJson);
            JSONArray jsonArray=jsonObject.getJSONArray("ResultScan");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                idProduct=object.getInt("idProduct");
                Log.d("id product: " ,idProduct+"");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return idProduct;
    }
}
