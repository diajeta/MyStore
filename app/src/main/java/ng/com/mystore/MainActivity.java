package ng.com.mystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ng.com.shoppinglist.db.Category;
import ng.com.shoppinglist.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements CategoryListAdapter.HandleCategoryClick {

    private MainActivityViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    CategoryListAdapter categoryListAdapter;
    Category categoryForEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Shopping List");
        noResultTextView = findViewById(R.id.noResult);
        recyclerView = findViewById(R.id.recyclerView);

        FloatingActionButton addNew = findViewById(R.id.addNewCategoryImageView);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog();
            }
        });
        intViewModel();
        initRecyclerView();
        viewModel.getAllCategoryList();
        Toast.makeText(this, "Tap on the button below to create new Categories.", Toast.LENGTH_LONG).show();
    }
    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryListAdapter  = new CategoryListAdapter(this, this);
        recyclerView.setAdapter(categoryListAdapter);
    }
    private void intViewModel(){
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getCategoryListObserver().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if(categories == null){
                    noResultTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    categoryListAdapter.setCategoryList(categories);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResultTextView.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Tap on a Category to create new Items.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void showAddCategoryDialog(){
        if(categoryForEdit == null) {
            Intent intent = new Intent(MainActivity.this, AddEditCategory.class);
            startActivityForResult(intent, 101);
        }else{
            Intent intent = new Intent(MainActivity.this, AddEditCategory.class);
            intent.putExtra("catEdit", categoryForEdit);
            startActivityForResult(intent, 102);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            String name = (String) data.getSerializableExtra("catName");
            viewModel.insertCategory(name);
            categoryListAdapter.notifyDataSetChanged();
        }
        else if(requestCode == 102 && resultCode == Activity.RESULT_OK){
            Category cat = (Category) data.getSerializableExtra("catList");
            viewModel.updateCategory(cat);
            categoryForEdit = null;
            categoryListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void itemClick(Category category) {
        Intent intent = new Intent(MainActivity.this, ShowItemsListActivity.class);
        intent.putExtra("category_id", category.uid);
        intent.putExtra("category_name", category.categoryName);
        startActivity(intent);
    }

    @Override
    public void editItem(Category category) {
        this.categoryForEdit = category;
        showAddCategoryDialog();
    }

    @Override
    public void removeItem(Category category) {
        viewModel.deleteCategory(category);
    }
}