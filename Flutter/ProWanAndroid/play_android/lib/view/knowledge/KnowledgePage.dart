
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:play_android/http/Http.dart';
import 'package:play_android/item/KnowledgeTreeItem.dart';
import 'package:play_android/http/api/Api.dart';
import 'package:play_android/utils/logutil.dart';

class KnowledgePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new KnowledgeState();
  }

}

class KnowledgeState extends State<KnowledgePage> {
  String TAG = "KnowledgeState";
  bool isLoading = true;
  List<Data> data = [];

  @override
  Widget build(BuildContext context) {
    LogUtils.d(TAG, "build 11 -------isLoading: ${isLoading}" );
    return new Scaffold(
        appBar: AppBar(
          title: Text("知识体系"),
          centerTitle: true,
        ),
        body: isLoading ? SpinKitCircle(
          itemBuilder: (context, int index) {
            return DecoratedBox(
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(5.0)
              ),
            );
          },
        ): data.length > 0 ? ListView(
          children: data.map((data) {
            return new GestureDetector(
                onTap: () {
                  //NavigatorUtils.gotoKnowledgeList(context, data.name, data);
                },
                child: new Card(
                  child: new Row(
                    children: <Widget>[
                      new Expanded(
                        child: new Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            new Container(
                              child: Text(data.name,
                                style: new TextStyle(
                                    fontSize: 18.0,
                                    color: Colors.black
                                ),
                              ),
                              margin: EdgeInsets.only(
                                  left: 10.0, bottom: 10.0, top: 15.0),
                            ),
                            Wrap(
                              children: data.children.map((item) {
                                return new Container(
                                  margin: EdgeInsets.only(
                                      left: 10.0,
                                      right: 10.0,
                                      bottom: 3.0,
                                      top: 3.0),
                                  child: Text(item.name, style: new TextStyle(
                                      color: Colors.grey,
                                      fontSize: 16.0
                                  ),),
                                );
                              }).toList(),
                            )
                          ],
                        ),
                      ),
                      new Icon(Icons.navigate_next),
                    ],
                  ),
                )
            );
          }).toList(),
        ) : new Center(
          child: new Text(
            "没有更多数据哦", style: new TextStyle(fontSize: 20, color: Colors.grey),),
        )
    );
  }

  @override
  void initState() {
    super.initState();
    isLoading = true;
    getKnowledgeData();
  }

  void getKnowledgeData() async {
    var url = Api.KNOWLEDGE_TREE;
    var response = await HttpUtil().get(url);
    var item = KnowledgeTreeItem.fromJson(response);
    setState(() {
      data = item.data;
      isLoading = false;
    });
  }
}