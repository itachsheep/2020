import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AwaitTestWidget extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return new MyAdjustState();
  }
}

class MyAdjustState extends State<AwaitTestWidget> {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "Await Pro",
      home: new Scaffold(
        appBar:new AppBar(
          title: new Text("Await Test"),
        ),
        body: new Center(
          child: new Column(
            children: <Widget>[
              new Text('hello world',textDirection: TextDirection.ltr,
                style: new TextStyle(fontSize: 40.0,color: Colors.black),),

              
            ],
          ),
        ),
      ),
    );
  }
}