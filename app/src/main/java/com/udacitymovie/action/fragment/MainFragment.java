package com.udacitymovie.action.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.udacitymovie.action.model.HttpObject;
import com.udacitymovie.action.model.MoviesModel;
import com.udacitymovie.action.uitls.NetworkUtils;
import com.udacitymovie.action.uitls.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.udacitymovie.action.activity.MainActivity;

/**
 * Created by hanyuezi on 17/10/15.
 */

public class MainFragment extends Fragment{
    private MainActivity activity;

    private RecyclerView mRv;
    private List<MoviesModel> list;
    private MovieRecycleViewAdapter adapter;

    private TextView mTvError;

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

        mRv = (RecyclerView) view.findViewById(R.id.rv_main);
        mRv.setLayoutManager(new GridLayoutManager(activity, 2));
        mRv.setItemAnimator(new DefaultItemAnimator());
        adapter = new MovieRecycleViewAdapter();
        mRv.setAdapter(adapter);

        mTvError = (TextView) view.findViewById(R.id.tv_main_error);

        new MovieTask().execute(NetworkUtils.MOVIES_BASE_URL);
        return view;
    }

    public void updateDataByPop(){
        Util.sortMoviesByPop(list);
        adapter.notifyDataSetChanged();
    }

    public void updateDataByVoteAverage(){
        Util.sortMoviesByVoteAverage(list);
        adapter.notifyDataSetChanged();
    }

    class MovieTask extends AsyncTask<String, Void, List<MoviesModel>> {

        @Override
        protected List<MoviesModel> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String location = params[0];
            URL url = NetworkUtils.buildUrl(activity, location);

            Log.e("", "MainActivity url.................." +url);
            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                List<MoviesModel> Arrlist = new ArrayList<>();
                try {
                    HttpObject httpObject = LoganSquare.parse(response, HttpObject.class);
                    Arrlist = httpObject.getResults();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("", "MainActivity.................." +response);
                return Arrlist;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MoviesModel> moviesModels) {
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
        }
    }

    private class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieViewHolder>{

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = activity.getLayoutInflater().inflate(R.layout.activity_main_item, null);
            return new MovieViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, final int position) {
            Log.e("", "onBindViewHolder.................." + (NetworkUtils.IMG_BASE_URL + list.get(position).getPoster_path()));

            holder.mIvImg.setLayoutParams(new LinearLayout.LayoutParams(Util.getScreenWidth(activity) / 2, (int) (Util.getScreenWidth
                    (activity) / 2 * 1.5)));
            Picasso.with(activity).load(NetworkUtils.IMG_BASE_URL + list.get(position).getPoster_path()).into(holder.mIvImg);
            holder.mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转详情页
                    Intent intent = new Intent(activity, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailActivity.EXTRA_NAME, list.get(position).getTitle());
                    intent.putExtra(MovieDetailActivity.EXTRA_IMG_URL, NetworkUtils.IMG_BASE_URL + list.get(position).getPoster_path());
                    intent.putExtra(MovieDetailActivity.EXTRA_DATE, list.get(position).getRelease_date());
                    intent.putExtra(MovieDetailActivity.EXTRA_DESC, list.get(position).getOverview());
                    intent.putExtra(MovieDetailActivity.EXTRA_POP, list.get(position).getVote_average() + "");
                    intent.putExtra(MovieDetailActivity.EXTRA_LANGUAGE, list.get(position).getPopularity() + "");
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
