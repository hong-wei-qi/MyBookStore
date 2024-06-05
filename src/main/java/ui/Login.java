package ui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.GlobalVariables;

/**
 * <h2><b>登入 UI介面</b></h2><br>
 * content: 內容<br>
 */
public class Login {
    
    // 內容
    public VBox content = new VBox();

    /**
     * 初始化 `登入 UI介面`
     */
    public Login() {
        content.setSpacing(10);
        content.setAlignment(Pos.CENTER);
        
        Label message = new Label();
        message.setStyle("-fx-text-fill: #d9534f;");
        
        Label label_account = new Label();
        label_account.setFont(new Font(15));
        label_account.setText("帳號");
        
        TextField account = new TextField();
        account.setFont(new Font(15));
        account.setPromptText("你的帳號...");
        
        Label label_password = new Label();
        label_password.setFont(new Font(15));
        label_password.setText("密碼");
        
        PasswordField  password = new PasswordField ();
        password.setFont(new Font(15));
        password.setPromptText("你的密碼...");
        
        Button submit = new Button();
        submit.setText("登入");
        submit.setOnAction((e) -> {
            GlobalVariables.now_user = GlobalVariables.userDAO.check(account.getText(), password.getText());
            if (GlobalVariables.now_user.isEmpty()) {
                System.out.println("登入失敗！ 帳號：" + account.getText() + ", 密碼：" + password.getText());
                message.setText("登入失敗！");
                return;
            }
            message.setText("");
            account.setText("");
            password.setText("");
            String user_name = GlobalVariables.userDAO.getById(GlobalVariables.now_user).getName();
            GlobalUIObject.INDEX.user_name_Label.setText(user_name);
            GlobalUIObject.CART.user_name_Label.setText(user_name);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            if (GlobalVariables.userDAO.permissions(GlobalVariables.now_user)) {
                stage.setScene(GlobalUIObject.ProductManageScene);
            } else {
                stage.setScene(GlobalUIObject.IndexScene);
            }
            stage.show();
        });
        
        content.getChildren().add(message);
        content.getChildren().addAll(label_account, account);
        content.getChildren().addAll(label_password, password);
        content.getChildren().add(submit);
    }
    
    /**
     * 檢查違法登入狀態 存在時退回登入介面
     */
    public void goToLogin(Stage stage) {
        if (GlobalVariables.userDAO.getById(GlobalVariables.now_user) == null) {
            GlobalVariables.now_user = "";
            stage.setScene(GlobalUIObject.LoginScene);
            stage.show();
        }
    }
    
    
}
