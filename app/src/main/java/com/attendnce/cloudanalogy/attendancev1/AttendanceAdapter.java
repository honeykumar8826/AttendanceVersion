package com.attendnce.cloudanalogy.attendancev1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AttendanceGetterSetter> items = new ArrayList<>();


    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, AttendanceGetterSetter obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AttendanceAdapter(Context context, List<AttendanceGetterSetter> items) {
        this.items = items;
        ctx = context;
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name,inTime,outTime,totalHours;

        public OriginalViewHolder(View v) {
            super(v);


            name = (TextView) v.findViewById(R.id.name);
            inTime = (TextView) v.findViewById(R.id.attendacein_Card);
            outTime = (TextView) v.findViewById(R.id.attendaceout_Card);
            totalHours = (TextView) v.findViewById(R.id.attendacetotalHours_Card);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_card, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {

            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final AttendanceGetterSetter p = items.get(position);
            view.name.setText(p.name);
            view.inTime.setText(p.inTime);
            view.outTime.setText(p.outTime);
            view.totalHours.setText(p.totalHours);
            setAnimation(view.itemView, position);

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
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
