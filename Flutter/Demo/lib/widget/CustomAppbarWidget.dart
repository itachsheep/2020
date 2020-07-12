import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// 这是一个可以指定SafeArea区域背景色的AppBar
/// PreferredSizeWidget提供指定高度的方法
/// 如果没有约束其高度，则会使用PreferredSizeWidget指定的高度
class CustomAppbarWidget extends StatefulWidget implements PreferredSizeWidget {
  final double contentHeight; //从外部指定高度
  Color navigationBarBackgroundColor; //设置导航栏背景的颜色
  Widget leadingWidget;
  Widget trailingWidget;
  String title;

  CustomAppbarWidget({
    @required this.leadingWidget,
    @required this.title,
    this.contentHeight = 44,
    this.navigationBarBackgroundColor = Colors.white,
    this.trailingWidget,
  }) : super();

  @override
  State<StatefulWidget> createState() {
    return new _CustomAppbarState();
  }

  @override
  Size get preferredSize => new Size.fromHeight(contentHeight);

}

class _CustomAppbarState extends State<CustomAppbarWidget> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new Container(
      color: widget.navigationBarBackgroundColor,
      child: new SafeArea(
        top: true,
        child: new Container(
            decoration: new UnderlineTabIndicator(
              borderSide: BorderSide(width: 1.0, color: Color(0xFFeeeeee)),
            ),
            height: widget.contentHeight,
            child: new Stack(
              alignment: Alignment.center,
              children: <Widget>[
                Positioned(
                  left: 0,
                  child: new Container(
                    padding: const EdgeInsets.only(left: 5),
                    child: widget.leadingWidget,
                  ),
                ),
                new Container(
                  child: new Text(widget.title,
                      style: new TextStyle(
                          fontSize: 17, color: Color(0xFF333333))),
                ),
                Positioned(
                  right: 0,
                  child: new Container(
                    padding: const EdgeInsets.only(right: 5),
                    child: widget.trailingWidget,
                  ),
                ),
              ],
            )),
      ),
    );
  }

}