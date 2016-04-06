package com.fulai.myapplication;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by fulai on 2016/3/21.
 */
public abstract class VolleyInterface<T> {
    private Context context;
    private Response.Listener<T> listener;
    private Response.ErrorListener errorlistener;

    public VolleyInterface(Context context) {
        this.context = context;
    }

    public Response.Listener<T> loadingListener() {
        listener = new Response.Listener<T>() {
            @Override
            public void onResponse(T s) {
                onMySuccess(s);
            }
        };
        return listener;
    }

    public Response.ErrorListener errorlistener() {
        errorlistener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onMyError(volleyError);
            }
        };
        return errorlistener;
    }

    public abstract void onMySuccess(T result);

    public abstract void onMyError(VolleyError volleyError);
}
