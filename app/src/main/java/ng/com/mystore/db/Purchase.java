package ng.com.mystore.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Purchase {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "shoppingChart")
    public List<Chart> shoppingChart;
    @ColumnInfo(name = "pdate")
    public String pdate;
    @ColumnInfo(name = "ptime")
    public String ptime;

    public Purchase(List<Chart> shoppingCart, String dateInt, String timeInt) {
        this.shoppingChart = shoppingCart;
        this.pdate = dateInt;
        this.ptime = timeInt;
    }

    public Purchase(int uid, List<Chart> shoppingChart, String pdate) {
        this.uid = uid;
        this.shoppingChart = shoppingChart;
        this.pdate = pdate;
    }
}
