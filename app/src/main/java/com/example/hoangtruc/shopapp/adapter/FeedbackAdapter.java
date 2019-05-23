package com.example.hoangtruc.shopapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.data.db.model.Review;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackHolder> {
    private Context mContext;
    private List<Review> mReviewList;
    private int limit;

    public FeedbackAdapter(Context context, List<Review> reviewList, int limit) {
        mContext = context;
        mReviewList = reviewList;
        this.limit = limit;
    }

    @NonNull
    @Override
    public FeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_feedback, parent, false);
        FeedbackHolder holder = new FeedbackHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackHolder holder, int position) {
        Review review = mReviewList.get(position);

        holder.mTextViewDate.setText(review.getDate());
//        holder.mTextViewTitle.setText(review.getTitle());
        holder.mTextViewReview.setText(review.getReview());
        holder.mRatingBar.setRating(review.getRating());
        Log.d("Date ",review.getDate());
    }

    @Override
    public int getItemCount() {
        if (mReviewList.size()<limit){
            return mReviewList.size();
        }else {
            if (limit!=0&&mReviewList.size()>limit){
                return limit;
            }else {
                return mReviewList.size();
            }
        }
    }

    public class FeedbackHolder extends RecyclerView.ViewHolder {
        TextView   mTextViewDate, mTextViewTitle, mTextViewReview;
        RatingBar mRatingBar;

        public FeedbackHolder(View itemView) {
            super(itemView);

            mTextViewDate = itemView.findViewById(R.id.textDate);
//            mTextViewTitle = itemView.findViewById(R.id.textviewTitle);
            mTextViewReview = itemView.findViewById(R.id.textviewReview);
            mRatingBar = itemView.findViewById(R.id.ratingFeedback);
        }
    }
}
