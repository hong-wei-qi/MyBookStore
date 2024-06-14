package ui;

import java.util.TreeMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.GlobalVariables;

/**
 * <h2><b>管理後臺 UI介面</b></h2><br>
 * header: 標頭 含有 各項功能menu_item<br>
 * content: 內容 TabPane<br>
 * scene: Tab集合
 */
public class Manage {

    // 標頭 含有 各項功能menu_item
    public MenuBar header;

    // 內容 TabPane
    public TabPane content;

    // Tab集合
    private TreeMap<String, Tab> scene;

    /**
     * 初始化 `管理後臺 UI介面`
     */
    public Manage() {
        set();
    }

    /**
     * 建構 `管理後臺 UI介面`
     */
    private void set() {
        content = new TabPane();
        resetScenes();
        setHeader();
    }

    /**
     * 設置管理後臺頁面
     */
    public Scene setManage() {
        set();
        VBox manageScene = new VBox();
        manageScene.setSpacing(10);
        manageScene.setPadding(new Insets(10, 10, 10, 10));
        manageScene.getStylesheets().add("/css/bootstrap3.css");
        manageScene.getChildren().addAll(this.header, this.content);
        return new Scene(manageScene, 1000, 700);
    }

    /**
     * 加入 Tab
     */
    public void addTab_OrderManageDetail(String order_id) {
        Tab newTab = new Tab("訂單管理 - " + order_id,
                (new OrderManageDetail(order_id)).setOrderManageDetail_MAIN()
        );
        if (!content.getTabs().contains(newTab)) {
            content.getTabs().add(newTab);
        }
        content.getSelectionModel().select(newTab);
    }

    /**
     * 建立 標頭
     */
    private void setHeader() {
        header = new MenuBar();
        Menu menu = new Menu("功能");
        for (String object_NAME : scene.keySet()) {
            MenuItem menuitem_object = new MenuItem(object_NAME);
            menuitem_object.setOnAction(e -> {
                if (!content.getTabs().contains(scene.get(object_NAME))) {
                    content.getTabs().add(scene.get(object_NAME));
                }
                content.getSelectionModel().select(scene.get(object_NAME));
            });
            menu.getItems().add(menuitem_object);
        }
        header.getMenus().add(menu);

        // 登出功能設置
        Menu menu_account = new Menu("帳戶");
        MenuItem menuitem_reload = new MenuItem("重新加載");
        menuitem_reload.setOnAction(e -> {
            resetScenes();
        });
        MenuItem menuitem_logout = new MenuItem("登出");
        menuitem_logout.setOnAction(e -> {
            Stage stage = (Stage) menuitem_logout.getParentPopup().getOwnerWindow();
            GlobalVariables.now_user = "";
            GlobalUIObject.LOGIN.goToLogin(stage);
        });
        menu_account.getItems().addAll(menuitem_reload, menuitem_logout);
        header.getMenus().add(menu_account);
    }

    /**
     * 設置 Tab集合
     */
    public void resetScenes() {
        this.scene = new TreeMap();
        this.scene.put(ProductManage.NAME,
                new Tab(ProductManage.NAME,
                        (new ProductManage()).setProductManage_MAIN()
                )
        );
        this.scene.put(OrderManage.NAME,
                new Tab(OrderManage.NAME,
                        (new OrderManage()).setOrderManage_MAIN()
                )
        );
        content.getTabs().clear();
    }
}
