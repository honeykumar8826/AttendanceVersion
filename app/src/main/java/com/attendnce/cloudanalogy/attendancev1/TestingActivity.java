package com.attendnce.cloudanalogy.attendancev1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestingActivity extends AppCompatActivity {
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this entry?");

        builder.setPositiveButton("Yes, please", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                Toast.makeText(getApplicationContext(), "Yes clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                Toast.makeText(getApplicationContext(), "No clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //creating alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
       /* b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
                //rest request
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response !=null)
                        {

                        }


                        Log.i("response", "onResponse: "+response);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error", "error: "+error);

                        // progressdialogPunchIn.dismiss();
                        Intent callSplash = new Intent(TestingActivity.this, InternetUnavailable.class);
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
                      *//*   fromdateconverted = new SimpleDateFormat("yyyy-MM-dd").format(from);
                       todateconverted = new SimpleDateFormat("yyyy-MM-dd").format(To);*//*
                     *//*   Log.i("todateconverted", "todateconverted: "+todateconverted);*//*
*//*
                     try {
                            datef=dateFormat.parse(String.valueOf(from));
                            dateto=dateFormat.parse(String.valueOf(To));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        *//**//*  fromdateconverted = dateFormat.format(datef);
                         todateconverted = dateFormat.format(dateto);*//**//*
                         Log.i("getParams", "getParams: "+datef+"//"+dateto);


                        fromdateconverted = new SimpleDateFormat("yyyy-MM-dd").format(datef);
                        todateconverted = new SimpleDateFormat("yyyy-MM-dd").format(dateto);*//*



                        Log.i("getParams", "getParams: "+userName+""+password+""+deviceId);
                        // Log.i("getParams", "getParams: "+datef.getDate());
                        // Log.i("getParams", "getParams: "+datef.getTime());


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", userName);
                        params.put("password", password);
                        params.put("deviceId", deviceId);
                        params.put("memberId", "a0G3C000001ctPS");

                      *//*  params.put("subject", subject_leave);
                        params.put("applicationBody", body_message);
                        params.put("leavetype", leave_type);
                        params.put("fromDate", from);
                        params.put("toDate", to);*//*
                       *//* params.put("deviceId", deviceId);*//*
                        params.put("method", "individualMemberAttendance");
                     *//*   Map<String, String> params = new HashMap<String, String>();
                        params.put("username", "harish.kumar@cloudanalogy.com");
                        params.put("password", from);
                        params.put("deviceId", body_message);

                        params.put("method", "leave");*//*

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


            }
        });*/
    }
}
