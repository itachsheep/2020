import 'package:flutter/services.dart';

class LogUtils {
  static const String TAG = "Demo.";
  static const log = const MethodChannel("android_log");

  static Future<String> i(String tag,String msg) async {
    try {
      String result = await log.invokeMethod(
          "logI", {'tag': tag, 'msg': msg});
      return result;
    } on PlatformException catch(e) {
      return "failed";
    }
  }

  static void d(String tag, String msg) {
    log.invokeMethod("logD",{'tag':tag,'msg':msg});
  }

  static void w(String tag, String msg) {
    print(TAG + tag + ": " + msg);
  }

}