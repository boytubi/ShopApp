package com.example.hoangtruc.shopapp.data.network.order;

import android.util.Log;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.DetailOrder;
import com.example.hoangtruc.shopapp.data.db.model.Order;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrderRequestData {
    public boolean requestOrder(Order order) {
        String url = Constant.SERVER_NAME;
        boolean result = false;
        List<HashMap<String, String>> hs = new ArrayList<>();
        HashMap<String, String> hsMethod = new HashMap<>();
        hsMethod.put("methodName", "doOrder");
        HashMap<String, String> hsListProduct = new HashMap<>();
        hsListProduct.put("danhsachsanpham",OrderJsonUtil.toJson(order));
        Log.d("danhsachsanpham",OrderJsonUtil.toJson(order));
        HashMap<String, String> hsNameCustomer = new HashMap<>();
        hsNameCustomer.put("tennguoinhan", order.getNameCustomer());
        HashMap<String, String> hsPhoneNumber = new HashMap<>();
        hsPhoneNumber.put("sdt", order.getPhoneNumber());
        HashMap<String, String> hsAddress = new HashMap<>();
        hsAddress.put("diachi", order.getAddress());
        HashMap<String, String> hsShipment = new HashMap<>();
        hsShipment.put("ship", String.valueOf(order.getShipment()));
        hs.add(hsMethod);
        hs.add(hsListProduct);
        hs.add(hsNameCustomer);
        hs.add(hsPhoneNumber);
        hs.add(hsAddress);
        hs.add(hsShipment);

        LoadJson loadJson = new LoadJson(url, hs);
        loadJson.execute();
        try {
            String dataJson = loadJson.get();
            Log.d("datajson",dataJson);
            JSONObject object = new JSONObject(dataJson);
            String strResult = object.getString("ketqua");
            if (strResult.equalsIgnoreCase("true")) {
                result = true;
            } else {
                result = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }
}
