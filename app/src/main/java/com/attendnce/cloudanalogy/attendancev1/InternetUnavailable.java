package com.attendnce.cloudanalogy.attendancev1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InternetUnavailable extends AppCompatActivity {

    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_unavailable);
        retry=findViewById(R.id.bt_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callSplash=new Intent(InternetUnavailable.this,SplashActivity.class);
                startActivity(callSplash);
                finish();
            }
        });
    }
}
