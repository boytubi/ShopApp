package com.example.hoangtruc.shopapp.presenter.login;

import android.content.Context;

public interface LoginMvpPresenter {
    void handleLogin(Context context,String username,String password);
    String getCurrentUser(Context context);
    void resetUser(Context context,String name);
}
