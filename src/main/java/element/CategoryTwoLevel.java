package element;

import java.util.Set;
import java.util.TreeMap;

/**
 * <h2><b>二階分類</b></h2>
 * 儲存一個 { Key:二階分類ID 與 Value:`分類`(element.Category) } 的字典<br>
 * 該字典統一稱為: <u>二階分類集合</u><br>
 * Value:`分類`統一稱為: <i>分類集合</i> { Key:一階分類ID 與 Value:分類中文名稱 }
 */
public class CategoryTwoLevel {

    // 二階分類中文名稱集合
    private Category category2L_name = new Category();
    
    // 二階分類集合 (儲存一個 { Key:分類ID 與 Value:`分類`(element.Category) } 的字典)
    private TreeMap<String, Category> category2L = new TreeMap();

    /**
     * 初始化 `分類` <u>二階分類中文名稱集合</u> 與 <u>二階分類集合</u> 為空
     */
    public CategoryTwoLevel() {
    }

    /**
     * 初始化 `分類` 預先設置<u>二階分類中文名稱集合</u> 與 <u>二階分類集合</u>
     *
     * @param category2L <u>二階分類集合</u>
     */
    public CategoryTwoLevel(Category category2L_name, TreeMap<String, Category> category2L) {
        this.category2L_name = category2L_name;
        this.category2L = category2L;
    }
    
    /**
     * 重新設置<u>二階分類中文名稱集合</u>
     *
     * @param category2L_name <u>二階分類中文名稱集合</u>
     * @return 是否重新設置成功
     */
    public boolean setCategory(Category category2L_name) {
        try {
            this.category2L_name = category2L_name;
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * 重新設置<u>二階分類集合</u>
     *
     * @param category2L <u>二階分類集合</u>
     * @return 是否重新設置成功
     */
    public boolean setCategory(TreeMap<String, Category> category2L) {
        try {
            this.category2L = category2L;
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * @return <u>二階分類中文名稱集合</u>
     */
    public Category getCategory2L_name() {
        return category2L_name;
    }
    
    /**
     * @return <u>二階分類集合</u>
     */
    public TreeMap<String, Category> getCategory() {
        return category2L;
    }

    /**
     * 藉由一階分類ID取得對應的二階分類ID
     *
     * @param category1L_id 一階分類ID
     * @return 二階分類ID
     */
    public String getCategory2L_id(String category1L_id) {
        for (String category2L_id : this.category2L.keySet()) {
            if (this.category2L.get(category2L_id).checkKeyExist(category1L_id)) {
                return category2L_id;
            }
        }
        return "";
        
    }
    
    /**
     * 藉由二階分類ID取得對應的<i>分類集合</i>
     *
     * @param category2L_id 二階分類ID
     * @return <i>分類集合</i>|null
     */
    public Category getCategory(String category2L_id) {
        try {
            return this.category2L.get(category2L_id);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    /**
     * 藉由二階分類ID取得對應的分類中文名稱
     *
     * @param category2L_id 二階分類ID
     * @return 分類中文名稱 | 空字串
     */
    public String getCategory2L_name(String category2L_id) {
        return this.category2L_name.getValue(category2L_id);
    }

    /**
     * 藉由一階分類ID與二階分類ID取得對應的分類中文名稱
     *
     * @param category1L_id 一階分類ID
     * @param category2L_id 二階分類ID
     * @return 分類中文名稱|""
     */
    public String getCategoryValue(String category1L_id, String category2L_id) {
        try {
            return this.category2L.get(category2L_id).getValue(category1L_id);
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    /**
     * 為<u>二階分類中文名稱集合</u>新增項目
     *
     * @param category2L_id 二階分類ID
     * @param category2L_name    二階分類中文名稱
     * @return 是否新增成功
     */
    public boolean setNewCategory2L_name(String category2L_id, String category2L_name) {
        try {
            this.category2L_name.setNewCategory(category2L_id, category2L_name);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    /**
     * 為<u>二階分類集合</u>新增項目
     *
     * @param category2L_id 二階分類ID
     * @param category    <i>分類集合</i>
     * @return 是否新增成功
     */
    public boolean setNewCategory(String category2L_id, Category category) {
        try {
            this.category2L.put(category2L_id, category);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * 為<i>分類集合</i>新增項目
     *
     * @param category2L_id 二階分類ID
     * @param category_id 分類ID
     * @param category 分類中文名稱
     * @return 是否新增成功
     */
    public boolean setNewCategory(String category2L_id, String category_id, String category) {
        try {
            this.category2L.get(category2L_id).setNewCategory(category_id, category);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * 檢查二階分類ID是否存在於<u>二階分類集合</u>
     *
     * @param category2L_id 二階分類ID
     * @return 是否存在
     */
    public boolean checkKeyExist(String category2L_id) {
        return this.category2L_name.checkKeyExist(category2L_id);
    }

    /**
     * 檢查一階分類ID是否存在於二階分類ID對應的<i>分類集合</i>中
     *
     * @param category2L_id 二階分類ID
     * @param category1L_id 一階分類ID
     * @return 是否存在
     */
    public boolean checkKeyExist(String category2L_id, String category1L_id) {
        return this.category2L.get(category2L_id).checkKeyExist(category1L_id);
    }

    /**
     * <u>二階分類集合</u>的所有Key，也就是所有二階分類ID
     *
     * @return 所有二階分類ID集合，按升序排序
     */
    public Set<String> keySet() {
        return this.category2L_name.keySet();
    }

    /**
     * 二階分類ID對應的<i>分類集合</i>的所有Key，也就是對應的一階分類ID
     *
     * @param category2L_id 二階分類ID
     * @return 對應的一階分類ID集合，按升序排序
     */
    public Set<String> keySet(String category2L_id) {
        return this.category2L.get(category2L_id).keySet();
    }

}
