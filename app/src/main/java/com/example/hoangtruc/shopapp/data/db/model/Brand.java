package com.example.hoangtruc.shopapp.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Brand implements Parcelable {
    private int mID, mPurchased;
    private String mNameBrand, mImage;

    protected Brand(Parcel in) {
        mID = in.readInt();
        mPurchased = in.readInt();
        mNameBrand = in.readString();
        mImage = in.readString();
    }

    public Brand() {
        mID=0;
        mPurchased=0;
        mNameBrand="";
        mImage="";
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel in) {
            return new Brand(in);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public int getPurchased() {
        return mPurchased;
    }

    public void setPurchased(int purchased) {
        mPurchased = purchased;
    }

    public String getNameBrand() {
        return mNameBrand;
    }

    public void setNameBrand(String nameBrand) {
        mNameBrand = nameBrand;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeInt(mPurchased);
        parcel.writeString(mNameBrand);
        parcel.writeString(mImage);
    }
}
