package com.attendnce.cloudanalogy.attendancev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

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

public class CompanyRule extends AppCompatActivity {
Button b;

RelativeLayout backButtoncompanyrule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_rule);

        backButtoncompanyrule=findViewById(R.id.backButtonAttendanceList);
        backButtoncompanyrule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CompanyRule.this,UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
      /*  b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
                //rest request
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        Log.i("response", "onResponse: "+response);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error", "error: "+error);

                        // progressdialogPunchIn.dismiss();


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



                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", userName);
                        params.put("password", password);
                        params.put("deviceId", deviceId);
                *//*        params.put("subject", subject_leave);
                        params.put("applicationBody", body_message);
                        params.put("leavetype", leave_type);
                        params.put("fromDate", from);
                        params.put("toDate", to);*//*
                        params.put("method", "getAppliedLeaves");
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
