package com.example.bipl.mpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.app.ApplicationManager;
import com.example.bipl.data.TrxBean;
import com.example.bipl.util.EditDialogListener;
import com.fgtit.data.wsq;
import com.fgtit.fpcore.FPMatch;
import com.fgtit.utils.ToastUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;

import android_serialport_api.AsyncFingerprint;
import android_serialport_api.SerialPortManager;

public class FingerActivity extends DialogFragment {
    TextView tv_title;
    View view;
    EditDialogListener dialogListener;
    public static final int	IMG_WIDTH=256;
    public static final int	IMG_HEIGHT=288;
    public static final int	IMG_SIZE=73728;
    public static final int	WSQBUFSIZE=200000;

    private AsyncFingerprint fingerprint;
    private int bWorkmode=0;
    private ImageView fingerprintImage;

    private ProgressDialog progressDialog;
    private Timer fpTimer=null;
    private TimerTask fpTask=null;
    private Handler fpHandler;
    private int count;
    private boolean IsUpImage=false;

    private Boolean status=false;

    private boolean bCancel=false;
    private int cancelCount=0;
    private int cancelMax=10;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new Dialog(getActivity(),getTheme()){
            @Override
            public void onBackPressed() {

            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_finger, container, false);
        initFingerprint();
        tv_title=(TextView)view.findViewById(R.id.textViewTitle);
        Typeface typeface=Typeface.createFromAsset(view.getContext().getAssets(), "font/palatino-linotype.ttf");
        tv_title.setTypeface(typeface);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogListener= (EditDialogListener) getActivity();

        fingerprintImage = (ImageView)view.findViewById(R.id.fingerprintImage);



        FPMatch.getInstance().InitMatch();
        if(progressDialog==null){
            IsUpImage=true;
            count = 1;
            bWorkmode=0;
            //showProgressDialog("Place finger£¡");
            bCancel=false;
            cancelCount=0;
            //fpTimerStart();
            fingerprint.FP_GetImage();
            dialogListener.capturedImage(status);
        }else{
            progressDialog.show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogListener.capturedImage(status);
              //  onCancel(getDialog());
            }
        },10000);

        return view;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }

    private void initFingerprint() {
        fingerprint = SerialPortManager.getInstance().getNewAsyncFingerprint();

        fingerprint.setOnGetImageListener(new AsyncFingerprint.OnGetImageListener() {
            @Override
            public void onGetImageSuccess() {
                cancleProgressDialog();
                if(IsUpImage){
                    if(fingerprintImage.getDrawable()==null) {
                        tv_title.setText("Processing...");
                        fingerprint.FP_UpImage();
                    }
                   // showProgressDialog("Processing...");
                }else{
                    fingerprint.FP_GenChar(1);
                }
            }

            @Override
            public void onGetImageFail() {
                if(bCancel){
                    cancleProgressDialog();
                   // ToastUtil.showToast(view.getContext(), "Cancel");
                }else{
                    fingerprint.FP_GetImage();
                }
            }
        });

        fingerprint.setOnUpImageListener(new AsyncFingerprint.OnUpImageListener() {
            @Override
            public void onUpImageSuccess(byte[] data) {
                Log.i("whw", "up image data.length="+data.length);
                DispFP(data);
                fingerprint.FP_GenChar(1);
            }

            @Override
            public void onUpImageFail() {
                Log.i("whw", "up image fail");
            }
        });

        fingerprint.setOnGenCharListener(new AsyncFingerprint.OnGenCharListener() {
            @Override
            public void onGenCharSuccess(int bufferId) {
                cancleProgressDialog();
                //showMessage("Generate features success£¡");

                fingerprint.FP_UpChar();
            }

            @Override
            public void onGenCharFail() {
                cancleProgressDialog();
                //showMessage("Generate features fail£¡");
                Log.i("whw", "validateFingerprint onGenCharFail");
            }
        });

    }


    public void DispFP(byte[] data){
        byte[] outdata=new byte[WSQBUFSIZE];
        byte[] wsqdata=new byte[WSQBUFSIZE];
        int[] wsqsize=new int[1];
        byte[] inpdata=new byte[IMG_WIDTH*IMG_HEIGHT];
        int inpsize=IMG_WIDTH*IMG_HEIGHT;
        System.arraycopy(data,1078, inpdata, 0, inpsize);
        wsq.getInstance().RawToWsq(inpdata,IMG_SIZE,IMG_WIDTH,IMG_HEIGHT, wsqdata, wsqsize,2.833755f);


        //dialogListener.updateResult(data);
        try {
            String filename=Environment.getExternalStorageDirectory()+"/test.wsq";
            File f=new File(filename);
            if(f.exists()){
                f.delete();
            }

            RandomAccessFile randomFile = new RandomAccessFile(filename, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write(wsqdata, 0, wsqsize[0]);
            randomFile.close();

            byte[] bytesArray = new byte[(int) f.length()];

            FileInputStream fis = new FileInputStream(f);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            dialogListener.imageString(bytesArray);
            //dialogListener.imageString(generateNoteOnSD(getContext(),"Test.txt",Base64.encodeToString(bytesArray,Base64.DEFAULT)));
            if(f.exists()){
                f.delete();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap image = BitmapFactory.decodeByteArray(data, 0,data.length);
        //image.setDensity(200);
        image.setDensity(50);
        //fingerprintImage.setBackgroundDrawable(new BitmapDrawable(image));
        fingerprintImage.setImageDrawable(new BitmapDrawable(image));
        if(fingerprintImage.getDrawable()!=null){
            tv_title.setText("Processed...");
            status=true;
        }

    }

    public void fpTimerStart() {
        if(fpTimer!=null){
            return;
        }
        fpTimer = new Timer();
        fpHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                cancelCount++;
                if(cancelCount>cancelMax){
                    bCancel=true;
                    fpTimerStop();
                }
                super.handleMessage(msg);
            }
        };
        fpTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                fpHandler.sendMessage(message);
            }
        };
        fpTimer.schedule(fpTask, 1000, 1000);
    }

    public void fpTimerStop() {
        if (fpTimer!=null) {
            fpTimer.cancel();
            fpTimer = null;
            fpTask.cancel();
            fpTask=null;
        }
    }

    private void showProgressDialog(String message) {
        //*
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage(message);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        //*/
    }

    private void cancleProgressDialog() {
        ///*
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
            progressDialog = null;
        }
        //*/
    }

    @Override
    public void onStop() {
        cancleProgressDialog();
        if(SerialPortManager.getInstance().isOpen()){
            bCancel=true;
            fpTimerStop();
            SerialPortManager.getInstance().closeSerialPort();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        cancleProgressDialog();
        if(SerialPortManager.getInstance().isOpen()){
            bCancel=true;
            fpTimerStop();
            SerialPortManager.getInstance().closeSerialPort();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        bCancel=false;
        cancelCount=0;
        initFingerprint();
    }

    @Override
    public void onStart() {
        super.onStart();
        bCancel=false;
        cancelCount=0;
        //initFingerprint();
    }


    public String generateNoteOnSD(Context context, String sFileName, String sBody) {
        String text=null;
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            text=sBody;
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
