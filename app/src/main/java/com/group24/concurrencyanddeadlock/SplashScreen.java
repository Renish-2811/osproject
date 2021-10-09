package com.group24.concurrencyanddeadlock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    TextView AppName,team;
    ImageView logo;
    Animation top_animation,bottom_animation;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        top_animation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.splash_top_image);
        bottom_animation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.splash_bottom_text);

        logo =findViewById(R.id.logo);
        AppName =findViewById(R.id.Appname);
        AppName.startAnimation(bottom_animation);
        logo.startAnimation(top_animation);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.SplashStatusBar));
        Thread thread = new Thread(){

            public void run(){

                try{
                    sleep(2000);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    {
                        Intent intent= new Intent(SplashScreen.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            }
        };thread.start();

    }
}