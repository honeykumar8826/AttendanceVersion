package com.attendnce.cloudanalogy.attendancev1;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    EditText editText_username, editText_password;
    Button loginButton;
    ProgressDialog dialog;

    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions= new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
           // Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions(getApplicationContext());
        updateAndroidSecurityProvider(this);
    }
    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {

            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }
    public boolean checkPermissions(Context context) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(context,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(LoginActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        else
        {
            setContentView(R.layout.activity_login);
            initComponent();
        }
        return true;
    }

    private void initComponent() {
        editText_username=findViewById(R.id.userNameEditBox);
        editText_password=findViewById(R.id.passwordEditBox);
        loginButton=findViewById(R.id.loginButton);
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait......");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            @Deprecated
            public void onClick(View view) {
                loginButtonListner();
            }
        });
    }

    public void loginButtonListner(){

        final String androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if(!editText_username.getText().toString().isEmpty() && !editText_password.getText().toString().isEmpty()){

            dialog.show();
           // ttps://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance
          //  https://cloudanalogy.secure.force.com/attdapp/services/apexrest/Attendance
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest sr = new StringRequest(Request.Method.POST, "https://vishalt-cloudanalogy.cs60.force.com/attdapp/services/apexrest/Attendance", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject result=new JSONObject(response);

                        if(result.get("userId").toString()!="null"){

                            if(!result.get("errorMessage").equals("Device Id already registered on another device.")) {

                                if(result.get("errorMessage").equals("You are already punched in.")){

                                    View layout = getLayoutInflater().inflate(R.layout.toast_custom, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                                    TextView text = (TextView) layout.findViewById(R.id.text);
                                    text.setTextColor(Color.WHITE);
                                    text.setText("Login Successfully");
                                    CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
                                    lyt_card.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(layout);
                                    toast.show();

                                    SharedPreferences.Editor editor = getSharedPreferences("LoginDetails", MODE_PRIVATE).edit();
                                    editor.putString("name", result.get("name").toString());
                                    editor.putString("username", editText_username.getText().toString());
                                    editor.putString("password", editText_password.getText().toString());
                                    editor.putString("deviceid", androidDeviceId);
                                    editor.apply();

                                    Intent userActivity=new Intent(LoginActivity.this,UserActivity.class);
                                    startActivity(userActivity);
                                    finish();
                                    return;
                                }
                                if(result.get("errorMessage").toString().equals("null")){

                                    View layout = getLayoutInflater().inflate(R.layout.toast_custom, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                                    TextView text = (TextView) layout.findViewById(R.id.text);
                                    text.setTextColor(Color.WHITE);
                                    text.setText("Login Successfully");
                                    CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
                                    lyt_card.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(layout);
                                    toast.show();

                                    SharedPreferences.Editor editor = getSharedPreferences("LoginDetails", MODE_PRIVATE).edit();
                                    editor.putString("name", result.get("name").toString());
                                    editor.putString("username", editText_username.getText().toString());
                                    editor.putString("password", editText_password.getText().toString());
                                    editor.putString("deviceid", androidDeviceId);
                                    editor.apply();

                                    Intent userActivity=new Intent(LoginActivity.this,UserActivity.class);
                                    startActivity(userActivity);
                                    finish();
                                    return;
                                }


                                relativeLayout=findViewById(R.id.loginPage);

                                Snackbar snackbar = Snackbar
                                        .make(relativeLayout,result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);

                                snackbar.show();


                            }else{

                                relativeLayout=findViewById(R.id.loginPage);

                                Snackbar snackbar = Snackbar
                                        .make(relativeLayout,result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);

                                snackbar.show();
                            }

                        }else{

                            relativeLayout=findViewById(R.id.loginPage);

                            Snackbar snackbar = Snackbar
                                    .make(relativeLayout,result.get("errorMessage").toString(), Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Intent callSplash=new Intent(LoginActivity.this,InternetUnavailable.class);
                    startActivity(callSplash);
                    finish();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", editText_username.getText().toString());
                    params.put("password", editText_password.getText().toString());
                    params.put("deviceId", androidDeviceId);
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
        }else{


            relativeLayout=findViewById(R.id.loginPage);

            Snackbar snackbar = Snackbar
                    .make(relativeLayout,"UserName and Password must required", Snackbar.LENGTH_LONG);

            snackbar.show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                //perms.put(Manifest.permission.ACCESS_WIFI_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                       // && perms.get(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    setContentView(R.layout.activity_login);
                    initComponent();

                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
                    alertDialogBuilder.setMessage("Are you sure ?");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    System.exit(0);
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkPermissions(getApplicationContext());
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                }
                return;
            }
        }
    }
}