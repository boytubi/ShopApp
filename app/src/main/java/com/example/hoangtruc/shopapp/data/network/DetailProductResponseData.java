package com.example.hoangtruc.shopapp.data.network;

import android.util.Log;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.ItemDescription;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DetailProductResponseData {
    public Product getDetailProduct(String methodName, String arrayName, int id) {
        Product product = new Product();
        List<ItemDescription> itemDescriptionList = new ArrayList<>();
        String url = Constant.SERVER_NAME;
        List<HashMap<String, String>> attr = new ArrayList<>();
        HashMap<String, String> hsMethod = new HashMap<>();
        hsMethod.put("methodName", methodName);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put("id", String.valueOf(id));

        attr.add(hsMethod);
        attr.add(hsIDProduct);

        LoadJson loadJson = new LoadJson(url, attr);
        loadJson.execute();

        try {
            String dataJson = loadJson.get();
            JSONObject jsonObject = new JSONObject(dataJson);
            JSONArray jsonArray = jsonObject.getJSONArray(arrayName);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                product.setID(object.getInt("MASP"));
                product.setNameProduct(object.getString("TENSP"));
                product.setPrice(object.getInt("GIATIEN"));
                product.setImage(object.getString("ANHLON"));
                product.setThumbnail(object.getString("ANHNHO"));
                product.setTotal(object.getInt("SOLUONG"));
                Log.d("total",product.getTotal()+"");
                product.setInformation(object.getString("THONGTIN"));
                product.setIDProductType(object.getInt("MALOAISP"));
                product.setIDBrand(object.getInt("MATHUONGHIEU"));
                product.setQuantityPurchased(object.getInt("LUOTMUA"));
                JSONArray arrayItemDescription = object.getJSONArray("THONGSOKYTHUAT");
                for (int j = 0; j < arrayItemDescription.length(); j++) {
                    ItemDescription itemDescription=new ItemDescription();
                    JSONObject objectItemDescription = arrayItemDescription.getJSONObject(j);
                    itemDescription.setItemName(objectItemDescription.getString("TENCHITIET"));
                    itemDescription.setItemValue(objectItemDescription.getString("GIATRI"));
                    itemDescriptionList.add(itemDescription);
                    Log.d("TENCHITIET",itemDescription.getItemName()+itemDescription.getItemValue());
                }
                product.setItemDescriptions(itemDescriptionList);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {


        }
        return product;
    }
}
