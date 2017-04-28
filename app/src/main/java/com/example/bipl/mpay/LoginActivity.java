package com.example.bipl.mpay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.app.ApplicationManager;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_title,tv_forgot;
    EditText edt_username,edt_password;
    Button btn_login;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        setContentView(R.layout.activity_login);
        tv_title=(TextView)findViewById(R.id.textViewTitle);
        tv_forgot=(TextView)findViewById(R.id.textViewForgot);
        edt_username=(EditText)findViewById(R.id.edittextUsername);
        edt_password=(EditText)findViewById(R.id.edittextPassword);
        btn_login=(Button)findViewById(R.id.buttonLogin);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.buttonLogin){
           new loginAsync().execute(edt_username.getText().toString(),edt_password.getText().toString());
          /* Intent intent=new Intent(LoginActivity.this,PaymentActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);*/
       }
    }

    public class loginAsync extends AsyncTask<String,String,Boolean>{
        ProgressDialog progressDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            String userName=params[0];
            String password=params[1];
            Integer appId=1;
            Boolean status=false;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            Gson gson = new Gson();
            String json=gson.toJson(ApplicationManager.Login(userName,password,appId));
            editor.putString("UserLoginBean",json);
            if(!json.equals(null)){
                status=true;
            }
            return status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
        }

    }
}
