import 'package:flutter/material.dart';
import 'package:testppt/logutil.dart';
final String TAG = "TestStatefulWidget";

class SampleStatefulWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
   return MaterialApp(
      title: "SampleStatefulWidget Demo",
      home: Scaffold(
        appBar: AppBar(
          title: Text("SampleStatefulWidget"),
        ),
        body: TestStatefulWidget()
        ),
      );
  }

}
class TestStatefulWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyState();
  }
}
class MyState extends State<TestStatefulWidget> {
  int _counter = 0;
  void _increment() {
    LogUtils.d(TAG, "increment");
    setState(() {//重新触发build方法
      _counter++;
    });
  }
  @override
  Widget build(BuildContext context) {
    LogUtils.d(TAG, "build");
    return Row(
      children: <Widget>[
        RaisedButton(
          onPressed: _increment,
          child: Text(
              "Increment",
            textDirection: TextDirection.ltr,
          ),
        ),
        Text("Count: $_counter",
          textDirection: TextDirection.ltr,
        )
      ],
    );
  }

  @override
  void initState() {
    super.initState();
    LogUtils.d(TAG, "initState");
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    LogUtils.d(TAG, "didChangeDependencies");
  }

  @override
  void didUpdateWidget(TestStatefulWidget oldWidget) {
    super.didUpdateWidget(oldWidget);
    LogUtils.d(TAG, "didUpdateWidget");
  }

  @override
  void deactivate() {
    super.deactivate();
    LogUtils.d(TAG, "deactivate");
  }

  @override
  void dispose() {
    super.dispose();
    LogUtils.d(TAG, "dispose");
  }


//  @override
//  void didChangeAppLifecycleState(AppLifecycleState state) {
//    print(state.toString());
//  }
//

}