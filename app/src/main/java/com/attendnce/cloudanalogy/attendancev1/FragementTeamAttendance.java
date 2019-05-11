package com.attendnce.cloudanalogy.attendancev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class FragementTeamAttendance extends Fragment {
   // RecyclerView teamRecycler;
    ShimmerRecyclerView teamRecycler;
    AdapterTeamAttendance adapter;
    List<AttendanceModelMyTeam> s= new ArrayList<AttendanceModelMyTeam>();
    public FragementTeamAttendance() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_team_attendance, container, false);
        teamRecycler=view.findViewById(R.id.shimmer_recycler_view);
        //
        String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
        //rest request
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("TAG", "onResponsekkkkkkkkkkkkkkkkkkkkkkkk--------: "+response);

                JSONObject teamattendance = null;

                if(response !=null)
                {
                    try {
                        teamattendance = new JSONObject(response);
                        String teamattendanceString = teamattendance.getString("teamMemberWrapper");
                        JSONArray jsonArray = new JSONArray(teamattendanceString);
                        Log.i("TAG", "teamMemberWrapper: "+jsonArray);

                        final JSONArray records = jsonArray;

                     /*   for (int i=0;i<records.length();i++) {
                            //String name=records.getJSONObject(i).getString("Name");
                            //Log.i("TAG", "records: name "+name);

                        }*/
                     s=getteamrecord(records);
                        Log.i("response", "onResponse: "+s.size());

                        teamRecycler.clearFocus();
                        teamRecycler.removeAllViews();
                        teamRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        teamRecycler.setItemAnimator(new DefaultItemAnimator());
                        adapter = new AdapterTeamAttendance(getContext(), getteamrecord(records));
                        teamRecycler.setAdapter(adapter);
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Log.i("response", "onResponse: "+response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "error: "+error);

                // progressdialogPunchIn.dismiss();
                Intent callSplash = new Intent(getContext(), InternetUnavailable.class);
                startActivity(callSplash);


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
                /*   Log.i("todateconverted", "todateconverted: "+todateconverted);*/
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
                      /*  params.put("subject", subject_leave);
                        params.put("applicationBody", body_message);
                        params.put("leavetype", leave_type);
                        params.put("fromDate", from);
                        params.put("toDate", to);*/
                params.put("method", "teamMember");
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

        return view;
    }

    private ArrayList<AttendanceModelMyTeam> getteamrecord(JSONArray records)
    {
        ArrayList<AttendanceModelMyTeam> attendancelist = new ArrayList<>();
        // Log.i("TAG", "datalis--:"+datalist);


        //  Log.i("TAG", "datalist.length()---: "+datalist.length());

        //Log.i("TAG", "getPlayers: records "+records);

        for(int i=0;i<records.length();i++){
            AttendanceModelMyTeam attendanceModelMyTeam = new AttendanceModelMyTeam();
            try {
                Log.i("getteamrecord", "iiiiiii: "+i);

              //  Log.i("TAG", "AttendanceModelMyTeam++>: "+records.length());
                String userid=records.getJSONObject(i).getString("Id");
                // JSONObject name= new JSONObject(username);
                if(!userid.equals("null"))
                {
                    attendanceModelMyTeam.setUserId(""+userid);

                }

                else{attendanceModelMyTeam.setUserId("");

                }

                String username=records.getJSONObject(i).getString("Name");
               // JSONObject name= new JSONObject(username);
                if(!username.equals("null"))
                {
                    attendanceModelMyTeam.setUser_name(""+username);

                }

                else{attendanceModelMyTeam.setUser_name("");

                }

                String doj=records.getJSONObject(i).getString("Date_Of_Joining__c");
                // JSONObject name= new JSONObject(username);
                if(!doj.equals("null"))
                {
                    attendanceModelMyTeam.setDateofJoining(""+doj);

                }

                else{attendanceModelMyTeam.setDateofJoining("");

                }
                attendancelist.add(attendanceModelMyTeam);
          /*      String doj=records.getJSONObject(i).getString("Date_Of_Joining__c");
                // JSONObject name= new JSONObject(username);
                if(!doj.equals("null"))
                {
                    attendanceModelMyTeam.setDateofJoining(""+doj);
                    attendancelist.add(attendanceModelMyTeam);
                }

                else{attendanceModelMyTeam.setDateofJoining("");

                }*/







            }
            catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }




        return attendancelist;
    }

/*
    private ArrayList<AttendanceModelMyTeam> setAttendance()
    {
        ArrayList<AttendanceModelMyTeam> attendance=new ArrayList<>();
        for(int i=0;i<4;i++){
            AttendanceModelMyTeam attendanceModelMyTeam = new AttendanceModelMyTeam();
            {
                String name ="Abhishek"+i;
                if (!name.equals("null")) {
                    attendanceModelMyTeam.setUser_name(name);
                }
                else {attendanceModelMyTeam.setUser_name("");}
                String email="2"+i;
                if(!email.equals("null")) {
                    attendanceModelMyTeam.setCurrentMonthLeave(email);
                }else {attendanceModelMyTeam.setCurrentMonthLeave("");}
                String phone= "23"+i;
                if (!phone.equals("null")) {
                    attendanceModelMyTeam.setCurrentMonthhalfday(phone);
                }else {attendanceModelMyTeam.setCurrentMonthhalfday("");}
                String id= "232"+i;

                if(!id.equals("null"))
                {
                    attendanceModelMyTeam.setDateofJoining(id);}else {attendanceModelMyTeam.setDateofJoining("");}
                attendance.add(attendanceModelMyTeam);
            }

        }
        return attendance;
    }
*/


}