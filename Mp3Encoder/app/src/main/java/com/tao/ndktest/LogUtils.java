package com.tao.ndktest;

import android.util.Log;

public class LogUtils {
    private static String TAG = "Mp3Encoder.";
    public  static void i(String tag, String mes) {
        Log.d(TAG + tag ,mes);
    }
}
