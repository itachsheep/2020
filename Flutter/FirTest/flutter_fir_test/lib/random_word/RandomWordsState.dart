
import 'dart:math';

import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

import 'RandomWords.dart';

class RandomWordsState extends State<RandomWordsWidget> {

  final _suggestions = <WordPair>[];
  final _save = new Set<WordPair>();
  final _biggerFont = const TextStyle(fontSize: 18.0);

  @override
  Widget build(BuildContext context) {
    /*final wordPair = new WordPair.random();
    return new Text(wordPair.asPascalCase);*/

    return new Scaffold(
      appBar:  new AppBar(

        title: new Text('StartUp name Generator'),

        actions: <Widget>[
          new IconButton(icon: new Icon(Icons.list), onPressed: _pushSaved)
        ],
      ),

      body: _buildSuggestions(),
    );
  }

  Widget _buildSuggestions() {

    return new ListView.builder(

        padding: const EdgeInsets.all(16.0),

        itemBuilder: (context,i) {
          if (i.isOdd) return new Divider();
          final index = i ~/ 2;
          if (index >= _suggestions.length) {
            _suggestions.addAll(generateWordPairs().take(10));
          }
          return _buildRow(_suggestions[index]);
        }

    );
  }

  Widget _buildRow(WordPair pair) {
    final alreadySave = _save.contains(pair);

    return new ListTile(

      title: new Text(pair.asPascalCase,style: _biggerFont),

      trailing: new Icon(alreadySave ? Icons.favorite : Icons.favorite_border,
      color: alreadySave ? Colors.red : null,),

      onTap: () {
        setState(() {
          if(alreadySave) {
            _save.remove(pair);
          } else {
            _save.add(pair);
          }
        });
      },
    );
  }

  void _pushSaved() {
    Navigator.of(context).push(
      new MaterialPageRoute(
          builder: (context) {

            final tiles = _save.map((pair){
              return new ListTile(
                title: new Text(pair.asPascalCase,
                style: _biggerFont,),
              );
            });

            final divided = ListTile.divideTiles(
                context: context, tiles: tiles)
                .toList();

            return new Scaffold(
              appBar: new AppBar(
                title: new Text("Saved Suggestions"),
              ),

              body: new ListView(children:divided),

            );
          }
      )
    );
  }

}