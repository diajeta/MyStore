package ng.com.mystore;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import ng.com.shoppinglist.db.Chart;

public final class MyConverter {
    @TypeConverter
    public final String listToJson(@NotNull List value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return (new Gson()).toJson(value);
    }

    @TypeConverter
    @NotNull
    public final List jsonToList(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Object var10000 = (new Gson()).fromJson(value, Chart[].class);
        Intrinsics.checkNotNullExpressionValue(var10000, "Gson().fromJson(value, Array<Chart>::class.java)");
        return ArraysKt.toList((Object[])var10000);
    }

}
