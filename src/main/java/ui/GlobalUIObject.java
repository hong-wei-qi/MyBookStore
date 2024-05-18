package ui;

import java.util.TreeMap;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class GlobalUIObject {

    // 主頁
    public static Index INDEX;

    // 購物車頁面
    public static Cart CART;

    // 主頁
    public static Scene IndexScene;

    // 購物車頁面 全選功能
    public static CheckBox SelectAll_inCartList;

    // 購物車頁面
    public static Scene CartScene;

    // 書籍(商品) UI物件 清單
    public static TreeMap<String, HBox> IndexGoodBlock_list = new TreeMap();
}
