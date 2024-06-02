package main;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.Cart;
import ui.GlobalUIObject;
import ui.Index;
import ui.Login;
import ui.ProductManage;
import ui.object.IndexGoodBlock;

/**
 * <h2><b>執行程式</b></h2><br>
 * run(stage);
 */
public class Run {

    /**
     * 執行
     *
     * @param stage
     */
    public static void run(Stage stage) {
        // setGlobalVariables();
        setGlobalUIObject();
        runScene(stage);
    }
    
    /**
     * 執行UI介面
     *
     * @param stage
     */
    public static void runScene(Stage stage) {
//        stage.setScene(GlobalUIObject.IndexScene);
//        stage.setScene(GlobalUIObject.LoginScene);
        stage.setScene(GlobalUIObject.ProductManageScene);
        stage.setTitle("書籍訂購系統");
        stage.show();
    }

    /**
     * 設定全域變數(GlobalVariables)
     */
    /*
    public static void setGlobalVariables() {
        setUserList();
        setCategory();
        setGoodList();
    }
    */

    /**
     * 設定全域變數(GlobalVariables) 用戶清單及目前操作用戶
     */
    /*
    public static void setUserList() {
        GlobalVariables.user_list.put("u001", new User("u001"));
        GlobalVariables.user_list.put("u002", new User("u002"));
        GlobalVariables.user_list.put("u003", new User("u003"));
        GlobalVariables.now_user = "u001";
    }
    */

    /**
     * 設定全域變數(GlobalVariables) 二階分類
     */
    /*
    public static void setCategory() {
        Category novel_category = new Category();
        novel_category.setNewCategory("chinese", "華文作品");
        novel_category.setNewCategory("translate", "翻譯作品");
        Category comics_category = new Category();
        comics_category.setNewCategory("ghost", "靈異/神怪");
        comics_category.setNewCategory("suspense", "懸疑推理");
        comics_category.setNewCategory("love", "戀愛");

        GlobalVariables.category.setNewCategory("novel", novel_category);
        GlobalVariables.category.setNewCategory("comics", comics_category);

        GlobalVariables.category.setNewCategory2L_name("novel", "輕小說");
        GlobalVariables.category.setNewCategory2L_name("comics", "漫畫");
    }
    */

    /**
     * 設定全域變數 書籍(商品)清單
     */
    /*
    public static void setGoodList() {
        GlobalVariables.good_list.put("nc001", new Book("nc001", "nc001.jpg", "幽靈少女想要重拾心跳(01)", "我遇見了一名死透的少女。\n沒錯，她已經死了，毫無疑問是名幽靈。\n觸摸不到的手腕，突然消失的氣息，難不成我真的撞鬼了？\n先不管這個，為什麼妳要用那種眼神盯著我的青梅竹馬？\n\n「欸，我想和柳夏萱交往，你來幫我吧。」\n「……妳喜歡的是女生？」\n\n含笑的琉璃色雙眸，似乎藏有什麼隱情。\n突如其來的一場邂逅，無法預料的一見鍾情，無可迴避的神鬼契約。\n我，新崇高中二年級生，左離鳴，接下來的任務是──\n幫助素未謀面的幽靈美少女，與我的青梅竹馬成功交往！？", 280, "chinese"));
        GlobalVariables.good_list.put("nc002", new Book("nc002", "nc002.jpg", "幽靈少女想要重拾心跳(02)", "「——這一次，可以為了我而畫嗎？」\n\n前陣子我遇見了一名幽靈少女——紫泱。\n她委託了一連串看似荒唐的事情，讓我的生活頓時熱鬧起來。\n緊接著，我在校慶被柳夏萱告白後，聖誕節悄悄來臨。\n短暫的同居生活結束，我與紫泱恢復為一般的同學關係。\n日常回歸平穩，接下來只要好好實現那份諾言就行了。\n然而筆電裡不知為誰而寫的小說，隱約不清的夢境，\n意外到訪的三叉路口，全部都讓這個季節再度躁動。\n\n十七歲的冬天，八年前的夏日……\n我們重拾被遺忘的記憶，令停滯的時光重新開始轉動——", 280, "chinese"));
        GlobalVariables.good_list.put("nc003", new Book("nc003", "nc003.jpg", "推理什麼的不重要啦你要吃章魚燒嗎", "馬歌真同學非常可愛——這是不容置疑的事實！名偵探世家的後人杜振邦如此認定。不過，難道可愛就是正義？校園接連出現謎團，準偵探專業認真的推理，居然每次都被歌真這個大外行的胡說八道狠狠打臉！這個世界到底怎麼了？現在連名偵探親戚們都想要調查歌真，懷疑她的祖先在百年前的超自然案件中，帶走了神秘的證物和真相。煩惱該調查還是討好心上人的準偵探，圍繞章魚燒展開的不重要推理事件，真相關鍵就在少女的可愛笑容中！", 225, "chinese"));
        GlobalVariables.good_list.put("nt001", new Book("nt001", "nt001.jpg", "真可憐（笑）", "關於橫次鈴親身體驗的故事，\n你現在閱讀的是，梨從相關人員那裡蒐集資訊寫成的書。\n\n大約在2000年左右，一張詭異的照片開始被瘋狂轉發。在昏暗的畫面中，一名穿制服的女子只露出脖子以下的部分，但圖檔畫質極差，感覺像是用舊式手機拍攝的。\n\n說起來這張照片也不是什麼奇怪的東西，但每當它一出現，就會有人回應。我漫不經心地瀏覽各大留言板，赫然發現有不少人在調查這張照片的由來，各種解讀資料一一浮上檯面──\n\n#詭異的QR Code\n#這是橫次鈴親身體驗的故事.docx\n#20210908.wav轉文字檔\n#收件匣(15)\n\n有人說這些資料就像一部驚悚小說，還誇張地說是都市傳說，但這真的是事實嗎？我半信半疑。直到聽說這張照片只是部分截圖，原圖似乎連著脖子以上的部位，我這才大吃一驚。\n\n為了看到原始照片，我開始瘋狂地閱讀資料，沒想到讀著讀著，竟感覺字字句句正在侵蝕我的內心，一股恐怖的氛圍慢慢浮現，而照片中的女子似乎變得越來越清晰……", 380, "translate"));
        GlobalVariables.good_list.put("nt002", new Book("nt002", "nt002.jpg", "人渣", "讓你見識一下真正的人渣會有什麼下場──\n令人衝擊的問題作品誕生！\n\n靠著與生俱來的容貌和能力歌頌青春的大學生──霞，\n在跟朋友智也進行無關緊要的對話時聊到高中同班同學──雲母，\n並與她在大學校園中重逢了。\n霞以平常結交炮友的手法去撩雲母，逐漸受到她單方面的愛慕。\n後來他慢慢厭惡起那樣的雲母，開始隨便應付她，\n甚至用殘酷的藉口提出分手，等待著霞的是──\n身為真正人渣的他也沒預料過，雲母那令人震撼的真相。\n還有他壓根不曾想過的某個人物會在暗中活躍。\n所以說，來吧，現在就讓你見識一下真正的「人渣」。", 220, "translate"));
        GlobalVariables.good_list.put("cg001", new Book("cg001", "cg001.jpg", "黑暗集會(01)", "因為自己的靈異體質，\n過去曾害朋友捲入中邪事件的大學生‧螢多朗。\n隨著上了大學，螢多朗決定當家庭教師來打工，\n沒想到竟然被他的第一個學生，天才少女‧夜宵看穿自己擁有靈異體質，甚至還邀請螢多朗一起前往靈異景點…！？\n令人顫慄的捉捕惡靈遊戲開幕！！", 99, "ghost"));
        GlobalVariables.good_list.put("cg002", new Book("cg002", "cg002.jpg", "黑暗集會(02)", "夜宵在得知詠子也受到了和螢多朗相同的詛咒後，\n便向螢多朗提議一起去殺了元凶的惡靈來解除詛咒。\n但接近靈異的行為和他想回歸社會的目標卻是完全相反，\n因此他無法立即給出答覆。\n與此同時，螢多朗竟在與詠子一起選修的\n都市傳說課堂上被惡靈襲擊了‧‧‧！？", 99, "ghost"));
        GlobalVariables.good_list.put("cs001", new Book("cs001", "cs001.jpg", "伊藤潤二愛藏版1 富江 (上)", "迷惑男人，讓他們陷入狂亂愛戀中的女子，她名為富江。不管被殺死幾次，歷經無數次死亡，依舊一再復活的美麗富江。富江啊！整個世界在妳面前都得俯首稱臣吧──", 105, "suspense"));
        GlobalVariables.good_list.put("cs002", new Book("cs002", "cs002.jpg", "伊藤潤二愛藏版2 富江 (下)", "每歷經一次死亡就會增加分身，持續死亡復活輪迴的美麗女子．富江。她煽動、唆使身邊的男人，熱切地希望能「殺死富江」。富江啊！我是如此渴望著妳的親吻──。", 105, "suspense"));
        GlobalVariables.good_list.put("cl001", new Book("cl001", "cl001.jpg", "白聖女與黑牧師 (1)", "長相可愛，卻有很多小毛病的聖女—賽西莉亞，以及外表嚴肅卻十分溫柔，對聖女過度保護、家事萬能的牧師—羅倫斯，兩人一起度過的每一天，無時無刻都在不自覺地放閃（！）有著奇幻世界觀的溫馨愛情喜劇（？）羅倫斯的特殊體質究竟是怎麼回事？趕快翻開此作一見分曉！", 100, "love"));
        GlobalVariables.good_list.put("cl002", new Book("cl002", "cl002.jpg", "白聖女與黑牧師 (2)", "聖女也是女孩子－－！純潔但有些迷糊的聖女賽西莉亞，其實偷偷暗戀著同住在一個屋簷下的牧師‧羅倫斯。然而照顧聖女精明能幹，對於感情卻很遲鈍的羅倫斯，似乎完全沒有察覺……兩人的「毫無自覺戀愛喜劇」第2集歡樂上演中！", 100, "love"));
    }
    */

    /**
     * 設定全域UI物件(GlobalUIObject)
     */
    public static void setGlobalUIObject() {
        IndexGoodBlock.IndexGoodObjectList();
        setIndexScene();
        setCartScene();
        setLoginScene();
        setProductManage();
    }

    /**
     * 設定全域UI物件(GlobalUIObject) 登入
     */
    public static void setLoginScene() {
        GlobalUIObject.LOGIN = new Login();
        VBox loginScene = new VBox();
        loginScene.setSpacing(10);
        loginScene.setPadding(new Insets(10, 10, 10, 10));
        loginScene.getStylesheets().add("/css/bootstrap3.css");
        loginScene.getChildren().addAll(GlobalUIObject.LOGIN.content);
        GlobalUIObject.LoginScene = new Scene(loginScene, 450, 250);
    }
    
    /**
     * 設定全域UI物件(GlobalUIObject) 主頁
     */
    public static void setIndexScene() {
        GlobalUIObject.INDEX = new Index();
        VBox indexScene = new VBox();
        indexScene.setSpacing(10);
        indexScene.setPadding(new Insets(10, 10, 10, 10));
        indexScene.getStylesheets().add("/css/bootstrap3.css");
        indexScene.getChildren().addAll(GlobalUIObject.INDEX.header, GlobalUIObject.INDEX.content);
        GlobalUIObject.IndexScene = new Scene(indexScene, 1000, 700);
    }

    /**
     * 設定全域UI物件(GlobalUIObject) 購物車頁面
     */
    public static void setCartScene() {
        GlobalUIObject.CART = new Cart();
        VBox cartScene = new VBox();
        cartScene.setSpacing(10);
        cartScene.setPadding(new Insets(10, 10, 10, 10));
        cartScene.getStylesheets().add("/css/bootstrap3.css");
        cartScene.getChildren().addAll(GlobalUIObject.CART.header, GlobalUIObject.CART.cartList, GlobalUIObject.CART.cartInfoBlock);
        GlobalUIObject.CartScene = new Scene(cartScene, 1000, 700);
    }
    
    public static void setProductManage() {
        GlobalUIObject.PRODUCT_MANAGE = new ProductManage();
        VBox productManageScene = new VBox();
        productManageScene.setSpacing(10);
        productManageScene.setPadding(new Insets(10, 10, 10, 10));
        productManageScene.getStylesheets().add("/css/bootstrap3.css");
        productManageScene.getChildren().addAll(GlobalUIObject.PRODUCT_MANAGE.content, GlobalUIObject.PRODUCT_MANAGE.console);
        GlobalUIObject.ProductManageScene = new Scene(productManageScene, 1000, 700);
    }
}
