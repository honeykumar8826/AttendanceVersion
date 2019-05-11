package com.attendnce.cloudanalogy.attendancev1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

public class LeaveStatusAdapter extends RecyclerView.Adapter<LeaveStatusAdapter.MyViewHolder> {

    private List<LeaveStatusGetterSetter> leavestatusrecord;

    private  OnItemClickListener listener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folding_layout_for_leave_status, parent, false);

        return new LeaveStatusAdapter.MyViewHolder(itemView);



    }

    public interface OnItemClickListener {
        void onItemClick(HolidayGetterSetter item);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

    /*    holder.bind(holidaylist.get(position), listener);*/

/*//manin data //manin data //manin data //manin data */
        LeaveStatusGetterSetter leavedata = leavestatusrecord.get(position);
        //date spliting by - this sign

        String changecreateddate =leavedata.getAppliedDate();

        /* final String   _date = changecreateddate;*/
        String[] _dateFormat=changecreateddate.split("-");
        String _day   =  _dateFormat[0];
        String _month     =_dateFormat[1];
        String _time   = _dateFormat[2];
        Log.i("_date", "chan_date: "+_day+""+_month);


        //


        holder.lstatus.setText("Yes");
        holder.lstartdate.setText(leavedata.getstartdate());
        holder.lenddate.setText(leavedata.getEnddate());
        /*//applied date will be foramted into many data*/

        holder.lapplieddate.setText(""+_time);

       holder.ldatewithmonth.setText(""+_month);


        holder.ldayname.setText(""+_day);


        holder.ltimeoncard.setText(""+_time);
        /*//for inner card *//*//for inner card *//*//for inner card *//*//for inner card */



        holder.folstatus.setText("Yes");
        holder.folsubject.setText(leavedata.getSubject());
        holder.folapplieddate.setText(leavedata.getAppliedDate());
        holder.folleavetype.setText(leavedata.getLeaveType());

        holder.foltlapproval.setText(leavedata.getTlapproval());
        holder.folhrapporval.setText(leavedata.getHrapproval());
        holder.foldescritpion.setText(leavedata.getDescription());

        holder.foltodate.setText(leavedata.getstartdate());
        holder.folfromdate.setText(leavedata.getEnddate());




        /*//for inner card *//*//for inner card *//*//for inner card *//*//for inner card */

        Log.i("TAG", "getDescription_getDescription: "+leavedata.getDescription());

        Log.i("onBindViewHolder", "onBindViewHolder: "+leavedata.getLeavestatus());
        Log.i("onBindViewHolder", "onBindViewHolder: "+leavedata.getstartdate());
        Log.i("onBindViewHolder", "onBindViewHolder: "+leavedata.getEnddate());



        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.fc.toggle(false);

            }
        });

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.fc.toggle(false);
            }
        });




    }
    public LeaveStatusAdapter(List<LeaveStatusGetterSetter> leavestatusrecord/*,OnItemClickListener listener*/) {
        this.leavestatusrecord = leavestatusrecord;
        Log.i("LeaveStatusAdapter", "LeaveStatusAdapter: "+leavestatusrecord);
     /*   this.listener = listener;*/
    }

    @Override
    public int getItemCount() {
        return  leavestatusrecord.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView   lapplieddate ,lstatus, lstartdate,lenddate,_desc,folstatus,folsubject,folapplieddate,folleavetype,foltlapproval,folhrapporval,
        foldescritpion,foltodate,folfromdate,ldatewithmonth,ldayname,ltimeoncard;
        FoldingCell fc;
        ImageView close;

        public MyViewHolder(View view) {
            super(view);

            lstatus = (TextView) view.findViewById(R.id.leave_status_record);
            lstartdate = (TextView) view.findViewById(R.id.to_date);
            lenddate = (TextView) view.findViewById(R.id.from_date);
            lapplieddate=(TextView) view.findViewById(R.id.applied_date_record);

            ldatewithmonth=(TextView) view.findViewById(R.id.datewithmonthname);
            ldayname=(TextView) view.findViewById(R.id._day);
            ltimeoncard=(TextView) view.findViewById(R.id._time);
            //
            folstatus = (TextView) view.findViewById(R.id.fstatus);
            folsubject = (TextView) view.findViewById(R.id.fsubject);
            folapplieddate = (TextView) view.findViewById(R.id.fapplieddate);
            folleavetype = (TextView) view.findViewById(R.id.fleavetype);
            foltlapproval = (TextView) view.findViewById(R.id.ftlapproval);
            folhrapporval = (TextView) view.findViewById(R.id.fhrapporval);
            foldescritpion = (TextView) view.findViewById(R.id.fdescription);
            foltodate = (TextView) view.findViewById(R.id.fto);
            folfromdate = (TextView) view.findViewById(R.id.ffrom);


            //

    /*        _desc=  view.findViewById(R.id.d);*/
            fc = (FoldingCell) view.findViewById(R.id.folding_cell);
            close=view.findViewById(R.id.cross);

        }
    }

    //seetting the data
/*    public void bind(final HolidayGetterSetter item, final OnItemClickListener listener) {
        name.setText(item.name);
        Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }*/



    /*//*/
}
