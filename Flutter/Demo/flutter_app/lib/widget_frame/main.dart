import 'package:flutter/material.dart';
import 'package:flutter_app/widget_frame/AwaitTestWidget.dart';
import 'package:flutter_app/widget_frame/NoMaterialWidget.dart';

void main() {
  //runApp(new NoMaterialWidget());
  runApp(new AwaitTestWidget());

  /*runApp(new MaterialApp(
    title: "11111111",
    home: new Scaffold(
      body: new Center(
        child: new Text('hello world',textDirection: TextDirection.ltr,),
      ),
    ),
  ));*/
}
