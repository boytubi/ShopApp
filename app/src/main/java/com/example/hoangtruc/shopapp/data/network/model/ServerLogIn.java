package com.example.hoangtruc.shopapp.data.network.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServerLogIn  {
    public String getCurrentUser(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("username",Context.MODE_PRIVATE);
        String name= sharedPreferences.getString("name","");
        return name;
    }
    public void saveCurrentUser(Context context, String name){
        SharedPreferences sharedPreferences=context.getSharedPreferences("username",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("name",name);
        editor.commit();
    }
    public  boolean checkLogIn(Context context,String username, String password){
         String name="";
         boolean check=false;
         String url= Constant.SERVER_NAME;
        List<HashMap<String,String>> attrs=new ArrayList<>();

        HashMap<String,String> hsMethod=new HashMap<>();
        hsMethod.put("methodName","login");
        HashMap<String,String> hsUsername=new HashMap<>();
        hsUsername.put("tendangnhap",username);
        HashMap<String,String> hsPassword=new HashMap<>();
        hsPassword.put("matkhau",password);

        attrs.add(hsMethod);
        attrs.add(hsUsername);
        attrs.add(hsPassword);
        LoadJson loadJson=new LoadJson(url,attrs);
        loadJson.execute();

        try {
            String dataJson=loadJson.get();
            JSONObject object=new JSONObject(dataJson);
            String result=object.getString("ketqua");
            if (result.equals("true")){
                name=object.getString("tennv");
                saveCurrentUser(context,name);
                check=true;
            }else {
                check=false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return check;
    }
}
