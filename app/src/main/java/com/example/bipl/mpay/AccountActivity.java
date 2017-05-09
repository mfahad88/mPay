package com.example.bipl.mpay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.app.ApplicationManager;
import com.example.bipl.data.AccountBean;
import com.example.bipl.data.PaymentBean;
import com.example.bipl.data.TrxBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class AccountActivity extends AppCompatActivity {
    EditText accName,accNum,accPoints,accpointWorth;
    TextView tv_accName,tv_accNum,tv_accPoints,tv_accpointWorth,tvTitle;
    RadioGroup radioGroup;
    RadioButton radioLoyality,radioAccount;
    SharedPreferences sharedPreferences,sharedPreferences2;
    Button btn_cancel;
    ImageView powerbtn;
    private String refNo;

    protected void clearSession() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor.clear();
        editor.commit();
        editor2.clear();
        editor2.commit();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences2=getSharedPreferences(PaymentActivity.MyPREFERENCES,PaymentActivity.CONTEXT);
        tvTitle= (TextView) findViewById(R.id.textViewTitle);
        tv_accName=(TextView)findViewById(R.id.textViewaccName) ;
        tv_accNum=(TextView)findViewById(R.id.textViewaccNum);
        tv_accPoints=(TextView)findViewById(R.id.textViewaccPoints);
        tv_accpointWorth=(TextView)findViewById(R.id.textViewaccpointWorth);
        accName= (EditText) findViewById(R.id.editTextaccName);
        accNum= (EditText) findViewById(R.id.editTextaccNum);
        accPoints= (EditText) findViewById(R.id.editTextpoints);
        accpointWorth=(EditText)findViewById(R.id.editTextpointsWorth);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioLoyality=(RadioButton)findViewById(R.id.radioLoyalty);
        radioAccount=(RadioButton)findViewById(R.id.radioAccount);
        btn_cancel=(Button)findViewById(R.id.buttonCancel) ;
        new accountAsync().execute();
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/palatino-linotype.ttf");
        tvTitle.setTypeface(typeface);
        accName.setTypeface(typeface);
        accNum.setTypeface(typeface);
        accPoints.setTypeface(typeface);
        accpointWorth.setTypeface(typeface);
        tv_accName.setTypeface(typeface);
        tv_accNum.setTypeface(typeface);
        tv_accPoints.setTypeface(typeface);
        tv_accpointWorth.setTypeface(typeface);
        radioLoyality.setTypeface(typeface);
        radioAccount.setTypeface(typeface);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)findViewById(checkedId);
                if(radioButton.getText().toString().trim().equals("Account")){
                    new paymentAccount().execute();
                }
                if(radioButton.getText().toString().trim().equals("Loyality")){
                    new paymentLoyality().execute();
                }
            }
        });
        powerbtn=(ImageView)findViewById(R.id.imageViewPower);
        powerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Confirmation...");
                builder.setCancelable(false);
                builder.setMessage("Are you sure want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            clearSession();
                            AccountActivity.this.finishAffinity();
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
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,PaymentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public class paymentAccount extends AsyncTask<Void,Void,Boolean>{
        String errorDesc;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(AccountActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("Loading...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            TrxBean trxBean=new TrxBean();
            AccountBean accountBean;
            Boolean status=false;
            try {

                JSONObject jsonObject=new JSONObject(sharedPreferences.getAll());
                JSONObject object=new JSONObject(jsonObject.getString("UserLoginBean"));
                JSONObject userObject=new JSONObject(object.getString("user"));
                Log.e("Token",object.getString("token"));
                Log.e("mId",userObject.getString("mId"));
                Log.e("oId",userObject.getString("oId"));
                Log.e("uId",userObject.getString("uId"));
                Log.e("CNIC",sharedPreferences2.getString("Cnic",""));
                Log.e("Amount",sharedPreferences2.getString("Amount","0"));


                trxBean.setFlag("TRX");
                trxBean.setCnic(sharedPreferences2.getString("Cnic",""));
                trxBean.setAmount(sharedPreferences2.getString("Amount","0"));
                trxBean.setToken(object.getString("token"));
                trxBean.setmId(userObject.getString("mId"));
                trxBean.setoId(userObject.getString("oId"));
                trxBean.setuId(userObject.getString("uId"));
                trxBean.setAppId(1);
                trxBean.setDescription("testing");
                trxBean.setProcessCode(0);
                trxBean.setProductId(1);
                Log.e("PaymentBean",trxBean.toString());
                accountBean=ApplicationManager.Account_TRX(trxBean);
                if(Integer.parseInt(accountBean.getErrorCode())==0 && Integer.parseInt(accountBean.getProcessCode())==1){
                    refNo= String.valueOf(accountBean.getTrxNoImal());
                    status=true;
                }else{
                    status=false;
                    errorDesc=accountBean.getErrorDesc();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
               Intent intent=new Intent(AccountActivity.this,TransactionActivity.class);
                intent.putExtra("refNo",refNo);
                intent.putExtra("Amount",new Double(sharedPreferences2.getString("Amount","0")));
                progressDialog.dismiss();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                progressDialog.dismiss();
                Toast.makeText(AccountActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class paymentLoyality extends AsyncTask<Void,Void,Boolean>{
        String errorDesc;

        @Override
        protected Boolean doInBackground(Void... params) {
            TrxBean trxBean=new TrxBean();
            AccountBean accountBean;
            Boolean status=false;
            try {

                JSONObject jsonObject=new JSONObject(sharedPreferences.getAll());
                JSONObject object=new JSONObject(jsonObject.getString("UserLoginBean"));
                JSONObject userObject=new JSONObject(object.getString("user"));
                Log.e("Token",object.getString("token"));
                Log.e("mId",userObject.getString("mId"));
                Log.e("oId",userObject.getString("oId"));
                Log.e("uId",userObject.getString("uId"));
                Log.e("CNIC",sharedPreferences2.getString("Cnic",""));
                Log.e("Amount",sharedPreferences2.getString("Amount","0"));


                trxBean.setFlag("LOY_TRX");
                trxBean.setCnic(sharedPreferences2.getString("Cnic",""));
                trxBean.setAmount(sharedPreferences2.getString("Amount","0"));
                trxBean.setToken(object.getString("token"));
                trxBean.setmId(userObject.getString("mId"));
                trxBean.setoId(userObject.getString("oId"));
                trxBean.setuId(userObject.getString("uId"));
                trxBean.setAppId(1);
                trxBean.setDescription("testing");
                trxBean.setProcessCode(0);
                trxBean.setProductId(1);
                Log.e("PaymentBean",trxBean.toString());
                accountBean=ApplicationManager.Account_TRX(trxBean);
                if(Integer.parseInt(accountBean.getErrorCode())==0 && Integer.parseInt(accountBean.getProcessCode())==1){
                    refNo= String.valueOf(accountBean.getTrxNoPoints());
                    status=true;
                }else{
                    status=false;
                    errorDesc=accountBean.getErrorDesc();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Intent intent=new Intent(AccountActivity.this,TransactionActivity.class);
                intent.putExtra("refNo",refNo);
                intent.putExtra("Amount",new Double(sharedPreferences2.getString("Amount","0")));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                Toast.makeText(AccountActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class accountAsync extends AsyncTask<Void,Void,JSONObject>{

        @Override
        protected JSONObject doInBackground(Void... params) {
            Bundle extras = getIntent().getExtras();
            String jsonMyObject="";
            JSONObject jsonObject = null;
            if (extras != null) {
                jsonMyObject = extras.getString("accountBean");
            }
            try {
                jsonObject=new JSONObject(jsonMyObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {

                accName.setText(jsonObject.getString("accTitle"));
                accNum.setText(jsonObject.getString("accNum"));
                accPoints.setText(jsonObject.getString("points"));
                accpointWorth.setText(jsonObject.getString("pointsWorth"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
