package com.example.bipl.mpay;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {
    TextView tv_title;
    EditText edtName,edtCNIC;
    Button btn_verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tv_title= (TextView) findViewById(R.id.textViewTitle);
        edtName= (EditText) findViewById(R.id.editTextName);
        edtCNIC= (EditText) findViewById(R.id.editTextCn);
        btn_verify= (Button) findViewById(R.id.buttonVerify);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        edtName.setTypeface(typeface);
        edtCNIC.setTypeface(typeface);
        btn_verify.setTypeface(typeface);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,TransactionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
