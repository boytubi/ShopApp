package com.example.hoangtruc.shopapp.presenter.review;

import com.example.hoangtruc.shopapp.data.db.model.Review;
import com.example.hoangtruc.shopapp.data.network.model.ServerReview;
import com.example.hoangtruc.shopapp.view.review.ReviewMvpView;

public class ReviewPresenter implements ReviewMvpPresenter {
    private ReviewMvpView mReviewMvpView;
    private ServerReview mServerReview;

    public ReviewPresenter(ReviewMvpView reviewMvpView) {
        mReviewMvpView = reviewMvpView;
        mServerReview=new ServerReview();
    }

    @Override
    public void onSeverReview(Review review) {
        boolean result=mServerReview.resultAddReview(review);
        if (result){
            mReviewMvpView.feedbackSuccessfully();
        }else {
            mReviewMvpView.feedbackFailed();
        }
    }
}
