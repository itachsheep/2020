
/**
 * https://www.jianshu.com/p/cc1e65d53074
 * 构造方法
 */
class Test {
  List<String> data;
  int errorCode;
  String errorMsg;

  Test.fromJson() {
    data = new List<String>();
    data.add('111');
    data.add('222');

    errorCode = 0;
    errorMsg = 'this is error';
  }
}