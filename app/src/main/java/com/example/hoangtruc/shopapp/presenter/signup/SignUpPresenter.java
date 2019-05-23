package com.example.hoangtruc.shopapp.presenter.signup;

import com.example.hoangtruc.shopapp.data.db.model.Person;
import com.example.hoangtruc.shopapp.data.network.model.ServerSignUp;
import com.example.hoangtruc.shopapp.view.signup.SignUpMvpView;

public class SignUpPresenter implements SignUpMvpPresenter {
    private SignUpMvpView mSignUpMvpView;
    private ServerSignUp mServerSignUp;
    public SignUpPresenter(SignUpMvpView signUpMvpView) {
        mSignUpMvpView = signUpMvpView;
        mServerSignUp=new ServerSignUp();
    }

    @Override
    public void onSeverSignUp(Person person) {
        boolean checkRegisterResponse=mServerSignUp.registerMember(person);
        if (checkRegisterResponse){
            mSignUpMvpView.registerSuccessfully();
        }else {
            mSignUpMvpView.registerFailed();
        }
    }
}
