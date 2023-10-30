package ng.com.mystore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ng.com.mystore.db.Items;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewolder> {

    private Context context;
    private List<Items> itemList;
    private HandleItemsClick clickListener;

    public ItemListAdapter(Context context, HandleItemsClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }
    public void setItemList(List<Items> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewolder holder, @SuppressLint("RecyclerView") int position) {
        String scrollMessage = "SellingPrice = "+ itemList.get(position).selling + ", CostPrice = "+ itemList.get(position).cost + ", Quantity = "+itemList.get(position).quantity;
        holder.dataset.setText(scrollMessage);
        holder.dataset.setVisibility(View.VISIBLE);
        holder.dataset.setSelected(true);
        holder.tvItemName.setText(itemList.get(position).itemName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(itemList.get(position));
            }
        });
        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItem(itemList.get(position));
            }
        });
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItem(itemList.get(position));
            }
        });
        if(this.itemList.get(position).completed){
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.tvItemName.setPaintFlags(0);
        }
    }

    @Override
    public int getItemCount() {
        if(itemList == null || itemList.size() == 0){
            return 0;
        }else{
            return itemList.size();
        }

    }

    public class MyViewolder extends RecyclerView.ViewHolder {
        TextView tvItemName, dataset;
        ImageView removeCategory, editCategory;
        public MyViewolder(@NonNull View itemView) {

            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvCategoryName);
            dataset = itemView.findViewById(R.id.dataset);
            removeCategory = itemView.findViewById(R.id.removeCategory);
            editCategory = itemView.findViewById(R.id.editCategory);
        }
    }

    public  interface HandleItemsClick{
        void itemClick(Items item);
        void editItem(Items item);
        void removeItem(Items item);
    }
}
