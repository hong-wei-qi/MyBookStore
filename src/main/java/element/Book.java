package element;

/**
 * <h2><b>書籍</b></h2>
 * book_id: 書籍ID<br>
 * image: 書籍圖片路徑<br>
 * name: 書籍名稱<br>
 * content: 書籍摘要<br>
 * price: 書籍價格<br>
 * category1L: 書籍一階分類<br>
 * category2L: 書籍二階分類
 */
public class Book {

    // 書籍ID
    private String book_id;
    // 書籍圖片路徑
    private String image;
    // 書籍名稱
    private String name;
    // 書籍摘要
    private String content;
    // 書籍價格
    private int price;
    // 書籍一階分類
    private String category1L;
    // 書籍二階分類
    private String category2L;

    /**
     * 初始化 `書籍` 沒有任何屬性
     */
    public Book() {
    }

    /**
     * 初始化 `書籍`
     *
     * @param book_id 書籍ID
     * @param image 書籍圖片路徑
     * @param name 書籍名稱
     * @param content 書籍摘要
     * @param price 書籍價格
     * @param category1L 書籍一階分類
     * @param category2L 書籍二階分類
     */
    public Book(String bool_id, String image, String name, String content, int price, String category1L, String category2L) {
        this.book_id = bool_id;
        this.image = image;
        this.name = name;
        this.content = content;
        this.price = price;
        this.category1L = category1L;
        this.category2L = category2L;
    }

    /**
     * @return 書籍ID
     */
    public String getBook_id() {
        return book_id;
    }

    /**
     * @return 書籍圖片路徑
     */
    public String getImage() {
        return image;
    }

    /**
     * @return 書籍名稱
     */
    public String getName() {
        return name;
    }

    /**
     * @return 書籍摘要
     */
    public String getContent() {
        return content;
    }

    /**
     * @return 書籍價格
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return 書籍一階分類
     */
    public String getCategory1L() {
        return category1L;
    }

    /**
     * @return 書籍二階分類
     */
    public String getCategory2L() {
        return category2L;
    }

    /**
     * 重新設置書籍ID
     *
     * @param book_id 書籍ID
     */
    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    /**
     * 重新設置書籍圖片路徑
     *
     * @param image 書籍圖片路徑
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 重新設置書籍名稱
     *
     * @param name 書籍名稱
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 重新設置書籍摘要
     *
     * @param content 書籍摘要
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 重新設置書籍價格
     *
     * @param price 書籍價格
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 重新設置書籍一階分類
     *
     * @param category1L 書籍一階分類
     */
    public void setCategory1L(String category1L) {
        this.category1L = category1L;
    }

    /**
     * 重新設置書籍二階分類
     *
     * @param category2L 書籍二階分類
     */
    public void setCategory2L(String category2L) {
        this.category2L = category2L;
    }

}
