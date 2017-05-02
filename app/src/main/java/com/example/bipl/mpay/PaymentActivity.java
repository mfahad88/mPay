package com.example.bipl.mpay;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
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
import com.example.bipl.data.TrxBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    int focusCheck=0;
    EditText edit_amount,edit_cnic;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        edit_amount=(EditText)findViewById(R.id.edittextAmount);
        edit_cnic=(EditText)findViewById(R.id.edittextCNIC);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "font/palatino-linotype.ttf");
        edit_amount.setTypeface(typeface);
        edit_cnic.setTypeface(typeface);
        initViews();




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
                edit_amount.append(((TextView) v).getText());
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


                new Trxasync().execute();
          /*  Intent intent=new Intent(PaymentActivity.this,FingerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            } else {
                focusCheck=0;
                edit_amount.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
                edit_cnic.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_gray));
                Toast.makeText(this, "Please check amount and cnic..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class Trxasync extends AsyncTask<Void,Void,Boolean> {
        ProgressDialog progressDialog;
        TrxBean trxBean=new TrxBean();


        @Override
        protected Boolean doInBackground(Void... params) {
            ApplicationManager.Account_TRX(trxBean);
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            trxBean.setAmount(Double.parseDouble(edit_amount.getText().toString()));
            trxBean.setAppId(1);
            trxBean.setCnic(edit_cnic.getText().toString().replaceAll("-",""));
            trxBean.setDescription("testing");
            trxBean.setErrorCode(sharedpreferences.getInt("errorCode",1));
            trxBean.setProcessCode(0);
            trxBean.setProductId(1);
            trxBean.setToken(sharedpreferences.getString("token",""));
            trxBean.setmId(sharedpreferences.getString("mId",""));
            trxBean.setoId(sharedpreferences.getString("oId",""));
            trxBean.setuId(sharedpreferences.getString("uId",""));
            progressDialog=new ProgressDialog(PaymentActivity.this);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Validating CNIC...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                progressDialog.dismiss();
                Intent i=new Intent(PaymentActivity.this,AccountActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }else{
                progressDialog.dismiss();
            }
        }
    }

}
