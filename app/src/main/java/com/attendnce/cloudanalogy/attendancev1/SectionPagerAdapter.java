package com.attendnce.cloudanalogy.attendancev1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {


    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LeaveApplyFragement leaveApplyFragement = new LeaveApplyFragement();
                return leaveApplyFragement;

            case 1:
                LeaveStatusFragement leaveStatusFragement = new LeaveStatusFragement();
                return leaveStatusFragement;

            case 2:
                LeaveSummaryFragement leaveSummaryFragement = new LeaveSummaryFragement();
                return leaveSummaryFragement;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Leave Apply";
            case 1:
                return "Leave Status";
            case 2:
                return "Leave Summary";
            default:
                return null;


        }



    }

}
