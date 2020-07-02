import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:play_android/http/Http.dart';
import 'package:play_android/http/api/Api.dart';
import 'package:play_android/item/ProjectTreeItem.dart';
import 'package:play_android/utils/logutil.dart';
import 'package:play_android/item/ProjectListItem.dart' as proListItem;
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
            return ;
          }).toList()),
        )
    );
  }
}

class ProjectListContent extends StatefulWidget {
  final int id;
  ProjectListContent(this.id,{Key key}):super(key: key);

  @override
  State<StatefulWidget> createState() {

    return new ProjectListState();
  }

}

class ProjectListState extends StatefulWidget {
  var pageSize = 15;
  var page = 0;
  bool isHasMore = false;
  List<proListItem.Datas> data = [];
  final int cid;

  final GlobalKey<RefreshIndicatorState> _refreshIndicatorKey =
      new GlobalKey<RefreshIndicatorState>();

  final ScrollController _scrollController =
      new ScrollController(keepScrollOffset: false);

  ProjectListState(this.cid);

  @override
  State<StatefulWidget> createState() {

    return null;
  }

}