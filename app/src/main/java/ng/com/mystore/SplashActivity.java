package ng.com.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Animation topAnimation, middleAnimation, bottomAnimation;
    TextView intro, authorName, site;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        /*SplashLauncher splashLauncher = new SplashLauncher();
        splashLauncher.start();*/

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        intro = findViewById(R.id.intro);
        authorName = findViewById(R.id.authorName);
        site = findViewById(R.id.site);

        intro.setAnimation(topAnimation);
        authorName.setAnimation(middleAnimation);
        site.setAnimation(bottomAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, Home.class));
                finish();
            }
        }, 3000);
    }
   /* private class SplashLauncher extends Thread{
        public void run(){
            try{
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(SplashActivity.this, Home.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }*/
}