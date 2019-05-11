package com.attendnce.cloudanalogy.attendancev1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterTeamMemberAttendance extends RecyclerView.Adapter<AdapterTeamMemberAttendance.MyViewHolder> {
    private List<ModelForMemberAttendance> teamemberatten;
    @NonNull
    @Override
    public AdapterTeamMemberAttendance.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_myteam_whole, parent, false);

        return new AdapterTeamMemberAttendance.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelForMemberAttendance wholeteammemberlist = teamemberatten.get(position);
        //date spliting by space symbol this sign for intime

        String[] _dateFormat=wholeteammemberlist.getTimein().split(" ");
        String date   =  _dateFormat[0];
        String time     =_dateFormat[1];
        String AmPM   = _dateFormat[2];



        //



        //date spliting by space symbol this sign for timeout

        String[] _dateFormat1 = wholeteammemberlist.getTimeout().split(" ");
        String date1   =  _dateFormat1[0];
        String time1     =_dateFormat1[1];
        String AmPM1  = _dateFormat1[2];

        Log.i("onBindViewHolder", "onBindViewHolder: "+time1);

        //


        holder.name.setText(wholeteammemberlist.getUsername());
        holder.intime.setText(""+time+""+AmPM);
        holder.outime.setText(""+time1+""+AmPM1);
        holder.totaltime.setText(wholeteammemberlist.getTotalhour());



    }

    public AdapterTeamMemberAttendance(List<ModelForMemberAttendance> teamemberatten) {
        this.teamemberatten = teamemberatten;
        Log.i("AdapterForHoliday", "AdapterForHoliday: "+teamemberatten);
    }



    @Override
    public int getItemCount() {

        return  teamemberatten.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, intime,outime,totaltime;
        public ImageView rimg;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.member_name);
            intime = (TextView) view.findViewById(R.id.member_intime);

            outime = (TextView) view.findViewById(R.id.member_outtime);
            totaltime = (TextView) view.findViewById(R.id.member_totaltime);
            // rimg = view.findViewById(R.id.imp_img);
        }
    }
}





