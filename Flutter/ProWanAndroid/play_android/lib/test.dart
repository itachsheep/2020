import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class TestPage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() {

    return new TestPageState();
  }
}

class TestPageState extends State<TestPage> {
  static int num = 1;
  TestPageState() {
    num++;
  }

  @override
  Widget build(BuildContext context) {

    return new Text('这是第 ${num}个界面',style: new TextStyle(color: Colors.black));
  }


  add({a, b ,c,callback}) {

    return a +  b + c;
  }
}