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

public class SearchResponseData {
   public List<Product> getListProductResult(String nameProduct,String nameMethod,String nameArray){
       List<Product> productList=new ArrayList<>();
       String dataJson="";
       List<HashMap<String,String>> hashMaps=new ArrayList<>();
       HashMap<String,String> hsNameMethod=new HashMap<>();
       hsNameMethod.put("methodName",nameMethod);
       HashMap<String,String> hsNameProduct=new HashMap<>();
       hsNameProduct.put("nameProduct",nameProduct);
       hashMaps.add(hsNameMethod);
       hashMaps.add(hsNameProduct);
       LoadJson loadJson=new LoadJson(Constant.SERVER_NAME,hashMaps);
       loadJson.execute();

       try {
           dataJson=loadJson.get();
           JSONObject jsonObject=new JSONObject(dataJson);
           JSONArray jsonArray=jsonObject.getJSONArray(nameArray);
           for(int i=0;i<jsonArray.length();i++){
               JSONObject object=jsonArray.getJSONObject(i);
               Product product=new Product();

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
      return  productList;
   }
}
