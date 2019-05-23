package com.example.hoangtruc.shopapp.view.review;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.data.db.model.Review;
import com.example.hoangtruc.shopapp.presenter.review.ReviewPresenter;
import com.squareup.picasso.Picasso;

public class ReviewActivity extends AppCompatActivity implements ReviewMvpView, View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    private ReviewPresenter mReviewPresenter;
    private Review mReview;
    private RatingBar mRatingBarFeedback;
    private EditText mEditTextTitle, mEditTextReview;
    private TextInputLayout mTextInputTitle,mTextInputReview;
    private Button mButtonFinish;
    private Bundle bundle;
    private int rating;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acticity_feedback);
        initializeViews();
        setTitle("");
        mReviewPresenter = new ReviewPresenter(this);

        bundle = getIntent().getExtras();

        mRatingBarFeedback.setOnRatingBarChangeListener(this);
        mButtonFinish.setOnClickListener(this);

    }

    private void initializeViews() {
        mRatingBarFeedback = findViewById(R.id.ratingReview);
        mEditTextReview = findViewById(R.id.edReview);
        mEditTextTitle = findViewById(R.id.edTitle);
        mButtonFinish = findViewById(R.id.buttonReview);
        mTextInputTitle=findViewById(R.id.input_edtitle);
        mTextInputReview=findViewById(R.id.input_edReview);
    }

    @Override
    public void feedbackSuccessfully() {
        Toast.makeText(this,"Review Successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void feedbackFailed() {
        Toast.makeText(this,"Review Failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonReview:
                writeReview();
                finish();
                break;

        }
    }

    private void writeReview() {
        String android_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        String title = String.valueOf(mEditTextTitle.getText());
        String review = String.valueOf(mEditTextReview.getText());
        String idFeedback = android_id;
        Log.d("ID Feedback",idFeedback);
        int idProduct = bundle.getInt("IDProduct", 0);
        String nameDevice = Build.MODEL;

        if (title.trim().length()>0){
            mTextInputTitle.setErrorEnabled(false);
            mTextInputTitle.setError("");
        }else {
            mTextInputTitle.setErrorEnabled(true);
            mTextInputTitle.setError("Title cannot be empty");
        }
        if (review.trim().length()>0){
            mTextInputReview.setErrorEnabled(false);
            mTextInputReview.setError("");
        }else {
            mTextInputReview.setErrorEnabled(true);
            mTextInputReview.setError("Review cannot be empty");
        }
        if (!mTextInputReview.isErrorEnabled()&&!mTextInputTitle.isErrorEnabled()){
            mReview =new Review();
//            mReview.setIdFeedBack("1");
            mReview.setIdProduct(idProduct);
            mReview.setNameDevice(nameDevice);
            mReview.setRating(rating);
            mReview.setTitle(title);
            mReview.setReview(review);
            mReviewPresenter.onSeverReview(mReview);
            }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
         rating= (int) v;
    }
}
