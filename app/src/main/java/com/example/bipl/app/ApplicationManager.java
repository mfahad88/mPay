package com.example.bipl.app;

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

/**
 * Created by fahad on 4/28/2017.
 */

public class ApplicationManager {
    private final static String NAMESPACE = "http://ws.bi.com/";
    private final static String URL = "http://192.168.161.1:59084/BIPOS_WS/BiPosWSService";


    public static UserLoginBean Login(String username,String password,Integer appId){
        Boolean status=false;
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

            if(Integer.parseInt(soapObject.getProperty("errorCode").toString())==0 && Integer.parseInt(soapObject.getProperty("status").toString())==1) {


                userLoginBean.setAppId(Integer.parseInt(soapObject.getProperty("appId").toString()));
                userLoginBean.setErrorCode(soapObject.getProperty("errorCode").toString());
                userLoginBean.setErrorDesc(soapObject.getProperty("errorDesc").toString());
                userLoginBean.setLoginId(soapObject.getProperty("loginId").toString());
                userLoginBean.setStatus(Integer.parseInt(soapObject.getProperty("status").toString()));
                userLoginBean.setToken(soapObject.getProperty("token").toString());
               /* userBean.setAreaAllowed(Double.valueOf(soapObject.getPropertyAsString("user")));
                userBean.setLocationX(Double.valueOf(soapObject.getProperty("locationX").toString()));
                userBean.setLocationY(Double.valueOf(soapObject.getProperty("locationY").toString()));
                userBean.setUserAddress(soapObject.getProperty("userAddress").toString());
                userBean.setUserContactNo(soapObject.getProperty("userContactNo").toString());
                userBean.setUserName(soapObject.getProperty("userName").toString());
                userBean.setmId(soapObject.getProperty("mId").toString());
                userBean.setoId(soapObject.getProperty("oId").toString());
                userBean.setuId(soapObject.getProperty("uId").toString());
                userLoginBean.setUser(userBean);*/


                //Log.e("LOGIN>>>>>>>>>", String.valueOf(soapObject1));
                return userLoginBean;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return userLoginBean;
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
