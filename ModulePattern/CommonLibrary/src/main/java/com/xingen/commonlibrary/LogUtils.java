package com.xingen.commonlibrary;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "Tao.";
    public static void d(String tag,String mes) {
        Log.d(TAG + tag,mes);
    }
}
