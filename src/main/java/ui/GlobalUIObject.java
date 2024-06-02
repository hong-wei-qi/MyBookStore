package ui;

import java.util.TreeMap;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GlobalUIObject {

    // 登入物件
    public static Login LOGIN;
    
    // 主頁物件
    public static Index INDEX;

    // 購物車頁面物件
    public static Cart CART;

    // 商品管理物件
    public static ProductManage PRODUCT_MANAGE;
    
    // 登入
    public static Scene LoginScene;
    
    // 主頁
    public static Scene IndexScene;

    // 購物車頁面 全選功能
    public static CheckBox SelectAll_inCartList;

    // 購物車頁面
    public static Scene CartScene;

    // 書籍(商品) UI物件 清單
    public static TreeMap<String, HBox> IndexGoodBlock_list = new TreeMap();
    
    // 商品管理
    public static Scene ProductManageScene;
}
