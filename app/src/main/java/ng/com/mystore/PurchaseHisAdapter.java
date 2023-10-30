package ng.com.mystore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ng.com.mystore.db.PurchaseCollection;

public class PurchaseHisAdapter extends RecyclerView.Adapter<PurchaseHisAdapter.MyViewHolder>{
    private Context context;
    List<PurchaseCollection> purchaseCollections;
    DeleteShoppingList deleteListener;

    public PurchaseHisAdapter(Context context, DeleteShoppingList deleteListener) {
        this.context = context;
        this.deleteListener = deleteListener;
    }

    public void setCollectionList(List<PurchaseCollection> purchaseCollections){
        this.purchaseCollections = purchaseCollections;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.historyrecycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.timefield.setText(this.purchaseCollections.get(position).getTime1());
        String contentView = "";
        for(int i = 0; i < this.purchaseCollections.get(position).getOneCollection().size(); i++){
            contentView += purchaseCollections.get(position).getOneCollection().get(i).getItemName()+" "+
                    purchaseCollections.get(position).getOneCollection().get(i).getQuantity()+" N"+
                    purchaseCollections.get(position).getOneCollection().get(i).getCost()+ " \n";
        }
        holder.contentfield.setText(contentView);
        int totalCost = 0;
        for(int i = 0; i < this.purchaseCollections.get(position).getOneCollection().size(); i++){
            totalCost = totalCost + purchaseCollections.get(position).getOneCollection().get(i).getCost();
        }
        holder.totalField.setText("The total is: N"+totalCost);
        holder.deletehis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theTime = purchaseCollections.get(holder.getAdapterPosition()).getTime1();
                deleteListener.deleteList(theTime);
                notifyDataSetChanged();
                Toast.makeText(context, "List deleted...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(purchaseCollections == null || purchaseCollections.size() == 0){
            return 0;
        }
        return purchaseCollections.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView timefield, contentfield, totalField;
        ImageView deletehis;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timefield = itemView.findViewById(R.id.timefield);
            contentfield = itemView.findViewById(R.id.contentfield);
            totalField = itemView.findViewById(R.id.totalField);
            deletehis = itemView.findViewById(R.id.deletehis);
        }
    }
    public interface DeleteShoppingList{
        void deleteList(String pTime);
    }
}
