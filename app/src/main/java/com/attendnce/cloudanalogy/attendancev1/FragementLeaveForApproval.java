package com.attendnce.cloudanalogy.attendancev1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragementLeaveForApproval extends Fragment {
    AdapterLeaveForApproval adapter;
    ShimmerRecyclerView recyclerPendingLeaves;
   // RecyclerView recyclerPendingLeaves;
    String changedate1,changedate2;
    public FragementLeaveForApproval() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_leave_for_approval, container, false);
        recyclerPendingLeaves=view.findViewById(R.id.shimmer_recycler_view);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("TAG", "onResponse: "+response);
                JSONObject pendingLeave = null;
                //Log.i("TAG", "onResponse:response "+response);
                try {
                    pendingLeave = new JSONObject(response);
                    String pendingLeaveString = pendingLeave.getString("leaveForApproval_Tl_Side");
                    JSONArray jsonArray = new JSONArray(pendingLeaveString);
                    Log.i("TAG", "onResponse:blb "+jsonArray);
                    try {
                        final JSONArray records = jsonArray;
                        for (int i=0;i<records.length();i++) {
                            //String name=records.getJSONObject(i).getString("Name");
                            //Log.i("TAG", "records: name "+name);

                        }


                        recyclerPendingLeaves.clearFocus();
                        recyclerPendingLeaves.removeAllViews();
                        recyclerPendingLeaves.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerPendingLeaves.setItemAnimator(new DefaultItemAnimator());
                        adapter = new AdapterLeaveForApproval(view.getContext(), getPlayers(records));
                        recyclerPendingLeaves.setAdapter(adapter);
                    } catch (Exception e) {

                        Log.i("TAG", "onResponse: "+e.getMessage());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("TAG","error"+error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "abhishek@cloudanalogy.com");
                params.put("password", "ca@123456");
                params.put("deviceId", "8f8f9731b54db7e5");
                params.put("method", "teamPendingLeavesFor_TL");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

        return view;
    }
    private ArrayList<PendingLeaveforTl> getPlayers(JSONArray records)
    {
        ArrayList<PendingLeaveforTl> playerMain=new ArrayList<>();
       // Log.i("TAG", "datalis--:"+datalist);


      //  Log.i("TAG", "datalist.length()---: "+datalist.length());
        Log.i("TAG", "getPlayers: "+records.length());
        Log.i("TAG", "getPlayers: records "+records);

        for(int i=0;i<records.length();i++){
            PendingLeaveforTl pendingLeaveforTl = new PendingLeaveforTl();
            try {



                String username=records.getJSONObject(i).getString("User__r");
                JSONObject name= new JSONObject(username);
                if(!username.equals("null"))
                {
                    pendingLeaveforTl.setUser_name(name.getString("Name"));
                   }

                   else{pendingLeaveforTl.setUser_name("");

                }

                String createdDate= records.getJSONObject(i).getString("CreatedDate");
                //
                if(createdDate != null)
                {
                    DateFormatUtil dateFormatUtil=new DateFormatUtil();
                    changedate1 = dateFormatUtil.DateChangeFormat1(createdDate);

                    Log.i("onResponse", "changecreateddate: "+changedate1);
                    changedate2= dateFormatUtil.DateChangeFormat2(changedate1);

                                    /*    final String   _date = changecreateddate;
                                        String[] _dateFormat=_date.split("-");
                                        String _day   =  _dateFormat[0];
                                        String _month     =_dateFormat[1];
                                        String _time   = _dateFormat[2];
                                        Log.i("_date", "chan_date: "+_day+""+_month);*/

                    pendingLeaveforTl.setAppliedDate(changedate2);

                }

                //
             /*   if(!createdDate.equals("null"))
                {





                    pendingLeaveforTl.setAppliedDate(createdDate);}*/

                //

                String leavedes= records.getJSONObject(i).getString("Leave_Description__c");

                if(!leavedes.equals("null"))
                {pendingLeaveforTl.setLeaveDescription(leavedes);}
                //
                //
                String approvedhr= records.getJSONObject(i).getString("Approved_by_HR__c");

                if(!approvedhr.equals("null"))
                {pendingLeaveforTl.setApprovedByhr(approvedhr);}


                //

                String startDate = records.getJSONObject(i).getString("StartDate__c");
                if(!startDate.equals("null"))
                {pendingLeaveforTl.setFromDate(startDate); }


                pendingLeaveforTl.setToDate(records.getJSONObject(i).getString("EndDate__c"));
                playerMain.add(pendingLeaveforTl);
            }
            catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }



        return playerMain;
    }

}