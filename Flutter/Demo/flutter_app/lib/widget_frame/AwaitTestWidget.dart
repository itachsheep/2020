import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/utils/LogUtils.dart';

class AwaitTestWidget extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return new MyAdjustState();
  }
}

class MyAdjustState extends State<AwaitTestWidget> {
  String TAG = 'MyAdjustState';
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
              
              new MaterialButton(color: Colors.blue,textColor: Colors.white, child: new Text('开始测试'),
                  onPressed: _testAwait)
            ],
          ),
        ),
      ),
    );
  }
  
  void _testAwait() {
    LogUtils.d(TAG, "_testAwait start");
//    Future future = new Future((){
//      LogUtils.w(TAG, "future -->");
//    });

    foo();
    LogUtils.d(TAG, "_testAwait end");
  }

  /// https://www.cnblogs.com/lxlx1798/p/11131674.html
  /// 在Dart中，有await标记的运算，其结果值是一个Future对象，
  /// Future并不是String类型，就报错了。那么怎么才正确获得异步的结果呢？
  /// Dart规定async标记的函数，只能由await来调用，
  ///
  /// 在Dart1.9中加入了async和await关键字，有了这两个关键字，我们可以更简洁的编写异步代码，
  /// 而不需要调用Future相关的API。他们允许你像写同步代码一样写异步代码和不需要使用Future接口。
  /// 相当于都Future相关API接口的另一种封装，提供了一种更加简便的操作Future相关API的方法
  ///
  ///    将 async 关键字作为方法声明的后缀时，具有如下意义
  ///
  ///被修饰的方法会将一个 Future 对象作为返回值
  ///该方法会同步执行其中的方法的代码直到第一个 await 关键字，然后它暂停该方法其他部分的执行；
  ///一旦由 await 关键字引用的 Future 任务执行完成，await的下一行代码将立即执行。
  ///
  foo() async {
    LogUtils.d(TAG, "foo start");
    String value = await bar();
    LogUtils.d(TAG, "foo end，res =  $value ");
}

  bar() async {
    LogUtils.d(TAG, "bar start");
    return " -----> bar = 100";
  }
}