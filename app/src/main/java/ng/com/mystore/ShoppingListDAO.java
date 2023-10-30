package ng.com.mystore;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ng.com.shoppinglist.db.Category;
import ng.com.shoppinglist.db.Items;
import ng.com.shoppinglist.db.Purchase;
import ng.com.shoppinglist.db.UnlockModel;

@Dao
public interface ShoppingListDAO {

    @Query("SELECT * FROM Category")
    List<Category> getAllCategoriesList();

    @Insert
    void insertCategory(Category...categories);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM Items WHERE categoryId = :catId")
    List<Items> getAllItems(int catId);
    @Query("SELECT * FROM Items WHERE uid = :uId")
    Items getAnItems(int uId);
    @Query("SELECT * FROM Items WHERE itemName = :itemName")
    Items getAnItemByName(String itemName);

    @Insert
    void insertItems(Items items);

    @Update
    void updateItems(Items items);

    @Delete
    void deleteItems(Items items);

    @Query("SELECT * FROM Purchase WHERE pdate BETWEEN :pdate1 AND :pdate2 ")
    List<Purchase> getAllPurchase(String pdate1, String pdate2);
   @Query("SELECT * FROM Purchase WHERE pdate = :pdate1")
   List<Purchase> getMonthPurchase(String pdate1);

    @Query("SELECT * FROM Purchase")
    List<Purchase> allPurchase();

    @Insert
    void insertPurchase(Purchase purchase);
    @Delete
    void deletePurchase(Purchase purchase);
    @Query("DELETE FROM Purchase WHERE ptime = :ptime")
    void deletePurchaseList(String ptime);

    @Insert
    void insertUnlock(UnlockModel unlockModel);
    @Query("SELECT * FROM UnlockModel")
    List<UnlockModel> unLockData();

    @Update
    void upDateUnlock(UnlockModel unlockModel);
}
