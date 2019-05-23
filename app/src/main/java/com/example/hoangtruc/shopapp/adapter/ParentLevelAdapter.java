package com.example.hoangtruc.shopapp.adapter;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.data.db.model.ProductType;
import com.example.hoangtruc.shopapp.data.network.model.ProductTypeTask;

import java.util.List;

public class ParentLevelAdapter extends BaseExpandableListAdapter {
    private List<ProductType> mProductTypeList;
    private Context mContext;
    private int mLastPosition = -1;
    private CustomExpListView customExpListView;

    public ParentLevelAdapter(List<ProductType> mProductTypeList, Context context) {
        this.mProductTypeList = mProductTypeList;
        mContext = context;
        ProductTypeTask productTypeTask = new ProductTypeTask();
        int count = mProductTypeList.size();

        for (int i = 0; i < count; i++) {
            int productTypeID = mProductTypeList.get(i).getProductTypeId();
            mProductTypeList.get(i).setListChildType(productTypeTask.getListProductType(productTypeID));
        }

    }

    @Override
    public int getGroupCount() {
        return mProductTypeList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (mProductTypeList.get(i).getListChildType().size() != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return mProductTypeList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mProductTypeList.get(i).getListChildType().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return mProductTypeList.get(i).getProductTypeId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return mProductTypeList.get(i).getListChildType().get(i1).getProductTypeId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        View view_group = view;
        GroupViewHolder viewHolder;
        if (view_group == null) {
            viewHolder = new GroupViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view_group = inflater.inflate(R.layout.layout_group_menu, viewGroup, false);

            viewHolder.txtNameProduct = view_group.findViewById(R.id.textview_name_product);
            viewHolder.imgExpand = view_group.findViewById(R.id.image_expand);
            view_group.setTag(viewHolder);
        }else {
            viewHolder= (GroupViewHolder) view_group.getTag();
        }
        int countListChild = mProductTypeList.get(i).getListChildType().size();
        if (countListChild > 0) {
            viewHolder.imgExpand.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgExpand.setVisibility(View.INVISIBLE);
        }

        if (isExpanded) {
            viewHolder.imgExpand.setImageResource(R.drawable.ic_remove_black_24dp);
        } else {
            viewHolder.imgExpand.setImageResource(R.drawable.ic_add_black_24dp);
        }


        viewHolder.txtNameProduct.setText(mProductTypeList.get(i).getNameType());

        return view_group;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        customExpListView = new CustomExpListView(mContext);
        ParentLevelAdapter adapter = new ParentLevelAdapter(mProductTypeList.get(i).getListChildType(), mContext);
        customExpListView.setAdapter(adapter);
        customExpListView.setGroupIndicator(null);

        int padding = customExpListView.getResources().getDimensionPixelOffset(R.dimen.dp_16);
        customExpListView.setPadding(padding, 0, 0, 0);
        adapter.notifyDataSetChanged();
        return customExpListView;
    }

    private class GroupViewHolder {
        TextView txtNameProduct;
        ImageView imgExpand;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}
