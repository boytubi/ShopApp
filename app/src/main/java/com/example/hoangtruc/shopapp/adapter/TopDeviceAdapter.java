package com.example.hoangtruc.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hoangtruc.shopapp.OnItemCLickListener;
import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.view.DetailProductActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopDeviceAdapter extends RecyclerView.Adapter<TopDeviceAdapter.ViewHolderTopDevices> {
    private Context mContext;
    private List<Product> mProductList;
      int mLayout;
    private Typeface mLight ;
    private Typeface mRegular ;
    public TopDeviceAdapter(Context context, List<Product> productList, int layout) {
        mContext = context;
        mProductList = productList;
        mLayout = layout;

        mLight=Typeface.createFromAsset(mContext.getAssets(),"fonts/MLight.ttf");
          mRegular=Typeface.createFromAsset(mContext.getAssets(),"fonts/MRegular.ttf");
    }

    @NonNull
    @Override
    public TopDeviceAdapter.ViewHolderTopDevices onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mLayout, parent, false);
        ViewHolderTopDevices viewHolderTopDevices = new ViewHolderTopDevices(view);

        return viewHolderTopDevices;
    }

    @Override
    public void onBindViewHolder(@NonNull final TopDeviceAdapter.ViewHolderTopDevices holder, int position) {
        Product product = mProductList.get(position);
        final Context context=holder.mView.getContext();
        holder.mTextViewTopDevices.setTypeface(mLight);
        holder.mTextViewTopDevices.setText(product.getNameProduct());
        holder.mTextViewPrice.setTypeface(mRegular);
        holder.mTextViewPrice.setText(String.valueOf(product.getPrice()));

        final int idProduct=product.getID();

        Picasso.get()
                .load(product.getImage())
                .into(holder.mImageViewTopDevices, new Callback() {
                    @Override
                    public void onSuccess() {
//                         holder.mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.setItemCLickListener(new OnItemCLickListener() {
            @Override
            public void onClickDetail(View view, int pos, boolean isLongClick) {
                Intent intent=new Intent(context, DetailProductActivity.class);
                intent.putExtra("IDProduct",idProduct);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class ViewHolderTopDevices extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextViewTopDevices;
        TextView mTextViewPrice;
        ImageView mImageViewTopDevices;
//        ProgressBar mProgressBar;
        OnItemCLickListener mItemCLickListener;
        View mView;
        public ViewHolderTopDevices(View itemView) {
            super(itemView);
            mView=itemView;
            mTextViewTopDevices = itemView.findViewById(R.id.textTopDevice);
            mImageViewTopDevices = itemView.findViewById(R.id.imageTopDevice);
//            mProgressBar=itemView.findViewById(R.id.progress_bar_TopDevice);
            mTextViewPrice=itemView.findViewById(R.id.textPriceDevice);
            mView.setOnClickListener(this);
        }

        public void setItemCLickListener(OnItemCLickListener itemCLickListener) {
            mItemCLickListener = itemCLickListener;
        }

        @Override
        public void onClick(View view) {
            mItemCLickListener.onClickDetail(mView,getAdapterPosition(),false);
        }
    }
}
