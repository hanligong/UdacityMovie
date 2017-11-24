package com.udacitymovie.action.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import com.udacitymovie.action.R;
import com.udacitymovie.action.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private MainFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Pop Movies");
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        this.fragment = (MainFragment) fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popularity_level:
                fragment.updateDataByPop();
                break;
            case R.id.menu_score:
                fragment.updateDataByVoteAverage();
                break;
            case R.id.menu_favorite:
                fragment.updateDataByFavorite();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
