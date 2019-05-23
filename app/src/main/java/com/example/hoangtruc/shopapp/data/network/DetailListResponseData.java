package com.example.hoangtruc.shopapp.data.network;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DetailListResponseData {
    public List<Product> getListProductsOfCategory(int brandID, String methodName, String arrayName, int offset) {
        List<Product> productList = new ArrayList<>();
        String url = Constant.SERVER_NAME;
        String dataJson="";
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsMethod = new HashMap<>();
        hsMethod.put("methodName", methodName);

        HashMap<String, String> hsBrandID = new HashMap<>();
        hsBrandID.put("id", String.valueOf(brandID));

        HashMap<String, String> hsLimit = new HashMap<>();
        hsLimit.put("offset", String.valueOf(offset));

        attrs.add(hsMethod);
        attrs.add(hsBrandID);
        attrs.add(hsLimit);

        LoadJson loadJson = new LoadJson(url, attrs);
        loadJson.execute();

        try {

            dataJson = loadJson.get();
            JSONObject jsonObject = new JSONObject(dataJson);
            JSONArray jsonArray = jsonObject.getJSONArray(arrayName);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                Product product = new Product();
                JSONObject object = jsonArray.getJSONObject(i);

                product.setID(object.getInt("MA"));
                product.setNameProduct(object.getString("NAME"));
                product.setPrice(object.getInt("GIA"));
                product.setImage(object.getString("HINH"));
                product.setThumbnail(object.getString("HINHNHO"));
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
}
