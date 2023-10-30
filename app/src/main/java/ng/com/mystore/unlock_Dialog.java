package ng.com.mystore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class unlock_Dialog extends AppCompatDialogFragment {
    private EditText code;
    private UnlockListener listener;
    private String expireDate;

    public unlock_Dialog(String expireDate) {
        this.expireDate = expireDate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_unluck, null);

        builder.setView(view)
                .setTitle("Unlock Code")
                .setMessage(expireDate)
                .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Unlock", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String unLockVale = code.getText().toString();
                        listener.getUnlock(unLockVale);
                    }
                });
        code = view.findViewById(R.id.code);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UnlockListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "Must implement UnlockListener");
        }
    }

    public interface UnlockListener{
        void getUnlock(String unLockVale);
    }
}
