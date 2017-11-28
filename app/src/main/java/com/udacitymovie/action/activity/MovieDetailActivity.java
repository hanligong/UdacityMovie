package com.udacitymovie.action.activity;

import android.content.ActivityNotFoundException;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.squareup.picasso.Picasso;
import com.udacitymovie.action.R;
import com.udacitymovie.action.contentProvider.MovieContract;
import com.udacitymovie.action.fragment.MainFragment;
import com.udacitymovie.action.inter.MovieHttpResponseInterface;
import com.udacitymovie.action.inter.MovieTrailerResponseInterface;
import com.udacitymovie.action.model.MoviesModel;
import com.udacitymovie.action.model.TrailerModel;
import com.udacitymovie.action.model.VideoModel;
import com.udacitymovie.action.response.TrailerObject;
import com.udacitymovie.action.response.VideoObject;
import com.udacitymovie.action.uitls.NetworkUtils;
import com.udacitymovie.action.uitls.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanyuezi on 17/10/15.
 */
public class MovieDetailActivity extends BaseActivity implements MovieHttpResponseInterface, MovieTrailerResponseInterface {

    private MoviesModel moviesModel;

    @BindView(R.id.tv_detail_username)
    TextView mTvName;
    @BindView(R.id.iv_detail_post)
    ImageView mIvPost;

    @BindView(R.id.detailMarkIv)
    ImageView mMarkIv;
    @BindView(R.id.detailMarkTv)
    TextView mMarkTv;

    @BindView(R.id.tv_detail_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_detail_date)
    TextView mTvDate;
    @BindView(R.id.tv_detail_pop)
    TextView mTvDop;
    @BindView(R.id.tv_detail_language)
    TextView mTvLanguage;

    @BindView(R.id.detailRv)
    RecyclerView mRv;

    @BindView(R.id.trailerTv)
    TextView mTrailerTv;

    private TrailerAdapter mTrailerAdapter;
    private List<VideoModel> list;

    private String trailerUrl;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    List<VideoModel> videoModels = (List<VideoModel>) msg.obj;
                    if (videoModels == null || videoModels.isEmpty()) {
                        return;
                    }
                    list.clear();
                    list.addAll(videoModels);
                    mTrailerAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    String content = (String) msg.obj;
                    mTrailerTv.setText(content);
            }
        }
    };


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

        list = new ArrayList();
        mRv.setLayoutManager(new GridLayoutManager(this, 1));
        mRv.setItemAnimator(new DefaultItemAnimator());
        mTrailerAdapter = new TrailerAdapter();
        mRv.setAdapter(mTrailerAdapter);

        //TODO 请求接口
        // https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US
        // https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=<<api_key>>&language=en-US&page=1
        OkHttpUtils.requestHttp(this, NetworkUtils.MOVIE_BASE_URL + moviesModel.getId() + "/videos?api_key=", this);

        OkHttpUtils.requestHttp(MovieDetailActivity.this, NetworkUtils.MOVIE_BASE_URL + moviesModel.getId() + "/reviews?api_key=", "&page=1", MovieDetailActivity.this);

        refreshMarkStatus();
    }

    private void refreshMarkStatus(){
        Uri uri = MovieContract.MovieEntry.getContentUri();
        Cursor cursor = getContentResolver()
                .query(uri,
                        MainFragment.MAIN_FORECAST_PROJECTION,
                        MovieContract.MovieEntry.COLUMN_POSTER_PATH + " = ? ",
                        new String[] { moviesModel.getPoster_path()},
                        null);
        if (null != cursor && cursor.getCount()> 0) {
            mMarkIv.setVisibility(View.GONE);
            mMarkTv.setVisibility(View.VISIBLE);
        } else {
            mMarkIv.setVisibility(View.VISIBLE);
            mMarkTv.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.detailMarkTv)
    void clickCancleMark() {
        Uri uri = MovieContract.MovieEntry.getContentUri();
        int row = getContentResolver().delete(uri, MovieContract.MovieEntry.COLUMN_POSTER_PATH + " = ?", new String[]{moviesModel.getPoster_path()});
        Log.e("MovieDetailActivity","delete" + row + "");
        if (row > 0) {
            Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
            refreshMarkStatus();
        } else {
            Toast.makeText(this, "取消收藏失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.detailMarkIv)
    void clickMark(){
        List<ContentValues> values = new ArrayList<ContentValues>();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, moviesModel.getVote_count());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, moviesModel.isVideo());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, moviesModel.getVote_average());
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, moviesModel.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, moviesModel.getPopularity());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, moviesModel.getPoster_path());
        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, moviesModel.getOriginal_language());
        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, moviesModel.getOriginal_title());
        contentValues.put(MovieContract.MovieEntry.COLUMN_GENRE_IDS, moviesModel.getGenre_ids());
        contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, moviesModel.getBackdrop_path());
        contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT, moviesModel.isAdult());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, moviesModel.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, moviesModel.getRelease_date());
        values.add(contentValues);
        int row = getContentResolver().bulkInsert(MovieContract.MovieEntry.getContentUri(), values.toArray(new ContentValues[1]));
        if (row > 0) {
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
            refreshMarkStatus();
        } else {
            Toast.makeText(this, "收藏失败", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void getTrailerUrl(String response) {
        if (TextUtils.isEmpty(response)) {
            return;
        }
        TrailerObject httpObject;
        try {
            httpObject = LoganSquare.parse(response, TrailerObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        List<TrailerModel> trailerModel = httpObject.getResults();
        if (null == trailerModel || trailerModel.isEmpty()) {
            return;
        }

        String contents = "";
        for (int i = 0;i < trailerModel.size();i++) {
            contents  = contents + trailerModel.get(i).getContent() + "\n";
        }
        Log.e("detail", contents);
        Message message = new Message();
        message.what = 3;
        message.obj = contents;
        handler.sendMessage(message);
    }

    @Override
    public void showData(String response) {
        if (TextUtils.isEmpty(response)) {
            return;
        }
        List<VideoModel> videoModels;
        VideoObject httpObject;
        try {
            httpObject = LoganSquare.parse(response, VideoObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        videoModels = httpObject.getResults();
        Message message = new Message();
        message.what = 2;
        message.obj = videoModels;
        handler.sendMessage(message);
    }

    class TrailerAdapter extends RecyclerView.Adapter<MovieDetailItemHolder>{

        @Override
        public MovieDetailItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.activity_detail_item, null);
            return new MovieDetailItemHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieDetailItemHolder holder, final int position) {
            holder.mTitleTv.setText("Trailer" + (position + 1));
            holder.mCl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + list.get(0).getKey()));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + list.get(0).getKey()));
                    try {
                        startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        startActivity(webIntent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (null == list || list.isEmpty()) {
                return 0;
            }
            return list.size();
        }
    }

    class MovieDetailItemHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTv;
        private ConstraintLayout mCl;

        public MovieDetailItemHolder(View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.detailTitleTv);
            mCl = itemView.findViewById(R.id.detailItemCl);
        }
    }
}
