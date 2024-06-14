package ui;

import java.util.TreeMap;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class GlobalUIObject {

    // 登入物件
    public static Login LOGIN;
    
    // 主頁物件
    public static Index INDEX;

    // 購物車頁面物件
    public static Cart CART;
    
    // 訂單物件
    public static Order ORDER;

    // 管理後臺物件
    public static Manage MANAGE;
    
    // 購物車頁面 全選功能
    public static CheckBox SelectAll_inCartList;

    // 書籍(商品) UI物件 清單
    public static TreeMap<String, HBox> IndexGoodBlock_list = new TreeMap();
    
    public static Scene OrderDetail_Scene;
    
}
