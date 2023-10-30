package ng.com.mystore;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ng.com.mystore.db.AppDatabase;
import ng.com.mystore.db.OneCollection;
import ng.com.mystore.db.Purchase;
import ng.com.mystore.db.PurchaseCollection;

public class PurchaseHistory extends AppCompatActivity implements PurchaseHisAdapter.DeleteShoppingList {

    PurchaseHisAdapter purchaseHisAdapter;

    AppDatabase appDatabase;
    TextView seeDate;
    RecyclerView recyclerView;
    Button select_date;
    List<Purchase> allPurchase;
    String dateRange;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        getSupportActionBar().setTitle("Get History of Purchases");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());

        select_date = findViewById(R.id.select_date);
        seeDate = findViewById(R.id.seeDate);
        recyclerView = findViewById(R.id.recyclerView);

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            PurchaseHistory.this,
                            mDateSetListener,
                            year, month, day);
                    dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String smonth = ""+month;
                String sday = ""+dayOfMonth;
                if(month < 10){
                    smonth = "0"+smonth;
                }if(dayOfMonth < 10){
                    sday = "0"+sday;
                }

                dateRange = year+"-"+smonth+"-"+sday;
                String dateString = "View Date is "+year+"-"+smonth+"-"+sday;
                seeDate.setText(dateString);

                allPurchase = appDatabase.shoppingListDAO().getMonthPurchase(dateRange);

                showRange();
            }
        };

        initRecylerView();

    }

    private void showRange() {
        List<PurchaseCollection> myCollection = new ArrayList<>();
        String showPurchase = "";
        String date2 = "";
        String time2 = "";
        String name = "";
        int quantity = 0;
        int cost = 0;
        int unitCost = 0;
        int itemId = 0;
        for(int i = 0; i < allPurchase.size(); i++){
            date2 = allPurchase.get(i).pdate;
            time2 = allPurchase.get(i).ptime;
            List<OneCollection> oneItem = new ArrayList<>();
            for(int j = 0; j < allPurchase.get(i).shoppingChart.size(); j++){
                quantity = allPurchase.get(i).shoppingChart.get(j).quantity;
                itemId = allPurchase.get(i).shoppingChart.get(j).itemId;
                name = appDatabase.shoppingListDAO().getAnItems(itemId).itemName;
                unitCost = appDatabase.shoppingListDAO().getAnItems(itemId).selling;
                cost = quantity*unitCost;
                oneItem.add(new OneCollection(name, quantity, cost));
            }
            myCollection.add(new PurchaseCollection(date2,time2,oneItem));
        }
        purchaseHisAdapter.setCollectionList(myCollection);
    }

        private void initRecylerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        purchaseHisAdapter = new PurchaseHisAdapter(this, this);
        recyclerView.setAdapter(purchaseHisAdapter);
    }

    @Override
    public void deleteList(String pTime) {
        appDatabase.shoppingListDAO().deletePurchaseList(pTime);
        allPurchase = appDatabase.shoppingListDAO().getMonthPurchase(dateRange);
        showRange();
    }
}