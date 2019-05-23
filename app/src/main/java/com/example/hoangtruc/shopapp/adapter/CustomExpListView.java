package com.example.hoangtruc.shopapp.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ExpandableListView;

public class CustomExpListView extends ExpandableListView {
    public CustomExpListView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        WindowManager windowManager= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Display display=windowManager.getDefaultDisplay();
//        Point outSize=new Point();
//        display.getSize(outSize);
//        int width=outSize.x;
//        int height=outSize.y;
//        heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        } catch (IllegalArgumentException e) {
        }
    }
}
