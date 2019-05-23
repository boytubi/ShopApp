package com.example.hoangtruc.shopapp.data.network.model;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

public class FacebookLogin  {
    private AccessTokenTracker mAccessTokenTracker;
    private AccessToken mAccessToken;
    public AccessToken getAccessToken(){
        mAccessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                     mAccessToken=currentAccessToken;
            }

        };
        mAccessToken=AccessToken.getCurrentAccessToken();
        return mAccessToken;
    }
    public void destroyToken(){
        mAccessTokenTracker.stopTracking();
    }
}
