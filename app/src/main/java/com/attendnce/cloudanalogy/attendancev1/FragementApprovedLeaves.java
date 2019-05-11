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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragementApprovedLeaves extends Fragment {

    RecyclerView approvedleaverecycler;
    public FragementApprovedLeaves() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_approved_leaves, container, false);
        approvedleaverecycler=view.findViewById(R.id.recycler_approvedleave);
        approvedleaverecycler.clearFocus();
        approvedleaverecycler.removeAllViews();
        approvedleaverecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        approvedleaverecycler.setItemAnimator(new DefaultItemAnimator());
        AdapterApprovedLeave adapter = new AdapterApprovedLeave(view.getContext(), setAttendance());
        approvedleaverecycler.setAdapter(adapter);
        return view;
    }
    private ArrayList<ModelApprovedLeave> setAttendance()
    {
        ArrayList<ModelApprovedLeave> attendance=new ArrayList<>();
        for(int i=0;i<4;i++){
            ModelApprovedLeave modelAttendanceMyTeam = new ModelApprovedLeave();
            {
                String name ="Leave"+i;
                if (!name.equals("null")) {
                    modelAttendanceMyTeam.setUser_name(name);
                }
                else {
                    modelAttendanceMyTeam.setUser_name("");}
                String email="2"+i;
                if(!email.equals("null")) {
                    modelAttendanceMyTeam.setCurrentMonthLeave(email);
                }else {
                    modelAttendanceMyTeam.setCurrentMonthLeave("");}
                String phone= "23"+i;
                if (!phone.equals("null")) {
                    modelAttendanceMyTeam.setCurrentMonthhalfday(phone);
                }else {
                    modelAttendanceMyTeam.setCurrentMonthhalfday("");}
                String id= "232"+i;

                if(!id.equals("null"))
                {
                    modelAttendanceMyTeam.setDateofJoining(id);}else {
                    modelAttendanceMyTeam.setDateofJoining("");}
                attendance.add(modelAttendanceMyTeam);
            }

        }
        return attendance;
    }


}