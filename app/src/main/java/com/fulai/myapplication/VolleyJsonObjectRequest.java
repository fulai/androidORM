package com.fulai.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by fulai on 2016/3/22.
 */
public class VolleyJsonObjectRequest {
    public static void requestGet(Context context, String url, String tag, VolleyInterface volleyInterface) {
        MyApplication.getHttpQueue().cancelAll(tag);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, volleyInterface.loadingListener(), volleyInterface.errorlistener());
        jsonObjectRequest.setTag(tag);
        MyApplication.getHttpQueue().add(jsonObjectRequest);
    }

    public static void requestPost(Context context, final String url, String tag, final Map<String, String> map, VolleyInterface volleyInterface) {
        final String string = JsonObjectToString(map, url);
        MyApplication.getHttpQueue().cancelAll(tag);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, volleyInterface.loadingListener(), volleyInterface.errorlistener()) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
            }

            @Override
            public byte[] getBody() {
                try {
                    return string == null ? null : string.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            string, "utf-8");
                    return null;
                }
            }
        };
        jsonObjectRequest.setTag(tag);
        MyApplication.getHttpQueue().add(jsonObjectRequest);
    }

    public static String JsonObjectToString(Map<String, String> map, String url) {
        Uri uri = Uri.parse(url);
        Uri.Builder builder = uri.buildUpon();
        for (Map.Entry<String, String> mapIndex : map.entrySet()) {
            builder.appendQueryParameter(mapIndex.getKey(), mapIndex.getValue());
        }
        Log.i("MainActivity", builder.build().getQuery());
        return builder.build().getQuery();
    }
}
