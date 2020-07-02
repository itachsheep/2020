import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:play_android/http/Http.dart';
import 'package:play_android/http/api/Api.dart';
import 'package:play_android/item/ProjectTreeItem.dart';
import 'package:play_android/utils/logutil.dart';
class ProjectPage extends StatefulWidget {
  ProjectPage({Key key}) : super(key:key);

  @override
  State<StatefulWidget> createState() {
    return new _ProjectPageState();
  }
}
class _ProjectPageState extends State<ProjectPage> {
  String tag = "ProjectPageState";
  List<Data> data = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _getTabList();
  }

  void _getTabList() async {
    var url = Api.PROJECT_TREE;
    var response = await HttpUtil().get(url);
    var item = ProjectTreeItem.fromJson(response);
    LogUtils.d(tag,"_getTabList item: " + item.toString());
    setState(() {
      data = item.data;
    });
  }


  @override
  Widget build(BuildContext context) {
    return data.length == 0 ?
    SpinKitCircle(
      itemBuilder: (_, int index){
        return DecoratedBox(
            decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(5.0),
            color: Colors.grey
            ),
        );
      },
    )
    :new DefaultTabController(
        length: data.length,
        child: new Scaffold(
          appBar: AppBar(
            title: Text("项目"),
            centerTitle: true,
            bottom: TabBar(
              isScrollable: true,
              tabs: data.map((item){
                return new Tab(
                  text: item.name,
                );
              }).toList(),
            ),
          ),
          body: new TabBarView(children: data.map((item){
            return Text(item.name + "-" + item.courseId.toString());
          }).toList()),
        )
    );

  }
}