package ng.com.mystore.viewModel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ng.com.mystore.db.Chart;

public class PurchaseItemViewModel extends ViewModel {
    public List<Chart> shoppingCart = new ArrayList<>();
    public void addToCart(int itemId, int quantity){
        shoppingCart.add(new Chart(itemId, quantity));
    }
}
