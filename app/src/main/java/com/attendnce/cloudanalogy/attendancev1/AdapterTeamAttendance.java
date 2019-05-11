package com.attendnce.cloudanalogy.attendancev1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AdapterTeamAttendance extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public List<AttendanceModelMyTeam> items = new ArrayList<>();

    private Context ctx;
    private int animation_type = 0;
    CustomFilterTeamAttendance filter;

    public interface OnItemClickListener {
        void onItemClick(View view, AttendanceModelMyTeam obj, int position);
    }


    public AdapterTeamAttendance(Context context, ArrayList<AttendanceModelMyTeam> items) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView user_name,dateofjoin,c_monthLeave,c_month_halfdayLeave;


        public ContactViewHolder(View v) {
            super(v);
            user_name =  v.findViewById(R.id.user_name);
            dateofjoin =  v.findViewById(R.id.doj);
            c_monthLeave=v.findViewById(R.id.c_month_leave);
            c_month_halfdayLeave=v.findViewById(R.id.c_half_dayleave);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myteam_attendance, parent, false);
        vh = new ContactViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.e("onBindViewHolder", "onBindViewHolder : " + position);
        if (holder instanceof ContactViewHolder) {
            ContactViewHolder view = (ContactViewHolder) holder;

            final AttendanceModelMyTeam myteam = items.get(position);
            view.user_name.setText(myteam.user_name);
            view.dateofjoin.setText(myteam.dateofJoining);
            view.c_monthLeave.setText(myteam.currentMonthLeave);
            view.c_month_halfdayLeave.setText(myteam.currentMonthhalfday);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent accIntent=new Intent(view.getContext(),AttendanceMyTeamMember.class);

                    String conid = myteam.getUserId();
                    accIntent.putExtra("userid", conid);

               /*     SharedPreferences.Editor editor = ctx.getSharedPreferences("useridforatten", MODE_PRIVATE).edit();
                    editor.putString("userid", conid);

                    editor.apply();*/
                    //
               /*     SharedPreferences prefs = ctx.getSharedPreferences("useridforatten", MODE_PRIVATE);

                    String userName = prefs.getString("username", null);
                    String password = prefs.getString("password", null);
                    String deviceId = prefs.getString("deviceid", null);*/
                    /*SharedPreferences prefs = ctx.getSharedPreferences("useridforatten", MODE_PRIVATE);
                    String restoredText = prefs.getString("userid", null);
                    Log.i("userid", "userid: "+restoredText);*/
                    //
                /*    SharedPreferences.Editor editor =ctx.getSharedPreferences("UserId", MODE_PRIVATE).edit();
                    editor.putString("user_id", conid);
                    editor.apply();*/
                    view.getContext().startActivity(accIntent);
                   // Toast.makeText(view.getContext(), "click"+conid, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    @Override
    public int getItemCount() {

        Log.i("getItemCount", "getItemCount: "+items.size());
        return items.size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;



}




