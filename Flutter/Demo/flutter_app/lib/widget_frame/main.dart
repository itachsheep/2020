import 'package:flutter/material.dart';
import 'package:flutter_app/widget_frame/AwaitTestWidget.dart';
import 'package:flutter_app/widget_frame/NoMaterialWidget.dart';
import 'package:flutter_app/widget_frame/RefreshTestWidget.dart';
import 'package:flutter_app/widget_frame/WebViewTestWidget.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

void main() {
  //runApp(new NoMaterialWidget());
  //runApp(new AwaitTestWidget());
  //runApp(new RefreshTestWidget());

  /**
   * 显示加载进度圆圈 spinkitCircle
   */
  /*runApp(new MaterialApp(
    title: "home test",
    home:new Home(),
  ));*/

  /**
   * webview加载一个页面
   */
  //runApp(new BridgetWidget());
  runApp(new WebViewTestWidget());

  /*runApp(new MaterialApp(
    title: "11111111",
    home: new Scaffold(
      body: new Center(
        child: new Text('hello world',textDirection: TextDirection.ltr,),
      ),
    ),
  ));*/
}
