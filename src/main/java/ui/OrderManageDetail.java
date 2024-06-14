package ui;

import element.GoodOrder;
import java.util.Optional;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import main.GlobalVariables;

/**
 * <h2><b>管理訂單內容 UI介面</b></h2><br>
 * header: 標頭 含有 { 回到所有訂單 }<br>
 * content: 訂單內容 { L:訂單基本資料 R:訂單明細 }<br>
 */
public class OrderManageDetail {
    
    // 訂單ID
    private String order_id = "";

    private element.Order order;

    // 訂單明細列表內容
    private ObservableList<GoodOrder> order_list;

    // 內容
    public HBox content;

    // 訂單基本資料
    public VBox orderData;

    // 訂單明細列表
    public TableView orderContent;

    /**
     * 初始化 `管理訂單內容 UI介面`
     */
    public OrderManageDetail(String order_id) {
        this.order_id = order_id;
        
        set();
    }

    /**
     * 建構 `管理訂單內容 UI介面`
     */
    public void set() {
        order = GlobalVariables.orderDAO.getById(order_id);
        content = new HBox();
        orderData = new VBox();
        orderContent = new TableView();
        setOrderData();
        setOrderContent();
        setContent();
    }

    /**
     * 設置管理訂單內容
     */
    public Scene setOrderManageDetail() {
        return new Scene(setOrderManageDetail_MAIN(), 700, 600);
    }

    /**
     * 設置管理訂單內容
     */
    public VBox setOrderManageDetail_MAIN() {
        set();
        VBox OrderManageDetailScene = new VBox();
        OrderManageDetailScene.setSpacing(10);
        OrderManageDetailScene.setPadding(new Insets(10, 10, 10, 10));
        OrderManageDetailScene.getStylesheets().add("/css/bootstrap3.css");
        OrderManageDetailScene.getChildren().addAll(this.content);
        return OrderManageDetailScene;
    }

    /**
     * 建立訂單明細列表
     */
    private void setContent() {
        content.setSpacing(10);
        content.setAlignment(Pos.BASELINE_CENTER);
        TilePane filling = new TilePane();
        content.getChildren().addAll(orderData, filling, orderContent);
    }

    /**
     * 建立訂單基本資料
     */
    private void setOrderData() {
        orderData.setSpacing(10);
        // 訂單ID
        Label orderId = new Label();
        orderId.setText("訂單ID： " + order_id);
        // 訂單總金額
        Label orderTotal = new Label();
        orderTotal.setText("總金額： " + order.getTotal());
        // 訂單狀態
        HBox orderComplete_box = new HBox();
        Label orderComplete = new Label();
        orderComplete.setText("訂單狀態： " + order.isCompleteString());
        Button set_orderComplete = new Button();
        set_orderComplete.setText((order.isComplete()) ? "標記為未完成" : "標記為完成");
        set_orderComplete.setOnAction(e -> {
            order.setComplete(!(order.isComplete()));
            orderComplete.setText("訂單狀態： " + order.isCompleteString());
            set_orderComplete.setText((order.isComplete()) ? "標記為未完成" : "標記為完成");
        });
        orderComplete_box.getChildren().addAll(orderComplete, set_orderComplete);
        // 完成修改
        Button orderAlter = new Button();
        orderAlter.setText("完成修改");
        orderAlter.setOnAction(e -> {
            TreeMap<String, GoodOrder> glist = new TreeMap();
            for (GoodOrder g : order_list) {
                g.setPrice(GlobalVariables.productDAO.getById(g.getGood_id()).getPrice());
                g.countSubtotal();
                glist.put(g.getGood_id(), g);
            }
            order.updataOrder(glist);
            GlobalVariables.orderDAO.update(order);
            GlobalUIObject.MANAGE.resetScenes();
        });
        // 刪除訂單
        Button delete = new Button();
        delete.setText("刪除訂單");
        delete.setOnAction((e) -> {
            Alert a = new Alert(AlertType.NONE);
            a.setAlertType(AlertType.CONFIRMATION);
            a.setHeaderText("確定刪除訂單？");
            Optional<ButtonType> result = a.showAndWait();
            if (result.get().equals(ButtonType.OK)) {
                GlobalVariables.orderDAO.delete(order_id);
                GlobalUIObject.MANAGE.resetScenes();
            } else {
                a.close();
            }
        });
        orderData.getChildren().addAll(orderId, orderTotal, orderComplete_box, orderAlter, delete);
    }

    /**
     * 建立訂單明細列表
     */
    private void setOrderContent() {
        this.resetGoodList();
        orderContent.setEditable(true);
        orderContent.setItems(this.order_list);
        orderContent.setFixedCellSize(60.0);
        orderContent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<GoodOrder, String> column_good_id = createTableColumn("商品ID", "good_id");
        column_good_id.setPrefWidth(200);
        TableColumn<GoodOrder, Integer> column_quantity = createTableColumn("數量", "quantity", new IntegerStringConverter());
        column_quantity.setPrefWidth(90);
        column_quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        orderContent.getColumns().addAll(column_good_id, column_quantity);
        column_quantity.setOnEditCommit(e -> {
            GoodOrder g = e.getRowValue();
            g.setQuantity((Integer) e.getNewValue());
            g.countSubtotal();
        });
    }

    /**
     * 建立表格欄位 文字格式
     *
     * @return TableColumn<GoodOrder, String> 欄位
     */
    private TableColumn<GoodOrder, String> createTableColumn(String column_name, String object_name) {
        TableColumn<GoodOrder, String> column = new TableColumn();
        column.setText(column_name);
        column.setCellValueFactory(new PropertyValueFactory(object_name));
        // column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setStyle("-fx-alignment: CENTER-LEFT;");
        return column;
    }

    /**
     * 建立表格欄位 整數格式
     *
     * @return TableColumn<GoodOrder, Integer> 欄位
     */
    private TableColumn<GoodOrder, Integer> createTableColumn(String column_name, String object_name, IntegerStringConverter c) {
        TableColumn<GoodOrder, Integer> column = new TableColumn();
        column.setText(column_name);
        column.setCellValueFactory(new PropertyValueFactory(object_name));
        // column.setCellFactory(TextFieldTableCell.forTableColumn(c));
        column.setStyle("-fx-alignment: CENTER;");
        return column;
    }

    /**
     * 訂單明細列表內容 建立
     */
    public void resetGoodList() {
        this.order_list = FXCollections.observableArrayList();
        var goodList = order.getList();
        for (String id : goodList.keySet()) {
            this.order_list.add(goodList.get(id));
        }
    }

}
