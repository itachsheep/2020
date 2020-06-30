import 'package:banner_view/banner_view.dart';
import 'package:flutter/material.dart';
import 'package:play_android/item/HomeItem.dart' as homeItem;
import 'package:play_android/utils/CommonUtil.dart';
import 'package:play_android/utils/logutil.dart';

class HomeListViewHelper {
  BuildContext _context;
  HomeListViewHelper(BuildContext context) {
    _context = context;
  }

  Widget getItem(List<homeItem.Datas> homeData,int i, bool isHasNoMore
      ,bool isLoading) {
    if (i == homeData.length) {
      if (isHasNoMore) {
        return buildNoMoreData();
      } else {
        return buildLoadMoreLoading(isLoading);
      }
    } else {
      var item = homeData[i];
      var date = DateTime.fromMillisecondsSinceEpoch(
          item.publishTime, isUtc: true);
      //return buildCardItem(item, date, i);
      return buildCardItem(homeData, item, date, i);
    }
  }

  Widget buildLoadMoreLoading(bool isLoading) {
    return new Padding(
      padding: const EdgeInsets.all(8.0),
      child: new Center(
        child: new Opacity(
          opacity: isLoading ? 1.0 : 0.0,
          child: new Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
//              SpinKitFadingCircle(
//                color: Colors.grey,
//                size: 30.0,
//              ),
              new Padding(padding: EdgeInsets.only(left: 10)),
              new Text("正在加载更多...")
            ],
          ),
        ),
      ),
    );
  }


  Widget buildNoMoreData() {
    return new Container(
      margin: EdgeInsets.only(top: 15.0, bottom: 15.0),
      alignment: Alignment.center,
      child: new Text("没有更多数据了"),
    );
  }

  Card buildCardItem(List<homeItem.Datas> homeData,
      homeItem.Datas item, DateTime date, int index) {
    return new Card(
        child: new InkWell(
          onTap: () {
            var url = homeData[index].link;
            var title = homeData[index].title;
            LogUtils.d(TAG, "on  item tap title ---- " + title + ", url: " + url);
            //NavigatorUtils.gotoDetail(context, url, title);
          },
          child: new Container(
            margin: EdgeInsets.only(left: 10, right: 10, top: 10, bottom: 10.0),
            child: new Column(
              children: <Widget>[
                new Row(
                  children: <Widget>[
                    new Container(
                      decoration: BoxDecoration(
                          borderRadius: new BorderRadius.circular(3.0),
                          border: new Border.all(
                              color: Colors.blue
                          )
                      ),
                      child: new Text(item.superChapterName,
                        style: new TextStyle(
                            color: Colors.blue
                        ),
                      ),
                    ),

                    new Container(
                      margin: EdgeInsets.only(left: 5.0),
                      child: new Text(item.author),

                    ),

                    new Expanded(child: new Container()),

                    new Text(
                      "${date.year}年${date.month}月${date.day}日 ${date
                          .hour}:${date.minute}",
                      style: new TextStyle(
                          fontSize: 12.0,
                          color: Colors.grey
                      ),
                    ),
                  ],
                ),
                new Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    new Container(
                      height: 80.0,
                      child: new Column(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          new Container(
                            width: CommonUtil.getScreenWidth(_context) - 100,
                            child: new Text(item.title,
                              softWrap: true, //换行
                              maxLines: 2,
                              style: new TextStyle(fontSize: 16.0),
                            ),
                            margin: EdgeInsets.only(top: 10.0),
                          ),
                          new Container(
                            child: new Text(
                              item.superChapterName + "/" + item.author,
                              style: new TextStyle(color: Colors.grey),
                            ),
                          ),
                        ],
                      ),
                    ),

                    item.envelopePic.isEmpty ? new Container(
                      width: 60.0,
                      height: 60.0,)
                        : new Image.network(
                      item.envelopePic,
                      width: 60.0,
                      height: 60.0,
                      fit: BoxFit.cover,
                    ),
                  ],
                ),
              ],
            ),
          ),
        )
    );
  }

}