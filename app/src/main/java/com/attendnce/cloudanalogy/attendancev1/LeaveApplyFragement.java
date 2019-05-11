package com.attendnce.cloudanalogy.attendancev1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class LeaveApplyFragement extends Fragment {
    Spinner spinner;
    List<String> list;
    Calendar myCalendar;
    EditText From,To,body,subject;
    Button submit;
    ImageView backbt;
    String to,from,body_message,subject_leave,leave_type,fromdateconverted,todateconverted,currentdate;
    ProgressDialog progressDialog;
    Date strDateto = null;
    Date enterDateto = null;
    Date strDatefrom = null;
    Date enterDatefrom = null;

    public LeaveApplyFragement() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      //  View view =  inflater.inflate(R.layout.fragment_leave_apply_fragement, container, false);

    progressDialog=new ProgressDialog(getContext());
       /**//*     progressDialog.setTitle("Login proceed");*//**/
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        View view =  inflater.inflate(R.layout.fragment_leave_apply_fragement, container, false);

        myCalendar = Calendar.getInstance();

        From= (EditText) view.findViewById(R.id.Birthday);
        body= (EditText) view.findViewById(R.id.body_text);
        submit=view.findViewById(R.id.submitbtn);
        subject=view.findViewById(R.id.subject_leave);

        body.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
   body.setScroller(new Scroller(getContext()));
        body.setMaxLines(10);
        body.setVerticalScrollBarEnabled(true);
        body.setMovementMethod(new ScrollingMovementMethod());

        To= (EditText) view.findViewById(R.id.date_to);
        // for from
        final DatePickerDialog.OnDateSetListener datefrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }

        };

        //for to
        final DatePickerDialog.OnDateSetListener dateto = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabe2();


            }

        };

      //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis() - 1000);



        From.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(getContext(), datefrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), dateto, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                to=To.getText().toString();
                from=From.getText().toString();
                body_message= body.getText().toString();
                subject_leave=subject.getText().toString();
                Log.i("onClick", "onClick: "+to+""+from);


                if(leave_type=="Half Day" || leave_type=="Full Day" )
                {
                    Log.i("onClick", "onClick+++++++++: ");

                    //

                    if(!from.isEmpty() && !body_message.isEmpty() && !subject_leave.isEmpty())
                    {
                        // Toast.makeText(ApplyLeaveActivity.this, "Your record successfully saved"+leave_type, Toast.LENGTH_LONG).show();

                        String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
                        //rest request
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response !=null)
                                {
                                    progressDialog.dismiss();
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
                                //finish();

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
                                Log.i("todateconverted", "todateconverted: "+todateconverted);
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
                                params.put("subject", subject_leave);
                                params.put("applicationBody", body_message);
                                params.put("leavetype", leave_type);
                                params.put("fromDate", from);
                                params.put("toDate", to);
                                params.put("method", "leave");
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
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Enter all the field"+body_message, Toast.LENGTH_LONG).show();
                    }

                    //



                }



                else

                {

                    if(!to.isEmpty() && !from.isEmpty() && !body_message.isEmpty() && !subject_leave.isEmpty())
                    {
                        // Toast.makeText(ApplyLeaveActivity.this, "Your record successfully saved"+leave_type, Toast.LENGTH_LONG).show();

                        String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
                        //rest request
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response !=null)
                                {
                                    progressDialog.dismiss();
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
                                //finish();

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
                                Log.i("todateconverted", "todateconverted: "+todateconverted);
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
                                params.put("subject", subject_leave);
                                params.put("applicationBody", body_message);
                                params.put("leavetype", leave_type);
                                params.put("fromDate", from);
                                params.put("toDate", to);
                                params.put("method", "leave");
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
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Enter all the field"+body_message, Toast.LENGTH_LONG).show();
                    }
                }



                //   https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance
                // http://vishalt-cloudanalogy.cs60.force.com/attdapp




            }
        });
//spinner code here
        list=new ArrayList<String>();
        final String[] personNames = {"Select any one","Half Day", "Full Day", "Multiple Days"};
        spinner = view.findViewById(R.id.mySpinner);
        if (spinner != null) {

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, personNames);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    leave_type=spinner.getSelectedItem().toString();
                    if(spinner.getSelectedItem().toString()=="Half Day")
                    {
                        To.setVisibility(View.INVISIBLE);
                    }
                    if(spinner.getSelectedItem().toString()=="Full Day")
                    {
                        To.setVisibility(View.INVISIBLE);
                    }
                    if(spinner.getSelectedItem().toString()=="Multiple Days")
                    {
                        To.setVisibility(View.VISIBLE);
                    }

                    list.add(spinner.getSelectedItem().toString());





                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(getContext(), "further process select any one", Toast.LENGTH_SHORT).show();
                }
            });

        }
        // Inflate the layout for this fragment
        return view;
    }

   private void updateLabel() {
       Date d = new Date();
       currentdate = android.text.format.DateFormat.format(" yyyy-MM-dd ", d.getTime()).toString();
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //
       String enteredDate= sdf.format(myCalendar.getTime());
       try {
             strDateto = sdf.parse(currentdate);

             enterDateto= sdf.parse(enteredDate);
           Log.i("strDateto", "strDateto: "+ enterDateto.compareTo(strDateto));
           if (enterDateto.compareTo(strDateto)<0) {
              // Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
               From.setText("");
               Toast.makeText(getContext(), "Entered the corect date", Toast.LENGTH_SHORT).show();


           }
           else
           {

               From.setText(sdf.format(myCalendar.getTime()));
           }
       }
       catch (ParseException e) {
           e.printStackTrace();
       }

       //
    /*    Date date= myCalendar.getTime();
        Log.i("updateLabel", "updateLabel: "+date.toString());
        From.setText(sdf.format(myCalendar.getTime()));*/

    }
    private void updateLabe2() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String enteredDate= sdf.format(myCalendar.getTime());
        try {
            strDatefrom = sdf.parse(currentdate);
            enterDatefrom= sdf.parse(enteredDate);
            if (enterDatefrom.compareTo(strDatefrom)<0) {
               // Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                To.setText("");
                Toast.makeText(getContext(), "Entered the corect date", Toast.LENGTH_SHORT).show();

            }
            else
            {
                To.setText(sdf.format(myCalendar.getTime()));

            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }



    }


}