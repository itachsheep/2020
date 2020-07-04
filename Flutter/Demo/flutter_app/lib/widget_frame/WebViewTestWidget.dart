import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/utils/LogUtils.dart';
import 'package:flutter_app/widget_frame/RefreshTestWidget.dart';
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

    return new MaterialApp(
      title: "111",
      home: new Scaffold(
        appBar: AppBar(
          title: new Text("哈哈"),
        ),
        body: new Column(
          children: <Widget>[
            RaisedButton(
              child: Text("跳转2"),
              onPressed: (){
                LogUtils.d(TAG, "onpress -----> goto webview");
                Navigator.push(context,
                    new CupertinoPageRoute(builder: (context) =>
                    new WebViewTestWidget()));
              },
            )
          ],
        ),
      ),
    );
  }


}
