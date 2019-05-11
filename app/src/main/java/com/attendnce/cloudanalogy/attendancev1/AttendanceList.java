package com.attendnce.cloudanalogy.attendancev1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class AttendanceList extends AppCompatActivity {

    private static final String TAG ="TAG" ;

    RelativeLayout backButton;
    private RecyclerView recyclerView;
    private MyAdapterAllAttendanceRecord mAdapter;
    private View parent_view;

    ProgressDialog dialog;

    ArrayList<AttendanceGetterSetter> items = new ArrayList<>();

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        dialog = new ProgressDialog(AttendanceList.this);
        dialog.setMessage("Please wait......");
        dialog.setCancelable(false);
        dialog.show();

        constraintLayout =  findViewById(R.id.attendancelist);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);

        backButton=findViewById(R.id.backButtonAttendanceList);


        parent_view = findViewById(android.R.id.content);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backToUseractivity=new Intent(AttendanceList.this,UserActivity.class);
                startActivity(backToUseractivity);
                finish();
            }
        });
        updateAndroidSecurityProvider(this);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,"https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if(response!=null){

                        JSONObject result = new JSONObject(response);

                    if(result.get("userId").toString()!="null"){

                        if(result.get("hasError").toString()!="true"){

                            String monthRecords=result.get("monthRecords").toString();


                            JSONArray monthlyRecords=new JSONArray(monthRecords);

                            for (int i=0;i<monthlyRecords.length();i++){



                                AttendanceGetterSetter attendanceGetterSetter=new AttendanceGetterSetter();

                                String timeIN=null,timeOut=null;

                                try{
                                    timeIN=monthlyRecords.getJSONObject(i).getString("Time_In__c");

                                }catch (Exception e){

                                }

                                try{
                                    timeOut=monthlyRecords.getJSONObject(i).getString("Time_Out__c");

                                }catch (Exception e){

                                }

                                String name=null;

                                if (timeIN!=null){

                                    SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                    TimeZone timeZone = TimeZone.getTimeZone("IST");
                                    isoFormat.setTimeZone(timeZone);
                                    Date date5 = isoFormat.parse(timeIN);
                                    name= new SimpleDateFormat("dd-MM-yyyy").format(date5);
                                    String time5 = new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(date5);
                                    attendanceGetterSetter.setInTime(time5);
                                    attendanceGetterSetter.setName(name);


                                }

                                if (timeOut!=null){

                                    SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                    TimeZone timeZone = TimeZone.getTimeZone("IST");
                                    isoFormat.setTimeZone(timeZone);
                                    Date date6 = isoFormat.parse(timeOut);
                                    String time6 = new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(date6);
                                    Log.i(TAG, "timeOut: "+time6);
                                    attendanceGetterSetter.setOutTime(time6);
                                }else{
                                    attendanceGetterSetter.setOutTime("00:00");
                                }

                                attendanceGetterSetter.setTotalHours(monthlyRecords.getJSONObject(i).getString("Total_Hours__c"));
                                items.add(attendanceGetterSetter);

                                mAdapter = new MyAdapterAllAttendanceRecord(getBaseContext(), items);
                                recyclerView.setLayoutManager(new LinearLayoutManager(AttendanceList.this));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);
                            }

                            dialog.dismiss();

                        }else{

                            dialog.dismiss();


                            Snackbar snackbar = Snackbar
                                    .make(constraintLayout,result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);

                            snackbar.show();

                        }
                    }else{

                        dialog.dismiss();


                        Snackbar snackbar = Snackbar
                                .make(constraintLayout,result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);

                        snackbar.show();
                    }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateAndroidSecurityProvider(AttendanceList.this);
                dialog.dismiss();
                Intent callSplash=new Intent(AttendanceList.this,InternetUnavailable.class);
                startActivity(callSplash);
                finish();
            }
        }){
            @Override
            protected Map<String,String> getParams(){

                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);

                String userName=prefs.getString("username", null);
                String password=prefs.getString("password", null);
                String deviceId=prefs.getString("deviceid", null);

                Map<String,String> params = new HashMap<String, String>();
                params.put("username",userName);
                params.put("password",password);
                params.put("deviceId", deviceId);
                params.put("method","monthRecords");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {

            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("SecurityException", "Google Play Services not available.");
        }
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(AttendanceList.this,R.style.MyDialogTheme).setIcon(android.R.drawable.ic_dialog_alert).setTitle(Html.fromHtml("<font color='#1976D2'>Exit</font>"))
                .setMessage(Html.fromHtml("<font color='#1976D2'>Are you sure you want to Exit ?</font>"))
                .setPositiveButton(Html.fromHtml("<font color='#fff'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AttendanceList.this.finishAffinity();
                        moveTaskToBack(true);
                        AttendanceList.this.finishAffinity();
                        System.exit(0);

                    }
                }).setNegativeButton(Html.fromHtml("<font color='#fff'>No</font>"), null).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
