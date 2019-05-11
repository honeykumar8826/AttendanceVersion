package com.attendnce.cloudanalogy.attendancev1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AttendanceMyTeamMember extends AppCompatActivity {
    ShimmerRecyclerView recyclerView;
    public AdapterTeamMemberAttendance mAdapter;
    public View parent_view;
    String name,timein,timeout,totaltime;
    ImageView backbutton;
    String useridofselectedmember,convertintime,convertouttime;
    RelativeLayout backButtonfromteamattend;
    ProgressDialog progressDialog;

    ArrayList<ModelForMemberAttendance> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_my_team_member);
        backButtonfromteamattend =  findViewById(R.id.backButtonAttendanceList);

        //intent for getting the id of the teammember
        Intent intent = getIntent();
        useridofselectedmember= intent.getExtras().getString("userid");
        Log.i("YourtransferredData", "YourtransferredData: "+useridofselectedmember);

        recyclerView =  findViewById(R.id.shimmer_recycler_view);
        backButtonfromteamattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AttendanceMyTeamMember.this, MyTeam.class);
                startActivity(intent);
                finish();
            }
        });


        String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
        //rest request
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null) {

                    try {
                        JSONObject result = new JSONObject(response);

                        Log.i("teamMemberAttenda","teamMemberAttenda"+result);

                        if(result.get("userId").toString()!="null"){


                          if(result.get("hasError").toString()!="true")
                            {
                                String teamMemberAttendWrapper=result.get("teamMemberAttendanceWrapper").toString();


                                 Log.i("holidayAndAttach", "holidayAndAttachmentList: "+teamMemberAttendWrapper);

                                JSONArray teamMemberAttendWrapperarray=new JSONArray(teamMemberAttendWrapper);
                                for (int i=0;i<teamMemberAttendWrapperarray.length();i++){

                                    name = teamMemberAttendWrapperarray.getJSONObject(i).getString("Name");
                                    timein = teamMemberAttendWrapperarray.getJSONObject(i).getString("Time_In__c");
                                    //
                                    if(timein != null)
                                    {
                                        DateFormatUtil dateFormatUtil=new DateFormatUtil();
                                        try {
                                           convertintime = dateFormatUtil.DateChangeFormat1(timein);
                                            Log.i("onResponse", "changecreateddate: "+convertintime);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    /*    Log.i("onResponse", "changecreateddate: "+changedate1);
                                        changedate2= dateFormatUtil.DateChangeFormat2(changedate1);

                                    *//*    final String   _date = changecreateddate;
                                        String[] _dateFormat=_date.split("-");
                                        String _day   =  _dateFormat[0];
                                        String _month     =_dateFormat[1];
                                        String _time   = _dateFormat[2];
                                        Log.i("_date", "chan_date: "+_day+""+_month);*//*

                                        pendingLeaveforTl.setAppliedDate(changedate2);*/

                                    }



                                    //
                                    timeout = teamMemberAttendWrapperarray.getJSONObject(i).getString("Time_Out__c");
                                    if(timeout != null)
                                    {
                                        DateFormatUtil dateFormatUtil=new DateFormatUtil();
                                        try {
                                            convertouttime = dateFormatUtil.DateChangeFormat1(timeout);
                                            Log.i("onResponse", "changecreateddate: "+convertouttime);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                    totaltime = teamMemberAttendWrapperarray.getJSONObject(i).getString("Total_Hours__c");
                                    Log.i("holidayAndAttach", "namelist_json: "+name);
                                    items.add(new ModelForMemberAttendance(name,convertintime,convertouttime, totaltime));


                                }

                                mAdapter = new AdapterTeamMemberAttendance( items);

                                recyclerView.setHasFixedSize(true);


                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

                                recyclerView.setLayoutManager(mLayoutManager);

                                recyclerView.setItemAnimator(new DefaultItemAnimator());

                                recyclerView.setAdapter(mAdapter);

                            }


                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AttendanceMyTeamMember.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
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
                Log.i("error", "error: "+error);

                progressDialog.dismiss();
                Intent callSplash = new Intent(AttendanceMyTeamMember.this, InternetUnavailable.class);
                startActivity(callSplash);
                finish();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);

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



                Log.i("getParams", "getParams: "+userName+""+password+""+deviceId);
                // Log.i("getParams", "getParams: "+datef.getDate());
                // Log.i("getParams", "getParams: "+datef.getTime());


                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("deviceId", deviceId);
                params.put("memberId",useridofselectedmember );

                params.put("method", "individualMemberAttendance");
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







        /* //code for data fecthing*//* //code for data fecthing*//* //code for data fecthing*//* //code for data fecthing*/




    }
}
