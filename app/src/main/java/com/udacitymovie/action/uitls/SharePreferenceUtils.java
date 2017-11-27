package com.udacitymovie.action.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hanyuezi on 17/11/24.
 */

public class SharePreferenceUtils {

    public static void saveIntSharePreference(Activity activity, int sort){
        SharedPreferences sp = activity.getSharedPreferences("movie", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("movieSort", sort);
        editor.commit();
    }

    public static int getIntSharePreference(Activity activity){
        SharedPreferences sp = activity.getSharedPreferences("movie", Context.MODE_PRIVATE);
        return sp.getInt("movieSort", 0);
    }

}
