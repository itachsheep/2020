import 'package:flutter/material.dart';
import 'package:flutter_app/widget_frame/AwaitTestWidget.dart';
import 'package:flutter_app/widget_frame/NavigatorTest.dart';
import 'package:flutter_app/widget_frame/NoMaterialWidget.dart';
import 'package:flutter_app/widget_frame/RefreshTestWidget.dart';
import 'package:flutter_app/widget_frame/WebViewTestWidget.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

void main() {
  //runApp(new NoMaterialWidget());
  /**
   * async await实践
   */
  //runApp(new AwaitTestWidget());
  /**
   * 显示加载进度圆圈 spinkitCircle
   */
  //runApp(new RefreshTestWidget());
  /*runApp(new MaterialApp(
    title: "home test",
    home:new Home(),
  ));*/

  /**
   * WebView加载一个页面
   */
  //runApp(new WebViewTestWidget());
  runApp(MaterialApp(
    title: "Bridget Test",
    home: new BridgetWidget(),
  ));

  /**
   * Navigator
   */
  /*runApp(MaterialApp(
    title: 'Navigation Basics',
    home: FirstRoute(),
  ));*/
}
