package ng.com.mystore.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Category implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "categoryName")
    public String categoryName;
}
