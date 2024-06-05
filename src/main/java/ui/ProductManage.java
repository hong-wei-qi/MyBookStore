package ui;

import element.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import main.GlobalVariables;

/**
 * <h2><b>商品管理 UI介面</b></h2><br>
 * product_list: 商品列表內容<br>
 * content: 商品列表<br>
 * console: 操作訊息
 */
public class ProductManage {

    // 商品列表內容
    private ObservableList<Book> product_list;

    public Button logout = new Button();
    
    // 商品列表
    public TableView content = new TableView();

    // 操作訊息
    public TextArea console = new TextArea();

    /**
     * 初始化 `商品管理 UI介面`
     */
    public ProductManage() {
        console.setEditable(false);
        setLogoutButton();
        setContent();
    }

    private void setLogoutButton() {
        logout.setText("登出");
        logout.setOnAction((e) -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            GlobalVariables.now_user = "";
            GlobalUIObject.LOGIN.goToLogin(stage);
        });
    }
    
    /**
     * 建立商品列表
     */
    private void setContent() {
        this.resetProductList();
        content.setEditable(true);
        content.setItems(this.product_list);
        content.setFixedCellSize(60.0);

        TableColumn<Book, String> column_book_id = createTableColumn("ID", "book_id");
        TableColumn<Book, String> column_name = createTableColumn("名稱", "name");
        TableColumn<Book, Integer> column_price = createTableColumn("價格", "price", new IntegerStringConverter());
        TableColumn<Book, String> column_category1L = createTableColumn("一階分類", "category1L");
        TableColumn<Book, String> column_image = createTableColumn("圖片路徑", "image");
        TableColumn<Book, String> column_content = createTableColumn("摘要", "content");
        column_content.setMaxWidth(250);
        content.getColumns().addAll(column_book_id, column_name, column_price, column_category1L, column_image, column_content);

        setOnEditCommitHandler(column_book_id, "book_id");
        setOnEditCommitHandler(column_name, "name");
        setOnEditCommitHandler(column_price, "price");
        setOnEditCommitHandler(column_category1L, "category1L");
        setOnEditCommitHandler(column_image, "image");
        setOnEditCommitHandler(column_content, "content");

        TableColumn<Book, Void> column_action = new TableColumn("操作");
        content.getColumns().add(column_action);
        column_action.setCellFactory(setActionButton());
    }

    /**
     * 建立表格欄位 文字格式
     *
     * @return TableColumn<Book, String> 欄位
     */
    private TableColumn<Book, String> createTableColumn(String column_name, String object_name) {
        TableColumn<Book, String> column = new TableColumn();
        column.setText(column_name);
        column.setCellValueFactory(new PropertyValueFactory(object_name));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setStyle( "-fx-alignment: CENTER-LEFT;");
        return column;
    }

    /**
     * 建立表格欄位 整數格式
     *
     * @return TableColumn<Book, Integer> 欄位
     */
    private TableColumn<Book, Integer> createTableColumn(String column_name, String object_name, IntegerStringConverter c) {
        TableColumn<Book, Integer> column = new TableColumn();
        column.setText(column_name);
        column.setCellValueFactory(new PropertyValueFactory(object_name));
        column.setCellFactory(TextFieldTableCell.forTableColumn(c));
        column.setStyle( "-fx-alignment: CENTER;");
        return column;
    }

    /**
     * 表格修改事件
     */
    private <T> void setOnEditCommitHandler(TableColumn<Book, T> column, String object_name) {
        column.setOnEditCommit((e) -> {
            Book product = e.getRowValue();
            switch (object_name) {
                case "book_id":
                    product.setBook_id(e.getNewValue().toString());
                    break;
                case "name":
                    product.setName(e.getNewValue().toString());
                    break;
                case "price":
                    product.setPrice((Integer) e.getNewValue());
                    break;
                case "category1L":
                    product.setCategory1L(e.getNewValue().toString());
                    break;
                case "image":
                    product.setImage(e.getNewValue().toString());
                    break;
                case "content":
                    product.setContent(e.getNewValue().toString());
                    break;
            }
        });
    }

    /**
     * 設定操作欄位按鈕
     */
    private Callback<TableColumn<Book, Void>, TableCell<Book, Void>> setActionButton() {
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = (final TableColumn<Book, Void> param) -> new TableCell<>() {
            
            private final Button btnDelete = new Button("刪除");
            private final Button btnUpdate = new Button("更新");
            private final Button btnDuplicate = new Button("複製");
            private final Button btnSave = new Button("加入");
            
            {
                btnDelete.setOnAction(event -> {
                    Book product = getTableView().getItems().get(getIndex());
                    product_list.remove(product);
                    GlobalVariables.productDAO.delete(product.getBook_id());
                    console.setText(console.getText() + "刪除: " + product.getName() + "\n");
                });
                btnDelete.getStyleClass().setAll("button", "danger");
                
                btnUpdate.setOnAction(event -> {
                    Book product = getTableView().getItems().get(getIndex());
                    GlobalVariables.productDAO.update(product);
                    console.setText(console.getText() + "更新: " + product.getName() + "\n");
                });
                btnUpdate.getStyleClass().setAll("button", "primary");
                
                btnDuplicate.setOnAction(event -> {
                    Book product = getTableView().getItems().get(getIndex());
                    product_list.add(new Book(product.getBook_id(), product.getImage(), product.getName(), product.getContent(), product.getPrice(), product.getCategory1L()));
                    console.setText(console.getText() + "複製: " + product.getName() + "\n");
                });
                btnDuplicate.getStyleClass().setAll("button", "warning");
                
                btnSave.setOnAction(event -> {
                    Book product = getTableView().getItems().get(getIndex());
                    GlobalVariables.productDAO.insert(product);
                    console.setText(console.getText() + "加入: " + product.getName() + "\n");
                });
                btnSave.getStyleClass().setAll("button", "success");
            }
            
            HBox pane = new HBox(btnDelete, btnUpdate, btnDuplicate, btnSave);
            
            {
                pane.setAlignment(Pos.CENTER);
                pane.setSpacing(10);
            }
            
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
        return cellFactory;
    }

    /**
     * 商品列表內容 建立
     */
    public void resetProductList() {
        this.product_list = FXCollections.observableArrayList();
        var list = GlobalVariables.productDAO.getAll();
        for (String id : list.keySet()) {
            this.product_list.add(list.get(id));
        }
    }
}
