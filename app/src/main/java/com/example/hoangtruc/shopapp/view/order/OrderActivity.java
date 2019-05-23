package com.example.hoangtruc.shopapp.view.order;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.common.DataCountry;
import com.example.hoangtruc.shopapp.data.db.model.Order;
import com.example.hoangtruc.shopapp.presenter.order.OrderMvpPresenter;
import com.example.hoangtruc.shopapp.presenter.order.OrderPresenter;
import com.example.hoangtruc.shopapp.view.CustomTypefaceSpan;
import com.example.hoangtruc.shopapp.view.main.MainActivity;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, OrderMvpView, Switch.OnCheckedChangeListener  {

    private Typeface mLight, mRegular;
    private TextView mTextViewOrderConfirmation;
    private EditText mEditTextName, mEditTextAddress;
    private TypefaceSpan mTypefaceSpan;
    private SpannableString mSpannableAddress, mSpannableName, mSpannablePhone;
    private Spinner spinnerNumber;
    private EditText mEditTextPhoneNumber;
    private Switch mSwitch;
    private Button mButtonDone;
    private TextView mTextViewSuccess, mTextViewNote;
    private LinearLayout mLinearLayoutSuccess, mLinearLayoutOverBox;
    private ImageView mImageViewHeart;
    private Button mButtonOk;
    private Animation mAnimationFromSmall, mAnimationFromNotthing, mAnimationForImage, mAnimationFromZero;
    private OrderPresenter mOrderPresenter;
    private int shipment = 0;

    private TextView mTextViewEmptyContact, mTextViewEmptyAddress, mTextViewEmptyPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mRegular = Typeface.createFromAsset(getAssets(), "fonts/MRegular.ttf");
        mLight = Typeface.createFromAsset(getAssets(), "fonts/MLight.ttf");
        mTypefaceSpan = new CustomTypefaceSpan(mLight);
        mSpannableAddress = new SpannableString("Street address,P.O. box, etc");
        mSpannableName = new SpannableString("Contact Name");
        mSpannablePhone = new SpannableString("Phone Number");
        mOrderPresenter = new OrderPresenter(this, getApplicationContext());
        initializeViews();
        manipulateViews();
    }

    private void initializeViews() {
        mTextViewOrderConfirmation = findViewById(R.id.textViewOrderConfirmation);
        mEditTextName = findViewById(R.id.edName);
        mEditTextAddress = findViewById(R.id.edAddress);
        mEditTextPhoneNumber = findViewById(R.id.edPhoneNumber);
        spinnerNumber = findViewById(R.id.spinnerNumber);
        mSwitch = findViewById(R.id.switchShipping);
        mButtonDone = findViewById(R.id.buttonDone);
        mTextViewSuccess = findViewById(R.id.txtSuccess);
        mTextViewNote = findViewById(R.id.txtNote);
        mLinearLayoutOverBox = findViewById(R.id.linearOverBox);
        mLinearLayoutSuccess = findViewById(R.id.linearSuccess);
        mImageViewHeart = findViewById(R.id.imageHeart);
        mButtonOk = findViewById(R.id.buttonOk);
        mAnimationFromSmall = AnimationUtils.loadAnimation(this, R.anim.fromsmal);
        mAnimationFromNotthing = AnimationUtils.loadAnimation(this, R.anim.fromnotthing);
        mAnimationForImage = AnimationUtils.loadAnimation(this, R.anim.forimage);
        mAnimationFromZero = AnimationUtils.loadAnimation(this, R.anim.fromzero);

        mTextViewEmptyContact = findViewById(R.id.textViewEmptyContact);
        mTextViewEmptyAddress = findViewById(R.id.textViewEmptyAddress);
        mTextViewEmptyPhone = findViewById(R.id.textViewEmptyPhone);

    }

    private void manipulateViews() {
        mTextViewOrderConfirmation.setTypeface(mRegular);
        mSpannableAddress.setSpan(mTypefaceSpan, 0, mSpannableAddress.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mEditTextAddress.setHint(mSpannableAddress);
        mSpannableName.setSpan(mTypefaceSpan, 0, mSpannableName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mEditTextName.setHint(mSpannableName);
        mEditTextName.setTypeface(mRegular);
        mEditTextAddress.setTypeface(mRegular);
        mEditTextPhoneNumber.setTypeface(mRegular);
        mEditTextPhoneNumber.setHint(mSpannablePhone);
        mTextViewNote.setTypeface(mRegular);
        mTextViewNote.setTypeface(mLight);
        ArrayAdapter<String> countryAreaCodesAdapter = new ArrayAdapter<String>(this
                , R.layout.spinner_item
                , DataCountry.countryAreaCodes);
//        countryAreaCodesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerNumber.setAdapter(countryAreaCodesAdapter);
        mSwitch.setTypeface(mRegular);
        mButtonDone.setTypeface(mLight);
        mLinearLayoutSuccess.setAlpha(0);
        mLinearLayoutOverBox.setAlpha(0);
        mImageViewHeart.setVisibility(View.GONE);
        mSwitch.setOnCheckedChangeListener(this);
        mButtonDone.setOnClickListener(this);
        mButtonOk.setOnClickListener(this);
        mEditTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mEditTextPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mEditTextAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

//       Contact name
        mEditTextName.addTextChangedListener(new GenericTextWatcher(mEditTextName));
        mEditTextAddress.addTextChangedListener(new GenericTextWatcher(mEditTextAddress));
        mEditTextPhoneNumber.addTextChangedListener(new GenericTextWatcher(mEditTextPhoneNumber));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOk:

                mLinearLayoutSuccess.startAnimation(mAnimationFromZero);
                mLinearLayoutOverBox.startAnimation(mAnimationFromZero);
                mImageViewHeart.startAnimation(mAnimationFromZero);
                mImageViewHeart.setVisibility(View.GONE);
                ViewCompat.animate(mLinearLayoutSuccess).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(mLinearLayoutOverBox).setStartDelay(1000).alpha(0).start();
                Intent iHome = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(iHome);
                finish();


                break;
            case R.id.buttonDone:
                if (mEditTextName.getText().toString().trim().isEmpty()) {
                    mEditTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_black_24dp, 0);
                    mTextViewEmptyContact.setVisibility(View.VISIBLE);
                }
                if (mEditTextAddress.getText().toString().trim().isEmpty()) {
                    mEditTextAddress.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_error_black_24dp,0);
                    mTextViewEmptyAddress.setVisibility(View.VISIBLE);
                }
                if (mEditTextPhoneNumber.getText().toString().trim().isEmpty()){
                    mEditTextPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_error_black_24dp,0);
                    mTextViewEmptyPhone.setVisibility(View.VISIBLE);
                }else  {
                    Order order = new Order();
                    String nameCustomer = mEditTextName.getText().toString();
                    String address = mEditTextAddress.getText().toString();
                    String phone = "+" + spinnerNumber.getSelectedItem().toString()
                            + mEditTextPhoneNumber.getText().toString();
                    order.setNameCustomer(nameCustomer);
                    order.setAddress(address);
                    order.setPhoneNumber(phone);
                    order.setShipment(shipment);
                    mOrderPresenter.doOrder(order);
                }
                break;
        }
    }

    @Override
    public void orderSuccess() {
        mLinearLayoutOverBox.setAlpha(1);
        mLinearLayoutOverBox.startAnimation(mAnimationFromNotthing);
        mLinearLayoutSuccess.setAlpha(1);
        mLinearLayoutSuccess.startAnimation(mAnimationFromSmall);
        mImageViewHeart.setVisibility(View.VISIBLE);
        mImageViewHeart.startAnimation(mAnimationForImage);
    }

    @Override
    public void orderFailed() {
        Toast.makeText(this, "Order Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(this, "You choose shipping at Checkout", Toast.LENGTH_SHORT).show();
            shipment = 1;
        } else {
            shipment = 0;
        }
    }
    private class GenericTextWatcher implements TextWatcher{
        private View mView;

        public GenericTextWatcher(View view) {
            mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch(mView.getId()){
                case R.id.edName:
                    mEditTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mTextViewEmptyContact.setVisibility(View.GONE);
                    break;
                case R.id.edAddress:
                    mEditTextAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mTextViewEmptyAddress.setVisibility(View.GONE);
                    break;
                case R.id.edPhoneNumber:
                    mEditTextPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mTextViewEmptyPhone.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
