package com.example.hoangtruc.shopapp.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constant {

//    public static final String SERVER_NAME="https://lazadatk.000webhostapp.com/server.php";
//    public static final String SERVER ="https://lazadatk.000webhostapp.com/";
//    public static final String SERVER_NAME="http://192.168.100.2/lazada/server.php";
//    public static final String SERVER ="http://192.168.100.2/lazada/";
    public static final String SERVER_NAME="https://pudor.serveo.net/lazada/server.php";
    public static final String SERVER ="https://pudor.serveo.net/lazada/";

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
