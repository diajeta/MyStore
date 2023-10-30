package ng.com.mystore.db;

public class OneCollection {
    private String itemName;
    private int quantity;
    private int cost;

    public OneCollection(String itemName, int quantity, int cost) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }
}
