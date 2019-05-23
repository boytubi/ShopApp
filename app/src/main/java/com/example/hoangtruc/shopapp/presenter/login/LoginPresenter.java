package com.example.hoangtruc.shopapp.presenter.login;

import android.content.Context;
import android.content.Intent;

import com.example.hoangtruc.shopapp.data.network.model.ServerLogIn;
import com.example.hoangtruc.shopapp.view.login.LoginActivity;
import com.example.hoangtruc.shopapp.view.login.LoginMvpView;
import com.example.hoangtruc.shopapp.view.main.MainActivity;

public class LoginPresenter implements LoginMvpPresenter {
    private LoginMvpView mLoginMvpView;
    private ServerLogIn mServerLogIn;

    public LoginPresenter(LoginMvpView loginMvpView) {
        mLoginMvpView = loginMvpView;
        mServerLogIn=new ServerLogIn();
    }

    public LoginPresenter() {
        mServerLogIn=new ServerLogIn();
    }

    @Override
    public void handleLogin(Context context,String username,String password) {
        boolean result=mServerLogIn.checkLogIn(context,username,password);
        if (result){
            mLoginMvpView.LoginSuccessfully();

        }else {
            mLoginMvpView.LoginFailed();
        }
    }

    @Override
    public String getCurrentUser(Context context) {
        String name =mServerLogIn.getCurrentUser(context);
        return name;
    }

    @Override
    public void resetUser(Context context,String name) {
        mServerLogIn.saveCurrentUser(context,name);
    }

}
