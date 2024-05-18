package ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * <h2><b>購物車 UI介面</b></h2>
 */
public class Cart {

    // 標頭 含有 { icon(回首頁) 切換用戶 }
    public HBox header = new HBox();
    
    // 購物車清單
    public VBox inCartList = new VBox();
    
    // 購物車資訊 包含送出訂單按鈕
    public VBox cartInfo = new VBox();
}
