//package com.udacitymovie.action;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.util.Log;
//import com.udacitymovie.action.inter.MovieHttpResponseInterface;
//import com.udacitymovie.action.inter.MovieTrailerResponseInterface;
//import com.udacitymovie.action.uitls.NetworkUtils;
//import java.io.IOException;
//import java.net.URL;
//
///**
// * Created by hanyuezi on 17/11/22.
// */
//
//public class HttpTask extends AsyncTask<String, Void, String>{
//
//    private Activity activity;
//    private MovieHttpResponseInterface httpResponse;
//    private MovieTrailerResponseInterface trailerHttpResponse;
//
//    private int type;
//
//    public HttpTask(Activity activity, MovieHttpResponseInterface httpResponse){
//        this.activity = activity;
//        this.httpResponse = httpResponse;
//    }
//
//    public HttpTask(Activity activity, MovieTrailerResponseInterface trailerHttpResponse, int type){
//        this.activity = activity;
//        this.trailerHttpResponse = trailerHttpResponse;
//        this.type = type;
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        if (params.length == 0) {
//            return null;
//        }
//        String location = params[0];
//        URL url;
//        if (params.length == 2) {
//            url = NetworkUtils.buildUrl(activity, location, params[1]);
//        } else {
//            url = NetworkUtils.buildUrl(activity, location);
//        }
//
//        Log.e("", "MainActivity url.................." +url);
//        try {
//            String response = NetworkUtils.getResponseFromHttpUrl(url);
//            return  response;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String response) {
//        super.onPostExecute(response);
//        if (type == 1) {
//            if (null != trailerHttpResponse) {
//                trailerHttpResponse.getTrailerUrl(response);
//            }
//            return;
//        }
//        if (null != response) {
//            httpResponse.showData(response);
//        }
//    }
//}
