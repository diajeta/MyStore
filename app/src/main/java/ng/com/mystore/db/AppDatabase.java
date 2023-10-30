package ng.com.mystore.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ng.com.shoppinglist.MyConverter;
import ng.com.shoppinglist.ShoppingListDAO;




@Database(entities = {Category.class, Items.class, Purchase.class, UnlockModel.class}, version = 2, exportSchema = false)
@TypeConverters(MyConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShoppingListDAO shoppingListDAO();
    public static AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
