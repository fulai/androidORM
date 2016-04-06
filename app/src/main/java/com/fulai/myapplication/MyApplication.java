package com.fulai.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by fulai on 2016/3/16.
 */
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
    /**
     * volley请求队列
     */
    public static RequestQueue queues;
    private static Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        queues = Volley.newRequestQueue(getApplicationContext());
        mRealm = Realm.getInstance(getApplicationContext());
    }

    /**
     * 返回请求队列
     *
     * @return
     */
    public static RequestQueue getHttpQueue() {
        return queues;
    }

    public static Realm getRealm() {
        if (mRealm != null) {
            return mRealm;
        }
        return null;
    }

    public static Realm getOtherRealm(Context mContext, String realmName) {
        Realm tempRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(mContext)
                                .name(realmName)
                                .build()
                );
        return tempRealm;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        thread.setDefaultUncaughtExceptionHandler(this);
    }
}
