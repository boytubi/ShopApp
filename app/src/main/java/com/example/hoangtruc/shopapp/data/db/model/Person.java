package com.example.hoangtruc.shopapp.data.db.model;

public class Person {
   private int mId,mType_Id,sex;
   private String username;
   private String password;
   private String name;
   private String address;
   private String birthOfDate;
   private String phoneNumber;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getType_Id() {
        return mType_Id;
    }

    public void setType_Id(int type_Id) {
        mType_Id = type_Id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(String birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
