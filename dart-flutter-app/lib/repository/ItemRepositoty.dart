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
import 'package:flutter_sample/model/item.dart';
import 'package:flutter_sample/remote/item_data_source.dart';

/// 商品情報アクセス用Repository
class ItemRepository {
  // TODO DataSourceとRepositoryの責任分界点があやふやなので、整理

  /// 商品情報アクセス用DataSource
  final ItemDataSource _itemDataSource;

  ItemRepository({@required ItemDataSource itemDataSource})
      : _itemDataSource = itemDataSource;

  /// 商品情報一覧を取得
  Future<List<Item>> getItemList() async {
    return _itemDataSource.getItemList();
  }

  /// 商品情報を取得
  Future<Item> getItem(String id) async {
    return _itemDataSource.getItem(id);
  }
}
