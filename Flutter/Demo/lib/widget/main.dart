import 'package:flutter/material.dart';
import 'package:flutter_app/widget/CustomAppbarWidget.dart';
import 'package:flutter_app/widget/AwaitTestWidget.dart';
import 'package:flutter_app/widget/NavigatorTest.dart';
import 'package:flutter_app/widget/NoMaterialWidget.dart';
import 'package:flutter_app/widget/RefreshTestWidget.dart';
import 'package:flutter_app/widget/WebViewTestWidget.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

void main() {
  //runApp(new NoMaterialWidget());
  /**
   * 1,async await实践
   */
  //runApp(new AwaitTestWidget());
  /**
   * 2,显示加载进度圆圈 spinkitCircle
   */
  //runApp(new RefreshTestWidget());
  /*runApp(new MaterialApp(
    title: "home test",
    home:new Home(),
  ));*/

  /**
   * 3,WebView加载一个页面
   */
  //runApp(new WebViewTestWidget());
  /*runApp(MaterialApp(
    title: "Bridget Test",
    home: new BridgetWidget(),
  ));*/

  /**
   * 4, Navigator 导航使用
   */
  /*runApp(MaterialApp(
    title: 'Navigation Basics',
    home: FirstRoute(),
  ));*/

  /**
   * 5，自定义标题栏 没做好。。
   */
  runApp(new MaterialApp(
    title: "test",
    home: new Scaffold(
      body: new CustomAppbarWidget(title: '日历',
          leadingWidget: new RaisedButton(child: new Text("<-"),),
          trailingWidget: new RaisedButton(child: new Text("->"),)),
    ),
  ));

}
