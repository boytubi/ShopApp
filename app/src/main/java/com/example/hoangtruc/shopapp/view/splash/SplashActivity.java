package com.example.hoangtruc.shopapp.view.splash;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.view.main.MainActivity;
import com.google.android.gms.common.util.Base64Utils;

public class SplashActivity extends AppCompatActivity {
    private LottieAnimationView mLottieAnimationView;
    private Button mButtonGetStarted;
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLottieAnimationView=findViewById(R.id.lottieAnimationView);
        startCheckAnimation();
        mButtonGetStarted=findViewById(R.id.buttonGetStarted);
        mButtonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSplash = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intentSplash);
                finish();
            }
        });

    }

    private void runSplash() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception ex) {

                } finally {
                    Intent intentSplash = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intentSplash);
                    finish();
                }
            }
        });
        thread.start();
    }
    private void startCheckAnimation(){
        ValueAnimator animator=ValueAnimator.ofFloat(0f,1f).setDuration(8000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mLottieAnimationView.setProgress((float)valueAnimator.getAnimatedValue());
            }
        });
        if (mLottieAnimationView.getProgress()==0f){
            animator.start();
        }else {
            mLottieAnimationView.setProgress(0f);
        }
    }

}
