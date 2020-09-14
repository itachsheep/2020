package com.tao.myndktest;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG,"onCreate");

    }
}
