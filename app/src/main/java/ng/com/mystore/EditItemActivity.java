package ng.com.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ng.com.shoppinglist.db.AppDatabase;
import ng.com.shoppinglist.db.Items;

public class EditItemActivity extends AppCompatActivity {

    AppDatabase appDatabase;
    Items items;
    EditText addNewItemInput, addNewItemQua,addNewItemCost, addNewItemSell;
    Button submitItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());

        addNewItemInput = findViewById(R.id.addNewItemInput2);
        addNewItemQua = findViewById(R.id.addNewItemQua2);
        addNewItemCost = findViewById(R.id.addNewItemCost2);
        addNewItemSell = findViewById(R.id.addNewItemSell2);
        submitItem = findViewById(R.id.submitItem2);

        String itemName = getIntent().getStringExtra("itemEdit");
        items = appDatabase.shoppingListDAO().getAnItemByName(itemName);

        try {
            String name = items.itemName;
            int quantity = items.quantity;
            int cost = items.cost;
            int selling = items.selling;

            addNewItemInput.setText(name);
            addNewItemQua.setText(""+quantity);
            addNewItemCost.setText(""+cost);
            addNewItemSell.setText(""+selling);
        } catch (Exception e) {
            Toast.makeText(EditItemActivity.this, "The error is "+e, Toast.LENGTH_SHORT).show();
        }

        submitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.itemName = addNewItemInput.getText().toString();
                items.quantity = Integer.parseInt(addNewItemQua.getText().toString());
                items.cost = Integer.parseInt(addNewItemCost.getText().toString());
                items.selling = Integer.parseInt(addNewItemSell.getText().toString());
                appDatabase.shoppingListDAO().updateItems(items);
                Intent intent = new Intent(EditItemActivity.this, Home.class);
                startActivity(intent);
            }
        });
    }
}