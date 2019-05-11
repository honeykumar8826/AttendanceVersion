package com.attendnce.cloudanalogy.attendancev1;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

public class LeaveTab extends AppCompatActivity {
    private ViewPager mvViewPager;
    private TabLayout mTabLayout;
    private  SectionPagerAdapter mSectionPagerAdapter;
    ImageView backbutton;
    RelativeLayout backbutton_leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_tab);
      /*  getSupportFragmentManager().addOnBackStackChangedListener(getListener());*/
   /*     float myTabLayoutSize = 360;
        if (DeviceInfo.getWidthDP(this) >= myTabLayoutSize ){
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }*/
        backbutton_leave=findViewById(R.id.backButtonleave);


       // backbutton=findViewById(R.id.back_button);

        backbutton_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LeaveTab.this,UserActivity.class);
                startActivity(intent);
            }
        });
        mvViewPager=findViewById(R.id.tab_page);
        mSectionPagerAdapter =new SectionPagerAdapter(getSupportFragmentManager());
        mvViewPager.setAdapter(mSectionPagerAdapter);
        mTabLayout=findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mvViewPager);
        //for folidn cell code is here

        //
    }

/*
    public void refreshMyData() {

        Toast.makeText(this, "ssssunnnn", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(LeaveTab.this,LeaveTab.class);
        startActivity(intent);
        finish();
    }
*/





    /*
    private FragmentManager.OnBackStackChangedListener getListener()
    {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
        {
            public void onBackStackChanged()
            {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null)
                {
                    MyFragment currFrag = (MyFragment) manager.findFragmentById(R.id.fragmentItem);

                    currFrag.onFragmentResume();
                }
            }
        };

        return result;
    }*/
}
