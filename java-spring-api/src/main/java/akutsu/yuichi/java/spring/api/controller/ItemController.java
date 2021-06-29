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

package akutsu.yuichi.java.spring.api.controller;

import akutsu.yuichi.java.spring.api.dto.ItemDetailDto;
import akutsu.yuichi.java.spring.api.dto.ItemDto;
import akutsu.yuichi.java.spring.api.entity.Item;
import akutsu.yuichi.java.spring.api.mapper.ItemMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品(item)情報にアクセスする為のコントローラ(APIのエンドポイント)
 * <p>@RestControllerを付与することでコントローラとしてDIコンテナに登録</p>
 * <p>@RequestMappingで対応するURLを指定</p>
 */
@RestController
@RequestMapping("items")
public class ItemController {

  private final ItemMapper itemMapper;

  public ItemController(ItemMapper itemMapper) {
    this.itemMapper = itemMapper;
  }

  /**
   * 商品情報の一覧を取得
   * <p>@GetMappingを付与することでHTTPのGetメソッドでのアクセスを受け付けるように設定</p>
   *
   * @return 商品情報一覧
   */
  @GetMapping
  public ResponseEntity<List<ItemDto>> get() {
    // 全件検索
    List<Item> items = itemMapper.selectAllItems();

    // テーブル情報からレスポンス情報へ詰め替え
    List<ItemDto> itemDtoList = new ArrayList<>();
    items.forEach(item -> {
      ItemDto itemDto = new ItemDto();
      itemDto.setId(item.getId());
      itemDto.setName(item.getName());
      itemDtoList.add(itemDto);
    });

    // ResponseEntityに設定するとHTTPのステータスと共にレスポンスを返却
    return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
  }

  /**
   * 商品詳細情報取得
   * <p>"{xx}"をパスに指定し、@PathVariable("xx")で取得</p>
   *
   * @param id 商品ID
   * @return 商品詳細情報
   */
  @GetMapping("/{id}")
  public ResponseEntity<ItemDto> get(@PathVariable("id") String id) {
    // ID指定で検索
    Item item = itemMapper.selectItem(id);

    if (item != null) {
      // 商品情報が取得できた場合、レスポンスへ変換して返却
      ItemDetailDto itemDetailDto = new ItemDetailDto();
      itemDetailDto.setId(item.getId());
      itemDetailDto.setName(item.getName());
      itemDetailDto.setDescription(item.getDescription());
      return new ResponseEntity<>(itemDetailDto, HttpStatus.OK);
    } else {
      // 取得できなかった場合、Httpステータス404(Not found)で返却
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
