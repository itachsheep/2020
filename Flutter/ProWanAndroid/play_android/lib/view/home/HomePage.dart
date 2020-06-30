import 'dart:async';

import 'package:flutter/material.dart';
//import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:play_android/http/api/Api.dart';
import 'package:play_android/item/BannerItem.dart' as bannerItem;
//import 'package:mou/item/HomeGridItem.dart';
import 'package:play_android/item/HomeItem.dart' as homeItem;
import 'package:play_android/utils/CommonUtil.dart';
import 'package:play_android/utils/NavigatorUtils.dart';
//import 'package:mou/utils/Toast.dart';
import 'dart:math';
import 'package:banner_view/banner_view.dart';
import 'package:play_android/http/Http.dart';
import 'package:play_android/utils/logutil.dart';
import 'package:play_android/view/home/HomeBannerHelper.dart';
import 'package:play_android/view/home/HomeListViewHelper.dart';
//import 'package:mou/view/SearchPage.dart';

class HomePage extends StatefulWidget {
  final String TAG = "HomePage";
  @override
  State<StatefulWidget> createState() {
    return new HomeState();
  }
}

class HomeState extends State<HomePage> {
  List<homeItem.Datas> homeData = [];
  List<bannerItem.Data> bannerList = [];
  final int headerCount = 1;
  var bannerIndex = 0;
  final int pageSize = 20;
  var page = 0;
  HomeListViewHelper homeListViewHelper;
  HomeBannerHelper homeBannerHelper;
  //是否在加载
  bool isLoading = false;

  //是否有更多数据
  bool isHasNoMore = false;

  //这个key用来在不是手动下拉，而是点击某个button或其它操作时，代码直接触发下拉刷新
  final GlobalKey<RefreshIndicatorState> _refreshIndicatorKey = new GlobalKey<
      RefreshIndicatorState>();
  final ScrollController _scrollController = new ScrollController(keepScrollOffset: false);

  @override
  Widget build(BuildContext context) {
    return new DefaultTabController(
        length: 2,
        child: new Scaffold(
            appBar: new AppBar(
              title: new Text("首页"),
              centerTitle: true,
              actions: <Widget>[
                new IconButton(
                    icon: new Icon(Icons.search),
                    onPressed: () {
                      NavigatorUtils.gotoSearch(context);
                    }
                )
              ],
            ),
            body: new RefreshIndicator(
                color: Colors.green,
                child: buildCustomScrollView(), onRefresh: refreshHelper)
        )
    );
  }

  @override
  void initState() {
    super.initState();
    //todo:
    LogUtils.d(TAG, "initState");
    homeListViewHelper = new HomeListViewHelper(context);
    homeBannerHelper = new HomeBannerHelper();

    getBannerList();
    getNewsListData(false);
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        if (!isLoading) {
          page++;
          getNewsListData(true);
        }
      }
    });
  }

  Future<Null> refreshHelper() {
    final Completer<Null> completer = new Completer<Null>();
    LogUtils.d(TAG, "refreshHelper");
    //清空数据
    homeData.clear();
    bannerList.clear();
    page = 0;
    getNewsListData(false, completer);
    getBannerList();
    return completer.future;
  }

  ListView buildCustomScrollView() {
    return new ListView.builder(

      ///保持ListView任何情况都能滚动，解决在RefreshIndicator的兼容问题。
      physics: const AlwaysScrollableScrollPhysics(),
      key: _refreshIndicatorKey,
      itemCount: homeData.length + headerCount + 1,
      controller: _scrollController,
      itemBuilder: (context, index) {
        if (index == 0) {
          return homeBannerHelper.buildBanner(bannerList, bannerIndex);
        } else {
          //return getItem(index - headerCount);
          return homeListViewHelper.getItem(homeData,
              (index - headerCount), isHasNoMore, isLoading);
        }
      },);
  }

  void getNewsListData(bool isLoadMore, [Completer completer]) async {
    if (isLoadMore) {
      setState(() => isLoading = true);
    }

    var response = await HttpUtil().get(
        Api.HOME_LIST + page.toString() + "/json");
    var item = new homeItem.HomeItem.fromJson(response);
    completer?.complete();
    if (item.data.datas.length < pageSize) {
      isHasNoMore = true;
    } else {
      isHasNoMore = false;
    }
    if (isLoadMore) {
      isLoading = false;
      homeData.addAll(item.data.datas);
      setState(() {});
    } else {
      setState(() {
        homeData = item.data.datas;
      });
    }
  }

  //获取轮播图接口
  void getBannerList() async {
    var response = await HttpUtil().get(Api.BANNER_LIST);
    var item = new bannerItem.BannerItem.fromJson(response);
    bannerList = item.data;
    setState(() {});
  }

  @override
  void dispose() {
    super.dispose();
    _scrollController.dispose();
  }
}


