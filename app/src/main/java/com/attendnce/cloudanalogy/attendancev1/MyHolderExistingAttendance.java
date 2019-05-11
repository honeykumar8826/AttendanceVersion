package com.attendnce.cloudanalogy.attendancev1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHolderExistingAttendance extends RecyclerView.ViewHolder implements View.OnClickListener  {

    TextView name,intime,outtime,totalhours;
    ImageView borderView;

    ItemClickListener itemClickListenerHistory;


    public MyHolderExistingAttendance (View itemView) {
        super(itemView);
        this.borderView=itemView.findViewById(R.id.borderattendanceCard);
        this.name=itemView.findViewById(R.id.name);
        this.intime=itemView.findViewById(R.id.attendacein_Card);
        this.outtime=itemView.findViewById(R.id.attendaceout_Card);
        this.totalhours=itemView.findViewById(R.id.attendacetotalHours_Card);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListenerHistory.onItemClick(v,getLayoutPosition());

    }

    public void setItemClickListenerHistory(ItemClickListener ic)
    {
        this.itemClickListenerHistory =ic;
    }
}
