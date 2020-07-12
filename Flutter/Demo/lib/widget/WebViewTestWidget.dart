import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/utils/LogUtils.dart';
import 'package:flutter_app/widget/RefreshTestWidget.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

class WebViewTestWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new WebViewTestState();
  }
}

class WebViewTestState extends State<WebViewTestWidget> {
  @override
  Widget build(BuildContext context) {
    //var url = ;
    return new MaterialApp(
      routes: {
        "/": (_) => new WebviewScaffold(
          url: "https://www.wanandroid.com/blog/show/2767",
          appBar: new AppBar(
            title: new Text("Widget webview"),
          ),
          withZoom: true,
          withLocalStorage: true,
          hidden: true,
          initialChild: Container(
            color: Colors.grey,
            child: const Center(
              child: Text('Waiting.....'),
            ),
          ),
        ),
      },
    );
  }
}

class BridgetWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: AppBar(
          title: new Text("BridgetWidget"),
        ),
        body: new Column(
          children: <Widget>[
            RaisedButton(
              child: Text("跳转 page2"),
              onPressed: (){
                LogUtils.d(TAG, "onPress  -----> goto page2");
                Navigator.push(
                  context,
                  CupertinoPageRoute(builder: (context) => Page2()),
                );
              },
            ),

            RaisedButton(
              child: Text("跳转 WebViewTestWidget2"),
              onPressed: (){
                LogUtils.d(TAG, "onPress CupertinoPageRoute -----> goto WebViewTestWidget2");
                Navigator.push(
                  context,
                  CupertinoPageRoute(builder: (context) => WebViewTestWidget2()),
                );
              },
            )
          ],
        ),
    );
  }
}
class WebViewTestWidget2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new WebviewScaffold(url: "https://www.wanandroid.com/blog/show/2767",
      appBar: new AppBar(
        title: new Text("WebViewTestWidget2"),
      ),
      withZoom: false,
      withLocalStorage: true,
      withJavascript: true,
    );
  }

}
class Page2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: new Text("Page2"),
        ),
        body: new Center(
          child: new Text("this is page2",
            style: new TextStyle(color: Colors.blue,fontSize: 50.0),
          ),
        ),
      );
  }
}

