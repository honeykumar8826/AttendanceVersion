package com.attendnce.cloudanalogy.attendancev1;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyTeam extends AppCompatActivity {
    private ViewPager mvViewPager;
    private TabLayout mTabLayout;
    private  MyteamSectionPagerAdapter myteamSectionPagerAdapter;
    ImageView backbutton;
    RelativeLayout backbutton_myteam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        mTabLayout=findViewById(R.id.main_tabs_myteam);
        //
        float myTabLayoutSize = 360;
        DisplayMetrics metrics = new DisplayMetrics();
        MyTeam.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int deviceWidth = metrics.widthPixels;
        if (deviceWidth>= myTabLayoutSize ){
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        //
        backbutton_myteam=findViewById(R.id.backButtonmy_team);
        backbutton_myteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyTeam.this,UserActivity.class);
                startActivity(intent);
            }
        });
        mvViewPager=findViewById(R.id.tab_page_myteam);
        myteamSectionPagerAdapter =new MyteamSectionPagerAdapter(getSupportFragmentManager());
        mvViewPager.setAdapter(myteamSectionPagerAdapter);

        mTabLayout.setupWithViewPager(mvViewPager);
    }
}

