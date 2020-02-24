
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class NoMaterialWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    /*return new Container(
      decoration: new BoxDecoration(color: Colors.white),
      child: new Center(
        child: new Text('Hello World',
            textDirection: TextDirection.ltr,
            style: new TextStyle(fontSize: 40.0, color: Colors.black87)),
      ),
    );*/

    return new Center(
      child: new Text('hello world',textDirection: TextDirection.ltr,
      style: new TextStyle(fontSize: 40.0,color: Colors.black),),
    );
  }

}