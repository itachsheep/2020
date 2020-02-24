import 'package:flutter/material.dart';

import 'package:english_words/english_words.dart';
import 'package:flutter_fir_test/random_word/RandomWords.dart';

//void main()  => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    /*return new MaterialApp(
      title: 'Welecom first flutter',
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text('Welecome app bar'),
        ),

        body: new Center(
          //child: new Text('Welecome center'),
          //child: new Text(wordPair.asPascalCase),
          child: new RandomWordsWidget(),
        ),
      ),
    );*/


    return new MaterialApp(
      title: "11111",
      home: new RandomWordsWidget(),
    );
  }

}