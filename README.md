BookStore 書籍訂購系統  
  
main  
> Main 主程式  
> Run 執行檔案  
> GlobalVariables 全域變數  
  
element  
> Book 書籍  
> Category 分類  
> CategoryTwoLevel 二階分類  
> GoodOrder 單項商品訂單  
> Order 訂單  
> Cart 個人購物車  
> User 用戶  
  
ui  
> Index 主頁 UI介面  
> Cart 購物車 UI介面  
> GlobalUIObject UI介面全域變數  
> object  
> > IndexGoodBlock 商品UI物件  
> > CartGoodBlock 購物車UI物件  
  
相關的全域變數都由 `main.Run.run()` 建立，同時初始化介面並執行。  
  
main  
> Run 執行程式  
> > static void run(stage)           : 執行  
> > static void runScene(stage)      : 執行UI介面  
> > static void setGlobalVariables() : 設定全域變數(GlobalVariables)  
> > static void setUserList()        : 設定全域變數(GlobalVariables) 用戶清單及目前操作用戶  
> > static void setCategory()        : 設定全域變數(GlobalVariables) 二階分類  
> > static void setGoodList()        : 設定全域變數(GlobalVariables) 書籍(商品)清單  
> > static void setGlobalUIObject()  : 設定全域UI物件(GlobalUIObject)  
> > static void setIndexScene()      : 設定全域UI物件(GlobalUIObject) 主頁  
> > static void setCartScene()       : 設定全域UI物件(GlobalUIObject) 購物車頁面  
  
> GlobalVariables 全域變數  
> > static String                 now_user              : 目前操作用戶  
> > static TreeMap<String, User>  user_list             : 用戶清單  
> > static CategoryTwoLevel       category              : 二階分類  
> > static Category               category2L_name       : 二階分類中文名稱  
> > static TreeMap<String, Book>  good_list             : 書籍(商品)清單  
> > static TreeMap<String, Order> order_list            : 訂單列表  
> > static ArrayList<String>      inCartList_SelectList : 在購物車頁面中 被選擇的項目清單  
  
ui  
> GlobalUIObject UI介面全域變數  
> > static Index                 INDEX                : 主頁物件  
> > static Cart                  CART                 : 購物車頁面物件  
> > static Scene                 IndexScene           : 主頁  
> > static CheckBox              SelectAll_inCartList : 購物車頁面 全選功能  
> > static Scene                 CartScene            : 購物車頁面  
> > static TreeMap<String, HBox> IndexGoodBlock_list  : 書籍(商品) UI物件 清單  
  
