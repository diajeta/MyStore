package ng.com.mystore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ng.com.mystore.db.AppDatabase;
import ng.com.mystore.db.Purchase;

public class PurchaseHistoryViewModel extends AndroidViewModel {
    private MutableLiveData<List<Purchase>> listOfPurchase;
    private AppDatabase appDatabase;
    public PurchaseHistoryViewModel(@NonNull Application application) {
        super(application);
        listOfPurchase = new MutableLiveData<>();
        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());
    }
    public MutableLiveData<List<Purchase>> getListOfPurchaseObserver(){
        return listOfPurchase;
    }
    public void getPurchaseRange(String date1){
        List<Purchase> purchaseList = appDatabase.shoppingListDAO().getMonthPurchase(date1);
        if(purchaseList.size()>0){
            listOfPurchase.postValue(purchaseList);
        }else {
            listOfPurchase.postValue(null);
        }
    }
    public void deletePurchase(Purchase purchase){
        appDatabase.shoppingListDAO().deletePurchase(purchase);
        String date1 = purchase.pdate;
        getPurchaseRange(date1);
    }
}
