package ng.com.mystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ng.com.mystore.db.Category;

public class AddEditCategory extends AppCompatActivity {

    EditText catName;
    Button submitCat;
    Category catList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);
        catName = findViewById(R.id.catName);
        submitCat = findViewById(R.id.submitCat);
        catList = new Category();
        try {
            catList = (Category) getIntent().getSerializableExtra("catEdit");
            catName.setText(catList.categoryName);
            submitCat.setText("SAVE CHANGES");
        } catch (Exception e) {
            e.printStackTrace();
        }

        submitCat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String categoryName = catName.getText().toString();
                Toast.makeText(AddEditCategory.this, "Category name is "+categoryName, Toast.LENGTH_SHORT).show();
                if(categoryName.isEmpty()){
                    Toast.makeText(AddEditCategory.this, "Enter a Category", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(catList == null){
                        Intent intent = new Intent();
                        intent.putExtra("catName", categoryName);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }else{
                        catList.categoryName = categoryName;
                        Intent intent = new Intent();
                        intent.putExtra("catList", catList);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }

                }
            }
        });
    }
}