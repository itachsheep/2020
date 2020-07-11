package com.tao.flutter_app;

import io.flutter.embedding.engine.plugins.FlutterPlugin;

public class TestFlutterPlugin implements FlutterPlugin {
    @Override
    public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
        flutterPluginBinding.getBinaryMessenger();

    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {

    }
}
