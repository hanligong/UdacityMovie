package com.udacitymovie.action.fragment;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bluelinelabs.logansquare.LoganSquare;
import com.squareup.picasso.Picasso;
import com.udacitymovie.action.R;
import com.udacitymovie.action.activity.MovieDetailActivity;
import com.udacitymovie.action.contentProvider.MovieContract;
import com.udacitymovie.action.inter.MovieHttpResponseInterface;
import com.udacitymovie.action.model.MoviesModel;
import com.udacitymovie.action.response.MovieObject;
import com.udacitymovie.action.uitls.NetworkUtils;
import com.udacitymovie.action.uitls.OkHttpUtils;
import com.udacitymovie.action.uitls.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.udacitymovie.action.activity.MainActivity;

/**
 * Created by hanyuezi on 17/10/15.
 */
public class MainFragment extends Fragment implements MovieHttpResponseInterface {
    private MainActivity activity;

    private RecyclerView mRv;
    private List<MoviesModel> list;
    private MovieRecycleViewAdapter adapter;

    private TextView mTvError;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<MoviesModel> moviesModels = (List<MoviesModel>) msg.obj;
                    if (null == moviesModels || moviesModels.isEmpty()) {
                        mRv.setVisibility(View.GONE);
                        mTvError.setVisibility(View.VISIBLE);
                        return;
                    }
                    mRv.setVisibility(View.VISIBLE);
                    mTvError.setVisibility(View.GONE);
                    list.clear();
                    list.addAll(moviesModels);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        list = new ArrayList<>();

        mRv = view.findViewById(R.id.rv_main);
        mRv.setLayoutManager(new GridLayoutManager(activity, 2));
        mRv.setItemAnimator(new DefaultItemAnimator());
        adapter = new MovieRecycleViewAdapter();
        mRv.setAdapter(adapter);

        mTvError = view.findViewById(R.id.tv_main_error);


        updateDataByPop();
        return view;
    }

    public void updateDataByPop(){
        OkHttpUtils.requestHttp(activity, NetworkUtils.MOVIE_BASE_URL + "popular?api_key=", this);
    }

    public void updateDataByVoteAverage(){
        OkHttpUtils.requestHttp(activity, NetworkUtils.MOVIE_BASE_URL + "top_rated?api_key=", this);
    }

    /**
     * 获取收藏电影列表
     */
    public void updateDataByFavorite(){
        Uri uri = MovieContract.MovieEntry.getContentUri();
        Cursor cursor = getActivity().getContentResolver().query(uri, MAIN_FORECAST_PROJECTION, null, null, null);
        List<MoviesModel> list = new ArrayList<>();
        for (int i = 0; i< cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            MoviesModel moviesModel = new MoviesModel();
            moviesModel.setId(cursor.getInt(INDEX_COLUMN_ID));
//            moviesModel.setVideo(cursor.getString(INDEX_COLUMN_VIDEO));
            moviesModel.setVote_average(cursor.getDouble(INDEX_COLUMN_VIDEO));
            moviesModel.setTitle(cursor.getString(INDEX_COLUMN_TITLE));
            moviesModel.setPopularity(cursor.getDouble(INDEX_COLUMN_POPULARITY));
            moviesModel.setPoster_path(cursor.getString(INDEX_COLUMN_POSTER_PATH));
            moviesModel.setOriginal_language(cursor.getString(INDEX_COLUMN_ORIGINAL_LANGUAGE));
            moviesModel.setGenre_ids(cursor.getString(INDEX_COLUMN_GENRE_IDS));
            moviesModel.setBackdrop_path(cursor.getString(INDEX_COLUMN_BACKDROP_PATH));
            moviesModel.setOverview(cursor.getString(INDEX_COLUMN_OVERVIEW));
            moviesModel.setRelease_date(cursor.getString(INDEX_COLUMN_RELEASE_DATE));
            moviesModel.setVote_count(cursor.getInt(INDEX_COLUMN_VOTE_COUNT));
            list.add(moviesModel);
        }
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public static final String[] MAIN_FORECAST_PROJECTION = {
            MovieContract.MovieEntry.COLUMN_ID,
            MovieContract.MovieEntry.COLUMN_VOTE_COUNT,
            MovieContract.MovieEntry.COLUMN_VIDEO,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POPULARITY,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.MovieEntry.COLUMN_GENRE_IDS,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_ADULT,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE
    };

    public static final int INDEX_COLUMN_ID = 0;
    public static final int INDEX_COLUMN_VOTE_COUNT = 1;
    public static final int INDEX_COLUMN_VIDEO = 2;
    public static final int INDEX_COLUMN_VOTE_AVERAGE = 3;
    public static final int INDEX_COLUMN_TITLE = 4;
    public static final int INDEX_COLUMN_POPULARITY = 5;
    public static final int INDEX_COLUMN_POSTER_PATH = 6;
    public static final int INDEX_COLUMN_ORIGINAL_LANGUAGE = 7;
    public static final int INDEX_COLUMN_ORIGINAL_TITLE = 8;
    public static final int INDEX_COLUMN_GENRE_IDS = 9;
    public static final int INDEX_COLUMN_BACKDROP_PATH = 10;
    public static final int INDEX_COLUMN_ADULT = 11;
    public static final int INDEX_COLUMN_OVERVIEW = 12;
    public static final int INDEX_COLUMN_RELEASE_DATE = 13;

    @Override
    public void showData(String response) {
        if (TextUtils.isEmpty(response)) {
            return;
        }
        List<MoviesModel> moviesModels;
        MovieObject httpObject;
        try {
            httpObject = LoganSquare.parse(response, MovieObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        moviesModels = httpObject.getResults();
        Message message = new Message();
        message.what = 1;
        message.obj = moviesModels;
        mHandler.sendMessage(message);
    }

    private class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieViewHolder>{

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = activity.getLayoutInflater().inflate(R.layout.activity_main_item, null);
            return new MovieViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, final int position) {
            final MoviesModel moviesModel = (MoviesModel) list.get(position);
            Log.e("", "onBindViewHolder.................." + (NetworkUtils.IMG_BASE_URL + moviesModel.getPoster_path()));

            holder.mIvImg.setLayoutParams(new LinearLayout.LayoutParams(Util.getScreenWidth(activity) / 2, (int) (Util.getScreenWidth
                    (activity) / 2 * 1.5)));
            Picasso.with(activity).load(NetworkUtils.IMG_BASE_URL + moviesModel.getPoster_path()).into(holder.mIvImg);
            holder.mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转详情页
                    Intent intent = new Intent(activity, MovieDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("MovieModel", moviesModel);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView mIvImg;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mIvImg = itemView.findViewById(R.id.iv_main_item);
        }
    }
}
