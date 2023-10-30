package ng.com.mystore.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UnlockModel {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "expire")
    public String expire;
    @ColumnInfo(name = "seed")
    public String seed;
    @ColumnInfo(name = "resolve")
    public String resolve;
}
