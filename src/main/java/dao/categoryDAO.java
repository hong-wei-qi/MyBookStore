package dao;

import element.Category;
import element.CategoryTwoLevel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

    private Connection conn;
    
    /**
     * 建立資料庫連接
     */
    private void getConn() {
        this.conn = BookStoreDB.getConnection();
    }
    
    /**
     * 取得所有分類
     * @return element.CategoryTwoLevel 二階分類
     */
    public CategoryTwoLevel getAll () {
        this.getConn();
        CategoryTwoLevel category = new CategoryTwoLevel();
        try {
            Category c_two = this.getTwoLevel();
            for (String c_two_id : c_two.keySet()) {
                category.setNewCategory2L_name(c_two_id, c_two.getValue(c_two_id));
                Category c_one = this.getOneLevel(c_two_id);
                category.setNewCategory(c_two_id, c_one);
            }
        } catch (Exception ex) {
            System.out.println("[CategoryDAO] getAll(取得所有分類) 失敗： " + ex.toString());
        }
        return category;
    }
    
    /**
     * 取得所有二階分類 不包含底下的一階分類
     * @return element.Category 二階分類
     */
    public Category getTwoLevel () {
        this.getConn();
        Category categoryTwoLevel = new Category();
        String query = "SELECT * FROM `category_two_level`;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                categoryTwoLevel.setNewCategory(
                        result.getString("category_2L_id"), 
                        result.getString("name")
                );
            }
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] getTwoLevel(取得所有二階分類) 失敗： " + ex.toString());
        }
        return categoryTwoLevel;
    }
    
    /**
     * 藉由二階分類ID 取得對應的所有一階分類
     * @param category2L_id 二階分類ID
     * @return Category 一階分類
     */
    public Category getOneLevel (String category2L_id) {
        this.getConn();
        Category category = new Category();
        String query = "SELECT `category_id`, `name` FROM category WHERE `category_2L_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category2L_id);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                category.setNewCategory(
                        result.getString("category_id"), 
                        result.getString("name")
                );
            }
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] getOneLevel(藉由二階分類ID 取得對應的所有一階分類) 失敗： " + ex.toString());
        }
        return category;
    }
    
    /**
     * 取得所有一階分類 (用途不明)
     */
    public Category getOneLevel () {
        this.getConn();
        Category category = new Category();
        String query = "SELECT `category_id`, `name` FROM category;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                category.setNewCategory(
                        result.getString("category_id"), 
                        result.getString("name")
                );
            }
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] getOneLevel(取得所有一階分類 (用途不明)) 失敗： " + ex.toString());
        }
        return category;
    }
    
    /**
     * 完整新增二階分類 不含底下的一階分類
     * @param category2L 二階分類
     * @return 是否新增成功
     */
    public boolean insertTwoLevel (Category category2L) {
        this.getConn();
        boolean success = false;
        try {
            for (String c_two_id : category2L.keySet()) {
                success = this.insertTwoLevel(
                        c_two_id, 
                        category2L.getValue(c_two_id)
                );
            }
        } catch (Exception ex) {
            System.out.println("[CategoryDAO] insertTwoLevel(完整新增二階分類) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 完整新增二階分類底下的所有一階分類
     * @param category2L_id 二階分類ID
     * @param category1L 一階分類
     * @return 是否新增成功
     */
    public boolean insertOneLevel (String category2L_id, Category category1L) {
        this.getConn();
        boolean success = false;
        try {
            for (String c_one_id : category1L.keySet()) {
                success = this.insertOneLevel(
                        c_one_id, 
                        category1L.getValue(c_one_id), 
                        category2L_id
                );
            }
        } catch (Exception ex) {
            System.out.println("[CategoryDAO] insertOneLevel(完整新增二階分類底下的所有一階分類) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 新增一個二階分類
     * @param category2L_id 二階分類ID
     * @param category2L_name 二階分類中文名稱
     * @return 是否新增成功
     */
    public boolean insertTwoLevel (String category2L_id, String category2L_name) {
        this.getConn();
        boolean success = false;
        String query = "INSERT INTO `category_two_level` (`category_2L_id`, `name`) VALUES(?, ?);";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category2L_id);
            state.setString(2, category2L_name);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] insertTwoLevel(新增一個二階分類) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 新增一個一階分類
     * @param category1L_id 一階分類ID
     * @param category1L_name 一階分類中文名稱
     * @param category2L_id 二階分類ID
     * @return 是否新增成功
     */
    public boolean insertOneLevel (String category1L_id, String category1L_name, String category2L_id) {
        this.getConn();
        boolean success = false;
        String query = "INSERT INTO `category` (`category_id`, `name`, `category_2L_id`) VALUES(?, ?, ?);";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category1L_id);
            state.setString(2, category1L_name);
            state.setString(3, category2L_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] insertOneLevel(新增一個一階分類) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 刪除一個二階分類 <b>包含它底下的所有一階分類</b>
     * @param category2L_id 二階分類ID
     * @return 是否刪除成功
     */
    public boolean deleteTwoLevel (String category2L_id) {
        this.getConn();
        boolean success = false;
        String query = "DELETE FROM `category` WHERE `category_2L_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category2L_id);
            success = state.executeUpdate() > 0;
            query = "DELETE FROM `category_two_level` WHERE `category_2L_id` = ?;";
            state = conn.prepareStatement(query);
            state.setString(1, category2L_id);
            success &= state.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] deleteTwoLevel(刪除一個二階分類 包含它底下的所有一階分類) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 刪除一個一階分類
     * @param category1L_id 一階分類ID
     * @return 是否刪除成功
     */
    public boolean deleteOneLevel (String category1L_id) {
        this.getConn();
        boolean success = false;
        String query = 
                "DELETE FROM `category` WHERE `category_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category1L_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] deleteOneLevel(刪除一個一階分類) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 更新一個二階分類 不包含Key
     * @param category2L_id 二階分類ID
     * @param category2L_name 二階分類中文名稱
     * @return 是否更新成功
     */
    public boolean updateTwoLevel (String category2L_id, String category2L_name) {
        this.getConn();
        boolean success = false;
        String query = "UPDATE `category_two_level` SET `name` = ? WHERE `category_2L_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category2L_name);
            state.setString(2, category2L_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] updateTwoLevel(更新一個二階分類 不包含Key) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 更新一個一階分類 不包含Key和對應的二階分類ID
     * @param category1L_id 一階分類ID
     * @param category1L_name 一階分類中文名稱
     * @return 是否更新成功
     */
    public boolean updateOneLevel (String category1L_id, String category1L_name) {
        this.getConn();
        boolean success = false;
        String query = "UPDATE `category` SET `name` = ? WHERE `category_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category1L_name);
            state.setString(2, category1L_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] updateOneLevel(更新一個一階分類 不包含Key) 失敗： " + ex.toString());
        }
        return success;
    }
    
    /**
     * 更新一個一階分類 不包含Key
     * @param category1L_id 一階分類ID
     * @param category1L_name 一階分類中文名稱
     * @param category2L_id 二階分類ID
     * @return 是否更新成功
     */
    public boolean updateOneLevel (String category1L_id, String category1L_name, String category2L_id) {
        this.getConn();
        boolean success = false;
        String query = "UPDATE `category` SET `name` = ?, `category_2L_id` = ? WHERE `category_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category1L_name);
            state.setString(2, category2L_id);
            state.setString(3, category1L_id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("[CategoryDAO] updateOneLevel(更新一個一階分類 不包含Key) 失敗： " + ex.toString());
        }
        return success;
    }
}
