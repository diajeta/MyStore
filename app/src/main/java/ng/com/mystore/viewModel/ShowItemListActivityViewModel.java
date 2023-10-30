package ng.com.mystore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ng.com.shoppinglist.db.AppDatabase;
import ng.com.shoppinglist.db.Items;

public class ShowItemListActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Items>> listOfItems;
    private AppDatabase appDatabase;
    public ShowItemListActivityViewModel(@NonNull Application application) {
        super(application);
        listOfItems = new MutableLiveData<>();

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getItemsListObserver(){
        return listOfItems;
    }

    public void getAllItemsList(int categoryId){
        List<Items> itemsList = appDatabase.shoppingListDAO().getAllItems(categoryId);
        if(itemsList.size()>0){
            listOfItems.postValue(itemsList);
        }else{
            listOfItems.postValue(null);
        }
    }

    public void insertItems(Items item){

        appDatabase.shoppingListDAO().insertItems(item);
        getAllItemsList(item.categoryId);
    }

    public void updateItems(Items item){
        appDatabase.shoppingListDAO().updateItems(item);
        getAllItemsList(item.categoryId);
    }

    public void deleteItems(Items item){
        appDatabase.shoppingListDAO().deleteItems(item);
        getAllItemsList(item.categoryId);
    }
}
