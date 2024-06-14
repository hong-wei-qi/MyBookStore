package element;

import java.util.TreeMap;

/**
 * <h2><b>訂單</b></h2>
 * order_id: 訂單ID<br>
 * user_id: 用戶ID<br>
 * list: 訂單商品清單<br>
 * amount: 商品項目數量<br>
 * quantity: 商品總數量<br>
 * total: 訂單總金額<br>
 * alter: 修改狀態 true: 允許被修改 || false: 禁止被修改<br>
 */
public class Order {

    // 訂單ID
    private String order_id;
    // 用戶ID
    private String user_id;
    // 訂單商品清單
    private TreeMap<String, GoodOrder> list;
    // 商品項目數量
    private int amount;
    // 商品總數量
    private int quantity;
    // 訂單總金額
    private int total;
    // 修改狀態 true: 允許被修改 || false: 禁止被修改
    private boolean alter;
    // 訂單是否已完成 true: 已完成 || false: 未完成
    private boolean complete;
    
    /**
     * 初始化 `訂單`
     *
     * @param order_id 訂單ID
     * @param user_id 用戶ID
     * @param amount 商品項目數量
     * @param quantity 商品總數量
     * @param total 訂單總金額
     * @param alter 修改狀態
     * @param complete 訂單是否已完成
     */
    public Order(String order_id, String user_id, int amount, int quantity, int total, int alter, int complete) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.amount = amount;
        this.quantity = quantity;
        this.total = total;
        this.alter = (alter == 1);
        this.complete = (complete == 1);
        this.list = new TreeMap();
    }
    
    /**
     * 初始化 `訂單`
     *
     * @param order_id 訂單ID
     * @param user_id 用戶ID
     * @param list 訂單商品清單
     * @param amount 商品項目數量
     * @param quantity 商品總數量
     * @param total 訂單總金額
     */
    public Order(String order_id, String user_id, TreeMap<String, GoodOrder> list, int amount, int quantity, int total) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.list = list;
        this.amount = amount;
        this.quantity = quantity;
        this.total = total;
        this.alter = true;
        this.complete = false;
    }
    
    /**
     * 初始化 `訂單`
     *
     * @param order_id 訂單ID
     * @param user_id 用戶ID
     * @param list 訂單商品清單
     * @param amount 商品項目數量
     * @param quantity 商品總數量
     * @param total 訂單總金額
     * @param alter 修改狀態
     */
//    public Order(String order_id, String user_id, TreeMap<String, GoodOrder> list, int amount, int quantity, int total, int alter) {
//        this.order_id = order_id;
//        this.user_id = user_id;
//        this.list = list;
//        this.amount = amount;
//        this.quantity = quantity;
//        this.total = total;
//        this.alter = (alter == 1);
//    }
    
    /**
     * 初始化 `訂單`
     *
     * @param order_id 訂單ID
     * @param user_id 用戶ID
     * @param amount 商品項目數量
     * @param quantity 商品總數量
     * @param total 訂單總金額
     * @param alter 修改狀態
     */
//    public Order(String order_id, String user_id, int amount, int quantity, int total, int alter) {
//        this.order_id = order_id;
//        this.user_id = user_id;
//        this.amount = amount;
//        this.quantity = quantity;
//        this.total = total;
//        this.alter = (alter == 1);
//        this.list = new TreeMap();
//    }

    /**
     * 修改訂單，如果此訂單還未被修改過。
     *
     * @param list 訂單商品清單
     * @param amount 商品項目數量
     * @param quantity 商品總數量
     * @param total 訂單總金額
     * @return 是否修改成功
     */
    public boolean alterOrder(TreeMap<String, GoodOrder> list, int amount, int quantity, int total) {
        if (this.complete) {
            return false;
        }
        if (!this.alter) {
            return false;
        }
        this.list = list;
        this.amount = amount;
        this.quantity = quantity;
        this.total = total;
        this.alter = false;
        return true;
    }
    
    /**
     * 修改訂單，如果此訂單還未被修改過。
     *
     * @param list 訂單商品清單
     * @return 是否修改成功
     */
    public boolean alterOrder(TreeMap<String, GoodOrder> list) {
        if (this.complete) {
            return false;
        }
        if (!this.alter) {
            return false;
        }
        this.list = list;
        this.amount = this.list.size();
        this.quantity = 0;
        this.total = 0;
        for (String id : this.list.keySet()) {
            this.quantity += this.list.get(id).getQuantity();
            this.total += this.list.get(id).getSubtotal();
        }
        this.alter = false;
        return true;
    }

    /**
     * 重新設置訂單是否已完成
     *
     * @param complete 訂單是否已完成
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    
    /**
     * @return 訂單是否已完成
     */
    public boolean isComplete() {
        return complete;
    }
    
    /**
     * @return 訂單是否已完成
     */
    public String isCompleteString() {
        return (complete) ? "已完成" : "未完成";
    }

    /**
     * @return 訂單ID
     */
    public String getOrder_id() {
        return order_id;
    }

    /**
     * @return 用戶ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @return 訂單商品清單
     */
    public TreeMap<String, GoodOrder> getList() {
        return list;
    }

    /**
     * @return 商品項目數量
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @return 商品總數量
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return 訂單總金額
     */
    public int getTotal() {
        return total;
    }

    /**
     * @return 修改狀態
     */
    public boolean isAlter() {
        return alter;
    }
}
