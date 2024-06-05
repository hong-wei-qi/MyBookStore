package main;

import dao.CategoryDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import element.Book;
import element.Cart;
import element.CategoryTwoLevel;
import element.Order;
import element.User;
import java.util.ArrayList;
import java.util.TreeMap;
public class GlobalVariables {
    
    public static CategoryDAO categoryDAO = new CategoryDAO();
    
    public static ProductDAO productDAO = new ProductDAO();

    public static UserDAO userDAO = new UserDAO();
    
    public static OrderDAO orderDAO = new OrderDAO();
    
    // 目前操作用戶
    public static String now_user = "";
    
    public static Cart cart = new Cart();

    // 用戶清單
    // public static TreeMap<String, User> user_list = new TreeMap();

    // 二階分類
    // public static CategoryTwoLevel category = new CategoryTwoLevel();

    // 書籍(商品)清單
    // public static TreeMap<String, Book> good_list = new TreeMap();

    // 訂單列表
    // public static TreeMap<String, Order> order_list = new TreeMap();

    // 在購物車頁面中 被選擇的項目清單
    public static ArrayList<String> inCartList_SelectList = new ArrayList();
}
