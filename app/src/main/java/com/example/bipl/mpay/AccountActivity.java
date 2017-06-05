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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
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
import com.example.bipl.util.EditDialogListener;
import com.example.bipl.util.FontHelper;

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
    String accountTitle,accountNum,Points,WorthPoints;
    final Handler handler=new Handler();
    byte[] imgFinger;
    @Override
    protected void onStart() {
        super.onStart();
        accName.setEnabled(false);
        accNum.setEnabled(false);
        accPoints.setEnabled(false);
        accpointWorth.setEnabled(false);
        tv_accName.setEnabled(false);
        tv_accNum.setEnabled(false);
        tv_accPoints.setEnabled(false);
        tvTitle.setEnabled(false);
        btn_cancel.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,6.0f));
        accName.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        accNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        accPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        accpointWorth.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        tv_accName.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        tv_accNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        btn_cancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        radioLoyality.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        radioAccount.setTextSize(TypedValue.COMPLEX_UNIT_PX, FontHelper.fontGenerator(this,4.0f));
        accName.setEnabled(true);
        accNum.setEnabled(true);
        accPoints.setEnabled(true);
        accpointWorth.setEnabled(true);
        tv_accName.setEnabled(true);
        tv_accNum.setEnabled(true);
        tv_accPoints.setEnabled(true);
        tvTitle.setEnabled(true);
        btn_cancel.setEnabled(true);
        if(accName.getText()!=null){
            accName.setText(accountTitle);
        }
        if(accNum.getText()!=null){
            accNum.setText(accountNum);
        }
        if(accPoints.getText()!=null){
            accPoints.setText(Points);
        }
        if(accpointWorth.getText()!=null){
            accpointWorth.setText(WorthPoints);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accName.setEnabled(false);
        accNum.setEnabled(false);
        accPoints.setEnabled(false);
        accpointWorth.setEnabled(false);
        tv_accName.setEnabled(false);
        tv_accNum.setEnabled(false);
        tv_accPoints.setEnabled(false);
        tvTitle.setEnabled(false);
        btn_cancel.setEnabled(false);
        if(accName.getText()!=null){
            accountTitle=accName.getText().toString();
        }
        if(accNum.getText()!=null){
            accountNum=accNum.getText().toString();
        }
        if(accPoints.getText()!=null){
            Points=accPoints.getText().toString();
        }
        if(accpointWorth.getText()!=null){
            WorthPoints=accpointWorth.getText().toString();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

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
        Log.e("sharedPreferences>>>>", String.valueOf(sharedPreferences.getAll()));
        Log.e("sharedPreferences2>>>>", String.valueOf(sharedPreferences2.getAll()));
        imgFinger= Base64.decode(sharedPreferences2.getString("finger",""),Base64.DEFAULT);
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
        new accountAsync().execute();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)findViewById(checkedId);
                if(group.getCheckedRadioButtonId()!=0) {
                    if (radioButton.getText().toString().trim().equals("Account")) {
                        handler.removeCallbacksAndMessages(null);
                        new paymentAccount().execute();
                    }
                    if (radioButton.getText().toString().trim().equals("Loyality")) {
                        handler.removeCallbacksAndMessages(null);
                        new paymentLoyality().execute();
                    }
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
                        logout();
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(AccountActivity.this,PaymentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        },60000);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,PaymentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                handler.removeCallbacksAndMessages(null);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
    public void logout(){
        try {
            Log.e("Logout>>>>>","Inside");

            JSONObject jsonObject = new JSONObject(sharedPreferences.getAll());
            JSONObject object = new JSONObject(jsonObject.getString("UserLoginBean"));
            JSONObject userObject = new JSONObject(object.getString("user"));
            ApplicationManager.Logout(object.getString("token"), object.getString("loginId"), object.getInt("appId"), userObject.getString("mId"), userObject.getString("oId"), userObject.getString("uId"));
        }catch(Exception e){
            e.printStackTrace();
        }
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
                trxBean.setThumb(imgFinger);
                trxBean.setAppId(1);
                trxBean.setDescription("mPAY");
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
                progressDialog.dismiss();
               Intent intent=new Intent(AccountActivity.this,TransactionActivity.class);
                intent.putExtra("refNo",refNo);
                intent.putExtra("Amount",new Double(sharedPreferences2.getString("Amount","0")));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                progressDialog.dismiss();
                Toast.makeText(AccountActivity.this, errorDesc, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(AccountActivity.this,PaymentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                handler.removeCallbacksAndMessages(null);
                startActivity(intent);
            }
        }
    }

    public class paymentLoyality extends AsyncTask<Void,Void,Boolean>{
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

                trxBean.setFlag("LOY_TRX");
                trxBean.setCnic(sharedPreferences2.getString("Cnic",""));
                trxBean.setAmount(sharedPreferences2.getString("Amount","0"));
                trxBean.setToken(object.getString("token"));
                trxBean.setmId(userObject.getString("mId"));
                trxBean.setoId(userObject.getString("oId"));
                trxBean.setuId(userObject.getString("uId"));
                trxBean.setThumb(imgFinger);
                trxBean.setAppId(1);
                trxBean.setDescription("mPAY");
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
                progressDialog.dismiss();
                Intent intent=new Intent(AccountActivity.this,TransactionActivity.class);
                intent.putExtra("refNo",refNo);
                intent.putExtra("Amount",new Double(sharedPreferences2.getString("Amount","0")));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                progressDialog.dismiss();
                Toast.makeText(AccountActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AccountActivity.this,PaymentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                handler.removeCallbacksAndMessages(null);
                startActivity(intent);
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
