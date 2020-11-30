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


import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_sample/model/item.dart';

/// 商品情報取得クラス
class ItemDataSource {
  ItemDataSource({@required Dio dio}) : _dio = dio;

  final Dio _dio;

  /// パス TODO ベタがきどうにかする(Constantを整理して環境依存取得)
  final String path = "http://localhost:8081/sample/v1/items";

  /// 商品一覧情報取得
  Future<List<Item>> getItemList() async {
    return _dio
        .get<List<dynamic>>(
          path,
        )
        .then((response) => (response.data)
            ?.map((e) => e == null ? null : Item.fromJson(e))
            ?.toList());
  }

  /// 商品情報取得
  Future<Item> getItem(String id) async {
    return _dio
        .get<Map<String, dynamic>>(
          path+"/"+id,
        )
        .then((response) => Item.fromJson(response.data));
  }
}
