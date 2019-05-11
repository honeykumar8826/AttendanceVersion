package com.attendnce.cloudanalogy.attendancev1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterForHoliday extends RecyclerView.Adapter<AdapterForHoliday.MyViewHolder> {
    private List<HolidayGetterSetter> holidaylist;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holidays_card_show, parent, false);

        return new MyViewHolder(itemView);
    }
    public AdapterForHoliday(List<HolidayGetterSetter> holidaypass) {
        this.holidaylist = holidaypass;
        Log.i("AdapterForHoliday", "AdapterForHoliday: "+holidaylist);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HolidayGetterSetter holiday = holidaylist.get(position);
        holder.rday.setText(holiday.getDay());
        holder.rdate.setText(holiday.getDate());



    }

    @Override
    public int getItemCount() {

        return  holidaylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rday, rdate;
       public ImageView rimg;
        public MyViewHolder(View view) {
            super(view);
            rday = (TextView) view.findViewById(R.id.imp_day);
            rdate = (TextView) view.findViewById(R.id.imp_date);
           // rimg = view.findViewById(R.id.imp_img);
        }
    }
}


