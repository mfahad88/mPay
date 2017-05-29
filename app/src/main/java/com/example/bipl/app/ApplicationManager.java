package com.example.bipl.app;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;


import com.example.bipl.data.AccountBean;
import com.example.bipl.data.PaymentBean;
import com.example.bipl.data.TrxBean;
import com.example.bipl.data.UserBean;
import com.example.bipl.data.UserLoginBean;
import com.example.bipl.mpay.R;


import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by fahad on 4/28/2017.
 */

public class ApplicationManager {
    private final static String NAMESPACE = "http://ws.bi.com/";
    //private final static String URL = "http://10.200.14.10:9082/BIPOS_WS/BiPosWSService";
    private final static String URL = "http://10.7.255.77:59084/BIPOS_WS/BiPosWSService";
//      private final static String URL = "http://192.168.161.1:59084/BIPOS_WS/BiPosWSService";
    private final static String SOAP_ACTION = "http://ws.bi.com/";
    private final static int TIMEOUT=10000;
    private final static String UNAME="adnan";
    private final static String UPASS="adnan123";

    @SuppressLint("LongLogTag")
    public static UserLoginBean Login(String username, String password, Integer appId){

        String METHOD_NAME = "LOGIN";
        HttpTransportSE httpTransportSE = null;
        UserLoginBean userLoginBean=new UserLoginBean();
        UserBean userBean=new UserBean();
        try {
            httpTransportSE = new HttpTransportSE(URL, TIMEOUT);
           // Thread.sleep(1);
           if(httpTransportSE.getServiceConnection().getResponseCode()==415) {
               SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
               SoapObject object = new SoapObject();
               object.addProperty("appId", appId);
               object.addProperty("loginId", username);
               object.addProperty("password", password);
               request.addProperty("arg0", object);

               SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
               envelope.setOutputSoapObject(request);

               httpTransportSE.debug = true;
               List<HeaderProperty> list = new ArrayList<>();
               list.add(new HeaderProperty("uname", UNAME));
               list.add(new HeaderProperty("pass", UPASS));
               httpTransportSE.call(SOAP_ACTION, envelope, list);


               SoapObject soapObject = (SoapObject) envelope.getResponse();
              //  Log.e("Request>>>>>",httpTransportSE.requestDump);
               if (Integer.parseInt(soapObject.getPropertyAsString("errorCode")) == 0 && Integer.parseInt(soapObject.getPropertyAsString("status")) == 1) {
                   Log.e("TOKEN", soapObject.getPropertyAsString("token"));
                   SoapObject soapobjectUser = (SoapObject) soapObject.getProperty("user");
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
               } else {
                   userLoginBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                   userLoginBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                   userLoginBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                   userLoginBean.setStatus(Integer.parseInt(soapObject.getPropertyAsString("status")));
               }
           }else {
              userLoginBean.setErrorCode("Check your internet connection");
              userLoginBean.setErrorCode("-1");
              userLoginBean.setStatus(-1);
           }
        } catch (Exception e) {
            userLoginBean.setErrorDesc("Check your internet connection");
            userLoginBean.setErrorCode("-1");
            userLoginBean.setStatus(-1);
           // Log.e("IOException>>>>>>>",e.getMessage());
            e.printStackTrace();
        }  finally {
            if(httpTransportSE!=null){
                httpTransportSE.reset();
                try {
                    httpTransportSE.getServiceConnection().disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return userLoginBean;
    }

    @SuppressLint("LongLogTag")
    public static Boolean Logout(String token, String loginId, Integer appId,String mId,String oId,String uId){
        Boolean status=false;
        String METHOD_NAME = "LOGOUT";
        HttpTransportSE httpTransportSE = null;
        UserLoginBean userLoginBean=new UserLoginBean();
        UserBean userBean=new UserBean();
        try {
            httpTransportSE = new HttpTransportSE(URL, TIMEOUT);
            if(httpTransportSE.getServiceConnection().getResponseCode()==415) {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SoapObject object = new SoapObject();

                object.addProperty("appId", appId);
                object.addProperty("loginId", loginId);
                object.addProperty("token", token);
                object.addProperty("mId", mId);
                object.addProperty("oId", oId);
                object.addProperty("uId", uId);
                request.addProperty("arg0", object);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                Thread.sleep(2000);
                httpTransportSE.debug = true;
                List<HeaderProperty> list = new ArrayList<>();
                list.add(new HeaderProperty("uname", UNAME));
                list.add(new HeaderProperty("pass", UPASS));
                httpTransportSE.call(SOAP_ACTION, envelope, list);


                SoapObject soapObject = (SoapObject) envelope.getResponse();
               // Log.e("Response>>>>>",httpTransportSE.responseDump);
                if (Integer.parseInt(soapObject.getPropertyAsString("errorCode")) == 0 && Integer.parseInt(soapObject.getPropertyAsString("status")) == 1) {
                    status=true;
                }
            }else {
                userLoginBean.setErrorCode("Check your internet connection");
                userLoginBean.setErrorCode("-1");
                userLoginBean.setStatus(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            userLoginBean.setErrorDesc(e.getMessage());
            userLoginBean.setErrorCode("-1");
            userLoginBean.setStatus(-1);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            userLoginBean.setErrorDesc(e.getMessage());
            userLoginBean.setErrorCode("-1");
            userLoginBean.setStatus(-1);

        } finally {
            if(httpTransportSE!=null){
                httpTransportSE.reset();
                try {
                    httpTransportSE.getServiceConnection().disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return status;
    }



    @SuppressLint("LongLogTag")
    public static AccountBean Account_TRX(TrxBean trxBean){
        String METHOD_NAME = "TRX";

        AccountBean accountBean = null;
        HttpTransportSE httpTransportSE=null;
        try {

            httpTransportSE = new HttpTransportSE(URL, TIMEOUT);
            if(httpTransportSE.getServiceConnection().getResponseCode()==415) {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SoapObject object = new SoapObject();
                Thread.sleep(2000);

                object.addProperty("appId", trxBean.getAppId());
                object.addProperty("cnic", trxBean.getCnic());
                object.addProperty("thumb", trxBean.getThumb());
                object.addProperty("flag", trxBean.getFlag());
                object.addProperty("token", trxBean.getToken());
                object.addProperty("mId", trxBean.getmId());
                object.addProperty("oId", trxBean.getoId());
                object.addProperty("uId", trxBean.getuId());
                object.addProperty("amount", trxBean.getAmount());
                object.addProperty("processCode", trxBean.getProcessCode());
                object.addProperty("productId", trxBean.getProductId());
                object.addProperty("description", trxBean.getDescription());
                request.addProperty("arg0", object);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //envelope.encodingStyle=envelope.env;
                envelope.setOutputSoapObject(request);
                Log.e("Request>>>>>>>>>>>",request.toString());
                httpTransportSE.debug = true;
                List<HeaderProperty> list = new ArrayList<>();
                list.add(new HeaderProperty("uname", UNAME));
                list.add(new HeaderProperty("pass", UPASS));
                new MarshalBase64().register(envelope);
                httpTransportSE.call(SOAP_ACTION, envelope, list);
                Log.e("Account_TRX_Request>>>>>>>>>>>",httpTransportSE.requestDump);
                SoapObject soapObject = (SoapObject) envelope.getResponse();


                if (trxBean.getFlag().equalsIgnoreCase("DF")) {
                    accountBean = new AccountBean();
                    if (Integer.parseInt(soapObject.getPropertyAsString("errorCode")) == 0) {
                        accountBean.setAccNum(soapObject.getPropertyAsString("accNum"));
                        accountBean.setAccTitle(soapObject.getPropertyAsString("accTitle"));
                        accountBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                        accountBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                        accountBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                        accountBean.setPoints(soapObject.getPropertyAsString("points"));
                        accountBean.setPointsWorth(soapObject.getPropertyAsString("pointsWorth"));
                    } else {
                        accountBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                        accountBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                        accountBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                        accountBean.setProcessCode(soapObject.getPropertyAsString("processCode"));
                        accountBean.setThumb(Byte.parseByte(soapObject.getPropertyAsString("thumb")));
                    }
                }

                if (trxBean.getFlag().equalsIgnoreCase("TRX")) {
                    accountBean = new AccountBean();
                    if (Integer.parseInt(soapObject.getPropertyAsString("errorCode")) == 0) {
                        accountBean.setAmount(soapObject.getPropertyAsString("amount"));
                        accountBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                        accountBean.setCnic(soapObject.getPropertyAsString("cnic"));
                        accountBean.setDescription(soapObject.getPropertyAsString("description"));
                        accountBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                        accountBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                        accountBean.setProcessCode(soapObject.getPropertyAsString("processCode"));
                        accountBean.setProductId(Long.valueOf(soapObject.getPropertyAsString("productId")));
                        //accountBean.setThumb(soapObject.getProperty("thumb"));
                        accountBean.setTrxNoImal(soapObject.getPropertyAsString("trxNoImal"));
                        /*accountBean.setTrxNoImal(Long.valueOf(soapObject.getPropertyAsString("trxNoImal")));*/
                        accountBean.setmId(soapObject.getPropertyAsString("mId"));
                        accountBean.setoId(soapObject.getPropertyAsString("oId"));
                        accountBean.setuId(soapObject.getPropertyAsString("uId"));

                    } else {
                        accountBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                        accountBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                        accountBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                        accountBean.setProcessCode(soapObject.getPropertyAsString("processCode"));
                        //accountBean.setThumb(Byte.parseByte(soapObject.getPropertyAsString("thumb")));
                    }
                }

                if (trxBean.getFlag().equalsIgnoreCase("LOY_TRX")) {
                    accountBean = new AccountBean();
                    if (Integer.parseInt(soapObject.getPropertyAsString("errorCode")) == 0) {
                        Log.e("TrxPoints>>>>>>>>>>>>>",soapObject.getPropertyAsString("trxNoPoints"));
                        accountBean.setAmount(soapObject.getPropertyAsString("amount"));
                        accountBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                        accountBean.setCnic(soapObject.getPropertyAsString("cnic"));
                        //accountBean.setDescription(soapObject.getPropertyAsString("description"));
                        accountBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                        //accountBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                        accountBean.setProcessCode(soapObject.getPropertyAsString("processCode"));
                        accountBean.setProductId(Long.valueOf(soapObject.getPropertyAsString("productId")));
//                        accountBean.setThumb(Byte.parseByte(soapObject.getPropertyAsString("thumb")));
                        if(soapObject.getPropertyAsString("trxNoPoints").equalsIgnoreCase("0")){
                            accountBean.setTrxNoImal(soapObject.getPropertyAsString("trxNoImal"));
                        }else{
                            accountBean.setTrxNoImal(soapObject.getPropertyAsString("trxNoPoints"));
                        }
                        accountBean.setmId(soapObject.getPropertyAsString("mId"));
                        accountBean.setoId(soapObject.getPropertyAsString("oId"));
                        accountBean.setuId(soapObject.getPropertyAsString("uId"));

                    } else {
                        accountBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                        accountBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                        accountBean.setErrorDesc(soapObject.getPropertyAsString("errorDesc"));
                        accountBean.setProcessCode(soapObject.getPropertyAsString("processCode"));
                        accountBean.setThumb(Byte.parseByte(soapObject.getPropertyAsString("thumb")));
                    }
                }
            }else {
                accountBean.setErrorCode("Check your internet connection");
                accountBean.setErrorCode("-1");

            }
            Log.e("Account_TRX_Response>>>>>>>>>>>",httpTransportSE.responseDump);
        }catch (IOException e) {
            Log.e("IOException>>>>>>>",e.getMessage());
            accountBean.setErrorDesc(e.getMessage());
            accountBean.setErrorCode("-1");
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            accountBean.setErrorDesc(e.getMessage());
            accountBean.setErrorCode("-1");
        } finally {
            if(httpTransportSE!=null){
                httpTransportSE.reset();
                try {
                    httpTransportSE.getServiceConnection().disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return accountBean;
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


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE=new HttpTransportSE(URL,20000);
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
