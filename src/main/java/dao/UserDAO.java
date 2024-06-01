package dao;

import element.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class UserDAO {

    private Connection conn;
    private final String CLASS_NAME = "UserDAO";

    /**
     * 建立資料庫連接
     */
    private void getConn() {
        this.conn = BookStoreDB.getConnection();
    }

    /**
     * 藉由帳號與密碼取得用戶ID 有誤時回傳空字串
     *
     * @param account 帳號
     * @param password 密碼
     * @return String 用戶ID
     */
    public String check(String account, String password) {
        this.getConn();
        String user_id = "";
        String query = "SELECT * FROM `user` WHERE `account` = ? && `password` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, account);
            state.setString(2, password);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                user_id = result.getString("user_id");
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "check(藉由帳號與密碼取得用戶ID 有誤時回傳空字串)", ex);
        }
        return user_id;
    }

    /**
     * 取得所有用戶
     *
     * @return TreeMap<String, element.User> 用戶清單<用戶ID, 用戶物件>
     */
    public TreeMap<String, User> getAll() {
        this.getConn();
        TreeMap<String, User> user_list = new TreeMap();
        String query = "SELECT * FROM `user`;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                User u = new User();
                u.setUser_id(result.getString("user_id"));
                u.setName(result.getString("name"));
                u.setAccount(result.getString("account"));
                u.setPassword(result.getString("password"));
                user_list.put(u.getUser_id(), u);
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getAll(取得所有用戶)", ex);
        }
        return user_list;
    }

    /**
     * 藉由ID取得特定用戶
     *
     * @param user_id 用戶ID
     * @return element.User|null 用戶物件
     */
    public User getById(String user_id) {
        this.getConn();
        User user = null;
        String query = "SELECT * FROM `user` WHERE `user_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, user_id);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                user = new User();
                user.setUser_id(result.getString("user_id"));
                user.setName(result.getString("name"));
                user.setAccount(result.getString("account"));
                user.setPassword(result.getString("password"));
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getById(藉由ID取得特定用戶)", ex);
        }
        return user;
    }

    /**
     * 新增用戶
     *
     * @param user 用戶物件
     * @return 是否新增成功
     */
    public boolean insert(User user) {
        this.getConn();
        boolean success = false;
        String query = "INSERT INTO `user` (`user_id`, `name`, `account`, `password`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, user.getUser_id());
            state.setString(2, user.getName());
            state.setString(3, user.getAccount());
            state.setString(4, user.getPassword());
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "insert(新增用戶)", ex);
        }
        return success;
    }

    /**
     * 刪除用戶
     *
     * @param user_id 用戶ID
     * @return 是否刪除成功
     */
    public boolean delete(String user_id) {
        this.getConn();
        boolean success = false;
        String query = "DELETE FROM `user` WHERE `user_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, user_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "delete(刪除用戶)", ex);
        }
        return success;
    }

    /**
     * 更新用戶
     *
     * @param user 用戶物件
     * @return 是否更新成功
     */
    public boolean update(User user) {
        this.getConn();
        boolean success = false;
        String query = "UPDATE `user` SET `name` = ?, `account` = ?, `password` = ? WHERE `user_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, user.getName());
            state.setString(2, user.getAccount());
            state.setString(3, user.getPassword());
            state.setString(4, user.getUser_id());
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "update(更新用戶)", ex);
        }
        return success;
    }

}
