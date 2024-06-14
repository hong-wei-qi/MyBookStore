package ui;

import element.GoodOrder;
import java.util.Optional;
import java.util.TreeMap;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import main.GlobalVariables;

/**
 * <h2><b>訂單內容 UI介面</b></h2><br>
 * header: 標頭 含有 { 回到所有訂單 }<br>
 * content: 訂單內容 { L:訂單基本資料 R:訂單明細 }<br>
 */
public class OrderDetail {
    
    // 訂單ID
    private String order_id = "";
    
    private element.Order order;
    
    // 訂單明細列表內容
    private ObservableList<GoodOrder> order_list;
    
    // 標頭 含有 { icon }
    public HBox header;
    
    // 內容
    public HBox content;
    
    // 訂單基本資料
    public VBox orderData;
    
    // 訂單明細列表
    public TableView orderContent;
    
    private boolean alter = false;
    
    /**
     * 初始化 `訂單內容 UI介面`
     */
    public OrderDetail(String order_id) {
        this.order_id = order_id;
        set();
    }
    
    /**
     * 建構 `訂單內容 UI介面`
     */
    public void set() {
        order = GlobalVariables.orderDAO.getById(order_id);
        header = new HBox();
        content = new HBox();
        orderData = new VBox();
        orderContent = new TableView();
        setHeader();
        setOrderData();
        setOrderContent();
        setContent();
    }

    /**
     * 設置訂單內容
     */
    public Scene setOrderDetail() {
        set();
        VBox OrderDetailScene = new VBox();
        OrderDetailScene.setSpacing(10);
        OrderDetailScene.setPadding(new Insets(10, 10, 10, 10));
        OrderDetailScene.getStylesheets().add("/css/bootstrap3.css");
        OrderDetailScene.getChildren().addAll(this.header, this.content);
        return new Scene(OrderDetailScene, 700, 600);
    }
    
    /**
     * 建立 標頭
     */
    public void setHeader() {
        header.setSpacing(10);
        header.setPrefWidth(950);
        header.setPrefHeight(80);
        header.setAlignment(Pos.CENTER_LEFT);

        // 查看所有訂單
        Button allOrder = new Button();
        allOrder.setText("所有訂單");
        allOrder.setOnAction((e) -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GlobalUIObject.LOGIN.goToLogin(stage);
            stage.setScene(GlobalUIObject.ORDER.setOrder());
            stage.show();
        });

        // 填充中間空白部分
        TilePane filling = new TilePane();
        header.setHgrow(filling, Priority.ALWAYS);
        header.getChildren().addAll(allOrder);
    }
    
    /**
     * 建立訂單明細列表
     */
    private void setContent() {
        content.setSpacing(10);
        content.setAlignment(Pos.BASELINE_CENTER);
        TilePane filling = new TilePane();
        header.setHgrow(filling, Priority.ALWAYS);
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
        Label orderComplete = new Label();
        orderComplete.setText("訂單狀態： " + order.isCompleteString());
        // 修改訂單 | .setDisable(false)
        Button orderAlter = new Button();
        orderAlter.setText("修改訂單");
        if (!(order.isAlter()) || order.isComplete()) {
            orderAlter.setDisable(true);
        }
        orderAlter.setOnAction(e -> {
            if (alter) {
                alter = false;
                orderAlter.setText("修改訂單");
                TreeMap<String, GoodOrder> glist = new TreeMap();
                for (GoodOrder g : order_list) {
                    g.countSubtotal();
                    glist.put(g.getGood_id(), g);
                }
                order.alterOrder(glist);
                GlobalVariables.orderDAO.alter(order);
                orderAlter.setDisable(true);
                orderContent.setEditable(false);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(setOrderDetail());
                stage.show();
            } else {
                alter = true;
                orderAlter.setText("完成修改");
                orderContent.setEditable(true);
            }
            
        });
        orderData.getChildren().addAll(orderId, orderTotal, orderComplete, orderAlter);
        
        // 取消訂單 | null
        if (!(order.isComplete())) {
            Button cancel = new Button();
            cancel.setText("取消訂單");
            orderData.getChildren().add(cancel);
            cancel.setOnAction((e) -> {
                Alert a = new Alert(AlertType.NONE);
                a.setAlertType(AlertType.CONFIRMATION);
                a.setHeaderText("確定取消訂單？");
                Optional<ButtonType> result = a.showAndWait();
                if (result.get().equals(ButtonType.OK)) {
                    GlobalVariables.orderDAO.delete(order_id);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    GlobalUIObject.LOGIN.goToLogin(stage);
                    stage.setScene(GlobalUIObject.ORDER.setOrder());
                    stage.show();
                } else {
                    a.close();
                }
            });
        }
    }
    
    /**
     * 建立訂單明細列表
     */
    private void setOrderContent() {
        this.resetGoodList();
        orderContent.setItems(this.order_list);
        orderContent.setFixedCellSize(60.0);
        orderContent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<GoodOrder, String> column_goodName = createTableColumn("商品名稱", "good_id");
        column_goodName.setPrefWidth(200);
        TableColumn<GoodOrder, Integer> column_price = createTableColumn("單價", "price", new IntegerStringConverter());
        column_price.setPrefWidth(70);
        TableColumn<GoodOrder, Integer> column_quantity = createTableColumn("數量", "quantity", new IntegerStringConverter());
        column_quantity.setPrefWidth(90);
        TableColumn<GoodOrder, Integer> column_subtotal = createTableColumn("小計", "subtotal", new IntegerStringConverter());
        column_subtotal.setPrefWidth(100);
        column_quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        orderContent.getColumns().addAll(column_goodName, column_price, column_quantity, column_subtotal);
        column_goodName.setCellValueFactory(cellData -> {
            GoodOrder g = cellData.getValue();
            return new ReadOnlyStringWrapper(GlobalVariables.productDAO.getById(g.getGood_id()).getName());
        });
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
        column.setStyle( "-fx-alignment: CENTER-LEFT;");
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
        column.setStyle( "-fx-alignment: CENTER;");
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
