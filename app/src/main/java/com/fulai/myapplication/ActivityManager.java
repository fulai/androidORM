package com.fulai.myapplication;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by fulai on 2016/3/16.
 */
public class ActivityManager {
    private static ActivityManager am = new ActivityManager();
    private WeakReference<Activity> mWeakReference = null;

    private ActivityManager() {
    }

    public static synchronized ActivityManager getInstance() {
        return am;
    }

    public Activity getCurrentActivity() {
        Activity activity = null;
        if (mWeakReference != null) {
            activity = mWeakReference.get();
        }
        return activity;
    }

    /**
     * 获取前台activity
     *
     * @param activity
     */
    public void setCurrentActivity(Activity activity) {
        mWeakReference = new WeakReference<Activity>(activity);
    }


}
