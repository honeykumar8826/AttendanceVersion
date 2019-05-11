package com.attendnce.cloudanalogy.attendancev1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button punchIn, punchOut;
    TextView inTime, outTime, totalHours, userName, email, designation, employeeId, primaryContact, secondaryContact,navshowname,navshowpass;
    String nameValue, emailValue, inTimeValue, outTimeValue, totalTimeValue;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    ProgressDialog dialog;
    EditText oldpass,newpass,confirmpass;
    Button updatepass;
    String enteroldpassword,enternewpassword,enterconfirmpassword;

    SharedPreferences sharedPreferences;
    CircularImageView imageView;
    ImageView attendanceListButton;
    RelativeLayout relativeLayout;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        /*//swipe reffresh code*/
       /* pullToRefresh = findViewById(R.id.pullup);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
              //  pullToRefresh.setRefreshing(false);
            }
        });

        dialog = new ProgressDialog(UserActivity.this);
        dialog.setMessage("Please wait......");
        dialog.setCancelable(false);
        dialog.show();

        sharedPreferences = getSharedPreferences("SavedPhoto", Context.MODE_PRIVATE);
        String encoded = sharedPreferences.getString("image", null);
        if (encoded != null) {

            byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            CircularImageView imageView = findViewById(R.id.profileimage);
            imageView.setImageBitmap(decodedByte);

        }

       relativeLayout = findViewById(R.id.useractivity);

        punchIn = findViewById(R.id.punchInButton);
        punchOut = findViewById(R.id.punchOutButton);
        inTime = findViewById(R.id.inTimeTextView);
        outTime = findViewById(R.id.outTimeTextView);
        totalHours = findViewById(R.id.totalTimeTextView);
        userName = findViewById(R.id.userNameTextView);
        email = findViewById(R.id.emailTextView);
        imageView = findViewById(R.id.profileimage);
        designation = findViewById(R.id.employeeDesignation);
        employeeId = findViewById(R.id.employeeId);
        primaryContact = findViewById(R.id.primaryContact);
        secondaryContact = findViewById(R.id.secondaryContact);
        attendanceListButton = findViewById(R.id.attendanceList);

        attendanceListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callAttendanceList = new Intent(UserActivity.this, AttendanceList.class);
                startActivity(callAttendanceList);
                finish();
            }
        });

        checkData(getApplicationContext());

        punchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                punchInListner();
            }
        });
        punchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                punchOutListner();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoUpload = new Intent(UserActivity.this, PhotoUpload.class);
                startActivity(photoUpload);
                finish();
            }
        });
        updateAndroidSecurityProvider(this);
    }

  private void refreshData() {
        Intent intent=new Intent(UserActivity.this,UserActivity.class);
        startActivity(intent);
       // finish();
    }



    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {

            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    public void punchOutListner() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserActivity.this, R.style.MyDialogTheme);
        alertDialogBuilder.setMessage("Are you sure you want to punch out?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        final ProgressDialog progressdialogPunchOut = new ProgressDialog(UserActivity.this);
                        progressdialogPunchOut.setMessage("Please wait......");
                        progressdialogPunchOut.setCancelable(false);
                        progressdialogPunchOut.show();


                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest sr = new StringRequest(Request.Method.POST, "https://cloudanalogy.secure.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressdialogPunchOut.dismiss();

                                punchIn.setVisibility(View.VISIBLE);
                                punchOut.setVisibility(View.GONE);

                                try {
                                    JSONObject result = new JSONObject(response);
                                    if (result.get("errorMessage").toString() != "null") {


                                        Snackbar snackbar = Snackbar
                                                .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                        startActivity(intent);
                                        finish();


                                    } else {

                                        final Dialog dialogPunchOut = new Dialog(UserActivity.this);
                                        dialogPunchOut.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                                        dialogPunchOut.setContentView(R.layout.dialog_dark);
                                        dialogPunchOut.setCancelable(true);

                                        dialogPunchOut.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                            @Override
                                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                                // Prevent dialog close on back press button
                                                return keyCode == KeyEvent.KEYCODE_BACK;
                                            }
                                        });


                                        sharedPreferences = getSharedPreferences("SavedPhoto", Context.MODE_PRIVATE);
                                        String encoded = sharedPreferences.getString("image", null);
                                        Bitmap decodedByte = null;
                                        if (encoded != null) {

                                            byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
                                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                            ((CircleImageView) dialogPunchOut.findViewById(R.id.imageDialogProfile)).setImageBitmap(decodedByte);
                                        }

                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                        lp.copyFrom(dialogPunchOut.getWindow().getAttributes());
                                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                                        ((TextView) dialogPunchOut.findViewById(R.id.userNameDialog)).setText(nameValue);


                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM d yyyy hh:mm a");

                                        Date dateIn = dateTimeFormat.parse(result.get("punchInTime").toString());
                                        @SuppressLint("SimpleDateFormat") String timeIn = new SimpleDateFormat("hh:mm a").format(dateIn);

                                        ((TextView) dialogPunchOut.findViewById(R.id.inTimeDialogPunchOut)).setText(timeIn);

                                        Date dateOut = dateTimeFormat.parse(result.get("punchOutTime").toString());
                                        @SuppressLint("SimpleDateFormat") String timeOut = new SimpleDateFormat("hh:mm a").format(dateOut);

                                        ((TextView) dialogPunchOut.findViewById(R.id.outTimeDialogPunchOut)).setText(timeOut);

                                        ((ImageButton) dialogPunchOut.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialogPunchOut.dismiss();
                                                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                        ((AppCompatButton) dialogPunchOut.findViewById(R.id.yesPunchOutDialog)).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                dialogPunchOut.dismiss();
                                                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                        dialogPunchOut.show();
                                        dialogPunchOut.getWindow().setAttributes(lp);
                                    }

                                } catch (Exception e) {
                                    progressdialogPunchOut.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressdialogPunchOut.dismiss();
                                Intent callSplash = new Intent(UserActivity.this, InternetUnavailable.class);
                                startActivity(callSplash);
                                finish();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {

                                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                                String name = prefs.getString("name", null);
                                String userName = prefs.getString("username", null);
                                String password = prefs.getString("password", null);
                                String deviceId = prefs.getString("deviceid", null);

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", userName);
                                params.put("password", password);
                                params.put("deviceId", deviceId);
                                params.put("method", "punchOut");

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
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void punchInListner() {

        final ProgressDialog progressdialogPunchIn = new ProgressDialog(UserActivity.this);
        progressdialogPunchIn.setMessage("Please wait......");
        progressdialogPunchIn.setCancelable(false);
        progressdialogPunchIn.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://cloudanalogy.secure.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                punchIn.setVisibility(View.GONE);
                punchOut.setVisibility(View.VISIBLE);
                progressdialogPunchIn.dismiss();

                JSONObject result = null;
                try {
                    result = new JSONObject(response);
                } catch (Exception e) {

                }

                try {
                    if (result.get("hasError").toString() == "true") {


                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        punchIn.setVisibility(View.VISIBLE);
                        punchOut.setVisibility(View.GONE);

                    } else {

                        final Dialog dialogPunchIn = new Dialog(UserActivity.this);
                        dialogPunchIn.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialogPunchIn.setContentView(R.layout.dialog_info);
                        dialogPunchIn.setCancelable(true);

                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                        TextView intimedialog = dialogPunchIn.findViewById(R.id.IntimePunchInDialog);
                        try {

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM d yyyy hh:mm a");

                            Date dateIn = dateTimeFormat.parse(result.get("punchInTime").toString());
                            @SuppressLint("SimpleDateFormat") String timeIn = new SimpleDateFormat("hh:mm a").format(dateIn);
                            intimedialog.setText(String.format("In Time - %s", timeIn));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        ((AppCompatButton) dialogPunchIn.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogPunchIn.dismiss();
                                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                        dialogPunchIn.show();
                        dialogPunchIn.getWindow().setAttributes(lp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                progressdialogPunchIn.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialogPunchIn.dismiss();
                Intent callSplash = new Intent(UserActivity.this, InternetUnavailable.class);
                startActivity(callSplash);
                finish();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                String name = prefs.getString("name", null);
                String userName = prefs.getString("username", null);
                String password = prefs.getString("password", null);
                String deviceId = prefs.getString("deviceid", null);

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("deviceId", deviceId);
                params.put("method", "punchIn");

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




        public void checkData (Context context){

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.POST, "https://cloudanalogy.secure.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject result = new JSONObject(response);

                        if (result.get("userId").toString() != "null") {

                            if (result.get("hasError").toString() != "true") {


                                nameValue = result.get("name").toString();
                                inTimeValue = result.get("punchInTime").toString();
                                outTimeValue = result.get("punchOutTime").toString();
                                totalTimeValue = result.get("totalTime").toString();
                                secondaryContact.setText(result.get("secondaryPhone").toString());
                                primaryContact.setText(result.get("primaryPhone").toString());
                                designation.setText(result.get("designation").toString());
                                employeeId.setText(result.get("empId").toString());

                                try {
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM d yyyy hh:mm a",Locale.US);

                                    if (inTimeValue != "null") {
                                        Date date5 = dateTimeFormat.parse(inTimeValue);
                                        @SuppressLint("SimpleDateFormat") String time5 = new SimpleDateFormat("hh:mm a").format(date5);
                                        inTime.setText(time5);
                                        punchIn.setVisibility(View.GONE);
                                        punchOut.setVisibility(View.VISIBLE);
                                    }

                                    if (outTimeValue != "null") {

                                        punchIn.setVisibility(View.VISIBLE);
                                        punchOut.setVisibility(View.GONE);
                                        Date date5 = dateTimeFormat.parse(outTimeValue);
                                        @SuppressLint("SimpleDateFormat") String time5 = new SimpleDateFormat("hh:mm a").format(date5);
                                        outTime.setText(time5);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(UserActivity.this, "Exception", Toast.LENGTH_SHORT).show();

                                }

                                if (inTimeValue != "null") {
                                    totalHours.setText(totalTimeValue);
                                }

                                userName.setText(nameValue);
                                email.setText(emailValue);
                                dialog.dismiss();

                            } else {

                                if (result.get("errorMessage").equals("You are already punched in.")) {

                                    nameValue = result.get("name").toString();
                                    inTimeValue = result.get("punchInTime").toString();
                                    outTimeValue = result.get("punchOutTime").toString();
                                    totalTimeValue = result.get("totalTime").toString();
                                    secondaryContact.setText(result.get("secondaryPhone").toString());
                                    primaryContact.setText(result.get("primaryPhone").toString());
                                    designation.setText(result.get("designation").toString());
                                    employeeId.setText(result.get("empId").toString());


                                    try {
                                        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy hh:mm a", Locale.US);

                                        if (inTimeValue != "null") {
                                            Date date5=   formatter.parse(inTimeValue);

                                            String time5= new SimpleDateFormat("hh:mm a").format(date5);
                                            inTime.setText(time5);
                                            punchIn.setVisibility(View.GONE);
                                            punchOut.setVisibility(View.VISIBLE);
                                        }

                                        if (outTimeValue != "null") {
                                            Date date5=   formatter.parse(outTimeValue);

                                            String time51= new SimpleDateFormat("hh:mm a").format(date5);

                                            punchIn.setVisibility(View.VISIBLE);
                                            punchOut.setVisibility(View.GONE);
                                            outTime.setText(time51);
                                        }
                                    } catch (Exception e) {


                                        Toast.makeText(UserActivity.this, "Exception...", Toast.LENGTH_SHORT).show();
                                    }

                                    if (totalTimeValue != "null") {
                                        totalHours.setText(totalTimeValue);
                                    }

                                    userName.setText(nameValue);
                                    email.setText(emailValue);
                                    dialog.dismiss();
                                } else {

                                    Log.i("TAG", "has error true not already punched In");
                                    dialog.dismiss();
                                    punchOut.setVisibility(View.INVISIBLE);
                                    punchIn.setVisibility(View.INVISIBLE);


                                    Snackbar snackbar = Snackbar
                                            .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                            }
                        } else {

                            dialog.dismiss();
                            punchOut.setVisibility(View.INVISIBLE);
                            punchIn.setVisibility(View.INVISIBLE);


                            Snackbar snackbar = Snackbar
                                    .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    updateAndroidSecurityProvider(UserActivity.this);
                    dialog.dismiss();
                    Intent callSplash = new Intent(UserActivity.this, InternetUnavailable.class);
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

                    emailValue = userName;

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", userName);
                    params.put("password", password);
                    params.put("deviceId", deviceId);
                    params.put("method", "login");

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

        @Override
        protected void onPause () {
            super.onPause();
            finish();
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}

*/
        SharedPreferences prefs1 = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String name_header = prefs1.getString("name", null);
        String userName_header = prefs1.getString("username", null);
        //navigation drawer code
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(UserActivity.this );
        navigationView.setItemIconTintList(null);
        //navigation header code is here
        View header = navigationView.getHeaderView(0);
        navshowname = (TextView)header.findViewById(R.id.userName);
        navshowname.setText(name_header);
        navshowpass=(TextView)header.findViewById(R.id.userEmail);

        navshowpass.setText(userName_header);


        // for nineteen k liye

        //


        /*//swipe reffresh code*/
        pullToRefresh = findViewById(R.id.pullup);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                //  pullToRefresh.setRefreshing(false);
            }
        });

        dialog = new ProgressDialog(UserActivity.this);
        dialog.setMessage("Please wait......");
        dialog.setCancelable(false);
        dialog.show();

        sharedPreferences = getSharedPreferences("SavedPhoto", Context.MODE_PRIVATE);
        String encoded = sharedPreferences.getString("image", null);
        if (encoded != null) {

            byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            CircularImageView imageView = findViewById(R.id.profileimage);
            imageView.setImageBitmap(decodedByte);

        }

        relativeLayout = findViewById(R.id.useractivity);

        punchIn = findViewById(R.id.punchInButton);
        punchOut = findViewById(R.id.punchOutButton);
        inTime = findViewById(R.id.inTimeTextView);
        outTime = findViewById(R.id.outTimeTextView);
        totalHours = findViewById(R.id.totalTimeTextView);
        userName = findViewById(R.id.userNameTextView);
        email = findViewById(R.id.emailTextView);
        imageView = findViewById(R.id.profileimage);
        designation = findViewById(R.id.employeeDesignation);
        employeeId = findViewById(R.id.employeeId);
        primaryContact = findViewById(R.id.primaryContact);
        secondaryContact = findViewById(R.id.secondaryContact);
        attendanceListButton = findViewById(R.id.attendanceList);

        attendanceListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callAttendanceList = new Intent(UserActivity.this, AttendanceList.class);
                startActivity(callAttendanceList);
                finish();
            }
        });

        checkData(getApplicationContext());

        punchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                punchInListner();
            }
        });
        punchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                punchOutListner();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoUpload = new Intent(UserActivity.this, PhotoUpload.class);
                startActivity(photoUpload);
                finish();
            }
        });
        updateAndroidSecurityProvider(this);
    }

    private void refreshData() {
        Intent intent=new Intent(UserActivity.this,UserActivity.class);
        startActivity(intent);
        // finish();
    }



    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {

            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    public void punchOutListner() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserActivity.this, R.style.MyDialogTheme);
        alertDialogBuilder.setMessage("Are you sure you want to punch out?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        final ProgressDialog progressdialogPunchOut = new ProgressDialog(UserActivity.this);
                        progressdialogPunchOut.setMessage("Please wait......");
                        progressdialogPunchOut.setCancelable(false);
                        progressdialogPunchOut.show();


                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest sr = new StringRequest(Request.Method.POST, "https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressdialogPunchOut.dismiss();

                                punchIn.setVisibility(View.VISIBLE);
                                punchOut.setVisibility(View.GONE);

                                try {
                                    JSONObject result = new JSONObject(response);
                                    if (result.get("errorMessage").toString() != "null") {


                                        Snackbar snackbar = Snackbar
                                                .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                        startActivity(intent);
                                        finish();


                                    } else {

                                        final Dialog dialogPunchOut = new Dialog(UserActivity.this);
                                        dialogPunchOut.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                                        dialogPunchOut.setContentView(R.layout.dialog_dark);
                                        dialogPunchOut.setCancelable(true);

                                        dialogPunchOut.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                            @Override
                                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                                // Prevent dialog close on back press button
                                                return keyCode == KeyEvent.KEYCODE_BACK;
                                            }
                                        });


                                        sharedPreferences = getSharedPreferences("SavedPhoto", Context.MODE_PRIVATE);
                                        String encoded = sharedPreferences.getString("image", null);
                                        Bitmap decodedByte = null;
                                        if (encoded != null) {

                                            byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
                                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                            ((CircleImageView) dialogPunchOut.findViewById(R.id.imageDialogProfile)).setImageBitmap(decodedByte);
                                        }

                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                        lp.copyFrom(dialogPunchOut.getWindow().getAttributes());
                                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                                        ((TextView) dialogPunchOut.findViewById(R.id.userNameDialog)).setText(nameValue);


                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM d yyyy hh:mm a");

                                        Date dateIn = dateTimeFormat.parse(result.get("punchInTime").toString());
                                        @SuppressLint("SimpleDateFormat") String timeIn = new SimpleDateFormat("hh:mm a").format(dateIn);

                                        ((TextView) dialogPunchOut.findViewById(R.id.inTimeDialogPunchOut)).setText(timeIn);

                                        Date dateOut = dateTimeFormat.parse(result.get("punchOutTime").toString());
                                        @SuppressLint("SimpleDateFormat") String timeOut = new SimpleDateFormat("hh:mm a").format(dateOut);

                                        ((TextView) dialogPunchOut.findViewById(R.id.outTimeDialogPunchOut)).setText(timeOut);

                                        ((ImageButton) dialogPunchOut.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialogPunchOut.dismiss();
                                                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                        ((AppCompatButton) dialogPunchOut.findViewById(R.id.yesPunchOutDialog)).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                dialogPunchOut.dismiss();
                                                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                        dialogPunchOut.show();
                                        dialogPunchOut.getWindow().setAttributes(lp);
                                    }

                                } catch (Exception e) {
                                    progressdialogPunchOut.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressdialogPunchOut.dismiss();
                                Intent callSplash = new Intent(UserActivity.this, InternetUnavailable.class);
                                startActivity(callSplash);
                                finish();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {

                                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                                String name = prefs.getString("name", null);
                                String userName = prefs.getString("username", null);
                                String password = prefs.getString("password", null);
                                String deviceId = prefs.getString("deviceid", null);

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", userName);
                                params.put("password", password);
                                params.put("deviceId", deviceId);
                                params.put("method", "punchOut");

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
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void punchInListner() {

        final ProgressDialog progressdialogPunchIn = new ProgressDialog(UserActivity.this);
        progressdialogPunchIn.setMessage("Please wait......");
        progressdialogPunchIn.setCancelable(false);
        progressdialogPunchIn.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://cloudanalogy.secure.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                punchIn.setVisibility(View.GONE);
                punchOut.setVisibility(View.VISIBLE);
                progressdialogPunchIn.dismiss();

                JSONObject result = null;
                try {
                    result = new JSONObject(response);
                } catch (Exception e) {

                }

                try {
                    if (result.get("hasError").toString() == "true") {


                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        punchIn.setVisibility(View.VISIBLE);
                        punchOut.setVisibility(View.GONE);

                    } else {

                        final Dialog dialogPunchIn = new Dialog(UserActivity.this);
                        dialogPunchIn.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialogPunchIn.setContentView(R.layout.dialog_info);
                        dialogPunchIn.setCancelable(true);

                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                        TextView intimedialog = dialogPunchIn.findViewById(R.id.IntimePunchInDialog);
                        try {

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM d yyyy hh:mm a");

                            Date dateIn = dateTimeFormat.parse(result.get("punchInTime").toString());
                            @SuppressLint("SimpleDateFormat") String timeIn = new SimpleDateFormat("hh:mm a").format(dateIn);
                            intimedialog.setText(String.format("In Time - %s", timeIn));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        ((AppCompatButton) dialogPunchIn.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogPunchIn.dismiss();
                                Intent intent = new Intent(UserActivity.this, UserActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                        dialogPunchIn.show();
                        dialogPunchIn.getWindow().setAttributes(lp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                progressdialogPunchIn.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialogPunchIn.dismiss();
                Intent callSplash = new Intent(UserActivity.this, InternetUnavailable.class);
                startActivity(callSplash);
                finish();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                String name = prefs.getString("name", null);
                String userName = prefs.getString("username", null);
                String password = prefs.getString("password", null);
                String deviceId = prefs.getString("deviceid", null);

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("deviceId", deviceId);
                params.put("method", "punchIn");

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




    public void checkData (Context context){
       // https://cloudanalogy.secure.force.com/attdapp/services/apexrest/Attendance
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject result = new JSONObject(response);

                    if (result.get("userId").toString() != "null") {

                        if (result.get("hasError").toString() != "true") {


                            nameValue = result.get("name").toString();
                            inTimeValue = result.get("punchInTime").toString();
                            outTimeValue = result.get("punchOutTime").toString();
                            totalTimeValue = result.get("totalTime").toString();
                            secondaryContact.setText(result.get("secondaryPhone").toString());
                            primaryContact.setText(result.get("primaryPhone").toString());
                            designation.setText(result.get("designation").toString());
                            employeeId.setText(result.get("empId").toString());

                            try {
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM d yyyy hh:mm a",Locale.US);

                                if (inTimeValue != "null") {
                                    Date date5 = dateTimeFormat.parse(inTimeValue);
                                    @SuppressLint("SimpleDateFormat") String time5 = new SimpleDateFormat("hh:mm a").format(date5);
                                    inTime.setText(time5);
                                    punchIn.setVisibility(View.GONE);
                                    punchOut.setVisibility(View.VISIBLE);
                                }

                                if (outTimeValue != "null") {

                                    punchIn.setVisibility(View.VISIBLE);
                                    punchOut.setVisibility(View.GONE);
                                    Date date5 = dateTimeFormat.parse(outTimeValue);
                                    @SuppressLint("SimpleDateFormat") String time5 = new SimpleDateFormat("hh:mm a").format(date5);
                                    outTime.setText(time5);
                                }
                            } catch (Exception e) {
                                Toast.makeText(UserActivity.this, "Exception", Toast.LENGTH_SHORT).show();

                            }

                            if (inTimeValue != "null") {
                                totalHours.setText(totalTimeValue);
                            }

                            userName.setText(nameValue);
                            email.setText(emailValue);
                            dialog.dismiss();

                        } else {

                            if (result.get("errorMessage").equals("You are already punched in.")) {

                                nameValue = result.get("name").toString();
                                inTimeValue = result.get("punchInTime").toString();
                                outTimeValue = result.get("punchOutTime").toString();
                                totalTimeValue = result.get("totalTime").toString();
                                secondaryContact.setText(result.get("secondaryPhone").toString());
                                primaryContact.setText(result.get("primaryPhone").toString());
                                designation.setText(result.get("designation").toString());
                                employeeId.setText(result.get("empId").toString());


                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy hh:mm a", Locale.US);

                                    if (inTimeValue != "null") {
                                        Date date5=   formatter.parse(inTimeValue);

                                        String time5= new SimpleDateFormat("hh:mm a").format(date5);
                                        inTime.setText(time5);
                                        punchIn.setVisibility(View.GONE);
                                        punchOut.setVisibility(View.VISIBLE);
                                    }

                                    if (outTimeValue != "null") {
                                        Date date5=   formatter.parse(outTimeValue);

                                        String time51= new SimpleDateFormat("hh:mm a").format(date5);

                                        punchIn.setVisibility(View.VISIBLE);
                                        punchOut.setVisibility(View.GONE);
                                        outTime.setText(time51);
                                    }
                                } catch (Exception e) {


                                    Toast.makeText(UserActivity.this, "Exception...", Toast.LENGTH_SHORT).show();
                                }

                                if (totalTimeValue != "null") {
                                    totalHours.setText(totalTimeValue);
                                }

                                userName.setText(nameValue);
                                email.setText(emailValue);
                                dialog.dismiss();
                            } else {

                                Log.i("TAG", "has error true not already punched In");
                                dialog.dismiss();
                                punchOut.setVisibility(View.INVISIBLE);
                                punchIn.setVisibility(View.INVISIBLE);


                                Snackbar snackbar = Snackbar
                                        .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        }
                    } else {

                        dialog.dismiss();
                        punchOut.setVisibility(View.INVISIBLE);
                        punchIn.setVisibility(View.INVISIBLE);


                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateAndroidSecurityProvider(UserActivity.this);
                dialog.dismiss();
                Intent callSplash = new Intent(UserActivity.this, InternetUnavailable.class);
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

                emailValue = userName;

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("deviceId", deviceId);
                params.put("method", "login");

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

 /*   @Override
    protected void onPause () {
        super.onPause();
        finish();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
        int id = Item.getItemId();

        if (id==R.id.everything_Leave) {
            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserActivity.this, LeaveTab.class);
            startActivity(intent);

        }
  /*      else if(id==R.id.fragement1)
        {
              Fragement1 fragement1=new Fragement1();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.first, fragement1)
                    .commit();

        }*/
        else if(id==R.id.holidays)
        {

            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserActivity.this, HolidaysActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.company_rule)
        {

            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserActivity.this, CompanyRule.class);
            startActivity(intent);
        }
        else if(id==R.id.birtday_aniversary)
        {
            Intent intent = new Intent(UserActivity.this, BirtdayAndAniversary.class);
            startActivity(intent);
            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(UserActivity.this, CompanyRule.class);
            startActivity(intent);*/
        }


        else if(id==R.id.myteam)
        {
            Intent intent = new Intent(UserActivity.this, MyTeam.class);
            startActivity(intent);
            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(UserActivity.this, CompanyRule.class);
            startActivity(intent);*/
        }

        else if(id==R.id.testing)
        {
            Intent intent = new Intent(UserActivity.this, TestingActivity.class);
            startActivity(intent);
            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(UserActivity.this, CompanyRule.class);
            startActivity(intent);*/
        }
   /*     else if(id==R.id.applyleave)
        {
            Intent intent = new Intent(UserActivity.this, ApplyLeaveActivity.class);
            startActivity(intent);
            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
           *//* Intent intent = new Intent(UserActivity.this, CompanyRule.class);
            startActivity(intent);*//*
        }*/
 /*       else if(id==R.id.birthday_dateofjoining)
        {

            //  Toast.makeText(this, ""+id , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserActivity.this, CompanyRule.class);
            startActivity(intent);
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                clearalldata();

                return true;
            case R.id.Change_Password:
                changepassword();
              //  Toast.makeText(this, "b", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changepassword() {


        final Dialog dialog = new Dialog(UserActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialog.setContentView(R.layout.changepasswordpopup);
        //getting the id of the field
        oldpass = dialog.findViewById(R.id.old_pass);


        newpass=dialog.findViewById(R.id.new_pass);
        confirmpass=dialog.findViewById(R.id.confirm_pass);
        updatepass=dialog.findViewById(R.id.update_pass);
        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteroldpassword= oldpass.getText().toString();
                enternewpassword=newpass.getText().toString();
                enterconfirmpassword=confirmpass.getText().toString();
                if(enternewpassword.equals(enterconfirmpassword))
                {
                    // response from the method
            /*        String  _Url="https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance";
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
                            Intent callSplash = new Intent(UserActivity.this, InternetUnavailable.class);
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
                *//*            String strDateFormat = "dd-MM-yyy HH:mm:ss z";
                            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                            // dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
                      *//**//*   fromdateconverted = new SimpleDateFormat("yyyy-MM-dd").format(from);
                       todateconverted = new SimpleDateFormat("yyyy-MM-dd").format(To);*//**//*
                            Log.i("todateconverted", "todateconverted: "+todateconverted);*//*
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
                            params.put("subject", enteroldpassword);
                            params.put("applicationBody", enternewpassword);

                            params.put("method", "leave");
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
                    queue.add(sr);*/

                    //
                    Toast.makeText(UserActivity.this, "password changed successfully"+""+enternewpassword+""+enterconfirmpassword, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(UserActivity.this, "enter confirm and new password same", Toast.LENGTH_LONG).show();
                }




               // Toast.makeText(UserActivity.this, "password changed successfully"+oldpass+""+enternewpassword+""+enterconfirmpassword, Toast.LENGTH_LONG).show();
            }
        });
        //getting the id of the field end
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.findViewById(R.id.relative1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void clearalldata() {
        SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        Intent intent=new Intent(UserActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}

