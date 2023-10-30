package ng.com.mystore.db;

import java.util.List;

public class PurchaseCollection {
    private String date1;
    private String time1;
    private List<OneCollection> oneCollection;

    public PurchaseCollection(String date1, String time1, List<OneCollection> oneCollection) {
        this.date1 = date1;
        this.time1 = time1;
        this.oneCollection = oneCollection;
    }

    public String getDate1() {
        return date1;
    }

    public String getTime1() {
        return time1;
    }

    public List<OneCollection> getOneCollection() {
        return oneCollection;
    }
}
