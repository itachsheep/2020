import 'ConstructorTest.dart';

/// https://zhuanlan.zhihu.com/p/88728224
///
///Dart语法篇之基础语法(一)
void main() {
  dynamic color = 'black';
  print(color);
  color = 0x00010;
  print(color);

  List<String> data;
  data = new List<String>();
  data.add("zhangsan");
  data.add('lisi');
  data.add('wangwu');

  data.forEach((v){
    print(v);
  });

  var item = Test.fromJson();
  
}