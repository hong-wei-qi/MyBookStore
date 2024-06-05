package dao;

import element.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

/**
 * 資料庫連接 db_sale_order
 */
public class SaleOrderDAO {

    private Connection conn;
    private final String CLASS_NAME = "SaleOrderDAO";

    /**
     * 建立資料庫連接
     */
    private void getConn() {
        this.conn = BookStoreDB.getConnection();
    }

    /**
     * 取得所有訂單 不含詳細內容
     *
     * @return TreeMap<String, element.Order> 訂單清單<訂單ID, 訂單物件>
     */
    public TreeMap<String, Order> getAll() {
        this.getConn();
        TreeMap<String, Order> order_list = new TreeMap();
        String query = "SELECT * FROM `sale_order`;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                Order o = new Order(
                        (result.getString("order_id")),
                        (result.getString("user_id")),
                        (result.getInt("amount")),
                        (result.getInt("quantity")),
                        (result.getInt("total")),
                        (result.getInt("alter"))
                );
                order_list.put(o.getOrder_id(), o);
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getAll(取得所有訂單 不含詳細內容)", ex);
        }
        return order_list;
    }

    /**
     * 取得特定用戶的所有訂單 不含詳細內容
     *
     * @param user_id 用戶ID
     *
     * @return TreeMap<String, element.Order> 訂單清單<訂單ID, 訂單物件>
     */
    public TreeMap<String, Order> getByUserId(String user_id) {
        this.getConn();
        TreeMap<String, Order> order_list = new TreeMap();
        String query = "SELECT * FROM `sale_order` WHERE `user_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, user_id);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                Order o = new Order(
                        (result.getString("order_id")),
                        (result.getString("user_id")),
                        (result.getInt("amount")),
                        (result.getInt("quantity")),
                        (result.getInt("total")),
                        (result.getInt("alter"))
                );
                order_list.put(o.getOrder_id(), o);
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getByUserId(取得特定用戶的所有訂單 不含詳細內容)", ex);
        }
        return order_list;
    }

    /**
     * 藉由ID取得特定訂單 不含詳細內容
     *
     * @param order_id 訂單ID
     * @return element.Order|null 訂單物件
     */
    public Order getById(String order_id) {
        this.getConn();
        Order order = null;
        String query = "SELECT * FROM `sale_order` WHERE `order_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order_id);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                order = new Order(
                        (result.getString("order_id")),
                        (result.getString("user_id")),
                        (result.getInt("amount")),
                        (result.getInt("quantity")),
                        (result.getInt("total")),
                        (result.getInt("alter"))
                );
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getById(藉由ID取得特定訂單 不含詳細內容)", ex);
        }
        return order;
    }

    /**
     * 新增訂單
     *
     * @param order 訂單物件
     * @return 是否新增成功
     */
    public boolean insert(Order order) {
        this.getConn();
        boolean success = false;
        String query = "INSERT INTO `sale_order` (`order_id`, `user_id`, `amount`, `quantity`, `total`, `alter`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order.getOrder_id());
            state.setString(2, order.getUser_id());
            state.setInt(3, order.getAmount());
            state.setInt(4, order.getQuantity());
            state.setInt(5, order.getTotal());
            state.setInt(6, (order.isAlter()) ? 1 : 0);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "insert(新增訂單)", ex);
        }
        return success;
    }

    /**
     * 刪除訂單
     *
     * @param order_id 訂單ID
     * @return 是否刪除成功
     */
    public boolean delete(String order_id) {
        this.getConn();
        boolean success = false;
        String query = "DELETE FROM `sale_order` WHERE `order_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "delete(刪除訂單)", ex);
        }
        return success;
    }

    /**
     * 用戶變更訂單
     *
     * @param order 訂單物件
     * @return 是否變更成功
     */
    public boolean alter(Order order) {
        this.getConn();
        boolean success = false;
        String query = "UPDATE `sale_order` SET `amount` = ?, `quantity` = ?, `total` = ?, `alter` = 0 WHERE `order_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setInt(1, order.getAmount());
            state.setInt(2, order.getQuantity());
            state.setInt(3, order.getTotal());
            state.setString(4, order.getOrder_id());
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "alter(用戶變更訂單)", ex);
        }
        return success;
    }

    /**
     * 更新訂單
     *
     * @param order 訂單物件
     * @return 是否更新成功
     */
    public boolean update(Order order) {
        this.getConn();
        boolean success = false;
        String query = "UPDATE `sale_order` SET `user_id` = ?, `amount` = ?, `quantity` = ?, `total` = ?, `alter` = ? WHERE `order_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order.getUser_id());
            state.setInt(2, order.getAmount());
            state.setInt(3, order.getQuantity());
            state.setInt(4, order.getTotal());
            state.setInt(5, (order.isAlter()) ? 1 : 0);
            state.setString(6, order.getOrder_id());
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "update(更新訂單)", ex);
        }
        return success;
    }
}
