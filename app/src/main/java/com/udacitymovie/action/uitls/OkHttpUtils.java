package com.udacitymovie.action.uitls;

import android.app.Activity;
import android.text.TextUtils;
import com.udacitymovie.action.inter.MovieHttpResponseInterface;
import com.udacitymovie.action.inter.MovieTrailerResponseInterface;
import java.io.IOException;
import java.net.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by hanyuezi on 17/11/23.
 */
public class OkHttpUtils {

    public static void requestHttp(final Activity activity, String url, final MovieHttpResponseInterface responseInterface){
        if (TextUtils.isEmpty(url)) {
            return;
        }
        URL buildUrl = NetworkUtils.buildUrl(activity, url);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(buildUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseInterface.showData(response.body().string());
                }
            }
        });
    }

    public static void requestHttp(Activity activity, String url, String page, final MovieTrailerResponseInterface responseInterface){
        if (TextUtils.isEmpty(url)) {
            return;
        }
        URL buildUrl = NetworkUtils.buildUrl(activity, url, page);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(buildUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                responseInterface.getTrailerUrl(body.string());
            }
        });
    }
}
