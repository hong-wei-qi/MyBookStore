package ui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import main.GlobalVariables;

/**
 * <h2><b>訂單 UI介面</b></h2><br>
 * header: 標頭 含有 { icon }<br>
 * content: 訂單列表<br>
 */
public class Order {

    // 訂單列表內容
    private ObservableList<element.Order> order_list;
    
    // 標頭 含有 { icon }
    public HBox header;
    
    // 訂單列表
    public TableView content;
    
    /**
     * 初始化 `訂單 UI介面`
     */
    public Order() {
        set();
    }
    
    /**
     * 建構 `訂單 UI介面`
     */
    public void set() {
        setHeader();
        setContent();
    }

    /**
     * 設置訂單頁面
     */
    public Scene setOrder() {
        set();
        VBox OrderScene = new VBox();
        OrderScene.setSpacing(10);
        OrderScene.setPadding(new Insets(10, 10, 10, 10));
        OrderScene.getStylesheets().add("/css/bootstrap3.css");
        OrderScene.getChildren().addAll(this.header, this.content);
        return new Scene(OrderScene, 1000, 700);
    }
    
    /**
     * 建立 標頭
     */
    private void setHeader() {
        header = new HBox();
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
        all_button.setOnAction((e) -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GlobalUIObject.LOGIN.goToLogin(stage);
            stage.setScene(GlobalUIObject.INDEX.setIndexScene());
            stage.show();
        });

        // 填充中間空白部分
        TilePane filling = new TilePane();
        header.setHgrow(filling, Priority.ALWAYS);
        header.getChildren().add(all_button);
    }
    
    /**
     * 建立訂單列表
     */
    private void setContent() {
        content = new TableView();
        this.resetOrderList();
//        content.setEditable(true);
        content.setItems(this.order_list);
        content.setFixedCellSize(60.0);
        content.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<element.Order, String> column_order_id = createTableColumn("訂單ID", "order_id");
        TableColumn<element.Order, String> column_complete = createTableColumn("訂單狀態", "complete");
        TableColumn<element.Order, Integer> column_total = createTableColumn("總金額", "total", new IntegerStringConverter());
        content.getColumns().addAll(column_order_id, column_complete, column_total);
        column_complete.setCellValueFactory(cellData -> {
            element.Order o = cellData.getValue();
            return new ReadOnlyStringWrapper(o.isCompleteString());
        });
        
        TableColumn<element.Order, Void> column_action = new TableColumn("");
        column_action.setCellFactory(setActionButton());
        content.getColumns().addAll(column_action);
    }

    /**
     * 建立表格欄位 文字格式
     *
     * @return TableColumn<element.Order, String> 欄位
     */
    private TableColumn<element.Order, String> createTableColumn(String column_name, String object_name) {
        TableColumn<element.Order, String> column = new TableColumn();
        column.setText(column_name);
        column.setCellValueFactory(new PropertyValueFactory(object_name));
        // column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setStyle( "-fx-alignment: CENTER-LEFT;");
        return column;
    }

    /**
     * 建立表格欄位 整數格式
     *
     * @return TableColumn<element.Order, Integer> 欄位
     */
    private TableColumn<element.Order, Integer> createTableColumn(String column_name, String object_name, IntegerStringConverter c) {
        TableColumn<element.Order, Integer> column = new TableColumn();
        column.setText(column_name);
        column.setCellValueFactory(new PropertyValueFactory(object_name));
        // column.setCellFactory(TextFieldTableCell.forTableColumn(c));
        column.setStyle( "-fx-alignment: CENTER;");
        return column;
    }

    /**
     * 設定操作欄位按鈕
     */
    private Callback<TableColumn<element.Order, Void>, TableCell<element.Order, Void>> setActionButton() {
        Callback<TableColumn<element.Order, Void>, TableCell<element.Order, Void>> cellFactory = (final TableColumn<element.Order, Void> param) -> new TableCell<>() {
            
            Button btnCheck = new Button("查看");
            
            {
                btnCheck.setOnAction(event -> {
                    element.Order order = getTableView().getItems().get(getIndex());
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    GlobalUIObject.LOGIN.goToLogin(stage);
                    stage.setScene((new OrderDetail(order.getOrder_id())).setOrderDetail());
                    stage.show();
                });
            }
            
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnCheck);
            }
        };
        return cellFactory;
    }

    /**
     * 訂單列表內容 建立
     */
    public void resetOrderList() {
        this.order_list = FXCollections.observableArrayList();
        var list = GlobalVariables.orderDAO.getByUserId(GlobalVariables.now_user);
        for (String id : list.keySet()) {
            this.order_list.add(list.get(id));
        }
    }
    
}
