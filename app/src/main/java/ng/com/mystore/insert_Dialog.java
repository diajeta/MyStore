package ng.com.mystore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import ng.com.shoppinglist.db.Items;

public class insert_Dialog extends AppCompatDialogFragment {
    Insert_DialogInterface insert_dialogInterface;

    TextView costPrice, sellingPrice, quantity;
    EditText purchaseQuantity;

    String title;
    int itemId;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.insert_dialog, null);
        costPrice = view.findViewById(R.id.costPrice);
        sellingPrice = view.findViewById(R.id.sellingPrice);
        quantity = view.findViewById(R.id.quantity);
        purchaseQuantity = view.findViewById(R.id.purchaseQuantity);

        /* Get the values of the cost price selling price and quantity of the Item */
        String theCostPrice = String.valueOf(insert_dialogInterface.selectedItem().cost);
        String theSellingPrice = String.valueOf(insert_dialogInterface.selectedItem().selling);
        String theQuantity = String.valueOf(insert_dialogInterface.selectedItem().quantity);

        title = insert_dialogInterface.selectedItem().itemName;
        itemId = insert_dialogInterface.selectedItem().uid;

        costPrice.setText("Item bought at N"+theCostPrice);
        sellingPrice.setText("Item to be sold at N"+theSellingPrice);
        quantity.setText("Quantity in Store "+theQuantity);
        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("Add To Chart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int quant = Integer.parseInt(purchaseQuantity.getText().toString());
                            insert_dialogInterface.applyItems(itemId, quant);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext().getApplicationContext(),"Please enter the quantity.", Toast.LENGTH_LONG).show();
                        }

                        // Toast.makeText(getContext().getApplicationContext(),"The id is "+itemId+" and the quantity is "+quant, Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        insert_dialogInterface = (Insert_DialogInterface) context;
        super.onAttach(context);
    }

    public interface Insert_DialogInterface{
        public void applyItems(int itemId, int quantity);
        public Items selectedItem();
    }

}
