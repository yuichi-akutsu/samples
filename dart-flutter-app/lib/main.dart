/*
 * MIT License
 *
 * Copyright (c) 2020 yuichi.akutsu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

import 'package:flutter/material.dart';
import 'package:flutter_sample/constant.dart';
import 'package:flutter_sample/ui/detail/detail_page.dart';
import 'package:flutter_sample/ui/top/top_page.dart';
import 'package:get/get.dart';
import 'package:hooks_riverpod/all.dart';

/// エントリーポイント
void main() {
  // まだよくわからないけど、これが無いと落ちる TODO 確認
  WidgetsFlutterBinding.ensureInitialized();
  // Riverpod(状態管理ライブラリ)配下でアプリを動かす
  runApp(ProviderScope(child: App()));
}

/// アプリ
class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Get(ルート管理ライブラリ)を利用(画面遷移を簡素化する為)
    // Getは状態管理, DIとかもできるらしいけどRiverPodと重複するので、ルーティング(画面遷移)のみに利用
    // TODO RiverPodでやってる箇所をGet使うとどんな感じになるか気が向いたら試す
    return GetMaterialApp(
      title: 'Sample', // TODO 不要？どこで使われるかわからないので確
      // テーマ設定(青)
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      // 初期起動ページ設定
      home: TopPage(),
      // ここでパスに対応するPage(Widget)を指定
      // WebでいうURLに画面を対応させるようなイメージ
      routes: {
        // トップページ
        Constant.pageTop: (context) => TopPage(),
        // 詳細ページ
        Constant.pageDetail: (context) => DetailPage()
      },
    );
  }
}
