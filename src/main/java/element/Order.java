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

    /**
     * 初始化 `訂單`
     *
     * @param order_id 訂單ID
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
    }

    /**
     * 修改訂單，如果此訂單還未被修改過。
     *
     * @param list 訂單商品清單
     * @param amount 商品項目數量
     * @param quantity 商品總數量
     * @param total 訂單總金額
     */
    public boolean alterOrder(TreeMap<String, GoodOrder> list, int amount, int quantity, int total) {
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
