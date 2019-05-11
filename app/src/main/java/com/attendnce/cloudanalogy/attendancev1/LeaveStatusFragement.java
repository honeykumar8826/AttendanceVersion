package com.attendnce.cloudanalogy.attendancev1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class LeaveStatusFragement extends Fragment {
    ImageView close;
    FoldingCell fc;
    //
    String	startdate;

    String  enddate  ;
    String  hrapprove;
    String  tlapprove;
    String  leavetype;
    String  descrip  ;
    String  subject  ;
    String  status   ;
    String createddate1;
    String changecreateddate;
    String changecreateddate2;


    //
    ShimmerRecyclerView recyclerView;
  //  RecyclerView recyclerView;
    public LeaveStatusAdapter mAdapter;
    public View parent_view;
    String holidaylist_json, name, date;
    //
    ArrayList<LeaveStatusGetterSetter> items = new ArrayList<>();

    public LeaveStatusFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     /*   Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);*/
        /*for refreshing the activity by fragement*//*for refreshing the activity by fragement*/
        View view = inflater.inflate(R.layout.fragment_leave_status_fragement, container, false);



        /*  // attach click listener to folding cell  // attach click listener to folding cell*/
       /*   fc = (FoldingCell) view.findViewById(R.id.folding_cell);*/
        /*  // attach click listener to folding cell  // attach click listener to folding cell*/

        /* //for close the folding cell*//* //for close the folding cell*/
  /*      close=view.findViewById(R.id.cross);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.toggle(false);
            }
        });*/
        /* //for close the folding cell*//* //for close the folding cell*/

        // Inflate the layout for this fragment


        /*   // attach click listener to folding cell  // attach click listener to folding cell*/
/*        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
            }
        });*/
        /*   // attach click listener to folding cell  // attach click listener to folding cell*/

        //for setting the layout for recycler view on folding cell
        recyclerView =  view.findViewById(R.id.shimmer_recycler_view);



        return view;

    }

    private void setrefreshforfragementdata() {

        items = new ArrayList<>();
        //
        String _Url = "https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
        //rest request
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.i("holidayAndAttach", "onResponse check: " + response);
                if (response != null) {

                    try {
                        JSONObject result = new JSONObject(response);

                        Log.i("holidayAndAttach", "onResponse check: " + result);

                        if (result.get("userId").toString() != "null") {


                            if (result.get("hasError").toString() != "true") {
                                String leaveListWrapper = result.get("leaveListWrapper").toString();


                                Log.i("holidayAndAttach", "leaveListWrapper: " + leaveListWrapper);
                                LeaveStatusGetterSetter leaveStatusGetterSetter = new LeaveStatusGetterSetter();
                                JSONArray leaveListWrapperarray = new JSONArray(leaveListWrapper);
                                for (int i = 0; i < leaveListWrapperarray.length(); i++) {

                                    startdate = leaveListWrapperarray.getJSONObject(i).getString("StartDate__c");

                                    enddate = leaveListWrapperarray.getJSONObject(i).getString("EndDate__c");
                                    hrapprove = leaveListWrapperarray.getJSONObject(i).getString("Approved_by_HR__c");
                                    tlapprove = leaveListWrapperarray.getJSONObject(i).getString("Approved_by_TL__c");
                                    leavetype = leaveListWrapperarray.getJSONObject(i).getString("LeaveType__c");
                                    descrip =leaveListWrapperarray.getJSONObject(i).getString("Leave_Description__c");
                                    subject = "Subject for leave";//leaveListWrapperarray.getJSONObject(i).getString("StartDate__c");
                                    status ="Pending" ;//leaveListWrapperarray.getJSONObject(i).getString("StartDate__c");
                                    createddate1=leaveListWrapperarray.getJSONObject(i).getString("CreatedDate");
                                    Log.i("onResponse", "createddate: "+createddate1);
                                    if(createddate1 != null)
                                    {
                                        DateFormatUtil dateFormatUtil=new DateFormatUtil();
                                        changecreateddate = dateFormatUtil.DateChangeFormat1(createddate1);

                                        Log.i("onResponse", "changecreateddate: "+date);
                                        changecreateddate2= dateFormatUtil.DateChangeFormat2(changecreateddate);

                                    /*    final String   _date = changecreateddate;
                                        String[] _dateFormat=_date.split("-");
                                        String _day   =  _dateFormat[0];
                                        String _month     =_dateFormat[1];
                                        String _time   = _dateFormat[2];
                                        Log.i("_date", "chan_date: "+_day+""+_month);*/



                                    }



                               /*    String startdate = leaveListWrapperarray.getJSONObject(i).getString("StartDate__c");
                                    Log.i("StartDate__c", "StartDate__c: "+startdate);
                                    String enddate = leaveListWrapperarray.getJSONObject(i).getString("EndDate__c");
                                    String hrapprove = leaveListWrapperarray.getJSONObject(i).getString("Approved_by_HR__c");
                                    String tlapprove = leaveListWrapperarray.getJSONObject(i).getString("Approved_by_TL__c");
                                    String leavetype = leaveListWrapperarray.getJSONObject(i).getString("LeaveType__c");
                                    String descrip ="a"; //leaveListWrapperarray.getJSONObject(i).getString("Leave_Description__c");
                                    String subject = "Subject for leave";//leaveListWrapperarray.getJSONObject(i).getString("StartDate__c");
                                    String status ="Pending" ;//leaveListWrapperarray.getJSONObject(i).getString("StartDate__c");*/


                                  /*  leaveStatusGetterSetter.setstartdate(startdate);
                                    leaveStatusGetterSetter.setEnddate(enddate);
                                    leaveStatusGetterSetter.setHrapproval(hrapprove);
                                    leaveStatusGetterSetter.setTlapproval(tlapprove);
                                    leaveStatusGetterSetter.setLeaveType(leavetype);
                                    leaveStatusGetterSetter.setDescription(descrip);
                                    leaveStatusGetterSetter.setSubject(subject);
                                    leaveStatusGetterSetter.setLeavestatus(status);
                                    */


                                    Log.i("TAG", "descripdescrip: "+descrip);




                                    items.add(new LeaveStatusGetterSetter(subject,leavetype,tlapprove, hrapprove, descrip,  status,changecreateddate2,  startdate, enddate));

                                    Log.i("startdate", "startdate: " + startdate);

                                }


                                mAdapter = new LeaveStatusAdapter(items);

                                recyclerView.setHasFixedSize(true);

                                // vertical RecyclerView
                                // keep movie_list_row.xml width to `match_parent`
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                                // horizontal RecyclerView
                                // keep movie_list_row.xml width to `wrap_content`
                                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                                recyclerView.setLayoutManager(mLayoutManager);

                                // adding inbuilt divider line
                                //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

                                // adding custom divider line with padding 16dp
                                // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());

                                recyclerView.setAdapter(mAdapter);
                                // progressDialog.dismiss();*/
                            }


                        } else {
                            //progressDialog.dismiss();
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.i("response", "onResponse: " + response);
                }

                //


//


                //


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "error: " + error);

                /*progressDialog.dismiss();*/
                Intent callSplash = new Intent(getContext(), InternetUnavailable.class);
                startActivity(callSplash);
                /*  finish();*/

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                SharedPreferences prefs = getActivity().getSharedPreferences("LoginDetails", MODE_PRIVATE);

                String userName = prefs.getString("username", null);
                String password = prefs.getString("password", null);
                String deviceId = prefs.getString("deviceid", null);
                Date datef = new Date();
                Date dateto = new Date();
                //


                //
                String strDateFormat = "dd-MM-yyy HH:mm:ss z";
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                // dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
                      /*   fromdateconverted = new SimpleDateFormat("yyyy-MM-dd").format(from);
                       todateconverted = new SimpleDateFormat("yyyy-MM-dd").format(To);*/

/*
                     try {
                            datef=dateFormat.parse(String.valueOf(from));
                            dateto=dateFormat.parse(String.valueOf(To));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        *//*  fromdateconverted = dateFormat.format(datef);
                         todateconverted = dateFormat.format(dateto);*//*
                         Log.i("getParams", "getParams: "+datef+"//"+dateto);


                        fromdateconverted = new SimpleDateFormat("yyyy-MM-dd").format(datef);
                        todateconverted = new SimpleDateFormat("yyyy-MM-dd").format(dateto);*/


                Log.i("getParams", "getParams: " + userName + "" + password + "" + deviceId);
                // Log.i("getParams", "getParams: "+datef.getDate());
                // Log.i("getParams", "getParams: "+datef.getTime());


                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("deviceId", deviceId);

                params.put("method", "getAppliedLeaves");
                     /*   Map<String, String> params = new HashMap<String, String>();
                        params.put("username", "harish.kumar@cloudanalogy.com");
                        params.put("password", from);
                        params.put("deviceId", body_message);

                        params.put("method", "leave");*/

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

        //


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG", "onResume kishor: ");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            setrefreshforfragementdata();
            Log.i("TAG", "setUserVisibleHint kishor: ");
        }
    }
/*
    public void refreshActivity(){
        LeaveTab leaveTab = (LeaveTab) getActivity();
        if(leaveTab!=null){
            leaveTab.refreshMyData();
        }
    }
*/

}