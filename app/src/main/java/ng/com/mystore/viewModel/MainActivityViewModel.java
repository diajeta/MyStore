package ng.com.mystore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ng.com.mystore.db.AppDatabase;
import ng.com.mystore.db.Category;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Category>> listOfCategories;
    private AppDatabase appDatabase;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        listOfCategories = new MutableLiveData<>();

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Category>> getCategoryListObserver(){
        return listOfCategories;
    }

    public void getAllCategoryList(){
        List<Category> categoryList = appDatabase.shoppingListDAO().getAllCategoriesList();
        if(categoryList.size()>0){
            listOfCategories.postValue(categoryList);
        }else{
            listOfCategories.postValue(null);
        }
    }

    public void insertCategory(String catName){
        Category category = new Category();
        category.categoryName = catName;
        appDatabase.shoppingListDAO().insertCategory(category);
        getAllCategoryList();
    }

    public void updateCategory(Category category){
        appDatabase.shoppingListDAO().updateCategory(category);
        getAllCategoryList();
    }

    public void deleteCategory(Category category){
        appDatabase.shoppingListDAO().deleteCategory(category);
        getAllCategoryList();
    }
}
