package com.example.hoangtruc.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hoangtruc.shopapp.OnItemCLickListener;
import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.common.Constant;
import com.example.hoangtruc.shopapp.data.db.model.Brand;
import com.example.hoangtruc.shopapp.view.main.DetailListProductActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopBrandAdapter extends RecyclerView.Adapter<TopBrandAdapter.ViewHolderTopBrands> {
    private Context mContext;
    private List<Brand> mBrandList;
    private boolean isCheck;
    private Typeface mLight;
    private Typeface mRegular;

    public TopBrandAdapter(Context context, List<Brand> brandList, boolean isCheck) {
        mContext = context;
        mBrandList = brandList;
        this.isCheck = isCheck;
        mLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/MLight.ttf");
        mRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/MRegular.ttf");
    }

    @NonNull
    @Override
    public ViewHolderTopBrands onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_top_brand, parent, false);
        ViewHolderTopBrands viewHolderTopBrands = new ViewHolderTopBrands(view);
        return viewHolderTopBrands;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderTopBrands holder, int position) {
        final Brand brand = mBrandList.get(position);
        holder.mTextViewTopBrand.setTypeface(mRegular);

        final Context context = holder.mView.getContext();
        if (isCheck) {
            holder.mTextViewTopBrand.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams((int)Constant.convertDpToPixel(120,mContext),(int)Constant.convertDpToPixel(60,mContext));
            holder.mLinearLayout.setLayoutParams(layoutParams);
            holder.mLinearLayout.requestLayout();

        } else {
            holder.mTextViewTopBrand.setText(brand.getNameBrand());
        }

        Picasso.get()
                .load(brand.getImage())
                .into(holder.mImageTopBrand, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.setOnItemCLickListener(new OnItemCLickListener() {
            @Override
            public void onClickDetail(View view, int pos, boolean isLongClick) {
                Intent iDetailListProducts = new Intent(context, DetailListProductActivity.class);
                iDetailListProducts.putExtra("ID", brand.getID());
                iDetailListProducts.putExtra("Check", isCheck);
                context.startActivity(iDetailListProducts);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBrandList.size();
    }

    public class ViewHolderTopBrands extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageTopBrand;
        TextView mTextViewTopBrand;
        ProgressBar mProgressBar;
        public View mView;
        public OnItemCLickListener mOnItemCLickListener;
        LinearLayout mLinearLayout;
        public ViewHolderTopBrands(View itemView) {
            super(itemView);
            mImageTopBrand = itemView.findViewById(R.id.imageTopBrand);
            mTextViewTopBrand = itemView.findViewById(R.id.textTopBrand);
            mProgressBar = itemView.findViewById(R.id.progress_bar_TopBrand);
            mLinearLayout=itemView.findViewById(R.id.linearBrand);
            mView = itemView;
            itemView.setOnClickListener(this);
        }

        public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
            mOnItemCLickListener = onItemCLickListener;
        }

        @Override
        public void onClick(View view) {
            mOnItemCLickListener.onClickDetail(view, getAdapterPosition(), false);
        }
    }
}
