package com.example.hoangtruc.shopapp.data.network.model;

import android.util.Log;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.ProductType;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductTypeTask {
    public List<ProductType> parseJSONProductType(String data) {
        List<ProductType> productTypeList = new ArrayList<ProductType>();
        try {
            Log.d("Data Json", data);
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonProductType = jsonObject.getJSONArray("LOAISANPHAM");
            int count = jsonProductType.length();
            for (int i = 0; i < count; i++) {
                JSONObject object = jsonProductType.getJSONObject(i);

                ProductType productType = new ProductType();
                productType.setProductTypeId(Integer.parseInt(object.getString("MALOAISP")));
                productType.setProductTypeIdParent(Integer.parseInt(object.getString("MALOAI_CHA")));
                productType.setNameType(object.getString("TENLOAISP"));


                productTypeList.add(productType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productTypeList;

    }

    public List<ProductType> getListProductType(int productTypeID) {
        List<ProductType> productTypeList = new ArrayList<>();
        String dataJson="";
        String url_local = Constant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hmProductTypeIDParent = new HashMap<>();
        hmProductTypeIDParent.put("maloaicha", String.valueOf(productTypeID));
        attrs.add(hmProductTypeIDParent);

        LoadJson downloadJSON=new LoadJson(url_local,attrs);
        downloadJSON.execute();

        try {
            dataJson=downloadJSON.get();
            ProductTypeTask productTypeTask=new ProductTypeTask();
            productTypeList=productTypeTask.parseJSONProductType(dataJson);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return productTypeList;
    }
}
