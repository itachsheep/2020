import 'package:play_android/utils/logutil.dart';

class ProjectTreeItem {
  List<Data> data;
  int errorCode;
  String errorMsg;

  ProjectTreeItem({this.data,this.errorCode,this.errorMsg});

  ProjectTreeItem.fromJson(Map<String,dynamic> json) {
    if(json['data'] != null) {
      data = new List<Data>();
      json['data'].forEach((v){
        data.add(new Data.fromJson(v));
        LogUtils.d('ProjectTreeItem', " http ---- " + v.toString());
      });
    }
    errorCode = json['errorCode'];
    errorMsg = json['errorMsg'];
  }


}

class Data {
  List<Null> children;
  int courseId;
  int id;
  String name;
  int order;
  int parentChapterId;
  bool userControlSetTop;
  int visible;

  Data(
      {this.children,
      this.courseId,
      this.id,
      this.name,
      this.order,
      this.parentChapterId,
      this.userControlSetTop,
      this.visible});

  Data.fromJson(Map<String,dynamic> json) {
    courseId = json['courseId'];
    id = json['id'];
    name = json['name'];
    order = json['order'];
    parentChapterId = json['parentChapterId'];
    userControlSetTop = json['userControlSetTop'];
    visible = json['visible'];
  }

  Map<String,dynamic> toJson() {
    final Map<String, dynamic> data = new Map();
    data['courseId'] = this.courseId;
    data['id'] = this.id;
    data['name'] = this.name;
    data['order'] = this.order;
    data['parentChapterId'] = this.parentChapterId;
    data['userControlSetTop'] = this.userControlSetTop;
    data['visible'] = this.visible;
    return data;
  }
}
