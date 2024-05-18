package element;

import java.util.TreeMap;

/**
 * <h2><b>個人購物車</b></h2>
 * 儲存一個 { Key:商品ID 與 Value:單項商品訂單(GoodOrder) } 的字典<br>
 * 該字典統一稱為: <u>購物車</u>
 */
public class Cart {

    protected TreeMap<String, GoodOrder> cart = new TreeMap();

    /**
     * 初始化 `個人購物車` <u>購物車</u>為空
     */
    public Cart() {
    }

    /**
     * @return <u>購物車</u>
     */
    public TreeMap<String, GoodOrder> getCart() {
        return cart;
    }

    /**
     * 清空<u>購物車</u>
     */
    public void clearCart() {
        this.cart.clear();
    }

    /**
     * 判斷商品存在於<u>購物車</u>中
     *
     * @param good_id 商品ID
     * @return 是否存在
     */
    public boolean inCart(String good_id) {
        return cart.containsKey(good_id);
    }

    /**
     * 將商品新增至購物車中，如果已經存在則將該商品數量加一
     *
     * @param good_id 商品ID
     * @param good_order 單項商品清單
     */
    public void addToCart(String good_id, GoodOrder good_order) {
        if (this.inCart(good_id)) {
            this.addQuantity(good_id);
            return;
        }
        cart.put(good_id, good_order);
    }

    /**
     * 將商品新增至購物車中，如果已經存在則將該商品數量加一
     *
     * @param good_id 商品ID
     * @param price 單價
     * @param quantity 數量
     */
    public void addToCart(String good_id, int price, int quantity) {
        if (this.inCart(good_id)) {
            this.addQuantity(good_id);
            return;
        }
        cart.put(good_id, new GoodOrder(good_id, price, quantity));
    }

    /**
     * 將商品新增至購物車中，前提是該商品存在於<u>購物車</u>
     *
     * @param good_id 商品ID
     */
    public void removeFromCart(String good_id) {
        this.cart.remove(good_id);
    }

    /**
     * 檢查所有商品的數量，如果有商品數量為0則將該商品從<u>購物車</u>中移除
     */
    public void checkGoodQuantity() {
        for (String good_id : cart.keySet()) {
            this.checkGoodQuantity(good_id);
        }
    }

    /**
     * 檢查特定商品的數量，如果該商品數量為0則將該商品從<u>購物車</u>中移除<br>
     * 前提是該商品存在於<u>購物車</u>
     *
     * @param good_id 商品ID
     */
    public void checkGoodQuantity(String good_id) {
        if (!(this.inCart(good_id))) {
            return;
        }
        if (this.cart.get(good_id).getQuantity() == 0) {
            this.cart.remove(good_id);
        }
    }

    /**
     * @return <u>購物車</u>中商品項目數量
     */
    public int getGoodAmount() {
        return this.cart.keySet().size();
    }

    /**
     * @return <u>購物車</u>中商品總數量
     */
    public int getGoodQuantity() {
        int q = 0;
        for (String good_id : cart.keySet()) {
            q += cart.get(good_id).getQuantity();
        }
        return q;
    }

    /**
     * 取得特定商品在<u>購物車</u>中的數量，前提是該商品存在於<u>購物車</u>
     *
     * @param good_id 商品ID
     * @return 數量
     */
    public int getGoodQuantity(String good_id) {
        if (!(this.inCart(good_id))) {
            return 0;
        }
        return cart.get(good_id).getQuantity();
    }

    /**
     * @return <u>購物車</u>總金額
     */
    public int getTotal() {
        int total = 0;
        for (String good_id : cart.keySet()) {
            total += cart.get(good_id).getSubtotal();
        }
        return total;
    }

    /**
     * 重新計算所有小計
     */
    public void countSubtotal() {
        for (String good_id : cart.keySet()) {
            cart.get(good_id).countSubtotal();
        }
    }

    /**
     * 重新計算特定商品的小計，前提是該商品存在於<u>購物車</u>
     *
     * @param good_id 商品ID
     */
    public void countSubtotal(String good_id) {
        if (!(this.inCart(good_id))) {
            return;
        }
        cart.get(good_id).countSubtotal();
    }

    /**
     * 重新設置特定商品的數量，前提是該商品存在於<u>購物車</u>
     *
     * @param good_id 商品ID
     * @param quantity 數量
     */
    public void setQuantity(String good_id, int quantity) {
        if (!(this.inCart(good_id))) {
            return;
        }
        cart.get(good_id).setQuantity(quantity);
        checkGoodQuantity(good_id);
    }

    /**
     * 增加特定商品的數量一單位，前提是該商品存在於<u>購物車</u>
     *
     * @param good_id 商品ID
     */
    public void addQuantity(String good_id) {
        if (!(this.inCart(good_id))) {
            return;
        }
        cart.get(good_id).addQuantity();
    }

    /**
     * 增加特定商品的數量N單位，前提是該商品存在於<u>購物車</u>
     *
     * @param good_id 商品ID
     * @param unit 欲增加的數量
     */
    public void addQuantity(String good_id, int unit) {
        if (!(this.inCart(good_id))) {
            return;
        }
        cart.get(good_id).addQuantity(unit);
    }

    /**
     * 減少特定商品的數量一單位，前提是該商品存在於<u>購物車</u><br>
     * 如果剩餘數量為0，則從<u>購物車</u>移除
     *
     * @param good_id 商品ID
     */
    public void decreaseQuantity(String good_id) {
        if (!(this.inCart(good_id))) {
            return;
        }
        cart.get(good_id).decreaseQuantity();
        checkGoodQuantity(good_id);
    }

    /**
     * 減少特定商品的數量N單位，前提是該商品存在於<u>購物車</u>
     * 如果剩餘數量為0，則從<u>購物車</u>移除
     *
     * @param good_id 商品ID
     * @param unit 欲減少的數量
     */
    public void decreaseQuantity(String good_id, int unit) {
        if (!(this.inCart(good_id))) {
            return;
        }
        cart.get(good_id).decreaseQuantity(unit);
        checkGoodQuantity(good_id);
    }

}
