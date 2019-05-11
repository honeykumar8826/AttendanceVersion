package com.attendnce.cloudanalogy.attendancev1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class MyteamSectionPagerAdapter extends FragmentPagerAdapter {


    public MyteamSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragementTeamAttendance fragementTeamAttendance = new FragementTeamAttendance();
                return fragementTeamAttendance;

            case 1:
                FragementLeaveForApproval fragementLeaveForApproval = new FragementLeaveForApproval();
                return fragementLeaveForApproval;


            case 2:
                FragementApprovedLeaves fragementApprovedLeaves = new FragementApprovedLeaves();
                return fragementApprovedLeaves;

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
                return "Team Attendance";
            case 1:
                return "Leave for Approval";
            case 2:
                return "Approved leaves";
            default:
                return null;


        }



    }

}
