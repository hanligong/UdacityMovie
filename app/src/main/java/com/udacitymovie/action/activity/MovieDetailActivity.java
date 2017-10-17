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
import com.udacitymovie.action.model.MoviesModel;
import com.udacitymovie.action.uitls.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanyuezi on 17/10/15.
 */
public class MovieDetailActivity extends BaseActivity{

    private MoviesModel moviesModel;

    @BindView(R.id.tv_detail_username)
    TextView mTvName;
    @BindView(R.id.iv_detail_post)
    ImageView mIvPost;
    @BindView(R.id.tv_detail_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_detail_date)
    TextView mTvDate;
    @BindView(R.id.tv_detail_pop)
    TextView mTvDop;
    @BindView(R.id.tv_detail_language)
    TextView mTvLanguage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Movie Detail");
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        moviesModel = bundle.getParcelable("MovieModel");

        mTvName.setText(moviesModel.getTitle());
        Picasso.with(this).load(NetworkUtils.IMG_BASE_URL + moviesModel.getPoster_path()).into(mIvPost);
        mTvDesc.setText(moviesModel.getOverview());
        mTvDate.setText(moviesModel.getRelease_date());
        mTvDop.setText(moviesModel.getPopularity() + "");
        mTvLanguage.setText(moviesModel.getVote_average() + "");
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
