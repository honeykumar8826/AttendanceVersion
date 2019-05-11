package com.attendnce.cloudanalogy.attendancev1;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class LeaveSummaryFragement extends Fragment {
    TextView totalleave,holiday,leavetaken,remainingleave;
    String stotalleave,sholiday,sleavetaken,sremainingleave;
    private  float[] yData = {6,3,2,1};
    private  String[] xData = {"Total","< 9 hours","Planned","Un planned"};
    private  static  String TAG = "MainActivit";
    PieChart pieChart;
    public LeaveSummaryFragement() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_leave_summary_fragement, container, false);
        //
        totalleave =  view.findViewById(R.id.total_leave);
        holiday =  view.findViewById(R.id.holiday);
        leavetaken =  view.findViewById(R.id.leave_taken);
        remainingleave =  view.findViewById(R.id.remaining_leave);
        setdataforleavesummary();
        //
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                dialog.setContentView(R.layout.filterpopup);
                TextView currentMonth, last30days, last3month, last6month,lastoneyear;
                currentMonth =  dialog.findViewById(R.id.current_month);
                last30days =  dialog.findViewById(R.id.last_thirtyDays);
                last3month =  dialog.findViewById(R.id.last_three_month);
                last6month =  dialog.findViewById(R.id.last_sixMonth);
                lastoneyear = dialog.findViewById(R.id.lastOne_year);
                dialog.show();

                dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                currentMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });


                last30days.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                last3month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                last6month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                lastoneyear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        /////////////////////////////////////////////////////////////////////
        /*PieChart pieChart = (PieChart) view.findViewById(R.id.piechart);
       // pieChart.setUsePercentValues(false);
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(5, 0));
        yvalues.add(new Entry(1, 1));
        yvalues.add(new Entry(2, 2));
        yvalues.add(new Entry(1, 3));
        // yvalues.add(new Entry(23, 4));
        // yvalues.add(new Entry(17, 5));

        PieDataSet dataSet = new PieDataSet(yvalues, "");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Total ");
        xVals.add("Less than nine ");
        xVals.add("Planned ");
        xVals.add("Unplanned ");
        // xVals.add("May");
        // xVals.add("June");
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25);
        pieChart.setHoleRadius(25);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13);
        data.setValueTextColor(Color.DKGRAY);
        //pieChart.setOnChartValueSelectedListener(getContext());
        pieChart.animateXY(1400, 1400);
        return view;*/
        /////////////////////////////////////////////////////////////
        pieChart = (PieChart) view.findViewById(R.id.piechart);
        //pieChart.setDescription(" Sales by employee In thousands $");
        pieChart.getDescription().setText("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(13);
        pieChart.setDrawEntryLabels(true);
        pieChart.animateXY(1400, 1400);
        addDataSet();


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: value selected from chart");
                Log.d(TAG, "onValueSelected: "+ e);
                Log.d(TAG, "onValueSelected: "+ h.toString());
                Toast.makeText(getContext(), ""+e.getY(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return view;
    }

    private void setdataforleavesummary() {

        String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
        //rest request
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, _Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response !=null)
                {
                    JSONObject result = null;
                    try {

                        result = new JSONObject(response);
                        if(result !=null)
                        {
                            if (result.get("userId").toString() != "null") {

                                if (result.get("hasError").toString() != "true") {

                                    String yearlyLeaveSummaryWrapper = result.get("yearlyLeaveSummaryWrapperList").toString();

                                    JSONArray yearlyLeaveSummaryarray = new JSONArray(yearlyLeaveSummaryWrapper);
                                /*    Log.i("yearlyLeaveSummaryarray", "yearlyLeaveSummaryarray: "+yearlyLeaveSummaryarray);*/
                                    sleavetaken = yearlyLeaveSummaryarray.getJSONObject(0).getString("totalPlannedLeave");
                                   /* Log.i("sleavetaken", "sleavetaken: "+sleavetaken);*/
                                    leavetaken.setText(""+sleavetaken);
                                    stotalleave = yearlyLeaveSummaryarray.getJSONObject(0).getString("totalLeave");
                                    /* Log.i("sleavetaken", "sleavetaken: "+sleavetaken);*/
                                    totalleave.setText(""+stotalleave);
                                    sholiday = yearlyLeaveSummaryarray.getJSONObject(0).getString("totalHolidays");
                                    /* Log.i("sleavetaken", "sleavetaken: "+sleavetaken);*/
                                    holiday.setText(""+sholiday);
                                    sremainingleave = yearlyLeaveSummaryarray.getJSONObject(0).getString("remainingLeave");
                                    /* Log.i("sleavetaken", "sleavetaken: "+sleavetaken);*/
                                    remainingleave.setText(""+sremainingleave);

                                }
                            }

                        }



                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.i("holidayAndAttach", "onResponse check: " + result);





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
                params.put("method", "yearlyLeaveSummary");
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

    private void addDataSet() {
        Log.d(TAG, "addDataSet: started");

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0 ; i <yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],xData[i % xData.length]));

        }
        for(int i = 0 ; i <xData.length;i++){
            xEntrys.add(xData[i]);

        }
        //create the DATA
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"Leaves");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        //add colors to dataSet
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.RED);
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(1);
        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setTextColor(Color.BLACK);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
    // Inflate the layout for this fragment

}




