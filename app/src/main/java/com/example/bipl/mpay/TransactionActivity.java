package com.example.bipl.mpay;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TransactionActivity extends AppCompatActivity {
    TextView tv_title,tv_ref,tv_amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        tv_title= (TextView) findViewById(R.id.textViewTitle);
        tv_ref= (TextView) findViewById(R.id.textViewRef);
        tv_amt= (TextView) findViewById(R.id.textViewAmt);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        tv_ref.setTypeface(typeface);
        tv_amt.setTypeface(typeface);
        tv_ref.setText("Reference No. "+getIntent().getStringExtra("refNo"));
        tv_amt.setText("Rs. "+getIntent().getDoubleExtra("Amount",0.00)+" /-.");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(TransactionActivity.this,PaymentActivity.class);
                startActivity(i);
            }
        },5000);
    }
}
