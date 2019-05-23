package com.example.hoangtruc.shopapp.data.network.order;

import com.example.hoangtruc.shopapp.data.db.model.DetailOrder;
import com.example.hoangtruc.shopapp.data.db.model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OrderJsonUtil {
    public static String toJson(Order order  ){
        try {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        List<DetailOrder> detailOrders=order.getDetailOrderList();
        for(int i=0;i<detailOrders.size();i++){
            JSONObject object=new JSONObject();
            object.put("idProduct",detailOrders.get(i).getIdProduct());
            object.put("quantity",detailOrders.get(i).getQuantity());
            jsonArray.put(object);
          }
          jsonObject.put("ListProduct",jsonArray);
           return jsonObject.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
