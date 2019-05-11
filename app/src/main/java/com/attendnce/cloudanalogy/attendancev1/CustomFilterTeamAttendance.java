package com.attendnce.cloudanalogy.attendancev1;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilterTeamAttendance extends Filter {
    AdapterTeamAttendance adapter;
    ArrayList<AttendanceModelMyTeam> filterList;


    public CustomFilterTeamAttendance(ArrayList<AttendanceModelMyTeam> filterList, AdapterTeamAttendance adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }



    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<AttendanceModelMyTeam> filteredPlayerHistories =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).user_name.toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayerHistories.add(filterList.get(i));
                }
            }

            results.count= filteredPlayerHistories.size();
            results.values= filteredPlayerHistories;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.items = (ArrayList<AttendanceModelMyTeam>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
