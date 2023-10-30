package ng.com.mystore;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ng.com.shoppinglist.db.AppDatabase;
import ng.com.shoppinglist.db.UnlockModel;

public class Home extends AppCompatActivity implements View.OnClickListener, unlock_Dialog.UnlockListener {
    AppDatabase appDatabase;
    List<UnlockModel> myUnlock = new ArrayList<>();
    UnlockModel unlockModel;
    RelativeLayout add, purchase, purchaseHis, profitHis;
    String userCode;
    String access = "granted";
    String messageToDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());
        unlockModel = new UnlockModel();

        add = findViewById(R.id.add);
        purchase = findViewById(R.id.purchase);
        purchaseHis = findViewById(R.id.purchaseHis);
        profitHis = findViewById(R.id.profitHis);

        add.setOnClickListener(this);
        purchase.setOnClickListener(this);
        purchaseHis.setOnClickListener(this);
        profitHis.setOnClickListener(this);

        codex();
    }

    private void codex() {
        int direction;
        try {
            myUnlock = appDatabase.shoppingListDAO().unLockData();
            unlockModel = myUnlock.get(0);
            direction = 1;
        } catch (Exception e) {
            direction = 0;
        }
        LocalDate today = null;
        LocalDate endTime = null;
        String seed;
        String end;
        if (direction == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                today = LocalDate.now();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                endTime = today.plusDays(14);
            }
            end = endTime.toString();
            seed = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
            try {
                unlockModel.expire = end;
                unlockModel.seed = seed;
                unlockModel.resolve = "notResolved";
                appDatabase.shoppingListDAO().insertUnlock(unlockModel);
                access = "granted";
                messageToDialog = "This is a trail version. It would end " + end + "\n" +
                        "To get the full version: \n" +
                        "1. copy this code -> " + seed + "\n" +
                        "2. Visit www.ahante.com.ng/MyStoreApp, click on PayNow and paste the code in the space provided after payment \n" +
                        "3. An unlocking code would be generated for you \n" +
                        "4. Paste the generated code in the space below and click the unlock button \n" +
                        "5. If you still want to continue with the trail version just click continue";

                unlock_Dialog unlock_dialog = new unlock_Dialog(messageToDialog);
                unlock_dialog.show(getSupportFragmentManager(), "Unlock Code");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Toast.makeText(this, "Hello there!! ", Toast.LENGTH_LONG).show();
            seed = unlockModel.seed;
            end = unlockModel.expire;
            LocalDate endDate = null;
            LocalDate presentDate = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                endDate = LocalDate.parse(end);

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                presentDate = LocalDate.now();

            }
            String isResolved = unlockModel.resolve;
            if(isResolved.equals("notResolved")){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(presentDate.isAfter(endDate)){
                        access = "notGranted";
                        messageToDialog = "Your trail period has expired\n" +
                                "To get the full version: \n" +
                                "1. copy this code -> " + seed + "\n" +
                                "2. Visit www.ahante.com.ng/MyStoreApp, click on PayNow and paste the code in the space provided after payment  \n" +
                                "3. An unlocking code would be generated for you \n" +
                                "4. Paste the generated code in the space below and click the unlock button";
                        unlock_Dialog unlock_dialog = new unlock_Dialog(messageToDialog);
                        unlock_dialog.show(getSupportFragmentManager(), "Unlock Code");
                    }else{
                        access = "granted";
                        messageToDialog = "This is a trail version. It would end " + end + "\n" +
                                "To get the full version: \n" +
                                "1. copy this code -> " + seed + "\n" +
                                "2. Visit www.ahante.com.ng/MyStoreApp, click on PayNow and paste the code in the space provided after payment  \n" +
                                "3. An unlocking code would be generated for you \n" +
                                "4. Paste the generated code in the space below and click the unlock button \n" +
                                "5. If you still want to continue with the trail version just click continue";
                        unlock_Dialog unlock_dialog = new unlock_Dialog(messageToDialog);
                        unlock_dialog.show(getSupportFragmentManager(), "Unlock Code");
                    }
                }
            }else {
                access = "granted";
            }
        }
    }


    @Override
    public void onClick(View v) {
        if(access.equals("granted")){
            if(v.equals(add)){
                startActivity(new Intent(Home.this, MainActivity.class));
            }else if(v.equals(purchase)){
                startActivity(new Intent(Home.this, PurchaseItems.class));
            }else if(v.equals(purchaseHis)){
                startActivity(new Intent(Home.this, PurchaseHistory.class));
            }else if(v.equals(profitHis)){
                startActivity(new Intent(Home.this, ProfitHistory.class));
            }
        }else{
            codex();
        }
    }

    @Override
    public void getUnlock(String unLockVale) {
        userCode = unLockVale;
        int sumSeed = 0;
        String[] alphabet = { "A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J"};
        String seed = unlockModel.seed;
        String [] strSeed = seed.split("");
        int size = strSeed.length;
        int [] intSeed = new int [size];

        for(int i=0; i<size; i++) {
            intSeed[i] = Integer.parseInt(strSeed[i]);
            sumSeed = sumSeed + Integer.parseInt(strSeed[i]);
        }
        String first = alphabet[intSeed[0]];
        String second = alphabet[intSeed[1]];
        int sumTimes = sumSeed*253;
        String matchCode = first+second+sumTimes;

        if(matchCode.equals(userCode)){

                myUnlock = appDatabase.shoppingListDAO().unLockData();
                unlockModel = myUnlock.get(0);
                unlockModel.resolve = "resolved";
                appDatabase.shoppingListDAO().upDateUnlock(unlockModel);

            Toast.makeText(this, "Unlocked Successfully, Enjoy the Product ", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "The Code you supplied is wrong ", Toast.LENGTH_LONG).show();
        }
    }
}