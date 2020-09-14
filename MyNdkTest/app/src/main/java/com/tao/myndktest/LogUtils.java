package com.tao.myndktest;

import android.util.Log;

public class LogUtils {
    private static String TAG = "MyNdkTest.";
    public static void d(String tag,String mes) {
        Log.d(TAG + tag,mes);
    }
}
