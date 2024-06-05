package dao;

import element.GoodOrder;
import element.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 資料庫連接 db_order_detail
 */
public class OrderDetailDAO {

    private Connection conn;
    private final String CLASS_NAME = "OrderDetailDAO";

    /**
     * 建立資料庫連接
     */
    private void getConn() {
        this.conn = BookStoreDB.getConnection();
    }

    /**
     * 取得所有單項商品訂單 (用途不明)
     *
     * @return ArrayList<element.GoodOrder> 單項商品訂單清單<單項商品訂單ID, 單項商品訂單物件>
     */
    public ArrayList<GoodOrder> getAll() {
        this.getConn();
        ArrayList<GoodOrder> order_detail_list = new ArrayList();
        String query = "SELECT * FROM `order_detail`;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                int subtotal = result.getInt("subtotal");
                int q = result.getInt("quantity");
                GoodOrder o = new GoodOrder(
                        (result.getString("product_id")),
                        (subtotal / q),
                        (q)
                );
                order_detail_list.add(o);
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getAll(取得所有單項商品訂單 (用途不明))", ex);
        }
        return order_detail_list;
    }

    /**
     * 藉由訂單ID取得對應的單項商品訂單
     *
     * @param order_id 訂單ID
     * @return TreeMap<String, element.GoodOrder> 單項商品訂單清單<商品ID, 單項商品訂單物件>
     */
    public TreeMap<String, GoodOrder> getById(String order_id) {
        this.getConn();
        TreeMap<String, GoodOrder> order_detail_list = new TreeMap();
        String query = "SELECT `product_id`, `quantity`, `subtotal` FROM `order_detail` WHERE `order_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order_id);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                int subtotal = result.getInt("subtotal");
                int q = result.getInt("quantity");
                GoodOrder o = new GoodOrder(
                        (result.getString("product_id")),
                        (subtotal / q),
                        (q)
                );
                order_detail_list.put(o.getGood_id(), o);
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getById(藉由訂單ID取得對應的單項商品訂單)", ex);
        }
        return order_detail_list;
    }

    /**
     * 新增單項商品訂單
     *
     * @param order_id 訂單ID
     * @param good_order 單項商品訂單物件
     * @return 是否新增成功
     */
    public boolean insert(String order_id, GoodOrder good_order) {
        this.getConn();
        boolean success = false;
        String query = "INSERT INTO `order_detail` (`order_id`, `product_id`, `quantity`, `subtotal`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order_id);
            state.setString(2, good_order.getGood_id());
            state.setInt(3, good_order.getQuantity());
            state.setInt(4, good_order.getSubtotal());
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "insert(新增單項商品訂單)", ex);
        }
        return success;
    }

    /**
     * 新增訂單中所有 單項商品訂單
     *
     * @param order 訂單物件
     * @return 是否新增成功
     */
    public boolean insert(Order order) {
        boolean success = false;
        var good_order = order.getList();
        int success_num = 0;
        try {
            boolean t;
            for (String product_id : good_order.keySet()) {
                t = this.insert(order.getOrder_id(), good_order.get(product_id));
                success_num += (t) ? 1 : 0;
                if (success_num == 1) {
                    success = t;
                    continue;
                }
                success &= t;
            }
            if (good_order.size() != success_num) {
                System.out.println(String.format("[%s] %s 異常：%d/%d(成功/總數)", CLASS_NAME, "insert(新增訂單中所有 單項商品訂單)", success_num, good_order.size()));
            }

        } catch (Exception ex) {
            BookStoreDB.exception(CLASS_NAME, "insert(新增訂單中所有 單項商品訂單)", ex);
        }
        return success;
    }

    /**
     * 刪除單項商品訂單 (用途不明)
     *
     * @param order_id 單項商品訂單ID
     * @param product_id 商品ID
     * @return 是否刪除成功
     */
    public boolean delete(String order_id, String product_id) {
        this.getConn();
        boolean success = false;
        String query = "DELETE FROM `order_detail` WHERE `order_id` = ? and `product_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order_id);
            state.setString(2, product_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "delete(刪除單項商品訂單 (用途不明))", ex);
        }
        return success;
    }

    /**
     * 刪除訂單中所有 單項商品訂單
     *
     * @param order_id 訂單ID
     * @return 是否新增成功
     */
    public boolean delete(String order_id) {
        this.getConn();
        boolean success = false;
        String query = "DELETE FROM `order_detail` WHERE `order_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, order_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "delete(刪除訂單中所有 單項商品訂單)", ex);
        }
        return success;
    }

    /**
     * 更新整筆訂單 單項商品訂單
     *
     * @param order 訂單物件
     * @return 是否更新成功
     */
    public boolean update(Order order) {
        boolean success = this.delete(order.getOrder_id());
        try {
            success &= this.insert(order);
        } catch (Exception ex) {
            BookStoreDB.exception(CLASS_NAME, "update(更新整筆訂單 單項商品訂單)", ex);
        }
        return success;
    }
}
