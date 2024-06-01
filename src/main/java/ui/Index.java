package ui;

import element.Book;
import element.Category;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.GlobalVariables;

/**
 * <h2><b>主頁 UI介面</b></h2><br>
 * header: 標頭 含有 { icon 二階分類選項 切換用戶 購物車 }<br>
 * content: 內容<br>
 * category1LBlock: 一階分類選項<br>
 * indexGoodBlock: 商品清單 UI
 */
public class Index {

    // 目前用戶名稱
    public Label user_name_Label = new Label();
    
    // 標頭 含有 { icon 二階分類選項 切換用戶 購物車 }
    public HBox header = new HBox();

    // 內容
    public HBox content = new HBox();

    // 一階分類選項
    public VBox category1LBlock = new VBox();

    // 商品清單 UI
    public ScrollPane indexGoodBlock = new ScrollPane();

    /**
     * 初始化 `主頁 UI介面`
     */
    public Index() {
        indexGoodBlock.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        indexGoodBlock.setPadding(new Insets(10, 10, 10, 10));
        indexGoodBlock.setPrefWidth(800);
        this.setHeader();
        this.setIndexGoodBlock();
        category1LBlock.setPrefWidth(150);
        category1LBlock.setSpacing(10);
        this.setCategory1LBlock();
        content.setSpacing(10);
        content.setPrefWidth(950);
        content.getChildren().addAll(this.category1LBlock, this.indexGoodBlock);
    }

    /**
     * 建立 標頭
     */
    public void setHeader() {
        header.setSpacing(10);
        header.setPrefWidth(950);
        header.setPrefHeight(80);
        header.setAlignment(Pos.CENTER_LEFT);

        // icon
        Image img = new Image("/imgs/icon.png");
        ImageView imgview = new ImageView(img);
        imgview.setFitHeight(60);
        imgview.setPreserveRatio(true);
        Button all_button = new Button();
        all_button.setPrefSize(100, 60);
        all_button.setGraphic(imgview);
        all_button.setOnAction((t) -> {
            this.setCategory1LBlock();
            this.setIndexGoodBlock();
        });

        // 二階分類選項
        TilePane category2L_Pane = new TilePane();
        category2L_Pane.setPrefHeight(60);
        category2L_Pane.setAlignment(Pos.CENTER_LEFT);
        category2L_Pane.setHgap(5);
        Category category2L = GlobalVariables.categoryDAO.getTwoLevel();
        for (String category2L_id : category2L.keySet()) {
            Button category2L_button = new Button();
            category2L_button.setText(category2L.getValue(category2L_id));
            category2L_button.setOnAction((t) -> {
                this.setCategory1LBlock(category2L_id);
                this.setIndexGoodBlock(category2L_id);
            });
            category2L_Pane.getChildren().add(category2L_button);
        }

        // 填充中間空白部分
        TilePane filling = new TilePane();
        header.setHgrow(filling, Priority.ALWAYS);

        // 切換用戶
//        ComboBox user_ChoiceBox = new ComboBox();
//        var user_list = GlobalVariables.userDAO.getAll();
//        for (String user_id : user_list.keySet()) {
//            user_ChoiceBox.getItems().add(user_id);
//        }
//        user_ChoiceBox.setOnAction((e) -> {
//            GlobalVariables.now_user = user_ChoiceBox.getSelectionModel().getSelectedItem().toString();
//            GlobalVariables.cart.clearCart();
//            // System.out.println(user_ChoiceBox.getSelectionModel().getSelectedItem());
//        });
//        user_ChoiceBox.getSelectionModel().select(GlobalVariables.now_user);

        Button logout = new Button();
        logout.setText("登出");
        logout.setOnAction((e) -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GlobalVariables.now_user = "";
            GlobalUIObject.LOGIN.goToLogin(stage);
        });

        // 購物車
        Button goToCart = new Button();
        goToCart.setText("購物車");
        goToCart.setOnAction((e) -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GlobalUIObject.LOGIN.goToLogin(stage);
            GlobalUIObject.SelectAll_inCartList.setSelected(false);
            GlobalUIObject.CART.setInCartList();
            stage.setScene(GlobalUIObject.CartScene);
            stage.show();
        });

        header.getChildren().addAll(all_button, category2L_Pane, filling, this.user_name_Label, logout,goToCart);
    }

    /**
     * 建立 一階分類選項 無二階分類 存在(所有)
     */
    public void setCategory1LBlock() {
        this.category1LBlock.getChildren().clear();
    }

    /**
     * 建立 一階分類選項 有二階分類 存在
     */
    public void setCategory1LBlock(String category2L_id) {
        this.category1LBlock.getChildren().clear();
        Category category1L = GlobalVariables.categoryDAO.getOneLevel(category2L_id);
        for (String category1L_id : category1L.keySet()) {
            Button category1L_button = new Button();
            category1L_button.setText(category1L.getValue(category1L_id));
            category1L_button.setOnAction((t) -> {
                this.setIndexGoodBlock(category2L_id, category1L_id);
            });
            this.category1LBlock.getChildren().add(category1L_button);
        }
    }

    /**
     * 建立 所有 書籍(商品) 的商品清單
     */
    public void setIndexGoodBlock() {
        VBox b = new VBox();
        b.setSpacing(10);
        for (String id : GlobalUIObject.IndexGoodBlock_list.keySet()) {
            b.getChildren().add(GlobalUIObject.IndexGoodBlock_list.get(id));
        }
        indexGoodBlock.setContent(b);
    }

    /**
     * 建立 特定二階分類 書籍(商品) 的商品清單
     *
     * @param category2L 二階分類ID
     */
    public void setIndexGoodBlock(String category2L) {
        VBox b = new VBox();
        b.setSpacing(10);
        for (String id : GlobalUIObject.IndexGoodBlock_list.keySet()) {
            if (!(GlobalVariables.categoryDAO.getTwoLevelID(GlobalVariables.productDAO.getById(id).getCategory1L()).equals(category2L))) {
                continue;
            }
            b.getChildren().add(GlobalUIObject.IndexGoodBlock_list.get(id));
        }
        indexGoodBlock.setContent(b);
    }

    /**
     * 建立 特定一階分類(包含二階分類) 書籍(商品) 的商品清單<br>
     * 先比對二階分類再比對一階分類
     *
     * @param category2L 二階分類ID
     * @param category1L 一階分類ID
     */
    public void setIndexGoodBlock(String category2L, String category1L) {
        VBox b = new VBox();
        b.setSpacing(10);
        for (String id : GlobalUIObject.IndexGoodBlock_list.keySet()) {
            Book product = GlobalVariables.productDAO.getById(id);
            if (!(GlobalVariables.categoryDAO.getTwoLevelID(product.getCategory1L()).equals(category2L))) {
                continue;
            }
            if (!(product.getCategory1L().equals(category1L))) {
                continue;
            }
            b.getChildren().add(GlobalUIObject.IndexGoodBlock_list.get(id));
        }
        indexGoodBlock.setContent(b);
    }
}
