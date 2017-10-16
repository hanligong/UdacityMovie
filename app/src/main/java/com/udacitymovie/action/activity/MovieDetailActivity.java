package com.udacitymovie.action.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.udacitymovie.action.R;

/**
 * Created by hanyuezi on 17/10/15.
 */
public class MovieDetailActivity extends BaseActivity{
    public static String EXTRA_NAME = "name";
    public static String EXTRA_IMG_URL = "imgUrl";
    public static String EXTRA_DATE = "date";
    public static String EXTRA_DESC = "desc";
    public static String EXTRA_POP = "pop";
    public static String EXTRA_LANGUAGE = "language";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Movie Detail");
        }

        Intent intent = getIntent();
        String name = intent.getStringExtra(EXTRA_NAME);
        String postUrl = intent.getStringExtra(EXTRA_IMG_URL);
        String desc = intent.getStringExtra(EXTRA_DESC);
        String date = intent.getStringExtra(EXTRA_DATE);
        String pop = intent.getStringExtra(EXTRA_POP);
        String language = intent.getStringExtra(EXTRA_LANGUAGE);

        TextView mTvName = (TextView) findViewById(R.id.tv_detail_username);
        mTvName.setText(name);

        ImageView mIvPost = (ImageView) findViewById(R.id.iv_detail_post);
        Picasso.with(this).load(postUrl).into(mIvPost);

        TextView mTvDesc = (TextView) findViewById(R.id.tv_detail_desc);
        mTvDesc.setText(desc);

        TextView mTvDate = (TextView) findViewById(R.id.tv_detail_date);
        mTvDate.setText(date);

        TextView mTvDop = (TextView) findViewById(R.id.tv_detail_pop);
        mTvDop.setText(pop);

        TextView mTvLanguage = (TextView) findViewById(R.id.tv_detail_language);
        mTvLanguage.setText(language);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
