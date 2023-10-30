package ng.com.mystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ng.com.shoppinglist.db.Items;
import ng.com.shoppinglist.viewModel.ShowItemListActivityViewModel;

public class ShowItemsListActivity extends AppCompatActivity implements ItemListAdapter.HandleItemsClick {

    private int category_id;
    private ItemListAdapter itemListAdapter;
    private ShowItemListActivityViewModel viewModel;
    private TextView noResult;
    private RecyclerView recyclerView;
    private Items itemToUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_list);

        category_id = getIntent().getIntExtra("category_id", 0);

        String categoryName = getIntent().getStringExtra("category_name");
        noResult = findViewById(R.id.noResult);

        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAddItemDialog();

            }
        });
        initRecylerView();
        initViewModel();
        viewModel.getAllItemsList(category_id);
        Toast.makeText(ShowItemsListActivity.this, "Tap on the red Bin icon to delete an Item\n and on the Pencil icon to edit.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(ShowItemListActivityViewModel.class);
        viewModel.getItemsListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if(items == null){
                    recyclerView.setVisibility(View.GONE);
                    noResult.setVisibility(View.VISIBLE);
                }else{
                    itemListAdapter.setItemList(items);
                    noResult.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void initRecylerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListAdapter = new ItemListAdapter(this, this);
        recyclerView.setAdapter(itemListAdapter);
    }
    private void showAddItemDialog(){
        if(itemToUpdate == null) {
            Intent intent = new Intent(ShowItemsListActivity.this, AddEditItem.class);
            startActivityForResult(intent, 111);
        }else{
            try {
                Intent intent = new Intent(ShowItemsListActivity.this, EditItemActivity.class);
                intent.putExtra("itemEdit", itemToUpdate.itemName);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(ShowItemsListActivity.this, "The error is "+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111 && resultCode == Activity.RESULT_OK){
           // Toast.makeText(ShowItemsListActivity.this, "Ready to enter new items?", Toast.LENGTH_SHORT).show();
            Items item = new Items();
            item.itemName = (String) data.getSerializableExtra("itemName");
            item.categoryId = category_id;
            item.quantity = Integer.parseInt((String) data.getSerializableExtra("quantity"));
            item.cost = Integer.parseInt((String) data.getSerializableExtra("cost"));
            item.selling = Integer.parseInt((String) data.getSerializableExtra("selling"));

            viewModel.insertItems(item);
            itemListAdapter.notifyDataSetChanged();
        }
        else if(requestCode == 112 && resultCode == Activity.RESULT_OK){
            Items items = (Items) data.getSerializableExtra("itemlist");
            viewModel.updateItems(items);
            itemToUpdate = null;
            itemListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void itemClick(Items item) {
        if(item.completed){
            item.completed = false;
        }else{
            item.completed= true;
        }
        viewModel.updateItems(item);
    }

    @Override
    public void editItem(Items item) {
        this.itemToUpdate = item;
        showAddItemDialog();
    }

    @Override
    public void removeItem(Items item) {
        viewModel.deleteItems(item);
    }
}