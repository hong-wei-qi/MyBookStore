package dao;

import element.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class ProductDAO {

    private Connection conn;
    private final String CLASS_NAME = "ProductDAO";

    /**
     * 建立資料庫連接
     */
    private void getConn() {
        this.conn = BookStoreDB.getConnection();
    }

    /**
     * 將資料庫取得的結果轉換成商品清單
     *
     * @param result 資料庫執行結果
     * @return TreeMap<String, element.Book> 商品清單<商品ID, 商品物件>
     */
    private TreeMap<String, Book> putTreeMap(ResultSet result) {
        TreeMap<String, Book> product_list = new TreeMap();
        try {
            while (result.next()) {
                Book b = new Book();
                b.setBook_id(result.getString("product_id"));
                b.setImage(result.getString("image"));
                b.setName(result.getString("name"));
                b.setContent(result.getString("content"));
                b.setPrice(result.getInt("price"));
                b.setCategory1L(result.getString("category_id"));
                product_list.put(b.getBook_id(), b);
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "putTreeMap(取得所有商品)", ex);
        }
        return product_list;
    }

    /**
     * 取得所有商品
     *
     * @return TreeMap<String, element.Book> 商品清單<商品ID, 商品物件>
     */
    public TreeMap<String, Book> getAll() {
        this.getConn();
        TreeMap<String, Book> product_list = new TreeMap();
        String query = "SELECT * FROM `product`;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet result = state.executeQuery();
            product_list = putTreeMap(result);
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getAll(取得所有商品)", ex);
        }
        return product_list;
    }

    /**
     * 藉由ID取得特定一項商品
     *
     * @param id 商品ID
     * @return element.Book|null 商品物件
     */
    public Book getById(String id) {
        this.getConn();
        Book b = null;
        String query = "SELECT * FROM `product` WHERE `product_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, id);
            ResultSet result = state.executeQuery();
            var product_list = putTreeMap(result);
            for (String pid : product_list.keySet()) {
                b = product_list.get(pid);
            }
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getById(藉由ID取得特定一項商品)", ex);
        }
        return b;
    }

    /**
     * 藉由名稱查詢商品
     *
     * @param name_field 搜尋字段
     * @return TreeMap<String, element.Book> 商品清單<商品ID, 商品物件>
     */
    public TreeMap<String, Book> findBtName(String name_field) {
        this.getConn();
        TreeMap<String, Book> product_list = new TreeMap();
        String query = "SELECT * FROM `product` WHERE `name` LIKE ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, "%" + name_field + "%");
            ResultSet result = state.executeQuery();
            product_list = putTreeMap(result);
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "findBtName(藉由名稱查詢商品)", ex);
        }
        return product_list;
    }

    /**
     * 取得特定二階分類的商品
     *
     * @param category2L_id 二階分類ID
     * @return TreeMap<String, element.Book> 商品清單<商品ID, 商品物件>
     */
    public TreeMap<String, Book> getByCategory2L(String category2L_id) {
        this.getConn();
        TreeMap<String, Book> product_list = new TreeMap();
        String query
                = "SELECT `product_id`, `image`, `product`.`name`, `content`, `price`, `product`.`category_id` "
                + "FROM `product` JOIN `category` USING(`category_id`) "
                + "WHERE `category_2L_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category2L_id);
            ResultSet result = state.executeQuery();
            product_list = putTreeMap(result);
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getByCategory2L(取得特定二階分類的商品)", ex);
        }
        return product_list;
    }

    /**
     * 取得特定一階分類的商品
     *
     * @param category1L_id 一階分類ID
     * @return TreeMap<String, element.Book> 商品清單<商品ID, 商品物件>
     */
    public TreeMap<String, Book> getByCategory1L(String category1L_id) {
        this.getConn();
        TreeMap<String, Book> product_list = new TreeMap();
        String query = "SELECT * FROM `product` WHERE `category_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, category1L_id);
            ResultSet result = state.executeQuery();
            product_list = putTreeMap(result);
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "getByCategory1L(取得特定一階分類的商品)", ex);
        }
        return product_list;
    }

    /**
     * 新增一項商品
     *
     * @param product 商品物件
     * @return 是否新增成功
     */
    public boolean insert(Book product) {
        this.getConn();
        boolean success = false;
        String query = "INSERT INTO `product` (`product_id`, `image`, `name`, `content`, `price`, `category_id`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, product.getBook_id());
            state.setString(2, product.getImage());
            state.setString(3, product.getName());
            state.setString(4, product.getContent());
            state.setInt(5, product.getPrice());
            state.setString(6, product.getCategory1L());
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "insert(新增一項商品)", ex);
        }
        return success;
    }

    /**
     * 新增一項商品
     *
     * @param book_id 商品ID
     * @param image 商品圖片路徑
     * @param name 商品名稱
     * @param content 商品摘要
     * @param price 商品價格
     * @param category1L 商品一階分類
     * @return 是否新增成功
     */
    public boolean insert(String book_id, String image, String name, String content, int price, String category1L) {
        boolean success = false;
        Book b = new Book(book_id, image, name, content, price, category1L);
        try {
            success = this.insert(b);
        } catch (Exception ex) {
            BookStoreDB.exception(CLASS_NAME, "insert(新增一項商品)", ex);
        }
        return success;
    }

    /**
     * 刪除一項商品
     *
     * @param id 商品ID
     * @return 是否刪除成功
     */
    public boolean delete(String id) {
        this.getConn();
        boolean success = false;
        String query = "DELETE FROM `product` WHERE `product_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, id);
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "delete(刪除一項商品)", ex);
        }
        return success;
    }

    /**
     * 刪除特定一階分類的商品
     *
     * @param category1L_id 一階分類ID
     * @return 是否刪除成功
     */
    public boolean deleteByCategory1L(String category1L_id) {
        boolean success = false;
        var ids = this.getByCategory1L(category1L_id).keySet();
        int success_num = 0;
        try {
            boolean t;
            for (String id : ids) {
                t = this.delete(id);
                success_num += (t) ? 1 : 0;
                if (success_num == 1) {
                    success = t;
                    continue;
                }
                success &= t;
            }
            if (ids.size() != success_num) {
                System.out.println(String.format("[%s] %s 異常：%d/%d(成功/總數)", CLASS_NAME, "deleteByCategory1L(刪除特定一階分類的商品)", success_num, ids.size()));
            }
        } catch (Exception ex) {
            BookStoreDB.exception(CLASS_NAME, "deleteByCategory1L(刪除特定一階分類的商品)", ex);
        }
        return success;
    }

    /**
     * 刪除特定二階分類的商品
     *
     * @param category2L_id 二階分類ID
     * @return 是否刪除成功
     */
    public boolean deleteByCategory2L(String category2L_id) {
        boolean success = false;
        var ids = this.getByCategory2L(category2L_id).keySet();
        int success_num = 0;
        try {
            boolean t;
            for (String id : ids) {
                t = this.delete(id);
                success_num += (t) ? 1 : 0;
                if (success_num == 1) {
                    success = t;
                    continue;
                }
                success &= t;
            }
            if (ids.size() != success_num) {
                System.out.println(String.format("[%s] %s 異常：%d/%d(成功/總數)", CLASS_NAME, "deleteByCategory2L(刪除特定二階分類的商品)", success_num, ids.size()));
            }
        } catch (Exception ex) {
            BookStoreDB.exception(CLASS_NAME, "deleteByCategory2L(刪除特定二階分類的商品)", ex);
        }
        return success;
    }

    /**
     * 更新特定一項商品
     *
     * @param product 商品物件
     * @return 是否更新成功
     */
    public boolean update(Book product) {
        this.getConn();
        boolean success = false;
        String query = "UPDATE `product` SET `image` = ?, `name` = ?, `content` = ?, `price` = ?, `category_id` = ? WHERE `product_id` = ?;";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, product.getImage());
            state.setString(2, product.getName());
            state.setString(3, product.getContent());
            state.setInt(4, product.getPrice());
            state.setString(5, product.getCategory1L());
            state.setString(6, product.getBook_id());
            success = state.executeUpdate() > 0;
        } catch (SQLException ex) {
            BookStoreDB.exception(CLASS_NAME, "update(更新特定一項商品)", ex);
        }
        return success;
    }

    /**
     * 更新特定一項商品
     *
     * @param book_id 商品ID
     * @param image 商品圖片路徑
     * @param name 商品名稱
     * @param content 商品摘要
     * @param price 商品價格
     * @param category1L 商品一階分類
     * @return 是否更新成功
     */
    public boolean update(String book_id, String image, String name, String content, int price, String category1L) {
        boolean success = false;
        Book b = new Book(book_id, image, name, content, price, category1L);
        try {
            success = this.update(b);
        } catch (Exception ex) {
            BookStoreDB.exception(CLASS_NAME, "update(更新特定一項商品)", ex);
        }
        return success;
    }

}
