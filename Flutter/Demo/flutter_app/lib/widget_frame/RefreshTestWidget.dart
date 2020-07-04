
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/utils/LogUtils.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

final String TAG = "RefreshTestWidget";
class RefreshTestWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new RefreshTestState();
  }
}

class RefreshTestState extends State<RefreshTestWidget> {
  List<String> data = [];
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    LogUtils.d(TAG, " build -----> ");
    return new MaterialApp(
      title: "Refresh Test",
      home: new Scaffold(
          appBar: new AppBar(
            title: new Text("hahah"),
          ),
          body: new Column(
            children: <Widget>[
          RaisedButton(
            child: Text('打33 loading'),
            onPressed: (){
              LogUtils.d(TAG, "onPress -> ");
              LoadingPage loadingPage = LoadingPage(context);
              loadingPage.show();
            },
          )
            ],
          )
      )
    );
  }
}

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('首页'),
        elevation: 0.0,
      ),
      body: Column(
        children: <Widget>[
          RaisedButton(
            child: Text('打开Loading'),
            onPressed: () {
              LoadingPage loadingPage = LoadingPage(context);
              loadingPage.show();
              Future.delayed(
                Duration(seconds: 3),
                    () {
                  loadingPage.close();
                },
              );
            },
          ),
        ],
      ),
    );
  }
}

class LoadingPage {
  final BuildContext _context;

  LoadingPage(this._context);

  ///打开loading
  void show({Function onClosed}) {
    showDialog(
      context: _context,
      builder: (context) {
        return SpinKitCircle(
          itemBuilder: (_, int index){
            return DecoratedBox(
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(5.0),
                  color: Colors.grey
              ),
            );
          },
        );
      },
    ).then((value) {
      onClosed(value);
    });
  }

  ///关闭loading
  void close() {
    Navigator.of(_context).pop();
  }
}