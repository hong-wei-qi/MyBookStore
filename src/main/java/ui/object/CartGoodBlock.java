package ui.object;

import element.Book;
import element.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.GlobalVariables;
import ui.GlobalUIObject;

/**
 * <h2><b>購物車UI物件</b></h2><br>
 * 採即時生成<br>
 * 目標固定為 目前操作用戶(GlobalVariables.now_user)
 */
public class CartGoodBlock {
    
    public static VBox CartGoodObjectList() {
        GlobalVariables.inCartList_SelectList.clear();
        VBox cgo_list = new VBox();
        cgo_list.setPrefSize(800, 400);
        if (GlobalVariables.userDAO.getById(GlobalVariables.now_user) == null) {
            cgo_list.setAlignment(Pos.CENTER);
            Label msg = new Label("查無用戶資料" + GlobalVariables.now_user);
            msg.setFont(new Font(20));
            cgo_list.getChildren().add(msg);
            return cgo_list;
        }
        if (GlobalVariables.cart.getCart().keySet().isEmpty()) {
            cgo_list.setAlignment(Pos.CENTER);
            Label msg = new Label("無商品資料");
            msg.setFont(new Font(20));
            cgo_list.getChildren().add(msg);
            return cgo_list;
        }
        cgo_list.setSpacing(10);
        for (String good_id : GlobalVariables.cart.getCart().keySet()) {
            cgo_list.getChildren().add(CartGoodObject(good_id));
        }
        return cgo_list;
    }

    /**
     * 建立 特定單項商品訂單 購物車UI物件
     */
    public static HBox CartGoodObject(String good_id) {
        HBox cgo = new HBox();
        User user = GlobalVariables.userDAO.getById(GlobalVariables.now_user);
        
        if (user == null) {
            return cgo;
        }
        GlobalVariables.cart.checkGoodQuantity();
        if (!(GlobalVariables.cart.inCart(good_id))) {
            return cgo;
        }
        
        cgo.setPrefWidth(800);
        cgo.setSpacing(10);
        cgo.setPadding(new Insets(0, 0, 10, 0));
        cgo.setAlignment(Pos.CENTER_LEFT);
        
        CheckBox check = new CheckBox();
        check.setOnAction((e) -> {
            CheckBox c = (CheckBox) e.getSource();
            if (GlobalVariables.inCartList_SelectList.indexOf(good_id) >= 0 && !(c.isSelected())) {
                GlobalVariables.inCartList_SelectList.remove(good_id);
                if (GlobalUIObject.SelectAll_inCartList.isSelected()) {
                    GlobalUIObject.SelectAll_inCartList.setSelected(false);
                }
//                System.out.println("取消選取" + good_id);
                return;
            }
            GlobalVariables.inCartList_SelectList.add(good_id);
            if (GlobalVariables.inCartList_SelectList.size() == GlobalVariables.cart.getGoodAmount()) {
                GlobalUIObject.SelectAll_inCartList.setSelected(true);
            }
//            System.out.println("選取" + good_id);
        });
        
        if (GlobalUIObject.SelectAll_inCartList.isSelected()) {
            check.setSelected(true);
            GlobalVariables.inCartList_SelectList.add(good_id);
        }
        Book product = GlobalVariables.productDAO.getById(good_id);
        
        Image img = new Image("/imgs/" + product.getImage());
        ImageView imgview = new ImageView(img);
        imgview.setFitHeight(130);
        imgview.setPreserveRatio(true);
        
        HBox info = new HBox();
        info.setPrefSize(470, 130);
        info.setSpacing(10);
        info.setAlignment(Pos.CENTER_LEFT);
        // 商品名稱
        Label name = new Label();
        name.setFont(new Font(15));
        name.setPrefSize(200, 20);
        name.setText(product.getName());
//        // 商品摘要
//        Label content = new Label();
//        content.setFont(new Font(15));
//        content.setPrefSize(500, 160);
//        content.setAlignment(Pos.TOP_LEFT);
//        content.setText(product.getContent());
        // 商品單價
        Label price = new Label();
        price.setFont(new Font(15));
        price.setPrefSize(70, 20);
        price.setAlignment(Pos.CENTER);
        price.setText(Integer.toString(product.getPrice()));
        // 數量
        TextField quantity = new TextField();
        quantity.setFont(new Font(15));
        quantity.setPrefSize(100, 20);
        quantity.setAlignment(Pos.CENTER);
        quantity.setText(Integer.toString(GlobalVariables.cart.getCart().get(good_id).getQuantity()));
        quantity.setOnAction((e) -> {
            GlobalVariables.cart.setQuantity(good_id, Integer.parseInt(quantity.getText()));
            GlobalUIObject.CART.setInCartList();
        });
        // 小計
        GlobalVariables.cart.getCart().get(good_id).countSubtotal();
        Label subtotal = new Label();
        subtotal.setFont(new Font(15));
        subtotal.setPrefSize(100, 20);
        subtotal.setAlignment(Pos.CENTER);
        subtotal.setText(Integer.toString(GlobalVariables.cart.getCart().get(good_id).getSubtotal()));
        info.getChildren().addAll(name/*, content*/, price, quantity, subtotal);
        
        VBox control = new VBox();
        control.setAlignment(Pos.CENTER);
        control.setPrefSize(200, 130);
        control.setSpacing(10);
        Button delete = new Button();
        delete.setPrefWidth(200);
        delete.setText("刪除");
        delete.setOnAction((t) -> {
//            System.out.println(GlobalVariables.now_user + " remove " + good_id + " from to cart.");
            GlobalVariables.cart.removeFromCart(good_id);
            GlobalUIObject.CART.setInCartList();
        });
        
        TilePane operate = new TilePane();
        operate.setHgap(10);
        Button addOne = new Button();
        addOne.setPrefWidth(90);
        addOne.setText("加一");
        addOne.setOnAction((t) -> {
//            System.out.println(GlobalVariables.now_user + " add one quantity " + good_id + " from to cart.");
            GlobalVariables.cart.addQuantity(good_id);
            GlobalUIObject.CART.setInCartList();
        });
        Button decreaseOne = new Button();
        decreaseOne.setPrefWidth(90);
        decreaseOne.setText("減一");
        decreaseOne.setOnAction((t) -> {
//            System.out.println(GlobalVariables.now_user + " decrease one quantity " + good_id + " from to cart.");
            GlobalVariables.cart.decreaseQuantity(good_id);
            GlobalUIObject.CART.setInCartList();
        });
        operate.getChildren().addAll(addOne, decreaseOne);
        control.getChildren().addAll(delete, operate);
        
        cgo.getChildren().addAll(check, imgview, info, control);
        return cgo;
    }
    
}
