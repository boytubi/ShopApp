package com.example.hoangtruc.shopapp.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Product implements Parcelable {
    private int mID;
    private int mPrice;
    private int mTotal;
    private int mIDProductType;
    private int mIDBrand;
    private int mIDPerson;
    private int mQuantityPurchased;
    private int mInventoryQuantity;
    private String mImage;
    private String mThumbnail;
    private String mInformation;
    private String mNameProduct;
    private String mNamePerson;
    private List<ItemDescription> mItemDescriptions;
    private byte[] imageCart;
    private int mTotalPrice;
    public Product(int ID,  String nameProduct,int price , byte[] imageCart, int quantityPurchased, int total,int totalPrice) {
        mID = ID;
        mPrice = price;
        mQuantityPurchased = quantityPurchased;
        mTotal = total;
        mNameProduct = nameProduct;
        this.imageCart = imageCart;
        mTotalPrice=totalPrice;
    }

    protected Product(Parcel in) {
        mID = in.readInt();
        mPrice = in.readInt();
        mTotal = in.readInt();
        mIDProductType = in.readInt();
        mIDBrand = in.readInt();
        mIDPerson = in.readInt();
        mQuantityPurchased = in.readInt();
        mInventoryQuantity = in.readInt();
        mImage = in.readString();
        mThumbnail = in.readString();
        mInformation = in.readString();
        mNameProduct = in.readString();
        mNamePerson = in.readString();
        imageCart = in.createByteArray();
        mTotalPrice = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        mTotalPrice = totalPrice;
    }

    public Product() {}

    public byte[] getImageCart() {
        return imageCart;
    }

    public void setImageCart(byte[] imageCart) {
        this.imageCart = imageCart;
    }

    public List<ItemDescription> getItemDescriptions() {
        return mItemDescriptions;
    }

    public void setItemDescriptions(List<ItemDescription> itemDescriptions) {
        mItemDescriptions = itemDescriptions;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public int getIDProductType() {
        return mIDProductType;
    }

    public void setIDProductType(int IDProductType) {
        mIDProductType = IDProductType;
    }

    public int getIDBrand() {
        return mIDBrand;
    }

    public void setIDBrand(int IDBrand) {
        mIDBrand = IDBrand;
    }

    public int getIDPerson() {
        return mIDPerson;
    }

    public void setIDPerson(int IDPerson) {
        mIDPerson = IDPerson;
    }

    public int getQuantityPurchased() {
        return mQuantityPurchased;
    }

    public void setQuantityPurchased(int quantityPurchased) {
        mQuantityPurchased = quantityPurchased;
    }

    public int getInventoryQuantity() {
        return mInventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        mInventoryQuantity = inventoryQuantity;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getInformation() {
        return mInformation;
    }

    public void setInformation(String information) {
        mInformation = information;
    }

    public String getNameProduct() {
        return mNameProduct;
    }

    public void setNameProduct(String nameProduct) {
        mNameProduct = nameProduct;
    }

    public String getNamePerson() {
        return mNamePerson;
    }

    public void setNamePerson(String namePerson) {
        mNamePerson = namePerson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeInt(mPrice);
        parcel.writeInt(mTotal);
        parcel.writeInt(mIDProductType);
        parcel.writeInt(mIDBrand);
        parcel.writeInt(mIDPerson);
        parcel.writeInt(mQuantityPurchased);
        parcel.writeInt(mInventoryQuantity);
        parcel.writeString(mImage);
        parcel.writeString(mThumbnail);
        parcel.writeString(mInformation);
        parcel.writeString(mNameProduct);
        parcel.writeString(mNamePerson);
        parcel.writeByteArray(imageCart);
        parcel.writeInt(mTotalPrice);
    }
}
