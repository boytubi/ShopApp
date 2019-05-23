package com.example.hoangtruc.shopapp.view.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.adapter.ParentLevelAdapter;
import com.example.hoangtruc.shopapp.adapter.ViewPagerAdapter;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.db.model.ProductType;
import com.example.hoangtruc.shopapp.data.db.model.Review;
import com.example.hoangtruc.shopapp.data.network.model.GoogleLogin;
import com.example.hoangtruc.shopapp.data.network.model.ProductTypeTask;
import com.example.hoangtruc.shopapp.presenter.detailproduct.DetailProductPresenter;
import com.example.hoangtruc.shopapp.presenter.login.LoginPresenter;
import com.example.hoangtruc.shopapp.presenter.main.menu.MenuPresenter;
import com.example.hoangtruc.shopapp.view.DetailProductMvpView;
import com.example.hoangtruc.shopapp.view.cart.CartActivity;
import com.example.hoangtruc.shopapp.view.login.LoginActivity;
import com.example.hoangtruc.shopapp.view.qrcode.BarcodeActivity;
import com.example.hoangtruc.shopapp.view.search.SearchActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.github.mzule.fantasyslide.FantasyListener;
import com.github.mzule.fantasyslide.SideBar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MenuMvpView {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerApapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    MenuPresenter menuPresenter;
    private ExpandableListView mExpandableList;
    private ParentLevelAdapter mParentLevelAdapter;
    private Menu mMenu;
    private AccessToken mAccessToken;
    private String mName;
    private MenuItem itemUser, itemLogout;
    private GoogleSignInClient mSignInClient;
    private GoogleSignInAccount mSignInAccount;
    private LoginPresenter mLoginPresenter;
    private Button btnSearch;
    private TextView mTextCart;
    private DetailProductPresenter detailProductPresenter;
    private boolean flagPause = false;
    private FrameLayout mFrameLayout;

    private ImageView mImageViewExpand;
    private TextView mTextViewUser;
    private TextView mTextViewNotification, mTextViewFavorites, mTextViewMyCart, mTextViewSettings, mTextViewLogin, mTextViewLogout;
    private SideBar mLeftSideBar;
    private CircleImageView mCircleImageViewAvatar;
    private TextView mTextViewCartCountMenu;
    private LinearLayout mLinearLayoutMyCart;
    private ImageView mImageViewCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initializeViews();
        manipulateViews();
        menuPresenter = new MenuPresenter(this);
//        menuPresenter.getListMenu();
        mLoginPresenter = new LoginPresenter();

        GoogleLogin googleLogin = new GoogleLogin();
        mSignInClient = googleLogin.getSignInClient(this);
        mSignInAccount = getIntent().getParcelableExtra("account");
        mSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        detailProductPresenter = new DetailProductPresenter(getApplicationContext());

        mTextCart = findViewById(R.id.textViewCartCount);
        mTextCart.setText(String.valueOf(detailProductPresenter.getCartCount(getApplicationContext())));
        mTextViewCartCountMenu.setText(String.valueOf(detailProductPresenter.getCartCount(getApplicationContext())));
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(iCart);
            }
        });
        updateInfoUser();
    }

    private void initializeViews() {
//        mToolbar = findViewById(R.id.toolbar_menu);
        //        mExpandableList = findViewById(R.id.expandable_list);
//        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewpager_main);
        mDrawerLayout = findViewById(R.id.drawerLayout);

        btnSearch = findViewById(R.id.buttonSearch);
        mFrameLayout = findViewById(R.id.menucart);
        mImageViewExpand = findViewById(R.id.imageExpand);
        mLeftSideBar = findViewById(R.id.sideBarLeft);
        mTextViewUser = findViewById(R.id.textViewUser);
        mTextViewNotification = findViewById(R.id.textViewNotification);
        mTextViewFavorites = findViewById(R.id.textViewFavorites);
        mTextViewMyCart = findViewById(R.id.textViewMyOrder);
        mTextViewSettings = findViewById(R.id.textViewSettings);
        mTextViewLogin = findViewById(R.id.textViewLogin);
        mTextViewLogout = findViewById(R.id.textViewLogout);
        mCircleImageViewAvatar = findViewById(R.id.circleImageUser);
        mTextViewCartCountMenu = findViewById(R.id.textViewCartMenu);
        mLinearLayoutMyCart=findViewById(R.id.linearMyCart);
        mImageViewCamera=findViewById(R.id.imageViewCamera);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flagPause) {

            mTextCart.setText(String.valueOf(detailProductPresenter.getCartCount(getApplicationContext())));
            mTextViewCartCountMenu.setText(String.valueOf(detailProductPresenter.getCartCount(getApplicationContext())));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        flagPause = true;
    }


//    @Override
//    public boolean onCreateOptionsMenu(final Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        this.mMenu = menu;
//        String username=mLoginPresenter.getCurrentUser(this);
//        MenuItem itemCart= menu.findItem(R.id.itemCart);
//        View viewCart=MenuItemCompat.getActionView(itemCart);
//        mTextCart=viewCart.findViewById(R.id.textViewCartCount);
//        mTextCart.setText(String.valueOf(detailProductPresenter.getCartCount(getApplicationContext())));
//        viewCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent iCart=new Intent(MainActivity.this, CartActivity.class);
//                startActivity(iCart);
//            }
//        });
//
//        mAccessToken = menuPresenter.getAccessTokenFacebook();
//        itemUser = menu.findItem(R.id.itemLogin);
//        itemLogout= menu.findItem(R.id.itemLogout);
//        if (mAccessToken != null) {
//            GraphRequest graphRequest = GraphRequest.newMeRequest(
//                    mAccessToken,
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            try {
//                                //get username from Facebook
//                                name = object.getString("name");
//                                ///set to item menu
//
//                                itemUser.setTitle(name);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//            Bundle parameters = new Bundle();
//            parameters.putString("fields", "name");
//            graphRequest.setParameters(parameters);
//            graphRequest.executeAsync();
//        }
//
//        if(mSignInAccount !=null){
//
//             itemUser.setTitle(mSignInAccount.getDisplayName());
//        }
//        if (!username.equals("")){
//            itemUser.setTitle(username);
//        }
//        if (mAccessToken != null||mSignInAccount !=null||!username.equals("")) {
//
//            itemLogout.setVisible(true);
//        }
//
//        return true;
//    }


    private void manipulateViews() {
//        mToolbar.setTitle("");
//        setSupportActionBar(mToolbar);
//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
//        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mActionBarDrawerToggle.syncState();
       //Viewpager
        mViewPagerApapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerApapter);
//        mTabLayout.setupWithViewPager(mViewPager);


        btnSearch.setOnClickListener(this);
        mImageViewExpand.setOnClickListener(this);
        mTextViewLogout.setOnClickListener(this);
        mTextViewLogin.setOnClickListener(this);
        mTextViewUser.setOnClickListener(this);
        mLinearLayoutMyCart.setOnClickListener(this);
        mImageViewCamera.setOnClickListener(this);
    }

    private void updateInfoUser() {
        String username = mLoginPresenter.getCurrentUser(this);
        mAccessToken = menuPresenter.getAccessTokenFacebook();
        if (mAccessToken != null) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(
                    mAccessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                //get username from Facebook
                                mName = object.getString("name");
                                ///set to item menu
                                try {
                                    URL url = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
                                    Picasso.get()
                                            .load(url.toString())
                                            .into(mCircleImageViewAvatar);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                mTextViewUser.setText(mName);
                                mTextViewLogin.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            //Request Graph API
            Bundle parameters = new Bundle();
            parameters.putString("fields", "name");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        }

        if (mSignInAccount != null) {
            mTextViewUser.setText(mSignInAccount.getDisplayName());
            mTextViewLogin.setVisibility(View.GONE);
        }
        if (!username.equals("")) {
            mTextViewUser.setText(username);
            mTextViewLogin.setVisibility(View.GONE);

        }
        if (mAccessToken != null || mSignInAccount != null || !username.equals("")) {

            mTextViewLogout.setVisibility(View.VISIBLE);
        }
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        switch (item.getItemId()) {
//            case R.id.itemLogin:
//                if (mAccessToken == null && mSignInAccount==null&&mLoginPresenter.getCurrentUser(this).equals("")) {
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
////
//                }
//                break;
//            case R.id.itemLogout:
//                if (mAccessToken != null) {
//                    LoginManager.getInstance().logOut();
//                    updateMenu();
//                }
//                if(mSignInAccount!=null){
//                    mSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            updateMenu();
//                        }
//                    });
//                }
//                if (!mLoginPresenter.getCurrentUser(this).equals("")){
//                    mLoginPresenter.resetUser(this,"");
//                    this.mMenu.clear();
//                    this.onCreateOptionsMenu(this.mMenu);
//                }
//                break;
//        }
//        return true;
//    }

    @Override
    public void displayListMenu(List<ProductType> productTypes) {
//        mParentLevelAdapter = new ParentLevelAdapter(productTypes, this);
//        mExpandableList.setAdapter(mParentLevelAdapter);
//        for (int i = 0; i < mParentLevelAdapter.getGroupCount(); i++) {
//            mExpandableList.expandGroup(i);
//        }
//        mParentLevelAdapter.notifyDataSetChanged();
    }


    //
    private void updateMenu() {
        mTextViewLogout.setVisibility(View.GONE);
        mTextViewLogin.setVisibility(View.VISIBLE);
        mTextViewUser.setText("Username");
        mCircleImageViewAvatar.setImageResource(R.drawable.imageuser);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSearch:
                Intent iSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(iSearch);
                break;
            case R.id.imageExpand:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.textViewLogin:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.linearUserInfo:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.textViewLogout:
                if (mAccessToken != null) {
                    LoginManager.getInstance().logOut();
                    updateMenu();
                }
                if (mSignInAccount != null) {
                    mSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateMenu();
                        }
                    });
                }
                if (!mLoginPresenter.getCurrentUser(this).equals("")) {
                    mLoginPresenter.resetUser(this, "");

                }
                break;
            case R.id.linearMyCart:
                Intent iCart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(iCart);
                break;
            case R.id.imageViewCamera:
                Intent iCamera=new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(iCamera);
                break;
        }
    }

//    public void onClickText(View view) {
//        switch (view.getId()) {
//
//
//
//
//
//        }
//    }

}
