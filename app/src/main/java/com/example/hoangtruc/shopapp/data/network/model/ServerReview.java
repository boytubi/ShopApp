package com.example.hoangtruc.shopapp.data.network.model;

import android.util.Log;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.Review;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServerReview {
    public List<Review> getListFeedback(int idProduct, String methodName, String arrayName, int offset){
        List<Review> reviewList=new ArrayList<>();
        String url=Constant.SERVER_NAME;
        List<HashMap<String,String>> attr=new ArrayList<>();
        HashMap<String,String> hsMethodName=new HashMap<>();
        hsMethodName.put("methodName",methodName);

        HashMap<String,String> hsIDProduct=new HashMap<>();
        hsIDProduct.put("idProduct", String.valueOf(idProduct));

        HashMap<String,String> hsOffset=new HashMap<>();
        hsOffset.put("offset", String.valueOf(offset));

        attr.add(hsMethodName);
        attr.add(hsIDProduct);
        attr.add(hsOffset);

        LoadJson loadJson=new LoadJson(url,attr);
        loadJson.execute();

        try {
            String dataJson=loadJson.get();
            JSONObject jsonObject=new JSONObject(dataJson);
            JSONArray jsonArray=jsonObject.getJSONArray(arrayName);
            for (int i=0;i<jsonArray.length();i++){
                Review review=new Review();
                JSONObject object=jsonArray.getJSONObject(i);
                review.setIdFeedBack(object.getString("IDFeedback"));
                review.setIdProduct(object.getInt("IDProduct"));
                review.setNameDevice(object.getString("NameDevice"));
                review.setDate(object.getString("Date"));
                review.setTitle(object.getString("Title"));
                review.setReview(object.getString("Review"));
                review.setRating(object.getInt("Rating"));
                reviewList.add(review);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewList;
    }
    public boolean resultAddReview(Review review) {
        String url = Constant.SERVER_NAME;
        boolean isCheck = false;
        List<HashMap<String, String>> attr = new ArrayList<>();

        HashMap<String,String> hsMethodName=new HashMap<>();
        hsMethodName.put("methodName","addFeedBack");

//        HashMap<String,String> hsIdFeedback=new HashMap<>();
//        hsIdFeedback.put("idFeedBack",review.getIdFeedBack());

        HashMap<String,String> hsidProduct=new HashMap<>();
        hsidProduct.put("idProduct", String.valueOf(review.getIdProduct()));

        HashMap<String,String> hsnameProduct=new HashMap<>();
        hsnameProduct.put("nameProduct",review.getNameDevice());

        HashMap<String,String> hstitle=new HashMap<>();
        hstitle.put("title",review.getTitle());

        HashMap<String,String> hsreview=new HashMap<>();
        hsreview.put("review",review.getReview());

        HashMap<String,String> hsrating=new HashMap<>();
        hsrating.put("rating", String.valueOf(review.getRating()));

        attr.add(hsMethodName);
//        attr.add(hsIdFeedback);
        attr.add(hsidProduct);
        attr.add(hsnameProduct);
        attr.add(hstitle);
        attr.add(hsreview);
        attr.add(hsrating);
        LoadJson loadJson=new LoadJson(url,attr);
        loadJson.execute();
        try {
            String dataJson=loadJson.get();
            Log.d("review ",dataJson);
            JSONObject jsonObject=new JSONObject(dataJson);
            String result=jsonObject.getString("ketqua");
            if (result.equalsIgnoreCase("true")){
                isCheck=true;
            }else {
                isCheck=false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isCheck;
    }
}
