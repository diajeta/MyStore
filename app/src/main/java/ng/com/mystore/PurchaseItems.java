package ng.com.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ng.com.shoppinglist.db.AppDatabase;
import ng.com.shoppinglist.db.Category;
import ng.com.shoppinglist.db.Chart;
import ng.com.shoppinglist.db.Items;
import ng.com.shoppinglist.db.Purchase;
import ng.com.shoppinglist.viewModel.PurchaseItemViewModel;

public class PurchaseItems extends AppCompatActivity implements insert_Dialog.Insert_DialogInterface, showCart_Dialog.shoCart_DialogIterface{

    PurchaseItemViewModel purchaseItemViewModel;

    AppDatabase appDatabase;
    Spinner userCat, userItem;
    Button display;
    TextView itemCount;
    ImageView imageCart;

    int categoryId;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_items);
        getSupportActionBar().setTitle("Purchase Items Now!!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        purchaseItemViewModel = new ViewModelProvider(this).get(PurchaseItemViewModel.class);



        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());
        List<Category> categoryListing = appDatabase.shoppingListDAO().getAllCategoriesList();


        userItem = findViewById(R.id.userItem);
        userCat = findViewById(R.id.userCat);
        display = findViewById(R.id.display);
        itemCount = findViewById(R.id.itemCount);
        imageCart = findViewById(R.id.imageCart);

        ArrayList<String> categoryList = new ArrayList<>();
        for(int i = 0; i < categoryListing.size(); i++){
            categoryList.add(categoryListing.get(i).categoryName);
        }



        ArrayAdapter<String> categoryListAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categoryList
        );


        userCat.setAdapter(categoryListAdapter);


        userCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = categoryListing.get(position).uid;
                populateItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_Dialog dialog = new insert_Dialog();
                dialog.show(getSupportFragmentManager(), "Insert Item");
            }
        });
        imageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int numItems = purchaseItemViewModel.shoppingCart.size();
                Toast.makeText(getApplicationContext(), "The number of items are "+numItems, Toast.LENGTH_LONG).show();*/
                showCart_Dialog showCart = new showCart_Dialog();
                try {
                    showCart.show(getSupportFragmentManager(), "Cart Items");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "The error is- "+e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void populateItem() {
        List<Items> itemsListing = appDatabase.shoppingListDAO().getAllItems(categoryId);
        ArrayList<String> itemList = new ArrayList<>();
        for(int i = 0; i < itemsListing.size(); i++){
            itemList.add(itemsListing.get(i).itemName);
        }
        if(itemsListing.size() > 0){
            ArrayAdapter<String> itemListAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    itemList
            );

            userItem.setAdapter(itemListAdapter);

            userItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    itemId = itemsListing.get(position).uid;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            Toast.makeText(this, "No Items under this Category\n Please add Items.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PurchaseItems.this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void applyItems(int itemId, int quantity) {
        purchaseItemViewModel.addToCart(itemId, quantity);
        itemCount.setText(""+purchaseItemViewModel.shoppingCart.size());
    }

    @Override
    public Items selectedItem() {
        return (Items) appDatabase.shoppingListDAO().getAnItems(itemId);
    }

    @Override
    public void save_purchase() {
        Toast.makeText(this, "Items are saved!!", Toast.LENGTH_LONG).show();
        List<Chart> shoppingCart = purchaseItemViewModel.shoppingCart;


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR);
        int minutes = cal.get(Calendar.MINUTE);

        int hourDay = cal.get(Calendar.HOUR_OF_DAY);

        month = month + 1;
        String smonth = ""+month;
        String sday = ""+day;
        String shour = ""+hour;
        String sminutes = ""+minutes;
        String amPm = "";

        if(month < 10){
            smonth = "0"+smonth;
        }if(day < 10){
            sday = "0"+sday;
        }if(hour < 10){
            shour = "0"+shour;
        }if(minutes < 10){
            sminutes = "0"+sminutes;
        }

        if(hourDay >= 12){
            amPm =  "PM";
        }else {
            amPm =  "AM";
        }
        String calDateString = year + "-" + smonth + "-" + sday;
        String calTimeString = shour + ":" + sminutes + " "+ amPm;

        Purchase purchase = new Purchase(shoppingCart, calDateString, calTimeString);
        appDatabase.shoppingListDAO().insertPurchase(purchase);
        clear_purchase();
    }

    @Override
    public String cartCollection() {
        String itemDisplay ="";
        int totalCost = 0;
        List<Chart> shoppingCart = purchaseItemViewModel.shoppingCart;
          /*for(int i = 0; i < shoppingCart.size(); i++){
              itemDisplay += shoppingCart.get(i).itemId;
          }*/
        for(Chart s : shoppingCart){
            int id = s.itemId;
            Items theItem = appDatabase.shoppingListDAO().getAnItems(id);
            itemDisplay += theItem.itemName + ": N"+ s.quantity * theItem.selling +"\n";
            //     shoppingCartDetails.add(theItem);
            totalCost += s.quantity * theItem.selling;
        }
        return itemDisplay+"Total Cost:- N"+totalCost;

    }

    @Override
    public void clear_purchase() {
        purchaseItemViewModel.shoppingCart.clear();
        itemCount.setText("0");
    }
}