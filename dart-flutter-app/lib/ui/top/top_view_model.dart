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

import 'package:flutter/foundation.dart';
import 'package:flutter_sample/model/item.dart';
import 'package:flutter_sample/provider/item_repository_provider.dart';
import 'package:flutter_sample/repository/ItemRepositoty.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

/// ViewModel取得用Provider
final topViewModelNotifierProvider = ChangeNotifierProvider(
    (ref) => TopViewModel(ref.read(itemRepositoryProvider)));

/// トップページ用ViewModel
class TopViewModel extends ChangeNotifier {

  // 商品情報アクセス用Repository
  final ItemRepository _itemRepository;

  TopViewModel(this._itemRepository);

  // 商品リスト
  List<Item> _item;

  /// 商品情報
  List<Item> get item => _item;

  /// 商品情報一覧を取得
  Future<void> getItem() async {
    return _itemRepository
        .getItemList()
        .then((value) => _item = value)
        .whenComplete(notifyListeners);
  }
}
