package com.example.bipl.mpay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.icu.text.NumberFormat;
import android.icu.util.Currency;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.app.ApplicationManager;
import com.example.bipl.data.AccountBean;
import com.example.bipl.data.TrxBean;
import com.example.bipl.util.EditDialogListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener,EditDialogListener {
    int focusCheck=0;
    EditText edit_amount,edit_cnic;
    TextView tvName,tvWelcome;
    SharedPreferences sharedpreferences,sharedpreferences2;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    DialogFragment dialogFragment;
    public static final String MyPREFERENCES = "paymentPrefs" ;
    public static final int CONTEXT=Context.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, LoginActivity.CONTEXT);
        sharedpreferences2 = getSharedPreferences(MyPREFERENCES, CONTEXT);
        tvName=(TextView)findViewById(R.id.textViewName);
        tvWelcome=(TextView)findViewById(R.id.textViewWelcome);
        edit_amount=(EditText)findViewById(R.id.edittextAmount);
        edit_cnic=(EditText)findViewById(R.id.edittextCNIC);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "font/palatino-linotype.ttf");
        edit_amount.setTypeface(typeface);
        edit_cnic.setTypeface(typeface);
        tvWelcome.setTypeface(typeface);
        tvName.setTypeface(typeface);
        initViews();

        try {
            JSONObject jsonObject=new JSONObject(sharedpreferences.getString("UserLoginBean",""));
            JSONObject jsonObject1=new JSONObject(jsonObject.getString("user"));
            tvName.setText(jsonObject1.getString("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    private void initViews() {

        edit_amount = $(R.id.edittextAmount);
        edit_cnic   = $(R.id.edittextCNIC);
        $(R.id.t9_key_0).setOnClickListener(this);
        $(R.id.t9_key_1).setOnClickListener(this);
        $(R.id.t9_key_2).setOnClickListener(this);
        $(R.id.t9_key_3).setOnClickListener(this);
        $(R.id.t9_key_4).setOnClickListener(this);
        $(R.id.t9_key_5).setOnClickListener(this);
        $(R.id.t9_key_6).setOnClickListener(this);
        $(R.id.t9_key_7).setOnClickListener(this);
        $(R.id.t9_key_8).setOnClickListener(this);
        $(R.id.t9_key_9).setOnClickListener(this);
        $(R.id.t9_key_reset).setOnClickListener(this);
        $(R.id.t9_key_backspace).setOnClickListener(this);
        $(R.id.t9_key_done).setOnClickListener(this);
        $(R.id.t9_key_dot).setOnClickListener(this);
        edit_amount.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
        edit_cnic.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_gray));

    }
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null && "number_button".equals(v.getTag())) {
            if(focusCheck==0) {
                edit_amount.append(((TextView) v).getText().toString());
            }if(focusCheck==1){
                if(edit_cnic.length()==5 || edit_cnic.length()==13) {

                    edit_cnic.setText(edit_cnic.getText()+"-");
                }
                if(!((TextView)v).getText().equals(".") && edit_cnic.getText().length()<15) {

                    edit_cnic.append(((TextView) v).getText());
                }
            }

        }

        switch (v.getId()) {
            case R.id.t9_key_backspace: { // handle backspace button
                // delete one character
                if(focusCheck==0) {
                    Editable editable = edit_amount.getText();
                    int charCount = editable.length();
                    if (charCount > 0) {
                        editable.delete(charCount - 1, charCount);
                    }
                }
                if(focusCheck==1) {
                    Editable editable = edit_cnic.getText();
                    int charCount = editable.length();
                    if (charCount > 0) {
                        editable.delete(charCount - 1, charCount);
                    }
                }
            }
            break;
            case R.id.t9_key_done: {
                focusCheck++;
            }

            break;
            case R.id.t9_key_reset: {
                edit_amount.setText(null);
                edit_cnic.setText(null);
                focusCheck=0;
                initViews();
            }
            break;
        }
        if(focusCheck==0){
            edit_amount.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
            edit_cnic.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_gray));
        }
        if(focusCheck==1){

            edit_amount.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_gray));
            edit_cnic.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
        }
        if(focusCheck==2) {
            if (!TextUtils.isEmpty(edit_amount.getText().toString()) && edit_cnic.getText().length()==15) {
                dialogFragment=new FingerActivity();
                dialogFragment.show(getSupportFragmentManager(),"Finger");

            } else {
                focusCheck=0;
                edit_amount.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
                edit_cnic.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_gray));
                Toast.makeText(this, "Please check amount and cnic..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void updateResult(String inputText) {
        Log.e("Finger>>>>>>>>>>>>",inputText);
        dialogFragment.dismiss();
        new Trxasync().execute();
    }


    public class Trxasync extends AsyncTask<Void,Void,Boolean>  {
        ProgressDialog progressDialog;
        String amount;
        String cnic;
        Boolean status=false;
        TrxBean trxBean;
        AccountBean accountBean;
        String errorDesc;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            SharedPreferences.Editor editor1=sharedpreferences2.edit();
            amount= edit_amount.getText().toString();
            cnic=edit_cnic.getText().toString().replaceAll("-","");
            editor1.putString("Cnic",cnic);
            editor1.putString("Amount",amount);
            editor1.commit();
            progressDialog=new ProgressDialog(PaymentActivity.this);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Validating CNIC...");
            progressDialog.show();

        }

        @SuppressLint("LongLogTag")
        @Override
        protected Boolean doInBackground(Void... params) {
            trxBean=new TrxBean();
            try{
                JSONObject jsonObject=new JSONObject(sharedpreferences.getAll());
                JSONObject object=new JSONObject(jsonObject.getString("UserLoginBean"));
                JSONObject userObject=new JSONObject(object.getString("user"));
                trxBean.setAppId(1);
                trxBean.setCnic(cnic);
                trxBean.setFlag("DF");
                trxBean.setToken(object.getString("token"));
                trxBean.setmId(userObject.getString("mId"));
                trxBean.setoId(userObject.getString("oId"));
                trxBean.setuId(userObject.getString("uId"));

            accountBean=ApplicationManager.Account_TRX(trxBean);
            if(Integer.parseInt(accountBean.getErrorCode())==0){
                status=true;
            }else{
                status=false;
                errorDesc=accountBean.getErrorDesc();
            }
            }catch (Exception e){
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                progressDialog.dismiss();
                Intent i=new Intent(PaymentActivity.this,AccountActivity.class);
                i.putExtra("accountBean",new Gson().toJson(accountBean));
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }else{
                progressDialog.dismiss();
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(PaymentActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setMessage(errorDesc);
                alertDialog.setTitle("Warning...");

                alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        edit_amount.setText(null);
                        edit_cnic.setText(null);
                        focusCheck=0;
                        initViews();
                    }
                });
                final AlertDialog dialog=alertDialog.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.button_border);
                dialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialog.dismiss();
                        edit_amount.setText(null);
                        edit_cnic.setText(null);
                        focusCheck=0;
                        initViews();
                    }
                }, 5000);

            }
        }


    }


}
