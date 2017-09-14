package com.example.bipl.mpay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bipl.util.GifImageView;

public class TransactionActivity extends AppCompatActivity {
    TextView tv_title,tv_ref,tv_amt;
    Context context;
    ImageView imgView;
    GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
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

        gifImageView=(GifImageView)findViewById(R.id.imageView2);
        tv_title= (TextView) findViewById(R.id.textViewTitle);
        tv_ref= (TextView) findViewById(R.id.textViewRef);
        tv_amt= (TextView) findViewById(R.id.textViewAmt);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        tv_ref.setTypeface(typeface);
        tv_amt.setTypeface(typeface);
        tv_ref.setText("Reference No. "+getIntent().getStringExtra("refNo"));
        tv_amt.setText("Rs. "+getIntent().getDoubleExtra("Amount",0.00)+" /-.");
        gifImageView.setGifImageResource(R.drawable.source);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(TransactionActivity.this,PaymentActivity.class);
                startActivity(i);
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_top,android.support.design.R.anim.abc_slide_out_top);
            }
        },5000);
        SharedPreferences sharedPreferences=getSharedPreferences(PaymentActivity.MyPREFERENCES,PaymentActivity.CONTEXT);
        //SharedPreferences sharedPreferences=context.getSharedPreferences(PaymentActivity.MyPREFERENCES,PaymentActivity.CONTEXT);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onBackPressed() {

    }
}
