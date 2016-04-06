package com.fulai.myapplication;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by fulai on 2016/3/21.
 */
public class VolleyStringRequest {
    private static StringRequest stringRequest;
    private static Context mContext;

    public static void requestGet(Context context, String url, String tag, VolleyInterface volleyinterface) {
        mContext = context;
        MyApplication.getHttpQueue().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.GET, url, volleyinterface.loadingListener(), volleyinterface.errorlistener());
        stringRequest.setTag(tag);
        MyApplication.getHttpQueue().add(stringRequest);
        //MyApplication.getHttpQueue().start();
    }

    public static void requestPost(Context context, String url, String tag, final Map<String, String> map, VolleyInterface volleyinterface) {
        mContext = context;
        MyApplication.getHttpQueue().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.POST, url, volleyinterface.loadingListener(), volleyinterface.errorlistener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        stringRequest.setTag(tag);
        MyApplication.getHttpQueue().add(stringRequest);
        //MyApplication.getHttpQueue().start();
    }

}
