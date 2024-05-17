package element;

/**
 * <h2><b>單項商品訂單</b></h2>
 * order_id: 訂單ID<br>
 * good_id: 商品ID<br>
 * price: 單價<br>
 * quantity: 數量<br>
 * subtotal: 小計
 */
public class GoodOrder {

    // 商品ID
    private String good_id;
    // 單價
    private int price;
    // 數量
    private int quantity;
    // 小計
    private int subtotal;

//    /**
//     * 初始化 `單項商品訂單` 沒有任何屬性
//     */
//    public GoodOrder() {
//        this.subtotal = 0;
//    }

    /**
     * 初始化 `單項商品訂單`
     *
     * @param good_id 商品ID
     * @param price 單價
     * @param quantity 數量
     */
    public GoodOrder(String good_id, int price, int quantity) {
        this.good_id = good_id;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = price * quantity;
    }


    /**
     * @return 商品ID
     */
    public String getGood_id() {
        return good_id;
    }

    /**
     * @return 單價
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return 數量
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return 小計
     */
    public int getSubtotal() {
        return subtotal;
    }

    /**
     * 重新設置商品ID
     *
     * @param good_id 商品ID
     */
    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    /**
     * 重新設置單價
     *
     * @param good_id 單價
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 重新計算小計
     */
    public void countSubtotal() {
        this.subtotal = this.price * this.quantity;
    }

    /**
     * 重新設置數量
     *
     * @param quantity 數量
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.countSubtotal();
    }

    /**
     * 增加數量一單位
     */
    public void addQuantity() {
        this.quantity++;
        this.countSubtotal();
    }

    /**
     * 增加數量N單位
     *
     * @param unit 欲增加的數量
     */
    public void addQuantity(int unit) {
        this.quantity += unit;
        this.countSubtotal();
    }
    
    /**
     * 減少數量一單位
     */
    public void decreaseQuantity() {
        if (this.quantity == 0) return;
        this.quantity--;
        this.countSubtotal();
    }

    /**
     * 減少數量N單位
     *
     * @param unit 欲減少的數量
     */
    public void decreaseQuantity(int unit) {
        this.quantity = (this.quantity == 0 || this.quantity < unit) ? 0 : (this.quantity-unit);
        this.countSubtotal();
    }
}
