package com.example.bipl.mpay;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bipl.app.ApplicationManager;
import com.example.bipl.data.TrxBean;
import com.example.bipl.util.EditDialogListener;

import org.json.JSONObject;

public class FingerActivity extends DialogFragment {
    TextView tv_title;
    View view;
    EditDialogListener dialogListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_finger, container, false);
        tv_title=(TextView)view.findViewById(R.id.textViewTitle);
        Typeface typeface=Typeface.createFromAsset(view.getContext().getAssets(), "font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        dialogListener= (EditDialogListener) getActivity();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogListener.updateResult("FingerXYZ");
            }
        },5000);


        return view;
    }
}
