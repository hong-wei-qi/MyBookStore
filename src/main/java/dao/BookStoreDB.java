package dao;

import java.sql.Connection;
import java.sql.DriverManager;
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

}
