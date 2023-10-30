package ng.com.mystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ng.com.mystore.db.Items;

public class AddEditItem extends AppCompatActivity {

    EditText addNewItemInput;
    EditText addNewItemQua;
    EditText addNewItemCost;
    EditText addNewItemSell;
    Button submitItem;
    Items itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

         addNewItemInput = findViewById(R.id.addNewItemInput);
         addNewItemQua = findViewById(R.id.addNewItemQua);
         addNewItemCost = findViewById(R.id.addNewItemCost);
         addNewItemSell = findViewById(R.id.addNewItemSell);

         submitItem = findViewById(R.id.submitItem);
         itemlist = new Items();
        try {
            itemlist = (Items) getIntent().getSerializableExtra("itemEdit");
            addNewItemInput.setText(itemlist.itemName);
            addNewItemQua.setText(itemlist.quantity);
            addNewItemCost.setText(itemlist.cost);
            addNewItemSell.setText(itemlist.selling);
            submitItem.setText("SAVE CHANGES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        submitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = addNewItemInput.getText().toString();
                String quantity = addNewItemQua.getText().toString();
                String cost = addNewItemCost.getText().toString();
                String selling = addNewItemSell.getText().toString();
                if(TextUtils.isEmpty(itemName) || TextUtils.isEmpty(quantity) || TextUtils.isEmpty(cost) || TextUtils.isEmpty(selling)){
                    Toast.makeText(AddEditItem.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(itemlist == null){
                        Intent intent = new Intent();
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("quantity", quantity);
                        intent.putExtra("cost", cost);
                        intent.putExtra("selling", selling);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }else{
                        itemlist.itemName = itemName;
                        itemlist.quantity = Integer.parseInt(quantity);
                        itemlist.cost = Integer.parseInt(cost);
                        itemlist.selling = Integer.parseInt(selling);
                        Intent intent = new Intent();
                        intent.putExtra("itemlist", (CharSequence) itemlist);
                        setResult(Activity.RESULT_OK, intent);
                        finish();

                    }
                }
            }
        });
    }
}