package com.attendnce.cloudanalogy.attendancev1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

public class AdapterLeaveForApproval extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public List<PendingLeaveforTl> items = new ArrayList<>();
    Button accept,reject;

    private Context ctx;
    private int animation_type = 0;
    CustomFilterPendingLeaves filter;

    public interface OnItemClickListener {
        void onItemClick(View view, PendingLeaveforTl obj, int position);
    }


    public AdapterLeaveForApproval(Context context, List<PendingLeaveforTl> items) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {


        public TextView lappliedDate,luser_name,lfromDate,ltoDate,ldatewithmonth,ldayname,ltimeoncard,
                fappliedDate,fuser_name,fleave_description,ffromDate,ftoDate,fapprovedByhr;
         public  ImageView replybutton;

        public  FoldingCell fc;
        public  ImageView close;

        public ContactViewHolder(View v) {
            super(v);
           /* //for outside card//for outside card//for outside card//for outside card*/
            luser_name =  v.findViewById(R.id.user_namelive);
            lappliedDate =  v.findViewById(R.id.leaveApplied_date);
            ltoDate=v.findViewById(R.id.toDate);
            lfromDate=v.findViewById(R.id.fromDate);


            ldatewithmonth=(TextView) v.findViewById(R.id.datewithmonthname);
            ldayname=(TextView) v.findViewById(R.id._day);
            ltimeoncard=(TextView) v.findViewById(R.id._time);


            /* //for outside card//for outside card//for outside card//for outside card*/

        /*    for inner card for inner card for inner card for inner card for inner card for inner card*/

            fuser_name =  v.findViewById(R.id.fname);
            fappliedDate =  v.findViewById(R.id.fapplieddate);
            ftoDate=v.findViewById(R.id.fto);
            ffromDate=v.findViewById(R.id.ffrom);
            //
            fleave_description=v.findViewById(R.id.fdescription);
            fapprovedByhr=v.findViewById(R.id.fhrapporval);
            //
            fc = (FoldingCell) v.findViewById(R.id.folding_cell);
            close=v.findViewById(R.id.cross);
            replybutton=v.findViewById(R.id.reply);


            /*    for inner card for inner card for inner card for inner card for inner card for inner card*/

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leavefor_approval, parent, false);
        vh = new ContactViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Log.e("onBindViewHolder", "onBindViewHolder : " + position);
        if (holder instanceof ContactViewHolder) {
            ContactViewHolder view = (ContactViewHolder) holder;

            final PendingLeaveforTl pendingLeaveforTl = items.get(position);
            view.luser_name.setText(pendingLeaveforTl.user_name);
           // view.lappliedDate.setText(pendingLeaveforTl.appliedDate);
            view.ltoDate.setText(pendingLeaveforTl.toDate);
            view.lfromDate.setText(pendingLeaveforTl.fromDate);

      /*      //for innner card //for innner card //for innner card //for innner card //for innner card*/
            view.fuser_name.setText(pendingLeaveforTl.user_name);
            view.fappliedDate.setText(pendingLeaveforTl.appliedDate);

            String changecreateddate =pendingLeaveforTl.appliedDate;




            /* set value on a outside card*/
            String[] _dateFormat=changecreateddate.split("-");
            String _day   =  _dateFormat[0];
            String _month     =_dateFormat[1];
            String _time   = _dateFormat[2];
            Log.i("_date", "chan_date: "+_day+""+_month);

            view.ltimeoncard.setText(""+_time);

            view.ldatewithmonth.setText(""+_month);


            view.ldayname.setText(""+_day);

             view.lappliedDate.setText(""+_time);

            /* set value on a outside card*/


            view.ftoDate.setText(pendingLeaveforTl.toDate);
            view.ffromDate.setText(pendingLeaveforTl.fromDate);
            view.fleave_description.setText(pendingLeaveforTl.leaveDescription);
            view.fapprovedByhr.setText(pendingLeaveforTl.approvedByhr);


            view.fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ContactViewHolder) holder).fc.toggle(false);

                }
            });

            view.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ContactViewHolder) holder).fc.toggle(false);
                }
            });

            view.replybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.setContentView(R.layout.replypopup);
                    //getting the id of the field
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    accept=dialog.findViewById(R.id.approve_leave);
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(), "hello", Toast.LENGTH_SHORT).show();
                        }
                    });
                    reject=dialog.findViewById(R.id.reject_leave);
                    dialog.findViewById(R.id.relative1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });
    /*        view.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.fc.toggle(false);
                }
            });*/


            /*      //for innner card //for innner card //for innner card //for innner card //for innner card*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* Intent accIntent=new Intent(view.getContext(),AttendanceMyTeamMember.class);
                    String conid=myteam.getUserId().toString();
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




