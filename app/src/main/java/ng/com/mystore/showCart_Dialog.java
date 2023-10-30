package ng.com.mystore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class showCart_Dialog extends AppCompatDialogFragment {
    shoCart_DialogIterface shoCart_DialogIterface;
    TextView cart_items;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.showcart_dialog, null);

        builder.setView(view)
                .setTitle("Shopping Items in Cart")
                .setNegativeButton("Save Items", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            shoCart_DialogIterface.save_purchase();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "The error is "+e, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setPositiveButton("Cansel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shoCart_DialogIterface.clear_purchase();
                    }
                });
        cart_items = view.findViewById(R.id.cart_items);
        cart_items.setText(shoCart_DialogIterface.cartCollection());

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        shoCart_DialogIterface = (shoCart_DialogIterface) context;
        super.onAttach(context);
    }

    public interface shoCart_DialogIterface{
        public void save_purchase();
        public String cartCollection();
        public void clear_purchase();
    }
}
