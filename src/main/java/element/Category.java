package element;

import java.util.Set;
import java.util.TreeMap;

/**
 * <h2><b>分類</b></h2>
 * 儲存一個 { Key:分類ID 與 Value:分類中文名稱 } 的字典<br>
 * 該字典統一稱為: <u>分類集合</u>
 */
public class Category {

    // 分類集合 (儲存一個 { Key:分類ID 與 Value:分類中文名稱 } 的字典)
    private TreeMap<String, String> category = new TreeMap();

    /**
     * 初始化 `分類` <u>分類集合</u>為空
     */
    public Category() {
    }

    /**
     * 初始化 `分類` 預先設置<u>分類集合</u>
     *
     * @param category <u>分類集合</u>
     */
    public Category(TreeMap<String, String> category) {
        this.category = category;
    }

    /**
     * 重新設置<u>分類集合</u>
     *
     * @param category <u>分類集合</u>
     * @return 是否重新設置成功
     */
    public boolean setCategory(TreeMap<String, String> category) {
        try {
            this.category = category;
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * @return <u>分類集合</u>
     */
    public TreeMap<String, String> getCategory() {
        return category;
    }

    /**
     * 為<u>分類集合</u>新增項目
     *
     * @param category_id 分類ID
     * @param category 分類中文名稱
     * @return 是否新增成功
     */
    public boolean setNewCategory(String category_id, String category) {
        try {
            this.category.put(category_id, category);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * 藉由分類ID取得對應的分類中文名稱
     *
     * @param category_id 分類ID
     * @return 分類中文名稱|空字串
     */
    public String getValue(String category_id) {
        try {
            return this.category.get(category_id);
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    /**
     * 藉由分類中文名稱取得對應的分類ID
     *
     * @param category 分類中文名稱
     * @return 分類ID|空字串
     */
    public String getKey(String category) {
        for (String id : this.category.keySet()) {
            if (this.category.get(id).equals(category)) {
                return id;
            }
        }
        return "";
    }

    /**
     * 檢查分類ID是否存在於<u>分類集合</u>
     *
     * @param category_id 分類ID
     * @return 是否存在
     */
    public boolean checkKeyExist(String category_id) {
        return this.category.containsKey(category_id);
    }

    /**
     * 檢查分類中文名稱是否存在於<u>分類集合</u>
     *
     * @param category 中文名稱
     * @return 是否存在
     */
    public boolean checkValueExist(String category) {
        return this.category.containsValue(category);
    }

    /**
     * <u>分類集合</u>的所有Key，也就是所有分類ID
     *
     * @return 所有分類ID集合，按升序排序
     */
    public Set<String> keySet() {
        return this.category.keySet();
    }
}
