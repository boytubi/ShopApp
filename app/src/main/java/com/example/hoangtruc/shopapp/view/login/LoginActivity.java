package com.example.hoangtruc.shopapp.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.data.network.model.GoogleLogin;
import com.example.hoangtruc.shopapp.presenter.login.LoginPresenter;
import com.example.hoangtruc.shopapp.view.main.MainActivity;
import com.example.hoangtruc.shopapp.view.signup.SignUpActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements LoginMvpView,View.OnClickListener {
    private ImageButton mImageButtonFacebook, mImageButtonGoogle;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private Button mBtnSignup,mButtonLogin;
    private LoginPresenter mLoginPresenter;
    private TextInputEditText mEditTextEmail,mEditTextPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        GoogleLogin googleLogin = new GoogleLogin();
        mSignInClient = googleLogin.getSignInClient(this);

        registerCallBackFaceBook();
//        getHashKey();
        initializeViews();
        mLoginPresenter=new LoginPresenter(this);

        mImageButtonFacebook.setOnClickListener(this);
        mImageButtonGoogle.setOnClickListener(this);
        mBtnSignup.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void registerCallBackFaceBook() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent iMain = new Intent(LoginActivity.this, MainActivity.class);
                        iMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iMain);
                        finish();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });

    }

    private void initializeViews() {
        mImageButtonFacebook = findViewById(R.id.imageButtonFacebook);
        mImageButtonGoogle = findViewById(R.id.imageButtonGoogle);
        mBtnSignup=findViewById(R.id.button_signUp);
        mButtonLogin=findViewById(R.id.button_login);
        mEditTextEmail=findViewById(R.id.editTextEmailLogin);
        mEditTextPassword=findViewById(R.id.editTextPasswordLogin);
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.hoangtruc.shopapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                    intentMain.putExtra("account", account);
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentMain);
                    finish();
                }

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButtonFacebook:
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                break;
            case R.id.imageButtonGoogle:
                SignInGoogle();
                break;
            case R.id.button_signUp:
                Intent iSignUp=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(iSignUp);
                break;
            case R.id.button_login:
                login();
                break;

        }
    }

    private void SignInGoogle() {
        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void LoginSuccessfully() {
        Intent iHome=new Intent(LoginActivity.this,MainActivity.class);
        iHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(iHome);
        finish();
    }

    @Override
    public void LoginFailed() {
        Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show();
    }
    private void login(){
        String username= String.valueOf(mEditTextEmail.getText());
        String password=String.valueOf(mEditTextPassword.getText());
        mLoginPresenter.handleLogin(this,username,password);
    }
}
