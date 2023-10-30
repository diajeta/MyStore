package ng.com.mystore;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

import ng.com.shoppinglist.db.AppDatabase;
import ng.com.shoppinglist.db.Purchase;

public class ProfitHistory extends AppCompatActivity {

    AppDatabase appDatabase;
    TextView seeDate;
    TextView seeCost;
    TextView seeSales;
    TextView seeProfit;
    Button select_date, end_date, show_select;
    List<Purchase> allPurchase;
    String dateRange, dateRange2;
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_history);
        getSupportActionBar().setTitle("Get History of Profit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());

        select_date = findViewById(R.id.select_date);
        end_date = findViewById((R.id.end_date));
        show_select = findViewById(R.id.show_select);
        seeDate = findViewById(R.id.seeDate);
        seeCost = findViewById(R.id.seeCost);
        seeSales = findViewById(R.id.seeSales);
        seeProfit = findViewById(R.id.seeProfit);


        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProfitHistory.this,
                        mDateSetListener,
                        year, month, day);
                dialog.show();

            }
        });
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProfitHistory.this,
                        mDateSetListener2,
                        year, month, day);
                dialog.show();

            }
        });
        show_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStart();

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

                //allPurchase = appDatabase.shoppingListDAO().getMonthPurchase(dateRange);
            }
        };

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
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

                dateRange2 = year+"-"+smonth+"-"+sday;
                //String dateString = "View Date is "+year+"-"+smonth+"-"+sday;
                //seeDate.setText(dateString);

                //allPurchase = appDatabase.shoppingListDAO().getMonthPurchase(dateRange);

               // allPurchase = appDatabase.shoppingListDAO().getAllPurchase(dateRangeBack, dateRange);

                //showRange();
            }
        };

    }
    private void setStart(){
        allPurchase = appDatabase.shoppingListDAO().getAllPurchase(dateRange, dateRange2);
        String dateString = "View Date is "+dateRange+" to "+dateRange2;
        seeDate.setText(dateString);
        showRange();
    }

    private void showRange() {

        int quantity = 0;
        int sales = 0;
        int amountSoldPerUnit = 0;
        int totalSales = 0;
        int costOfItem = 0;
        int costPerUnit = 0;
        int totalCost = 0;
        int itemId = 0;
        for(int i = 0; i < allPurchase.size(); i++){

            for(int j = 0; j < allPurchase.get(i).shoppingChart.size(); j++){
                quantity = allPurchase.get(i).shoppingChart.get(j).quantity;
                itemId = allPurchase.get(i).shoppingChart.get(j).itemId;

                amountSoldPerUnit = appDatabase.shoppingListDAO().getAnItems(itemId).selling;
                sales = quantity*amountSoldPerUnit;
                totalSales += sales;
                costPerUnit = appDatabase.shoppingListDAO().getAnItems(itemId).cost;
                costOfItem = quantity*costPerUnit;
                totalCost += costOfItem;

            }

        }
        int profit = totalSales - totalCost;
        String output1 = "Total cost of Items is N"+ totalCost;
        seeCost.setText(output1);

        String output2 = "Total sales of Items is N"+ totalSales;
        seeSales.setText(output2);

        String output3 = "The profit made is N"+ profit;
        seeProfit.setText(output3);

    }
}