package com.example.bipl.mpay;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TransactionActivity extends AppCompatActivity {
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        tv_title= (TextView) findViewById(R.id.textViewTitle);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(TransactionActivity.this,PaymentActivity.class);
                startActivity(i);
            }
        },5000);
    }
}
