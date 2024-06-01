package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 資料庫連接 db_book_store
 */
public class BookStoreDB {

    // 已連線物件
    private static Connection conn = null;

    // 連線所需資料
    private static final String DB_URL = "jdbc:mariadb://127.0.0.1:3306/db_book_store";
    private static final String USER = "mis";
    private static final String PASSWORD = "mis123";

    /**
     * 取得連線物件
     *
     * @return Connection 已連線物件
     */
    public static Connection getConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            } else {
                conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return conn;
    }
    
    /**
     * 執行 查詢
     * @param query SQL查詢語法
     * @param method 引用方法對象 { 類別, 方法 }
     * @return ResultSet|null 查詢結果
     */
    public static ResultSet select(String query, String[] method_data) {
        getConnection();
        ResultSet result = null;
        try {
            PreparedStatement state = conn.prepareStatement(query);
            result = state.executeQuery();
        } catch (SQLException ex) {
            exception(method_data[0], method_data[1], ex);
        }
        return result;
    }
    
    /**
     * 執行例外
     * @param class_name 類別名稱
     * @param method_name 引用方法對象名稱
     * @param ex 例外
     */
    public static void exception(String class_name, String method_name, SQLException ex) {
        System.out.println(String.format("[%s] %s 失敗： ", class_name, method_name) + ex.toString());
    }
    
    /**
     * 執行例外
     * @param class_name 類別名稱
     * @param method_name 引用方法對象名稱
     * @param ex 例外
     */
    public static void exception(String class_name, String method_name, Exception ex) {
        System.out.println(String.format("[%s] %s 失敗： ", class_name, method_name) + ex.toString());
    }

}
