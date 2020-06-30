import 'package:banner_view/banner_view.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:play_android/item/BannerItem.dart' as bannerItem;

class HomeBannerHelper {

  Widget buildBanner(List<bannerItem.Data> bannerList, var bannerIndex) {
    return new Container(
      child: bannerList.length > 0 ? new BannerView(
        bannerList.map((bannerItem.Data item) {
          return new GestureDetector(
              onTap: () {
                //NavigatorUtils.gotoDetail(context, item.url, item.title);
              },
              child: new Image.network(
                item.imagePath, fit: BoxFit.cover,)
          );
        }).toList(),
        cycleRolling: false,
        autoRolling: true,
        indicatorMargin: 8.0,
        indicatorNormal: this._indicatorItem(
            Colors.white),
        indicatorSelected: this._indicatorItem(
            Colors.white, selected: true),
        indicatorBuilder: (context, indicator) {
          return this._indicatorContainer(indicator,bannerList,bannerIndex);
        },
        onPageChanged: (index) {
          bannerIndex = index;
        },
      ) : new Container(),
      width: double.infinity,
      height: 200.0,
    );
  }

  Widget _indicatorItem(Color color, {bool selected = false}) {
    double size = selected ? 10.0 : 6.0;
    return new Container(
      width: size,
      height: size,
      decoration: new BoxDecoration(
        color: color,
        shape: BoxShape.rectangle,
        borderRadius: new BorderRadius.all(
          new Radius.circular(5.0),
        ),
      ),
    );
  }

  Widget _indicatorContainer(Widget indicator,
      List<bannerItem.Data> bannerList, var bannerIndex) {
    var container = new Container(
      height: 40.0,
      child: new Stack(
        children: <Widget>[
          new Opacity(
            opacity: 0.5,
            child: new Container(
              color: Colors.grey[300],
            ),
          ),
          new Container(
            margin: EdgeInsets.only(right: 10.0),
            child: new Align(
              alignment: Alignment.centerRight,
              child: indicator,
            ),
          ),
          new Align(
              alignment: Alignment.centerLeft,
              child: new Container(
                margin: EdgeInsets.only(left: 15),
                child: new Text(bannerList[bannerIndex].title),
              )
          ),
        ],
      ),
    );
    return new Align(
      alignment: Alignment.bottomCenter,
      child: container,
    );
  }
}