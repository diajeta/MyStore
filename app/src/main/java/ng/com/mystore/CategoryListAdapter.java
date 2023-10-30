package ng.com.mystore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ng.com.mystore.db.Category;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewolder> {

    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick clickListener;

    public CategoryListAdapter(Context context, HandleCategoryClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }
    public void setCategoryList(List<Category> categoryList){
        this.categoryList = categoryList;
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
        holder.tvCategoryName.setText(categoryList.get(position).categoryName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(categoryList.get(position));
            }
        });
        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItem(categoryList.get(position));
            }
        });
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItem(categoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(categoryList == null || categoryList.size() == 0){
            return 0;
        }else{
            return categoryList.size();
        }

    }

    public class MyViewolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView removeCategory, editCategory;
        public MyViewolder(@NonNull View itemView) {

            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            removeCategory = itemView.findViewById(R.id.removeCategory);
            editCategory = itemView.findViewById(R.id.editCategory);
        }
    }

    public  interface HandleCategoryClick{
        void itemClick(Category category);
        void editItem(Category category);
        void removeItem(Category category);
    }
}
