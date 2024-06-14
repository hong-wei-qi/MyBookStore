package ui;

import java.util.TreeMap;
import javafx.scene.control.Menu;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * <h2><b>管理後臺 UI介面</b></h2><br>
 * header: 標頭 含有 各項功能menu_item<br>
 * content: 內容 TabPane<br>
 */
public class Manage {
    // 標頭 含有 各項功能menu_item
    public Menu header;
    public TabPane content;
    private TreeMap<String, VBox> scene;
    
    /**
     * 初始化 `管理後臺 UI介面`
     */
    public Manage() {
        
    }
    
    private void setHeader() {
        header = new Menu("功能");
    }
    
    private void setContent() {
        content = new TabPane();
    }
}
