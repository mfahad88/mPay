package com.example.bipl.mpay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
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
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.app.ApplicationManager;
import com.example.bipl.data.AccountBean;
import com.example.bipl.data.TrxBean;
import com.example.bipl.util.EditDialogListener;
import com.example.bipl.util.FontHelper;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;


public class PaymentActivity extends AppCompatActivity implements View.OnClickListener,EditDialogListener {
    int focusCheck=0;
    EditText edit_amount,edit_cnic;
    TextView tvName,tvWelcome;
    SharedPreferences sharedpreferences,sharedpreferences2;
    Typeface typeface;
    ImageView powerbtn,mPay;
    String amount,cnic;
    LinearLayout layoutHeader,subheading,layoutmPay,layoutRed,layoutAmount,layoutCnic,layoutKeypad,layoutUpper;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    DialogFragment dialogFragment;
    public static final String MyPREFERENCES = "paymentPrefs" ;
    public static final int CONTEXT=Context.MODE_PRIVATE;
    private byte[] imgFinger;
    protected void clearSession() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        edit_amount.setEnabled(false);
        edit_cnic.setEnabled(false);
        edit_amount.setText(null);
        edit_cnic.setText(null);
        powerbtn.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double density = dm.density * 160;
        double x = Math.pow(dm.widthPixels / density, 2);
        double y = Math.pow(dm.heightPixels / density, 2);
        double screenInches = Math.sqrt(x + y);
        Log.e("inches: {}", String.valueOf(screenInches));
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,7.0f));
        edit_amount.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,5.0f));
        edit_cnic.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,5.0f));

        edit_amount.setEnabled(true);
        edit_cnic.setEnabled(true);
        powerbtn.setEnabled(true);
        if(amount!=null){
            edit_amount.setText(amount);
        }
        if(cnic!=null){
            edit_cnic.setText(cnic);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        edit_amount.setEnabled(false);
        edit_cnic.setEnabled(false);
        edit_amount.setText(null);
        edit_cnic.setText(null);
        powerbtn.setEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        amount=edit_amount.getText().toString();
        cnic=edit_cnic.getText().toString();
        edit_amount.setEnabled(false);
        edit_cnic.setEnabled(false);
        edit_amount.setText(null);
        edit_cnic.setText(null);
        powerbtn.setEnabled(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        edit_amount.setEnabled(false);
        edit_cnic.setEnabled(false);
        edit_amount.setText(null);
        edit_cnic.setText(null);
        powerbtn.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          if(savedInstanceState!=null){
            savedInstanceState.clear();
        }

        setContentView(R.layout.activity_payment);
        layoutUpper=(LinearLayout)findViewById(R.id.layoutUpper);
        subheading=(LinearLayout)findViewById(R.id.subheadingLayout);
        layoutHeader=(LinearLayout)findViewById(R.id.layoutHeader);
        layoutmPay=(LinearLayout)findViewById(R.id.layoutmPay);
        layoutRed=(LinearLayout)findViewById(R.id.layoutRed);
        layoutAmount=(LinearLayout)findViewById(R.id.layoutAmount);
        layoutCnic=(LinearLayout)findViewById(R.id.layoutCnic);
        layoutKeypad=(LinearLayout)findViewById(R.id.layoutKeypad);
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, LoginActivity.CONTEXT);
        sharedpreferences2 = getSharedPreferences(MyPREFERENCES, CONTEXT);
        tvName=(TextView)findViewById(R.id.textViewName);
        mPay=(ImageView)findViewById(R.id.imageView3);
        edit_amount=(EditText)findViewById(R.id.edittextAmount);
        edit_cnic=(EditText)findViewById(R.id.edittextCNIC);
        typeface=Typeface.createFromAsset(getAssets(), "font/palatino-linotype.ttf");
        edit_amount.setTypeface(typeface);
        edit_cnic.setTypeface(typeface);

        tvName.setTypeface(typeface);
        initViews();
        powerbtn=(ImageView)findViewById(R.id.imageViewPower);
        powerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(PaymentActivity.this);
                builder.setTitle("Confirmation...");
                builder.setCancelable(false);
                builder.setMessage("Are you sure want to exit?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            clearSession();
                            PaymentActivity.this.finishAffinity();
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
                        alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                        alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
                    }
                });
                alertDialog.show();

            }
        });
       
        try {
            JSONObject jsonObject=new JSONObject(sharedpreferences.getString("UserLoginBean",""));
            JSONObject jsonObject1=new JSONObject(jsonObject.getString("user"));
            tvName.setText("Welcome "+jsonObject1.getString("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    @Override
    public void onBackPressed() {

    }

    private void initViews() {
        focusCheck=0;
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
        if(amount!=null && cnic!=null){
            edit_amount.setText(amount);
            edit_cnic.setText(cnic);
        }

    }
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null && "number_button".equals(v.getTag())) {
            if(focusCheck==0) {
                if(edit_amount.getText().length()<6) {
                    edit_amount.append(((TextView) v).getText());
                }
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
                dialogFragment.setCancelable(false);
                dialogFragment.show(getSupportFragmentManager(),"Finger");

            } else {
                focusCheck=0;
                edit_amount.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
                edit_cnic.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_gray));
                Toast.makeText(this, "Please check amount and cnic...", Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    public void imageString(byte[] inputText) {
        imgFinger=inputText;
    }

    @Override
    public void capturedImage(Boolean status) {
        if (status){
            //dialogFragment.dismiss();
            new Trxasync().execute();
        }else if(status==false){
            if(dialogFragment.isVisible()) {
                dialogFragment.dismiss();
                onRestart();
                initViews();

            }
        }
    }

    public void logout(){
        try {
            Log.e("Logout>>>>>","Inside");

            JSONObject jsonObject = new JSONObject(sharedpreferences.getAll());
            JSONObject object = new JSONObject(jsonObject.getString("UserLoginBean"));
            JSONObject userObject = new JSONObject(object.getString("user"));
            ApplicationManager.Logout(object.getString("token"), object.getString("loginId"), object.getInt("appId"), userObject.getString("mId"), userObject.getString("oId"), userObject.getString("uId"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public class Trxasync extends AsyncTask<Void,Void,Boolean> {
        ProgressDialog progressDialog;
        String amount;
        String cnic;
        Boolean status=false;
        TrxBean trxBean;
        AccountBean accountBean;
        String errorDesc;
        SharedPreferences.Editor editor,editor1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            editor= sharedpreferences.edit();
            editor1=sharedpreferences2.edit();
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
                Log.e("UserLoginBean>>>>>>>>>>>>>>",object.toString());
                trxBean.setAppId(1);
                trxBean.setCnic(cnic);
                trxBean.setFlag("DF");
                trxBean.setThumb(imgFinger);
                trxBean.setToken(object.getString("token"));
                trxBean.setmId(userObject.getString("mId"));
                trxBean.setoId(userObject.getString("oId"));
                trxBean.setuId(userObject.getString("uId"));

            accountBean=ApplicationManager.Account_TRX(trxBean);
            if(Integer.parseInt(accountBean.getErrorCode())==0){
                status=true;
                editor1.putString("finger", Base64.encodeToString(imgFinger,Base64.DEFAULT));
                editor1.commit();
            }else{
                status=false;
                errorDesc=accountBean.getErrorDesc();
            }
            }catch (Exception e){
                status=false;
                errorDesc=e.getMessage();
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
                Toast.makeText(PaymentActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
                dialogFragment.dismiss();
                onRestart();
                initViews();
            }
        }
    }
}
