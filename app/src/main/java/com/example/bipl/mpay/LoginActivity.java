package com.example.bipl.mpay;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.BI_Controls.BIProgressDialog;
import com.example.bipl.app.ApplicationManager;
import com.example.bipl.data.UserLoginBean;
import com.example.bipl.util.FontHelper;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_title,tv_forgot;
    EditText edt_username,edt_password;
    Button btn_login;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final int CONTEXT=Context.MODE_PRIVATE;
    SharedPreferences sharedpreferences;
    ImageView powerbtn;
    String editUser,editPass;
    private Boolean internetStatus=false;
    @Override
    protected void onStart() {
        super.onStart();
        edt_username.setEnabled(false);
        edt_password.setEnabled(false);
        btn_login.setEnabled(false);
        powerbtn.setEnabled(false);
        edt_username.setText(null);
        edt_password.setText(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new internetCheck().execute();
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,7.0f));
        edt_username.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        edt_password.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        btn_login.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        tv_forgot.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        edt_username.setEnabled(true);
        edt_password.setEnabled(true);
        btn_login.setEnabled(true);
        powerbtn.setEnabled(true);
        if(editUser!=null){
            edt_username.setText(editUser);
        }
        if(editPass!=null){
            edt_password.setText(editPass);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        edt_username.setEnabled(false);
        edt_password.setEnabled(false);
        btn_login.setEnabled(false);
        powerbtn.setEnabled(false);
        edt_username.setText(null);
        edt_password.setText(null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        edt_username.setEnabled(true);
        edt_password.setEnabled(true);
        btn_login.setEnabled(true);
        powerbtn.setEnabled(true);
        edt_username.setText(null);
        edt_password.setText(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        editUser=edt_username.getText().toString();
        editPass=edt_password.getText().toString();
        edt_username.setEnabled(false);
        edt_password.setEnabled(false);
        btn_login.setEnabled(false);
        powerbtn.setEnabled(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);

        tv_title=(TextView)findViewById(R.id.textViewTitle);
        tv_forgot=(TextView)findViewById(R.id.textViewForgot);
        edt_username=(EditText)findViewById(R.id.edittextUsername);
        edt_password=(EditText)findViewById(R.id.edittextPassword);
        btn_login=(Button)findViewById(R.id.buttonLogin);
        powerbtn=(ImageView)findViewById(R.id.imageViewPower);

        sharedpreferences = getSharedPreferences(MyPREFERENCES,CONTEXT);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        tv_forgot.setTypeface(typeface);
        edt_username.setTypeface(typeface);
        edt_password.setTypeface(typeface);
        btn_login.setTypeface(typeface);
        btn_login.setOnClickListener(this);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        powerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);

                builder.setTitle("Confirmation...");
                builder.setCancelable(false);
                builder.setMessage("Are you sure want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            clearSession();
                            LoginActivity.this.finishAffinity();
                            System.exit(0);
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                final AlertDialog alertDialog=builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
                    }
                });

                alertDialog.show();
            }
        });
    }



    @Override
    public void onBackPressed() {

    }


    protected void clearSession() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonLogin){
            if(edt_username.getText().toString().trim()!=null && edt_password.getText().toString().trim()!=null
                    && !edt_username.getText().toString().trim().equals("") && !edt_password.getText().toString().trim().equals("") && internetStatus) {
                new loginAsync().execute(edt_username.getText().toString(), edt_password.getText().toString());
            }
        }
    }

    public class loginAsync extends AsyncTask<String,String,Boolean>{
        BIProgressDialog progressDialog;
        String errorMessage;
        String name;

        @Override
        protected Boolean doInBackground(String... params) {

            String userName=params[0];
            String password=params[1];
            Integer appId=1;
            Boolean status=false;

            SharedPreferences.Editor editor = sharedpreferences.edit();
            Gson gson = new Gson();
            UserLoginBean loginBean=ApplicationManager.Login(userName,password,appId,getApplicationContext());
            if(loginBean.getErrorCode().equals("0") && loginBean.getStatus()==1) {
                String json = gson.toJson(loginBean);
                editor.putString("UserLoginBean", json);
                if (!json.equals(null)) {
                    status = true;
                    name=loginBean.getUser().getUserName();
                }
                editor.commit();
            }else{
                errorMessage=loginBean.getErrorDesc();
                status=false;
            }
            return status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new BIProgressDialog(LoginActivity.this);
            progressDialog.showProgressDialog("Loading...","Please Wait");

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            if(aBoolean==true){

                Intent i=new Intent(LoginActivity.this,PaymentActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_top,android.support.design.R.anim.abc_slide_out_top);
            }else{
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }

    }

    public class internetCheck extends AsyncTask<Void,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            return ApplicationManager.isInternetWorking();
        }

        @SuppressLint("LongLogTag")
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            internetStatus=aBoolean;
           // internetStatus=true;
            if(!internetStatus){
                Toast.makeText(LoginActivity.this, "Check internet connectivity", Toast.LENGTH_SHORT).show();
            }
            Log.e("Executing internet check", String.valueOf(internetStatus));
        }
    }
}
