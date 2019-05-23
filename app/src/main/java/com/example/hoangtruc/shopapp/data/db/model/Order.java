package com.example.hoangtruc.shopapp.data.db.model;

import java.util.List;

public class Order {
    private int mID;
    private String mDate,mExpectedArrivalDate;
    private String mNameCustomer;

    private String mPhoneNumber;
    private String mAddress;
    private int mShipment;
 private List<DetailOrder> mDetailOrderList;

    public List<DetailOrder> getDetailOrderList() {
        return mDetailOrderList;
    }

    public void setDetailOrderList(List<DetailOrder> detailOrderList) {
        mDetailOrderList = detailOrderList;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getExpectedArrivalDate() {
        return mExpectedArrivalDate;
    }

    public void setExpectedArrivalDate(String expectedArrivalDate) {
        mExpectedArrivalDate = expectedArrivalDate;
    }

    public String getNameCustomer() {
        return mNameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        mNameCustomer = nameCustomer;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public int getShipment() {
        return mShipment;
    }

    public void setShipment(int shipment) {
        mShipment = shipment;
    }
}
