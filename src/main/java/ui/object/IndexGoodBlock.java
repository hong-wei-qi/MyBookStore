package ui.object;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.GlobalVariables;
import ui.GlobalUIObject;

public class IndexGoodBlock {
    
    /**
     * 建立 所有 書籍(商品) UI物件 並存於GlobalUIObject
     */
    public static void IndexGoodObjectList() {
        for (String i : GlobalVariables.good_list.keySet()) {
            IndexGoodObject(i);
        }
    }
    
    /**
     * 建立 特定 書籍(商品) UI物件 存於GlobalUIObject
     * @param good_id 商品ID
     */
    public static void IndexGoodObject(String good_id) {
        HBox igb = new HBox();
        igb.setSpacing(10);
        igb.setPadding(new Insets(0, 0, 10, 0));
        
        Image img = new Image("/imgs/" + GlobalVariables.good_list.get(good_id).getImage());
        ImageView imgview = new ImageView(img);
        imgview.setFitHeight(200);
        imgview.setPreserveRatio(true);
        
        VBox info = new VBox();
        info.setPrefSize(500, 200);
        info.setSpacing(10);
        info.setAlignment(Pos.TOP_LEFT);
        Label name = new Label();
        name.setFont(new Font(15));
        Label content = new Label();
        content.setFont(new Font(15));
        name.setPrefSize(500, 20);
        content.setPrefSize(500, 160);
        content.setAlignment(Pos.TOP_LEFT);
        name.setText(GlobalVariables.good_list.get(good_id).getName());
        content.setText(GlobalVariables.good_list.get(good_id).getContent());
        info.getChildren().addAll(name, content);
        
        VBox control = new VBox();
        control.setPrefSize(300, 200);
        control.setSpacing(10);
        Label price = new Label();
        price.setFont(new Font(17));
        price.setPrefSize(300, 20);
        price.setText("價格：" + GlobalVariables.good_list.get(good_id).getPrice());
        Button add = new Button();
        add.setText("加入購物車");
        add.setOnAction((t) -> {
            System.out.println(GlobalVariables.now_user + " add " + good_id + " in to cart.");
            GlobalVariables.user_list.get(GlobalVariables.now_user).addToCart(good_id, GlobalVariables.good_list.get(good_id).getPrice(), 1);
        });
        control.getChildren().addAll(price, add);
        
        igb.getChildren().addAll(imgview, info, control);
        GlobalUIObject.IndexGoodBlock_list.put(good_id, igb);
    }
}
