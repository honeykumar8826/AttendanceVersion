package com.attendnce.cloudanalogy.attendancev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView= (ImageView)findViewById(R.id.logo_attendance);
         Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);

        imageView.startAnimation(fadeInAnimation);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                String name = prefs.getString("name", null);
                if (name != null) {

                    Intent userActivity=new Intent(SplashActivity.this,UserActivity.class);
                    startActivity(userActivity);
                    finish();
                }else{

                    Intent login=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(login);
                    finish();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
