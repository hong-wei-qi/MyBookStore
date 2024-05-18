package element;

/**
 * <h2><b>用戶</b></h2>
 * 繼承 `購物車(Cart)`<br>
 * user_id: 用戶ID
 */
public class User extends Cart {

    private String user_id;

    /**
     * 初始化 `個人購物車` <u>購物車</u>為空
     */
    public User(String user_id) {
        super();
        this.user_id = user_id;
    }

    /**
     * @return 用戶ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 重新用戶ID
     *
     * @param user_id 用戶ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
