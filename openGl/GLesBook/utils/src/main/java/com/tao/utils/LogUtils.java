package com.tao.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "GLDemo.";

    public static void d(String tag, String msg) {
        Log.d(TAG + tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.d(TAG + tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(TAG + tag, msg);
    }

    public static void v(String tag, String msg) {
        Log.w(TAG + tag, msg);
    }
}
