package com.attendnce.cloudanalogy.attendancev1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BirtdayAndAniversary extends AppCompatActivity {
      ShimmerRecyclerView recyclerView;
    //RecyclerView recyclerView;
    public Birtday_AniversaryAdapter mAdapter;
    public View parent_view;
    String name,date;
    ImageView backbutton;
    Button c;
    RelativeLayout backButtonforbirthday;

    ProgressDialog progressDialog;

    ArrayList<Birthday_AniversaryGetterSetter> items = new ArrayList<>();

    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birtday_and_aniversary);
       // recyclerView=findViewById(R.id.recyclerView);
       recyclerView=findViewById(R.id.shimmer_recycler_view);
        backButtonforbirthday=findViewById(R.id.backButtonbirthday);
        backButtonforbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BirtdayAndAniversary.this,UserActivity.class);
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
                Log.i("holidayAndAttach", "onResponse check: "+response);
              if(response!=null) {

                    try {
                        JSONObject result = new JSONObject(response);

                        Log.i("holidayAndAttach", "onResponse check: "+result);

                        if(result.get("userId").toString()!="null"){


                          if(result.get("hasError").toString()!="true")
                            {
                                String weeklyBirthdayList=result.get("weeklyBirthdayList").toString();


                                Log.i("holidayAndAttach", "holidayAndAttachmentList: "+weeklyBirthdayList);

                                JSONArray BirthdayListarray=new JSONArray(weeklyBirthdayList);
                                for (int i=0;i<BirthdayListarray.length();i++){

                                      String   birthdaydatefromjson = BirthdayListarray.getJSONObject(i).getString("Date_Of_Birth__c");
                                      String   dateofjoiningfromjson = BirthdayListarray.getJSONObject(i).getString("Date_Of_Joining__c");
                                    String   namefromjson = BirthdayListarray.getJSONObject(i).getString("Name");

                                    Birthday_AniversaryGetterSetter birthday_aniversaryGetterSetter = new Birthday_AniversaryGetterSetter();
                                    if(birthdaydatefromjson =="true" )
                                    {


                                        birthday_aniversaryGetterSetter.setName(namefromjson);
                                        birthday_aniversaryGetterSetter.setDateofbirtday(birthdaydatefromjson);

                                    }
                                    else
                                    {

                                        birthday_aniversaryGetterSetter.setName(namefromjson);
                                        birthday_aniversaryGetterSetter.setDateofbirtday(dateofjoiningfromjson);

                                    }

                                    items.add(birthday_aniversaryGetterSetter);
                                   /* Log.i("holidayAndAttach", "hbirtdaydatafromjsonn: "+birtdaydatafromjson);*/


                          /*          JSONArray holidaylist=new JSONArray(holidaylist_json);
                                    for(int j=0;j<holidaylist.length();j++) {

                                        HolidayGetterSetter holidayGetterSetter = new HolidayGetterSetter();
                                        name = holidaylist.getJSONObject(j).get("Name").toString();
                                        date=holidaylist.getJSONObject(j).get("Date__c").toString();

                                        holidayGetterSetter.setDay(name);
                                        holidayGetterSetter.setDate(date);

                                        items.add(holidayGetterSetter);
                                        Log.i("holidayAndAttach", "holidayattachmentarray: " + name);
                                    }*/
                                }

                                mAdapter = new Birtday_AniversaryAdapter( items);

                                recyclerView.setHasFixedSize(true);

                                // vertical RecyclerView
                                // keep movie_list_row.xml width to `match_parent`
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

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
                               // progressDialog.dismiss();
                            }


                        }
                        else
                        {
                            //progressDialog.dismiss();
                            Toast.makeText(BirtdayAndAniversary.this, "something went wrong", Toast.LENGTH_SHORT).show();
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

                //progressDialog.dismiss();
                Intent callSplash = new Intent(BirtdayAndAniversary.this, InternetUnavailable.class);
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

                params.put("method", "Next7DaysBirthday");
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

    }
}
