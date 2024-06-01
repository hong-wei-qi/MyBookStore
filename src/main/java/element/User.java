package element;

/**
 * <h2><b>用戶</b></h2>
 * 繼承 `購物車(Cart)`<br>
 * user_id: 用戶ID
 * name: 用戶名稱
 * account: 用戶帳號
 * password: 用戶密碼
 */
public class User /* extends Cart */ {

    private String user_id;
    private String name;
    private String account;
    private String password;

    /**
     * 初始化 `個人購物車` <u>購物車</u>為空
     */
    public User() {
        // super();
    }
    
    /**
     * 初始化 `個人購物車` <u>購物車</u>為空
     */
    public User(String user_id) {
        // super();
        this.user_id = user_id;
        this.name = "less";
        this.account = "null";
        this.password = "null";
    }

    /**
     * 初始化 `個人購物車` <u>購物車</u>為空
     */
    public User(String user_id, String name, String account, String password) {
        super();
        this.user_id = user_id;
        this.name = name;
        this.account = account;
        this.password = password;
    }

    /**
     * @return 用戶ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @return 用戶名稱
     */
    public String getName() {
        return name;
    }

    /**
     * @return 用戶帳號
     */
    public String getAccount() {
        return account;
    }

    /**
     * @return 用戶密碼
     */
    public String getPassword() {
        return password;
    }

    /**
     * 重設用戶ID
     *
     * @param user_id 用戶ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * 重設用戶名稱
     *
     * @param name 用戶名稱
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 重設用戶帳號
     *
     * @param account 用戶帳號
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 重設用戶密碼
     *
     * @param password 用戶密碼
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
