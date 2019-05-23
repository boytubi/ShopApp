package com.example.hoangtruc.shopapp.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.adapter.FeedbackAdapter;
import com.example.hoangtruc.shopapp.adapter.ViewPagerSliderAdapter;
import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.DatabaseHelper;
import com.example.hoangtruc.shopapp.data.db.model.ItemDescription;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.data.db.model.ProductType;
import com.example.hoangtruc.shopapp.data.db.model.Review;
import com.example.hoangtruc.shopapp.presenter.detailproduct.DetailProductPresenter;
import com.example.hoangtruc.shopapp.view.cart.CartActivity;
import com.example.hoangtruc.shopapp.view.main.MenuMvpView;
import com.example.hoangtruc.shopapp.view.review.ReviewActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity implements DetailProductMvpView, ViewPager.OnPageChangeListener, View.OnClickListener {
    private int mIdProduct;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;
    private final static int DELAY = 4000;
    private int page = 0;
    private ViewPagerSliderAdapter viewPagerSliderAdapter;
    private Handler mHandler;
    private LinearLayout mSliderDotsPanel;
    private int mDotsCount;
    private ImageView[] dots;
    private Menu mMenu;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private DatabaseHelper mDatabaseHelper;

    private Product mProduct;
    private TextView mTextviewInfor, mTextViewNameProduct, mTextViewPrice, mTextViewSale;
    private TextView mTextviewFeedback;
    private ImageView mImageViewExpandItemDescription;
    private LinearLayout mLnItemDescription;
    private boolean isCheckExpanded = true;
    private DetailProductPresenter detailProductPresenter;
    private Button mButtonAddCart, mButtonRemoveCart;
    private TextView mTextViewCartCount;
    private RecyclerView mRecyclerViewFeedback;
    private FeedbackAdapter mFeedbackAdapter;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (viewPagerSliderAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            mViewPager.setCurrentItem(page);

            mHandler.postDelayed(this, DELAY);
        }
    };
    private Typeface mLight, mRegular;
    private NumberFormat mNumberFormat;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorTransparent));
        setContentView(R.layout.activity_detail_product);
        mLight = Typeface.createFromAsset(getAssets(), "fonts/MLight.ttf");
        mRegular = Typeface.createFromAsset(getAssets(), "fonts/MRegular.ttf");
        mNumberFormat=new DecimalFormat("###,###");
        mHandler = new Handler();
        initializeViews();

        Intent intent = this.getIntent();
        mIdProduct = intent.getIntExtra("IDProduct", 0);
        detailProductPresenter = new DetailProductPresenter(this, getApplicationContext());
        detailProductPresenter.detailProduct(mIdProduct);
        detailProductPresenter.listFeedback(mIdProduct, 0);


        mTextviewFeedback.setOnClickListener(this);
        this.updateButtonCart(mProduct);

        customDotsView();
    }

    private void initializeViews() {
        mViewPager = findViewById(R.id.viewpagerSlider);
        mSliderDotsPanel = findViewById(R.id.layoutDots);
        mToolbar = findViewById(R.id.toolbar_detail);
        mAppBarLayout = findViewById(R.id.app_bar);
        mCollapsingToolbarLayout = findViewById(R.id.collapsingDetailProduct);
        mTextviewInfor = findViewById(R.id.textviewInfor);
        mLnItemDescription = findViewById(R.id.linearItemDescription);
        mImageViewExpandItemDescription = findViewById(R.id.expandItemDescription);
        mTextViewNameProduct = findViewById(R.id.textViewNameProduct);
        mTextViewPrice = findViewById(R.id.textViewPrice);
        mTextViewSale = findViewById(R.id.textViewSale);
        mTextviewFeedback = findViewById(R.id.textviewFeedback);
        mRecyclerViewFeedback = findViewById(R.id.recyclerFeedbackDetail);

        mButtonAddCart = findViewById(R.id.buttonAddToCart);
        mButtonRemoveCart = findViewById(R.id.buttonRemoveToCart);


        mCollapsingToolbarLayout.setTitle(" ");
        setSupportActionBar(mToolbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;
            boolean isShow = false;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;

                    showOption(R.id.action_favorite, R.id.action_share, R.id.action_cart);
                } else if (isShow) {
                    isShow = false;

                    hideOption(R.id.action_favorite, R.id.action_share, R.id.action_cart);
                }
            }
        });
    }

    @Override
    public void displayDetailProduct(Product product) {

        mProduct = product;
        mTextviewInfor.setText(mProduct.getInformation().substring(0, 100));
        if (mTextviewInfor.length() < 100) {
            mImageViewExpandItemDescription.setVisibility(View.GONE);
        } else {
            mImageViewExpandItemDescription.setVisibility(View.VISIBLE);
            mImageViewExpandItemDescription.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    isCheckExpanded = !isCheckExpanded;

                    if (isCheckExpanded) {
                        mImageViewExpandItemDescription.setImageDrawable(getDrawableExpand(R.drawable.ic_expand_less_black_24dp));
                        mTextviewInfor.setText(mProduct.getInformation());

                    } else {
                        mImageViewExpandItemDescription.setImageDrawable(getDrawableExpand(R.drawable.ic_expand_more_24dp));
                        mTextviewInfor.setText(mProduct.getInformation().substring(0, 100));
                        mImageViewExpandItemDescription.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        mTextViewNameProduct.setTypeface(mLight);
        mTextViewNameProduct.setText(mProduct.getNameProduct());

        mTextViewPrice.setTypeface(mRegular);
        mTextViewPrice.setText(mNumberFormat.format(mProduct.getPrice())+" VND");
        displayItemDescription(mProduct);
    }

    private void displayItemDescription(Product product) {
        List<ItemDescription> itemDescriptions = product.getItemDescriptions();

        for (int i = 0; i < itemDescriptions.size(); i++) {
            LinearLayout lnRow = new LinearLayout(this);
            lnRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            lnRow.setOrientation(LinearLayout.HORIZONTAL);
//            lnRow.setPadding(dpToPx(R.dimen.dp_3),dpToPx(R.dimen.dp_3),dpToPx(R.dimen.dp_3),dpToPx(R.dimen.dp_3));
            TextView textName = new TextView(this);
            textName.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f));
            textName.setText(itemDescriptions.get(i).getItemName());

            TextView textValue = new TextView(this);
            textName.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f));

            textValue.setText(itemDescriptions.get(i).getItemValue());
            textValue.setTextColor(getColorText(R.color.colorBlack));
            lnRow.addView(textName);
            lnRow.addView(textValue);

            mLnItemDescription.addView(lnRow);
        }
    }

    private Drawable getDrawableExpand(int id) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= 21) {
            drawable = ContextCompat.getDrawable(this, id);
        } else {
            drawable = getResources().getDrawable(id);
        }
        return drawable;

    }

    private int getColorText(int id) {
        int color;
        if (Build.VERSION.SDK_INT >= 21) {
            color = ContextCompat.getColor(this, id);
        } else {
            color = getResources().getColor(id);
        }
        return color;
    }



    @Override
    public void displaySliderProduct(String[] thumbnail) {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < thumbnail.length; i++) {

            SliderProductFragment sliderProductFragment = new SliderProductFragment();
            Bundle bundle = new Bundle();
            bundle.putString("thumbnail", Constant.SERVER + thumbnail[i]);
            sliderProductFragment.setArguments(bundle);
            mFragmentList.add(sliderProductFragment);

        }
        viewPagerSliderAdapter = new ViewPagerSliderAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(viewPagerSliderAdapter);
        mViewPager.setPageTransformer(true, new CubeInDepthTransformation());
        setCustomScrollerToViewPage();
        viewPagerSliderAdapter.notifyDataSetChanged();

        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void displayListFeedback(List<Review> reviewList) {
        mFeedbackAdapter = new FeedbackAdapter(this, reviewList, 3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewFeedback.setLayoutManager(layoutManager);
        mRecyclerViewFeedback.setAdapter(mFeedbackAdapter);
        mFeedbackAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCartCount() {
        mTextViewCartCount.setText(String.valueOf(detailProductPresenter.getCartCount(getApplicationContext())));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mDotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    private void setCustomScrollerToViewPage() {
        Field mScroller;
        try {
            Interpolator sInterpolator = new DecelerateInterpolator();
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            CustomScroller scroller = new CustomScroller(mViewPager.getContext(), sInterpolator);
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void customDotsView() {
        mDotsCount = viewPagerSliderAdapter.getCount();
        dots = new ImageView[mDotsCount];
        for (int i = 0; i < mDotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            mSliderDotsPanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        hideOption(R.id.action_share);
        MenuItem item = menu.findItem(R.id.action_cart);
        View viewCart = MenuItemCompat.getActionView(item);
        mTextViewCartCount = viewCart.findViewById(R.id.textViewCartCount);

        hideOption(R.id.action_favorite, R.id.action_share, R.id.action_cart);
        mTextViewCartCount.setText(String.valueOf(detailProductPresenter.getCartCount(getApplicationContext())));
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
    }

    private void hideOption(int id_1, int id_2, int id_3) {
        List<Integer> idList = new ArrayList<>();
        idList.add(id_1);
        idList.add(id_2);
        idList.add(id_3);
        for (int i = 0; i < idList.size(); i++) {
            MenuItem item = mMenu.findItem(idList.get(i));
            item.setVisible(false);
        }
    }

    private void showOption(int id_1, int id_2, int id_3) {
        List<Integer> idList = new ArrayList<>();
        idList.add(id_1);
        idList.add(id_2);
        idList.add(id_3);
        for (int i = 0; i < idList.size(); i++) {
            MenuItem item = mMenu.findItem(idList.get(i));
            item.setVisible(true);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textviewFeedback:
                Intent iReview = new Intent(this, ReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("IDProduct", mProduct.getID());
                iReview.putExtras(bundle);
                startActivity(iReview);
                break;
        }
    }


    @Override
    public void updateButtonCart(final Product product) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                return detailProductPresenter.checkCart(product.getID());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
                    mButtonAddCart.setVisibility(View.GONE);
                    mButtonRemoveCart.setVisibility(View.VISIBLE);
                } else {
                    mButtonRemoveCart.setVisibility(View.GONE);
                    mButtonAddCart.setVisibility(View.VISIBLE);

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        mButtonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQuantityPurchased(1);
                detailProductPresenter.addCart(product);

            }
        });
        mButtonRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailProductPresenter.removeCart(product);


            }
        });

    }

}
