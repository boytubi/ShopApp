package com.example.hoangtruc.shopapp.view.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.data.db.model.Person;
import com.example.hoangtruc.shopapp.presenter.signup.SignUpPresenter;

public class SignUpActivity  extends AppCompatActivity implements SignUpMvpView,View.OnClickListener{
    private TextInputEditText mEditTextEmailSignUp,mEditTextPasswordSignUp,mEditTextPasswordConfirmSignUp;
    private SignUpPresenter mSignUpPresenter;
    private Button mButtonSignUp;
    private Person mPerson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeViews();
        mPerson=new Person();
        mSignUpPresenter=new SignUpPresenter(this);

        mButtonSignUp.setOnClickListener(this);
    }
    private void initializeViews(){
        mEditTextEmailSignUp=findViewById(R.id.editTextEmailSignUp);
        mEditTextPasswordSignUp=findViewById(R.id.editTextPasswordSignUp);
        mEditTextPasswordConfirmSignUp=findViewById(R.id.editTextPasswordConfirmSignUp);
        mButtonSignUp=findViewById(R.id.button_register);
    }

    @Override
    public void registerSuccessfully() {
        Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerFailed() {
        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View  view) {
          String username= String.valueOf(mEditTextEmailSignUp.getText());
          String password=String.valueOf(mEditTextPasswordSignUp.getText());
          String confirmPassword=String.valueOf(mEditTextPasswordConfirmSignUp.getText());

          if (password.equals(confirmPassword)) {
              int at = username.indexOf('@');
              mPerson.setUsername(username);
              mPerson.setName(username.substring(0, at));
              mPerson.setPassword(password);
              mPerson.setType_Id(2);
              mSignUpPresenter.onSeverSignUp(mPerson);
          }else{
              Toast.makeText(this,"Passwords does not match",Toast.LENGTH_SHORT).show();
          }
    }
}
