package com.example.hoangtruc.shopapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hoangtruc.shopapp.data.db.model.Product;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shop.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_CART_TABLE = "CREATE TABLE " + DatabaseContact.CartEntry.TABLE_NAME + " (" +
                DatabaseContact.CartEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContact.CartEntry.COLUMN_ID_PRODUCT + " INTEGER, " +
                DatabaseContact.CartEntry.COLUMN_NAME_PRODUCT + " TEXT NOT NULL, " +
                DatabaseContact.CartEntry.COLUMN_PRICE + " INTEGER, " +
                DatabaseContact.CartEntry.COLUMN_IMAGE + " BLOB, " +
                DatabaseContact.CartEntry.COLUMN_QUANTITY + " INTEGER, " +
                DatabaseContact.CartEntry.COLUMN_INVENTORY + " INTEGER, "+
                DatabaseContact.CartEntry.COLUMN_TOTAL_PRICE +" INTEGER "+"); ";
        sqLiteDatabase.execSQL(SQL_CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContact.CartEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addCart(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContact.CartEntry.COLUMN_ID_PRODUCT, product.getID());
        contentValues.put(DatabaseContact.CartEntry.COLUMN_NAME_PRODUCT, product.getNameProduct());
        contentValues.put(DatabaseContact.CartEntry.COLUMN_PRICE, product.getPrice());
        contentValues.put(DatabaseContact.CartEntry.COLUMN_IMAGE, product.getImageCart());
        contentValues.put(DatabaseContact.CartEntry.COLUMN_QUANTITY, product.getQuantityPurchased());
        contentValues.put(DatabaseContact.CartEntry.COLUMN_INVENTORY, product.getTotal());
        contentValues.put(DatabaseContact.CartEntry.COLUMN_TOTAL_PRICE, product.getPrice());
        db.insert(DatabaseContact.CartEntry.TABLE_NAME, null, contentValues);

        db.close();
    }
    public void deleteProduct(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete(DatabaseContact.CartEntry.TABLE_NAME,DatabaseContact.CartEntry.COLUMN_ID_PRODUCT+ "=" +id,null);
    }

    public void updateQuantity(int id,int quantity,int price){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseContact.CartEntry.COLUMN_QUANTITY,quantity);
        contentValues.put(DatabaseContact.CartEntry.COLUMN_TOTAL_PRICE,price);
        db.update(DatabaseContact.CartEntry.TABLE_NAME
                ,contentValues
                ,DatabaseContact.CartEntry.COLUMN_ID_PRODUCT+" = "+id
                ,null);

    }
    public Product getProduct(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Product product=null;
        String[] columns = {
                DatabaseContact.CartEntry.COLUMN_ID_PRODUCT,
                DatabaseContact.CartEntry.COLUMN_NAME_PRODUCT,
                DatabaseContact.CartEntry.COLUMN_PRICE,
                DatabaseContact.CartEntry.COLUMN_IMAGE,
                DatabaseContact.CartEntry.COLUMN_QUANTITY,
                DatabaseContact.CartEntry.COLUMN_INVENTORY,
                DatabaseContact.CartEntry.COLUMN_TOTAL_PRICE
        };

        Cursor cursor=db.query(DatabaseContact.CartEntry.TABLE_NAME,
                columns,
                DatabaseContact.CartEntry.COLUMN_ID_PRODUCT+" = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            byte[] image= cursor.getBlob(3);
            product=new Product(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1)
                    ,cursor.getInt(2)
                     ,image
                      ,cursor.getInt(4)
                     ,cursor.getInt(5)
                      ,cursor.getInt(6));
        }
        cursor.close();
//        db.close();
        return product;

    }
    public List<Product> getAllProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> productList = new ArrayList<>();
        String sortOrder = DatabaseContact.CartEntry._ID + " ASC ";
        String[] columns = {
                DatabaseContact.CartEntry._ID,
                DatabaseContact.CartEntry.COLUMN_ID_PRODUCT,
                DatabaseContact.CartEntry.COLUMN_NAME_PRODUCT,
                DatabaseContact.CartEntry.COLUMN_PRICE,
                DatabaseContact.CartEntry.COLUMN_IMAGE,
                DatabaseContact.CartEntry.COLUMN_QUANTITY,
                DatabaseContact.CartEntry.COLUMN_INVENTORY,
                DatabaseContact.CartEntry.COLUMN_TOTAL_PRICE
        };
        Cursor cursor=db.query(DatabaseContact.CartEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()){
            do {
                Product product=new Product();
                product.setID((int) Long.parseLong(cursor.getString(cursor.getColumnIndex(DatabaseContact.CartEntry.COLUMN_ID_PRODUCT))));
                product.setNameProduct(cursor.getString(cursor.getColumnIndex(DatabaseContact.CartEntry.COLUMN_NAME_PRODUCT)));
                product.setPrice(cursor.getInt(cursor.getColumnIndex(DatabaseContact.CartEntry.COLUMN_PRICE)));
                product.setImageCart(cursor.getBlob(cursor.getColumnIndex(DatabaseContact.CartEntry.COLUMN_IMAGE)));
                product.setQuantityPurchased(cursor.getInt(cursor.getColumnIndex(DatabaseContact.CartEntry.COLUMN_QUANTITY)));
                product.setTotal(cursor.getInt(cursor.getColumnIndex(DatabaseContact.CartEntry.COLUMN_INVENTORY)));
                product.setTotalPrice(cursor.getInt(cursor.getColumnIndex(DatabaseContact.CartEntry.COLUMN_TOTAL_PRICE)));
                productList.add(product);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
    public void deleteAllProducts(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete(DatabaseContact.CartEntry.TABLE_NAME,null,null);
    }
}
