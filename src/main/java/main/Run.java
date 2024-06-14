package main;

import javafx.stage.Stage;
import ui.Cart;
import ui.GlobalUIObject;
import ui.Index;
import ui.Login;
import ui.Manage;
import ui.Order;

/**
 * <h2><b>執行程式</b></h2><br>
 * run(stage);
 */
public class Run {

    /**
     * 執行
     *
     * @param stage
     */
    public static void run(Stage stage) {
        // setGlobalVariables();
        setGlobalUIObject();
        runScene(stage);
    }
    
    /**
     * 執行UI介面
     *
     * @param stage
     */
    public static void runScene(Stage stage) {
        
//        GlobalVariables.now_user = "u-001";
//        stage.setScene(GlobalUIObject.INDEX.setIndexScene());

        stage.setScene(GlobalUIObject.LOGIN.setLoginScene());
        
//        GlobalVariables.now_user = "a-001";
//        stage.setScene(GlobalUIObject.MANAGE.setManage());
        
        stage.setTitle("書籍訂購系統");
        stage.show();
    }

    /**
     * 設定全域UI物件(GlobalUIObject)
     */
    public static void setGlobalUIObject() {
        GlobalUIObject.LOGIN = new Login();
        GlobalUIObject.INDEX = new Index();
        GlobalUIObject.CART = new Cart();
        GlobalUIObject.ORDER = new Order();
        GlobalUIObject.MANAGE = new Manage();
    }
}
