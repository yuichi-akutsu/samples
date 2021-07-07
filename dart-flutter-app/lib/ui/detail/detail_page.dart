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

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import 'package:flutter_sample/component/loading.dart';
import 'package:flutter_sample/model/item.dart';
import 'package:flutter_sample/ui/detail/detail_view_model.dart';
import 'package:get/get.dart';
import 'package:hooks_riverpod/all.dart';

/// 詳細ページ
class DetailPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // 遷移元から引き渡されたパラメータ取得
    String id = Get.arguments;

    return Scaffold(
      appBar: AppBar(
        title: Text("detail"),
      ),
      body: HookBuilder(
        builder: (context) {
          // providerからViewModelのインスタンス取得
          // TODO ここからsnapshot取得までがイマイチ理解できてないので、確認
          final detailViewModel = context.read(detailViewModelNotifierProvider);
          Item item = useProvider(
              detailViewModelNotifierProvider.select((value) => value.item));
          detailViewModel.getItem(id);

          // TODO パラメータありの場合のuseMemoizedの使い方を調べる
          // 暫定でitemのnull判定で代用(ページ再表示に前回の表示が残ってしまう)
          // final snapshot = useFuture(detailViewModel.getItem(id));
          // final snapshot = useFuture(useMemoized(detailViewModel.getItem));

          // if (snapshot.connectionState == ConnectionState.waiting) {
          if (item == null) {
            return Loading();
          }

          // TODO なんか、もうちょっと出すけど、Topで出してるしモチベーション無いなぁ
          return Text(item.name);
        },
      ),
    );
  }
}
