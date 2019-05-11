package com.attendnce.cloudanalogy.attendancev1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyAdapterAllAttendanceRecord extends RecyclerView.Adapter<MyHolderExistingAttendance> {

    Context c;
    ArrayList<AttendanceGetterSetter> playerHistories;

    public MyAdapterAllAttendanceRecord(Context ctx, ArrayList<AttendanceGetterSetter> playerHistories)
    {
        this.c=ctx;
        this.playerHistories = playerHistories;
    }

    @Override
    public MyHolderExistingAttendance onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_card,null);
        MyHolderExistingAttendance holder=new MyHolderExistingAttendance(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyHolderExistingAttendance holder,final int position) {


        if(playerHistories.get(position).getOutTime()=="00:00"){
            SimpleDateFormat isoFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date date5 = null;
            try {
                date5 = isoFormat.parse(playerHistories.get(position).getInTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.intime.setText(new SimpleDateFormat("hh:mm a").format(date5));
            holder.outtime.setText(playerHistories.get(position).getOutTime());

        }else{

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            try {
                Date date2 = dateFormat.parse(playerHistories.get(position).getOutTime());
                Date date1 = dateFormat.parse(playerHistories.get(position).getInTime());
                long difference = date2.getTime() - date1.getTime();
                int seconds = (int) difference / 1000;

                int hours = seconds / 3600;
                if (hours>=9){
                    holder.borderView.setBackgroundResource(R.drawable.shape_round_solid_green);
                }else {
                    holder.borderView.setBackgroundResource(R.drawable.shape_round_red);
                }

                SimpleDateFormat isoFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                Date date5 = null,date6=null;
                try {
                    date5 = isoFormat.parse(playerHistories.get(position).getInTime());
                    date6 = isoFormat.parse(playerHistories.get(position).getOutTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.intime.setText(new SimpleDateFormat("hh:mm a").format(date5));
                holder.outtime.setText(new SimpleDateFormat("hh:mm a").format(date6));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.name.setText(playerHistories.get(position).getName());
        holder.totalhours.setText(playerHistories.get(position).getTotalHours());
        setAnimation(holder.itemView, position);
    }

    //GET TOTAL NUM OF PLAYERS
    @Override
    public int getItemCount() {
        return playerHistories.size();
    }
    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, 4);
            lastPosition = position;
        }
    }


}
