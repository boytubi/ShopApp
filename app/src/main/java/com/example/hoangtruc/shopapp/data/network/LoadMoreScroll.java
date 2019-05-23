package com.example.hoangtruc.shopapp.data.network;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.hoangtruc.shopapp.view.main.DetailListProductActivity;

public class LoadMoreScroll extends RecyclerView.OnScrollListener {
    private boolean loading = true;
    private int mVisibleThresold = 0;
    private int mTotalItemCount = 0;
    private int mFirstInvisibleItem = 0;
    private RecyclerView.LayoutManager mLayoutManager;
    private ILoadMore mILoadMore;
    private Context mContext;

    public LoadMoreScroll(RecyclerView.LayoutManager layoutManager, ILoadMore ILoadMore) {
        mLayoutManager = layoutManager;
        mILoadMore = ILoadMore;

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            mVisibleThresold = mLayoutManager.getChildCount();
            mTotalItemCount = mLayoutManager.getItemCount();
            if (mLayoutManager instanceof LinearLayoutManager) {
                mFirstInvisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            } else if (mLayoutManager instanceof GridLayoutManager) {
                mFirstInvisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            }
            if (loading){
                if((mVisibleThresold+mFirstInvisibleItem)>=mTotalItemCount){
                    loading=false;
                    mILoadMore.onLoadMore(mTotalItemCount);
                }

            }
        }
    }
}
