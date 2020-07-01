package com.tao.flutter_app;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.nio.ByteBuffer;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  private static final String TAG = "MainActivity";
  private MethodChannel methodChannel;

  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    GeneratedPluginRegistrant.registerWith(flutterEngine);
    //flutterEngine.getPlugins().add
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    BinaryMessenger messenger = new BinaryMessenger() {
      @Override
      public void send(String s, ByteBuffer byteBuffer) {
        DebugLog.i(TAG,"send s1: " + s);
      }

      @Override
      public void send(String s, ByteBuffer byteBuffer, BinaryReply binaryReply) {
        DebugLog.i(TAG,"send s2: " + s);
      }

      @Override
      public void setMessageHandler(String s, BinaryMessageHandler binaryMessageHandler) {
        DebugLog.i(TAG,"setMessageHandler s: " + s);
      }
    };
    //new MethodChannel(getFlutterEngine())
    methodChannel = new MethodChannel(messenger, "android_log");
    methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
      @Override
      public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.method.equals("logI")) {
          String tag = (String)methodCall.argument("tag");
          String msg = (String)methodCall.argument("msg");
          DebugLog.i(tag,msg);
          //result.success("success!!");
        } else if(methodCall.method.equals("logD")) {
          DebugLog.i(TAG,"onMethodCall logD --------- ");
          //result.success("success DebugLogD");
          //callFlutter();
        }
      }
    });

  }

  private void callFlutter() {
    DebugLog.i(TAG,"callFlutter");
    methodChannel.invokeMethod("getName", null,
            new MethodChannel.Result() {
              @Override
              public void success(Object o) {
                DebugLog.i(TAG,"callFlutter success");
                String res = (String)o;
                DebugLog.i(TAG,"callFlutter result: " + res);
              }

              @Override
              public void error(String s, String s1, Object o) {
                DebugLog.i(TAG,"callFlutter error");
              }

              @Override
              public void notImplemented() {
                DebugLog.i(TAG,"callFlutter notImplemented");
              }
            });
  }
}
