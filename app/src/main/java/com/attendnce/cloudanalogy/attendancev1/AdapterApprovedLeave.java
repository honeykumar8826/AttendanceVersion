package com.attendnce.cloudanalogy.attendancev1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterApprovedLeave extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public List<ModelApprovedLeave> items = new ArrayList<>();

    private Context ctx;
    private int animation_type = 0;
    CustomFilterApprovedLeave filter;

    public interface OnItemClickListener {
        void onItemClick(View view, ModelApprovedLeave obj, int position);
    }


    public AdapterApprovedLeave(Context context, ArrayList<ModelApprovedLeave> items) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView user_name,dateofjoin,c_monthLeave,c_month_halfdayLeave;


        public ContactViewHolder(View v) {
            super(v);
            user_name =  v.findViewById(R.id.name);
            dateofjoin =  v.findViewById(R.id.doj);
            c_monthLeave=v.findViewById(R.id.c_month_leave);
            c_month_halfdayLeave=v.findViewById(R.id.c_half_dayleave);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.approvedleaves, parent, false);
        vh = new ContactViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.e("onBindViewHolder", "onBindViewHolder : " + position);
        if (holder instanceof ContactViewHolder) {
            ContactViewHolder view = (ContactViewHolder) holder;

            final ModelApprovedLeave approvedLeave = items.get(position);
            view.user_name.setText(approvedLeave.user_name);
            view.dateofjoin.setText(approvedLeave.dateofJoining);
            view.c_monthLeave.setText(approvedLeave.currentMonthLeave);
            view.c_month_halfdayLeave.setText(approvedLeave.currentMonthhalfday);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Item Clicked", Toast.LENGTH_SHORT).show();
                   /* Intent accIntent=new Intent(view.getContext(),AttendanceMyTeamMember.class);
                    String conid=approvedLeave.getUserId().toString();
                    SharedPreferences.Editor editor =ctx.getSharedPreferences("UserId", MODE_PRIVATE).edit();
                    editor.putString("user_id", conid);
                    editor.apply();
                    view.getContext().startActivity(accIntent);*/
                }
            });


        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;



}




