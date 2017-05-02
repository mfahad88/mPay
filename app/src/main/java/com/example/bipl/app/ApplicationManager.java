package com.example.bipl.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.bipl.data.UserBean;
import com.example.bipl.data.UserLoginBean;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fahad on 4/28/2017.
 */

public class ApplicationManager {
    private final static String NAMESPACE = "http://ws.bi.com/";
    private final static String URL = "http://10.7.255.77:59084/BIPOS_WS/BiPosWSService";


    public static UserLoginBean Login(String username,String password,Integer appId){

        String METHOD_NAME = "LOGIN";
        String SOAP_ACTION = "http://ws.bi.com/";
        UserLoginBean userLoginBean=new UserLoginBean();
        UserBean userBean=new UserBean();
        try{


            SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
            SoapObject object=new SoapObject();

            object.addProperty("appId",appId);
            object.addProperty("loginId",username);
            object.addProperty("password",password);
            request.addProperty("arg0",object);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE=new HttpTransportSE(URL);
            httpTransportSE.debug=true;
            List<HeaderProperty> list=new ArrayList<>();
            list.add(new HeaderProperty("uname","adnan"));
            list.add(new HeaderProperty("pass","adnan123"));
            httpTransportSE.call(SOAP_ACTION, envelope,list);
            SoapObject soapObject=(SoapObject)envelope.getResponse();

            SoapObject soapobjectUser=(SoapObject)soapObject.getProperty("user");

            if(Integer.parseInt(soapObject.getPropertyAsString("errorCode"))==0 && Integer.parseInt(soapObject.getPropertyAsString("status"))==1) {
                Log.e("TOKEN",soapObject.getPropertyAsString("token"));

                userLoginBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                userLoginBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                userLoginBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                userLoginBean.setLoginId(soapObject.getPropertyAsString("loginId"));
                userLoginBean.setStatus(Integer.parseInt(soapObject.getPropertyAsString("status")));
                userLoginBean.setToken(soapObject.getPropertyAsString("token"));
                userBean.setAreaAllowed(Double.valueOf(soapobjectUser.getPropertyAsString("areaAllowed")));
                userBean.setLocationX(Double.valueOf(soapobjectUser.getPropertyAsString("locationX")));
                userBean.setLocationY(Double.valueOf(soapobjectUser.getPropertyAsString("locationY")));
                userBean.setUserAddress(soapobjectUser.getPropertyAsString("userAddress"));
                userBean.setUserContactNo(soapobjectUser.getPropertyAsString("userContactNo"));
                userBean.setUserName(soapobjectUser.getPropertyAsString("userName"));
                userBean.setmId(soapobjectUser.getPropertyAsString("mId"));
                userBean.setoId(soapobjectUser.getPropertyAsString("oId"));
                userBean.setuId(soapobjectUser.getPropertyAsString("uId"));
                userLoginBean.setUser(userBean);
                return userLoginBean;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return userLoginBean;
    }

    @SuppressLint("LongLogTag")
    public static void Account_TRX(Map<String,?> map){
        String METHOD_NAME = "ACCOUNT_TRX";
        String SOAP_ACTION = "http://ws.bi.com/";
        try{
            SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
            SoapObject object=new SoapObject();

            object.addProperty("amount",map.get("amount"));
            object.addProperty("cnic",map.get("cnic"));
            object.addProperty("description",map.get("description"));
            object.addProperty("errorCode",map.get("errorCode"));
            object.addProperty("processCode",map.get("processCode"));
            object.addProperty("productId",map.get("productId"));
            object.addProperty("thumb",map.get("thumb"));
            object.addProperty("token",map.get("token"));
            object.addProperty("mId",map.get("mId"));
            object.addProperty("oId",map.get("oId"));
            object.addProperty("uId",map.get("uId"));
            request.addProperty("arg0",object);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE=new HttpTransportSE(URL);
            httpTransportSE.debug=true;
            List<HeaderProperty> list=new ArrayList<>();
            list.add(new HeaderProperty("uname","adnan"));
            list.add(new HeaderProperty("pass","adnan123"));
            httpTransportSE.call(SOAP_ACTION, envelope,list);
            SoapObject soapObject=(SoapObject)envelope.getResponse();
            Log.e(">>>>>>>SoapObject<<<<<<<<<",soapObject.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e(">>>>>>>MAP<<<<<<<<",map.toString());
    }

    public static void helloWorld(String name){
        Boolean status=false;
        String METHOD_NAME = "getHelloWorldAsString";
        String SOAP_ACTION = "http://ws.bi.com/getHelloWorldAsString";
        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo pi = new PropertyInfo();
            pi.setName("arg0");
            pi.setValue(name);
            pi.setType(String.class);
            request.addProperty(pi);


            Log.e("Request>>>>>>>>",request.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE=new HttpTransportSE(URL);
            httpTransportSE.debug=true;
            List<HeaderProperty> list=new ArrayList<>();
            list.add(new HeaderProperty("uname","adnan"));
            list.add(new HeaderProperty("pass","adnan123"));
            httpTransportSE.call(SOAP_ACTION, envelope,list);
            Log.e("LOGIN>>>>>>>>>",envelope.getResponse().toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
