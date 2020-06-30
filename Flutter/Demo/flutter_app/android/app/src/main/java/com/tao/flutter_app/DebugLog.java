package com.tao.flutter_app;

import android.util.Log;

public class DebugLog {

    private static final String TAG = "DebugLog.";
    public static void i(String tag, String msg) {
        Log.i(TAG + tag,msg);
    }
}
