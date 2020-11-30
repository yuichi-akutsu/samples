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
import 'package:flutter_hooks/flutter_hooks.dart';
import 'package:flutter_sample/component/loading.dart';
import 'package:flutter_sample/constant.dart';
import 'package:flutter_sample/model/item.dart';
import 'package:flutter_sample/ui/top/top_view_model.dart';
import 'package:get/get.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class TopPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("top"),
      ),
      body: HookBuilder(
        builder: (context) {
          // TODO ここからsnapshot取得までがイマイチ理解できてないので、確認
          final topViewModel = context.read(topViewModelNotifierProvider);
          List<Item> item = useProvider(
              topViewModelNotifierProvider.select((value) => value.item));
          final snapshot = useFuture(useMemoized(topViewModel.getItem));

          // APIからレスポンスをもらうまではLoadingを表示
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Loading();
          }

          // TODO 検索ボックスとかつけようかな
          // TODO Gridとかで整える
          return ListView.builder(
            itemCount: item.length,
            itemBuilder: (_, i) {
              // 項目をくくってタップを受け付ける
              return GestureDetector(
                onTap: () {
                  // 詳細ページへ遷移(選択された商品IDを引き渡す)
                  Get.toNamed(Constant.pageDetail, arguments: item[i].id);
                },
                child: Row(
                  children: [
                    Text(item[i].id),
                    Text(item[i].name),
                    Image.network(
                      "http://localhost:180/html5.png", // TODO ちゃんとするw
                      width: 50,
                    )
                  ],
                ),
              );
            },
          );
        },
      ),
    );
  }
}
