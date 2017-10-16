package com.udacitymovie.action.uitls;

import android.app.Activity;
import android.view.WindowManager;

import com.udacitymovie.action.model.MoviesModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hanyuezi on 17/10/15.
 */

public class Util {
    public static int getScreenWidth(Activity activity){
        WindowManager wm = (WindowManager) activity.getSystemService(activity.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Activity activity){
        WindowManager wm = (WindowManager) activity.getSystemService(activity.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static void sortMoviesByVoteAverage(List<MoviesModel> list){
        Collections.sort(list, new Comparator<MoviesModel>(){

            @Override
            public int compare(MoviesModel moviesModel1, MoviesModel moviesModel2) {
                /* 返回负数表示：o1 小于o2，
                 * 返回0 表示：o1和o2相等，
                 * 返回正数表示：o1大于o2。 */
                if (moviesModel1.getVote_average() > moviesModel2.getVote_average()) {
                    return -1;
                }
                if (moviesModel1.getVote_average() == moviesModel2.getVote_average()) {
                    return 0;
                }
                return 1;
            }
        });
    }

    public static void sortMoviesByPop(List<MoviesModel> list){
        Collections.sort(list, new Comparator<MoviesModel>(){

            @Override
            public int compare(MoviesModel moviesModel1, MoviesModel moviesModel2) {
                /* 返回负数表示：o1 小于o2，
                 * 返回0 表示：o1和o2相等，
                 * 返回正数表示：o1大于o2。 */
                if (moviesModel1.getPopularity() > moviesModel2.getPopularity()) {
                    return -1;
                }
                if (moviesModel1.getPopularity() == moviesModel2.getPopularity()) {
                    return 0;
                }
                return 1;
            }
        });
    }

}
