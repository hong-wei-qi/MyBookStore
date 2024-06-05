package ui;

import element.GoodOrder;
import element.Order;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.GlobalVariables;
import ui.object.CartGoodBlock;

/**
 * <h2><b>購物車 UI介面</b></h2><br>
 * header: 標頭 含有 { icon(回首頁) 切換用戶 }<br>
 * cartList: 購物車清單UI<br>
 * inCartList: 購物車內清單<br>
 * cartInfoBlock: 購物車資訊區塊 包含送出訂單按鈕 (將訂單儲存到全域變數中)<br>
 * cartInfo: 購物車資訊
 */
public class Cart {

    // 目前用戶名稱
    public Label user_name_Label = new Label();
    
    // 標頭 含有 { icon(回首頁) 切換用戶 }
    public HBox header = new HBox();

    // 購物車清單UI
    public VBox cartList = new VBox();

    // 購物車內清單
    public ScrollPane inCartList = new ScrollPane();

    // 購物車資訊區塊 包含送出訂單按鈕 (將訂單儲存到全域變數中)
    public VBox cartInfoBlock = new VBox();

    // 購物車資訊
    public Label cartInfo = new Label();

    /**
     * 初始化 `購物車 UI介面`
     */
    public Cart() {
        inCartList.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        inCartList.setPadding(new Insets(10, 10, 10, 10));
        inCartList.setPrefWidth(800);
        this.setHeader();
        this.setCartList();
        this.setCartInfoBlock();
    }

    /**
     * 建立 標頭
     */
    public void setHeader() {
        header.setSpacing(10);
        header.setPrefWidth(950);
        header.setPrefHeight(80);
        header.setAlignment(Pos.BASELINE_RIGHT);

        // icon
        Image img = new Image("/imgs/icon.png");
        ImageView imgview = new ImageView(img);
        imgview.setFitHeight(60);
        imgview.setPreserveRatio(true);
        Button all_button = new Button();
        all_button.setPrefSize(100, 60);
        all_button.setGraphic(imgview);
        all_button.setOnAction((e) -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GlobalUIObject.LOGIN.goToLogin(stage);
            stage.setScene(GlobalUIObject.IndexScene);
            stage.show();
        });

        // 填充中間空白部分
        TilePane filling = new TilePane();
        header.setHgrow(filling, Priority.ALWAYS);

        // 購物車
        header.getChildren().addAll(all_button, filling, this.user_name_Label);

        // 測試按鈕
        Button test = new Button();
        test.setText("show order.");
        test.setOnAction((e) -> {
            showOrder();
        });
        header.getChildren().add(test);
    }

    /**
     * 建立 購物車清單區塊
     */
    public void setCartList() {

        GlobalUIObject.SelectAll_inCartList = new CheckBox();
        GlobalUIObject.SelectAll_inCartList.setOnAction((e) -> {
            setInCartList();
        });
        HBox info = new HBox();
        info.setPrefWidth(800);
        info.setSpacing(10);
        info.setAlignment(Pos.CENTER_LEFT);
        // 商品明細 { 圖片, 商品名稱 }
        Label good = new Label();
        good.setFont(new Font(15));
        good.setPrefWidth(86 + 200);
        good.setAlignment(Pos.CENTER);
        good.setText("商品明細");
        // 商品單價
        Label price = new Label();
        price.setFont(new Font(15));
        price.setPrefWidth(70);
        price.setAlignment(Pos.CENTER);
        price.setText("單價");
        // 數量
        Label quantity = new Label();
        quantity.setFont(new Font(15));
        quantity.setPrefWidth(90);
        quantity.setAlignment(Pos.CENTER);
        quantity.setText("數量");
        // 小計
        Label subtotal = new Label();
        subtotal.setFont(new Font(15));
        subtotal.setPrefWidth(100);
        subtotal.setAlignment(Pos.CENTER);
        subtotal.setText("小計");
        // 變更明細
        Label operate = new Label();
        operate.setFont(new Font(15));
        operate.setPrefWidth(200);
        operate.setAlignment(Pos.CENTER);
        operate.setText("變更明細");

        info.getChildren().addAll(GlobalUIObject.SelectAll_inCartList, good, price, quantity, subtotal, operate);

        this.cartList.setPrefWidth(800);
        this.cartList.getChildren().addAll(info, this.inCartList);
    }

    /**
     * 建立 購物車清單
     */
    public void setInCartList() {
        this.inCartList.setContent(CartGoodBlock.CartGoodObjectList());
        this.setCartInfo();
    }

    /**
     * 建立 購物車資訊區塊
     */
    public void setCartInfoBlock() {
        this.cartInfoBlock = new VBox();
        this.cartInfoBlock.setPrefWidth(800);

        HBox data = new HBox();
        data.setPrefWidth(800);
        data.setPadding(new Insets(10, 10, 10, 10));
        Button deleteAll = new Button();
        deleteAll.setText("刪除選取");
        deleteAll.setOnAction((e) -> {
            for (String selectItem : GlobalVariables.inCartList_SelectList) {
                GlobalVariables.cart.removeFromCart(selectItem);
//                System.out.println(GlobalVariables.now_user + " remove " + selectItem + " from to cart.");
            }
            if (GlobalUIObject.SelectAll_inCartList.isSelected()) {
                GlobalUIObject.SelectAll_inCartList.setSelected(false);
            }
            this.setInCartList();
        });
        // 填充中間空白部分
        TilePane filling = new TilePane();
        data.setHgrow(filling, Priority.ALWAYS);
        this.cartInfo.setFont(new Font(15));
        this.cartInfo.setTextAlignment(TextAlignment.RIGHT);
        this.setCartInfo();
        data.getChildren().addAll(deleteAll, filling, this.cartInfo);

        HBox submit = new HBox();
        submit.setAlignment(Pos.CENTER_RIGHT);
        Button checkout = new Button();
        checkout.setText("送出訂單");
        checkout.setOnAction((e) -> {
            if (GlobalVariables.cart.getGoodAmount() == 0) {
                return;
            }
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String order_id = myDateObj.format(myFormatObj);
            var cart = GlobalVariables.cart.getCart();
            TreeMap<String, GoodOrder> order_list = new TreeMap();
            for (String i : cart.keySet()) {
                GoodOrder good = new GoodOrder(cart.get(i).getGood_id(), cart.get(i).getPrice(), cart.get(i).getQuantity());
                order_list.put(i, good);
            }
            Order order = new Order(
                    order_id,
                    GlobalVariables.now_user,
                    order_list,
                    GlobalVariables.cart.getGoodAmount(),
                    GlobalVariables.cart.getGoodQuantity(),
                    GlobalVariables.cart.getTotal()
            );
            // GlobalVariables.order_list.put(order_id, order);
            GlobalVariables.orderDAO.insert(order);
            GlobalVariables.cart.clearCart();
            setInCartList();
        });
        submit.getChildren().add(checkout);

        this.cartInfoBlock.getChildren().addAll(data, submit);
    }

    /**
     * 生成 購物車資訊
     */
    public void setCartInfo() {
        this.cartInfo.setText(String.format("共 %d 項商品，數量 %d 個\n總金額：%d",
                GlobalVariables.cart.getGoodAmount(),
                GlobalVariables.cart.getGoodQuantity(),
                GlobalVariables.cart.getTotal()
        ));
    }

    /**
     * 測試用 展示目前操作用戶的所有訂單
     */
    public void showOrder() {
        System.out.println("\n" + GlobalVariables.now_user + "已成立訂單資訊");
        var order_list = GlobalVariables.orderDAO.getByUserId(GlobalVariables.now_user);
        for (String order_id : order_list.keySet()) {
            if (!(order_list.get(order_id).getUser_id().equals(GlobalVariables.now_user))) {
                continue;
            }
            System.out.println("訂單ID：" + order_list.get(order_id).getOrder_id());
            System.out.println("總金額：" + order_list.get(order_id).getTotal());
            System.out.println("修改狀態：" + ((order_list.get(order_id).isAlter()) ? "還可修改" : "不可修改"));
            System.out.println("訂單明細");
            var good_order_list = order_list.get(order_id).getList();
            System.out.println("商品ID | 商品名稱 | 單價 | 數量 | 小計");
            for (String good_id : good_order_list.keySet()) {
                System.out.println(String.format("%s | %s | %d | %d | %d",
                        good_order_list.get(good_id).getGood_id(),
                        GlobalVariables.productDAO.getById(good_id).getName(),
                        good_order_list.get(good_id).getPrice(),
                        good_order_list.get(good_id).getQuantity(),
                        good_order_list.get(good_id).getSubtotal()
                ));
            }
            System.out.println("==================================================\n");
        }

    }

}
