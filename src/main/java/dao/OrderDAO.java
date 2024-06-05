package dao;

import element.Order;
import java.util.TreeMap;

/**
 * DAO連接 SaleOrderDAO, OrderDetailDAO
 */
public class OrderDAO {

    private final String CLASS_NAME = "OrderDAO";
    private final SaleOrderDAO SALE_ORDER_DAO = new SaleOrderDAO();
    private final OrderDetailDAO ORDER_DETAIL_DAO = new OrderDetailDAO();

    /**
     * 取得所有訂單
     *
     * @return TreeMap<String, element.Order> 訂單清單<訂單ID, 訂單物件>
     */
    public TreeMap<String, Order> getAll() {
        TreeMap<String, Order> order_list = SALE_ORDER_DAO.getAll();
        for (String id : order_list.keySet()) {
            var good_order = ORDER_DETAIL_DAO.getById(id);
            for (String gId : good_order.keySet()) {
                order_list.get(id).getList().put(gId, good_order.get(gId));
            }
        }
        return order_list;
    }

    /**
     * 取得特定用戶的所有訂單
     *
     * @param user_id 用戶ID
     *
     * @return TreeMap<String, element.Order> 訂單清單<訂單ID, 訂單物件>
     */
    public TreeMap<String, Order> getByUserId(String user_id) {
        TreeMap<String, Order> order_list = SALE_ORDER_DAO.getByUserId(user_id);
        for (String id : order_list.keySet()) {
            var good_order = ORDER_DETAIL_DAO.getById(id);
            for (String gId : good_order.keySet()) {
                order_list.get(id).getList().put(gId, good_order.get(gId));
            }
        }
        return order_list;
    }

    /**
     * 藉由ID取得特定訂單
     *
     * @param order_id 訂單ID
     * @return element.Order|null 訂單物件
     */
    public Order getById(String order_id) {
        Order order = SALE_ORDER_DAO.getById(order_id);
        var good_order = ORDER_DETAIL_DAO.getById(order_id);
        for (String gId : good_order.keySet()) {
            order.getList().put(gId, good_order.get(gId));
        }
        return order;
    }

    /**
     * 新增一筆完整訂單
     *
     * @param order 訂單物件
     * @return 是否新增成功
     */
    public boolean insert(Order order) {
        boolean success = SALE_ORDER_DAO.insert(order);
        success &= ORDER_DETAIL_DAO.insert(order);
        return success;
    }

    /**
     * 刪除訂單
     *
     * @param order_id 訂單ID
     * @return 是否刪除成功
     */
    public boolean delete(String order_id) {
        boolean success = ORDER_DETAIL_DAO.delete(order_id);
        success &= SALE_ORDER_DAO.delete(order_id);
        return success;
    }

    /**
     * 用戶變更訂單
     *
     * @param order 訂單物件
     * @return 是否變更成功
     */
    public boolean alter(Order order) {
        boolean success = SALE_ORDER_DAO.alter(order);
        success &= ORDER_DETAIL_DAO.update(order);
        return success;
    }

    /**
     * 更新訂單
     *
     * @param order 訂單物件
     * @return 是否更新成功
     */
    public boolean update(Order order) {
        boolean success = SALE_ORDER_DAO.update(order);
        success &= ORDER_DETAIL_DAO.update(order);
        return success;
    }

}
