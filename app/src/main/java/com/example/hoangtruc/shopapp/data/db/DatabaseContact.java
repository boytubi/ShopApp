package com.example.hoangtruc.shopapp.data.db;

import android.provider.BaseColumns;

public class DatabaseContact  {
    public static class CartEntry implements BaseColumns{
        public static final  String TABLE_NAME="Cart";
        public static final  String COLUMN_ID_PRODUCT="id";
        public static final  String COLUMN_NAME_PRODUCT="NAME_PRODUCT";
        public static final  String COLUMN_PRICE = "PRICE";
        public static final  String COLUMN_IMAGE ="IMAGE";
        public static final  String COLUMN_QUANTITY="QUANTITY";
        public static final  String COLUMN_INVENTORY="INVENTORY";
        public static final  String COLUMN_TOTAL_PRICE="TOTAL_PRICE";
    }
}
