package com.example.hoangtruc.shopapp.data.network.model;

import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.Person;
import com.example.hoangtruc.shopapp.network.LoadJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServerSignUp  {
    public  boolean registerMember(Person person){
        String url= Constant.SERVER_NAME;
        List<HashMap<String,String>> attrs=new ArrayList<>();
        boolean result=false;
        HashMap<String,String> hsMethodName=new HashMap<>();
        hsMethodName.put("methodName","register");

        HashMap<String,String> hsName=new HashMap<>();
        hsName.put("tennv",person.getName());

        HashMap<String,String> hsUsername=new HashMap<>();
        hsUsername.put("tendangnhap",person.getUsername());

        HashMap<String,String> hsPassword=new HashMap<>();
        hsPassword.put("matkhau",person.getPassword());

        HashMap<String,String> hsTypePerson=new HashMap<>();
        hsTypePerson.put("maloainv", String.valueOf(person.getType_Id()));
        attrs.add(hsMethodName);
        attrs.add(hsName);
        attrs.add(hsUsername);
        attrs.add(hsPassword);
        attrs.add(hsTypePerson);
        LoadJson loadJson=new LoadJson(url,attrs);
        loadJson.execute();

        try {
            String dataJson=loadJson.get();
            JSONObject jsonObject=new JSONObject(dataJson);
            String resultResponse=jsonObject.getString("ketqua");
            if (resultResponse.equalsIgnoreCase("true")){
                 result=true;
            }else {
                result=false;
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
