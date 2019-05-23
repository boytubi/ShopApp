package com.example.hoangtruc.shopapp.presenter.detailproduct;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hoangtruc.shopapp.data.db.DatabaseHelper;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.db.model.Review;
import com.example.hoangtruc.shopapp.data.network.DetailProductResponseData;
import com.example.hoangtruc.shopapp.data.network.model.ServerReview;
import com.example.hoangtruc.shopapp.view.DetailProductMvpView;
import com.example.hoangtruc.shopapp.view.main.MenuMvpView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DetailProductPresenter implements DetailProductMvpPresenter {
    private DetailProductMvpView mDetailProductMvpView;
    private DetailProductResponseData mDetailProductResponseData;
    private String [] listThumbnail;
    private ServerReview mServerReview;
    private DatabaseHelper mDatabaseHelper;
    private Context  mContext;
    private MenuMvpView mMenuMvpView;
    public DetailProductPresenter(DetailProductMvpView detailProductMvpView,Context context    ) {
        mDetailProductMvpView = detailProductMvpView;

        mDetailProductResponseData=new DetailProductResponseData();
        mServerReview =new ServerReview();
        mContext=context;
    }

    public DetailProductPresenter( Context context) {
        mContext=context;
    }

    @Override
    public void detailProduct(int idProduct) {
        Product product ;
        product=mDetailProductResponseData.getDetailProduct("getProductDetail","PRODUCT_DETAIL",idProduct);
        if (product!=null){
            listThumbnail=product.getThumbnail().split(",");
            mDetailProductMvpView.displayDetailProduct(product);
            mDetailProductMvpView.displaySliderProduct(listThumbnail);
        }else {

        }
    }

    @Override
    public void listFeedback(int idProduct,int offset) {
        List<Review> reviewList;
        reviewList=mServerReview.getListFeedback(idProduct,"getListFeedback","LISTFEEDBACK",offset);
        mDetailProductMvpView.displayListFeedback(reviewList);

    }
//

    @Override
    public void addCart(final Product product  ) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                if (!checkCart(product.getID() )) {
//                    byte[] imageCart=;
                    product.setImageCart(getByteArrayImage(product.getImage()));
                    Log.d("ImageCart",product.getQuantityPurchased()+"");
                    mDatabaseHelper = new DatabaseHelper(mContext);
                    mDatabaseHelper.addCart(product);

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mDetailProductMvpView.updateCartCount();

                mDetailProductMvpView.updateButtonCart(product);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }


    @Override
    public void removeCart(final Product product  ) {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                if(checkCart(product.getID() )) {
                    mDatabaseHelper = new DatabaseHelper(mContext);
                    mDatabaseHelper.deleteProduct(product.getID());

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mDetailProductMvpView.updateCartCount();

                mDetailProductMvpView.updateButtonCart(product);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public boolean checkCart(int id ) {
        mDatabaseHelper =new DatabaseHelper(mContext);

        if (mDatabaseHelper.getProduct(id)!=null){

            return true;
        }else {
            return false;
        }
    }

    @Override
    public int getCartCount(Context context ) {
        mDatabaseHelper =new DatabaseHelper(context);
        List<Product>  productList=mDatabaseHelper.getAllProduct();
        return productList.size();
    }


    private byte[] getByteArrayImage(String url){
        try {

            URL imageUrl=new URL(url);
            URLConnection connection=imageUrl.openConnection();

            InputStream inputStream=connection.getInputStream();
            BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);

            ByteArrayOutputStream buffer=new ByteArrayOutputStream();
            byte[] data=new byte[500];
            int current=0;

            while ((current=bufferedInputStream.read(data,0,data.length))!=-1){
                buffer.write(data,0,current);
            }
            return  buffer.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
