package com.example.bipl.mpay;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
