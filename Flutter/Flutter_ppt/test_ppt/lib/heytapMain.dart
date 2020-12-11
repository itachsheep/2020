import 'package:flutter/material.dart';

void main() {
  runApp(Text(
    "111",
    textDirection: TextDirection.ltr,
  ));
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    /*return MaterialApp(
      title: "Welcome To Flutter",
      home: Scaffold(
        appBar: AppBar(
          title: Text("This is AppBar"),
        ),
        body: Center(
          child: Text("Center Body"),
        ),
      ),
    );*/

    /*return MaterialApp(
      title: "Welcome To Flutter",
      home: Center(
        child: Column(
          children: [
            Text("11111"),
            Text("2222"),
            Text("2222"),
            Text("2222")
          ],
        ),
      ),
    );*/

    /* return Center(
      child: Text("111111",
        textDirection: TextDirection.ltr, ),
    );*/

    /*return MaterialApp(
     home: Text("eeeesf"),
   );*/

    return Text("111");
  }
}
