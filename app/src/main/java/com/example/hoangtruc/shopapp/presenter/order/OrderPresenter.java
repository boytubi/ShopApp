package com.example.hoangtruc.shopapp.presenter.order;

import android.content.Context;

import com.example.hoangtruc.shopapp.data.db.DatabaseHelper;
import com.example.hoangtruc.shopapp.data.db.model.DetailOrder;
import com.example.hoangtruc.shopapp.data.db.model.Order;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.network.order.OrderRequestData;
import com.example.hoangtruc.shopapp.view.order.OrderMvpView;

import java.util.ArrayList;
import java.util.List;

public class OrderPresenter implements OrderMvpPresenter {
    private OrderRequestData mOrderRequestData;
    private OrderMvpView mOrderMvpView;
    private Context mContext;

    public OrderPresenter(OrderMvpView orderMvpView, Context context) {
        mOrderMvpView = orderMvpView;
        mOrderRequestData=new OrderRequestData();
        mContext = context;
    }

    @Override
    public void doOrder(Order order) {
        DatabaseHelper  databaseHelper=new DatabaseHelper(mContext);
        List<Product> productList=databaseHelper.getAllProduct();
        List<DetailOrder> detailOrders=new ArrayList<>();
        for(int i=0;i<productList.size();i++){
            DetailOrder detailOrder=new DetailOrder();
            detailOrder.setIdProduct(productList.get(i).getID());
            detailOrder.setQuantity(productList.get(i).getQuantityPurchased());
            detailOrders.add(detailOrder);
        }
        order.setDetailOrderList(detailOrders);
        if (mOrderRequestData.requestOrder(order)){
            mOrderMvpView.orderSuccess();
            databaseHelper.deleteAllProducts();
        }else {
            mOrderMvpView.orderFailed();
        }
    }
}
