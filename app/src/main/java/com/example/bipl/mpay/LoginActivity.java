package com.example.bipl.mpay;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView tv_title,tv_forgot;
    EditText edt_username,edt_password;
    Button btn_login;

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
        Typeface typeface=Typeface.createFromAsset(getAssets(), "font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        tv_forgot.setTypeface(typeface);
        edt_username.setTypeface(typeface);
        edt_password.setTypeface(typeface);
        btn_login.setTypeface(typeface);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,PaymentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
