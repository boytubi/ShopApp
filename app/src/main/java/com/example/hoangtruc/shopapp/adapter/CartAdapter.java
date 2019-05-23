package com.example.hoangtruc.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hoangtruc.shopapp.OnItemCLickListener;
import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.data.db.DatabaseHelper;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.presenter.cart.CartPresenter;
import com.example.hoangtruc.shopapp.presenter.detailproduct.DetailProductPresenter;
import com.example.hoangtruc.shopapp.view.DetailProductActivity;
import com.example.hoangtruc.shopapp.view.cart.CartActivity;
import com.example.hoangtruc.shopapp.view.cart.CartMvpView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHoderCart>   {
    private List<Product> mProductList;
    private Context mContext;
    private DatabaseHelper databaseHelper;
    private Typeface mRegular;
    private CartPresenter mCartPresenter;
    private CartMvpView mCartMvpView;
    public CartAdapter(List<Product> productList, Context context,CartMvpView cartMvpView) {
        mProductList = productList;
        mContext = context;
        databaseHelper = new DatabaseHelper(mContext);
        mCartMvpView=cartMvpView;

    }

    @NonNull
    @Override
    public ViewHoderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_recycler_cart, parent, false);
        ViewHoderCart viewHoderCart = new ViewHoderCart(view);

        return viewHoderCart;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoderCart holder, final int position) {
        final Product product = mProductList.get(position);
        final int idProduct = product.getID();
        final Context context = holder.mView.getContext();
        byte[] bitmapdata = product.getImageCart();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        holder.mImageViewCart.setImageBitmap(bitmap);

        holder.mTextViewNameCart.setText(product.getNameProduct());
        holder.setOnItemCLickListener(new OnItemCLickListener() {
            @Override
            public void onClickDetail(View view, int pos, boolean isLongClick) {
                Intent iDetailProduct = new Intent(context, DetailProductActivity.class);
                iDetailProduct.putExtra("IDProduct", idProduct);
                context.startActivity(iDetailProduct);
            }
        });
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.deleteProduct(idProduct);
                mProductList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.mElegantNumberButton.setRange(1, product.getTotal());
        holder.mElegantNumberButton.setNumber(String.valueOf(product.getQuantityPurchased()));

        final int priceForOneProduct = product.getPrice();
        Log.d("Quantity ", String.valueOf(priceForOneProduct));
        final NumberFormat format = new DecimalFormat("###,###");
        String price = format.format(product.getTotalPrice());
        holder.mTextViewPriceCart.setTypeface(mRegular);
        holder.mTextViewPriceCart.setText(price + " VND");

        holder.mElegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                int priceUpdate = priceForOneProduct * newValue;
                databaseHelper.updateQuantity(idProduct, newValue, priceUpdate);
                holder.mTextViewPriceCart.setText(format.format(priceUpdate) + " VND");
               if(oldValue<newValue) {
                   mCartMvpView.setTotalChange(priceForOneProduct);
               }else {
                   mCartMvpView.setTotalChange(-priceForOneProduct);

               }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }



    public class ViewHoderCart extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageViewCart;
        TextView mTextViewNameCart, mTextViewPriceCart;
        public OnItemCLickListener mOnItemCLickListener;
        View mView;
        ImageButton mImageButton;
        ElegantNumberButton mElegantNumberButton;

        public ViewHoderCart(View itemView) {
            super(itemView);
            mView = itemView;
            mImageViewCart = itemView.findViewById(R.id.imageProductCart);
            mTextViewNameCart = itemView.findViewById(R.id.textViewNameProductCart);
            mImageButton = itemView.findViewById(R.id.imBtnDeleteCart);
            mElegantNumberButton = itemView.findViewById(R.id.elegantButton);
            mTextViewPriceCart = itemView.findViewById(R.id.txtPriceCart);
            mView.setOnClickListener(this);
            mImageButton.setOnClickListener(this);

            mRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/MRegular.ttf");

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
