package com.example.hoangtruc.shopapp.view.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hoangtruc.shopapp.OnItemCLickListener;
import com.example.hoangtruc.shopapp.R;
import com.example.hoangtruc.shopapp.adapter.TopDeviceAdapter;
import com.example.hoangtruc.shopapp.data.db.model.Product;
import com.example.hoangtruc.shopapp.presenter.search.SearchPresenter;
import com.example.hoangtruc.shopapp.view.SearchViewFormatter;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchMvpView {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TopDeviceAdapter mTopDeviceAdapter;
    private SearchPresenter mSearchPresenter;
    private SearchView mSearchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mToolbar = findViewById(R.id.toolbarSearch);
        mRecyclerView = findViewById(R.id.recyclerSearch);
        setSupportActionBar(mToolbar);
        mSearchPresenter=new SearchPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchViewFormatter searchViewFormatter = new SearchViewFormatter();
        searchViewFormatter
                .setSearchBackGroundResource(R.drawable.bg_searchview)
                .setSearchIconResource(R.drawable.ic_search_white_24dp, true, false) //true to icon inside edittext, false to outside
                .setSearchTextColorResource(R.color.colorWhite)
                .setSearchHintColorResource(R.color.colorGray)
                .setSearchHintText("Search")
                .setSearchCloseIconResource(R.drawable.ic_close_black_24dp)
                .setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                .format(mSearchView);
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                this.finish();
                break;
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchPresenter.getListResult(query);
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void displayResult(List<Product> products) {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mTopDeviceAdapter = new TopDeviceAdapter(this, products, R.layout.custom_horizontal_item_list);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTopDeviceAdapter);
        mTopDeviceAdapter.notifyDataSetChanged();
    }
}
