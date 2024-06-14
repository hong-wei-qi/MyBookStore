package ui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import main.GlobalVariables;

/**
 * <h2><b>訂單管理 UI介面</b></h2><br>
 * content: 訂單列表<br>
 */
public class OrderManage {
    
    public static final String NAME = "訂單管理";

    // 訂單列表內容
    private ObservableList<element.Order> order_list;
    
    // 訂單列表
    public TableView content;
    
    /**
     * 初始化 `訂單 UI介面`
     */
    public OrderManage() {
        set();
    }
    
    /**
     * 建構 `訂單 UI介面`
     */
    public void set() {
        setContent();
    }

    /**
     * 設置訂單頁面
     */
    public Scene setOrderManage() {
        return new Scene(setOrderManage_MAIN(), 1000, 700);
    }
    
    /**
     * 設置訂單頁面
     */
    public VBox setOrderManage_MAIN() {
        set();
        VBox OrderManageScene = new VBox();
        OrderManageScene.setSpacing(10);
        OrderManageScene.setPadding(new Insets(10, 10, 10, 10));
        OrderManageScene.getStylesheets().add("/css/bootstrap3.css");
        OrderManageScene.getChildren().addAll(this.content);
        return OrderManageScene;
    }
    
    /**
     * 建立訂單列表
     */
    private void setContent() {
        content = new TableView();
        this.resetOrderList();
        content.setItems(this.order_list);
        content.setFixedCellSize(60.0);
        content.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<element.Order, String> column_order_id = createTableColumn("訂單ID", "order_id");
        TableColumn<element.Order, String> column_user_id = createTableColumn("用戶ID", "user_id");
        TableColumn<element.Order, String> column_complete = createTableColumn("訂單狀態", "complete");
        TableColumn<element.Order, String> column_alter = createTableColumn("是否可修改", "alter");
        TableColumn<element.Order, Integer> column_total = createTableColumn("總金額", "total", new IntegerStringConverter());
        content.getColumns().addAll(column_order_id, column_complete, column_total);
        column_complete.setCellValueFactory(cellData -> {
            element.Order o = cellData.getValue();
            return new ReadOnlyStringWrapper(o.isCompleteString());
        });
        column_alter.setCellValueFactory(cellData -> {
            element.Order o = cellData.getValue();
            return new ReadOnlyStringWrapper(o.isAlterString());
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
                    GlobalUIObject.MANAGE.addTab_OrderManageDetail(order.getOrder_id());
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
        var list = GlobalVariables.orderDAO.getAll();
        for (String id : list.keySet()) {
            this.order_list.add(list.get(id));
        }
    }
    
}
