package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.Index;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Run.setGlobalVariables();
        Run.setGlobalUIObject();
        Index index = new Index();
        
        VBox test = new VBox();
        test.setSpacing(10);
        test.setPrefSize(700, 500);
        test.setPadding(new Insets(10, 10, 10, 10));
        test.getStylesheets().add("/css/bootstrap3.css");
        
        
        test.getChildren().addAll(index.header, index.content);
        Scene scene = new Scene(test, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("書籍訂購系統");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
