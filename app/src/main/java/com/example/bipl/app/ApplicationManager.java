package com.example.bipl.app;

import android.annotation.SuppressLint;
import android.util.Log;


import com.example.bipl.data.AccountBean;
import com.example.bipl.data.PaymentBean;
import com.example.bipl.data.TrxBean;
import com.example.bipl.data.UserBean;
import com.example.bipl.data.UserLoginBean;


import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
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
    private final static String URL = "http://192.168.161.1:59084/BIPOS_WS/BiPosWSService";


    @SuppressLint("LongLogTag")
    public static UserLoginBean Login(String username, String password, Integer appId){

        String METHOD_NAME = "LOGIN";
        String SOAP_ACTION = "http://ws.bi.com/";
        UserLoginBean userLoginBean=new UserLoginBean();
        UserBean userBean=new UserBean();
        try {


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapObject object = new SoapObject();

            object.addProperty("appId", appId);
            object.addProperty("loginId", username);
            object.addProperty("password", password);
            request.addProperty("arg0", object);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL, 5000);
            //Thread.sleep(2000);
            httpTransportSE.debug = true;
            List<HeaderProperty> list = new ArrayList<>();
            list.add(new HeaderProperty("uname", "adnan"));
            list.add(new HeaderProperty("pass", "adnan123"));
            httpTransportSE.call(SOAP_ACTION, envelope, list);
            SoapObject soapObject = (SoapObject) envelope.getResponse();

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

        } catch (HttpResponseException e) {
            Log.e("HttpResponseException>>>>>>>",e.getMessage());
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            Log.e("SoapFault>>>>>>>",soapFault.getMessage());
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.e("XmlPullParserException>>>>>>>",e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            userLoginBean.setErrorDesc(e.getMessage());
            userLoginBean.setErrorCode("-1");
            userLoginBean.setStatus(-1);
            Log.e("IOException>>>>>>>",e.getMessage());
            e.printStackTrace();
        }

        return userLoginBean;
    }



    public static AccountBean Account_TRX(TrxBean trxBean){
        String METHOD_NAME = "TRX";
        String SOAP_ACTION = "http://ws.bi.com/";
        AccountBean accountBean = null;

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapObject object = new SoapObject();


            object.addProperty("appId", trxBean.getAppId());
            object.addProperty("cnic", trxBean.getCnic());
            object.addProperty("thumb", "");
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
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.debug = true;
            List<HeaderProperty> list = new ArrayList<>();
            list.add(new HeaderProperty("uname", "adnan"));
            list.add(new HeaderProperty("pass", "adnan123"));

            httpTransportSE.call(SOAP_ACTION, envelope, list);
            SoapObject soapObject = (SoapObject) envelope.getResponse();
            Log.e("Response>>>>>>>>>>", soapObject.toString());
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
                    accountBean.setThumb(Byte.parseByte(soapObject.getPropertyAsString("thumb")));
                    accountBean.setTrxNoImal(Long.valueOf(soapObject.getPropertyAsString("trxNoImal")));
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
        }catch (IOException e) {
            accountBean.setErrorDesc(e.getMessage());
            accountBean.setErrorCode("-1");

            Log.e("IOException>>>>>>>",e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return accountBean;
    }

  /*  public static int Payment(PaymentBean paymentBean){
        String METHOD_NAME = "TRX";
        String SOAP_ACTION = "http://ws.bi.com/";
        int trxNo=0;

        try{
            SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
            SoapObject object=new SoapObject();


            object.addProperty("appId",paymentBean.getAppId());
            object.addProperty("cnic",paymentBean.getCnic());
            object.addProperty("thumb","");
            object.addProperty("flag",paymentBean.getFlag());
            object.addProperty("token",paymentBean.getToken());
            object.addProperty("mId",paymentBean.getmId());
            object.addProperty("oId",paymentBean.getoId());
            object.addProperty("uId",paymentBean.getuId());
            object.addProperty("amount",paymentBean.getAmount());
            object.addProperty("processCode",paymentBean.getProcessCode());
            object.addProperty("productId",paymentBean.getProductId());
            object.addProperty("description",paymentBean.getDescription());
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
            Log.e("Request>>>>>>>>>>>>>",httpTransportSE.requestDump);
            Log.e("Response>>>>>>>>>>>>>",httpTransportSE.responseDump);
            if(soapObject.getPropertyAsString("errorCode").equals("0")&& soapObject.getPropertyAsString("processCode").equals("1")){
                trxNo= Integer.parseInt(soapObject.getPropertyAsString("trxNoImal"));
                Log.e("Payment>>>>>>>>",soapObject.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return trxNo;
    }*/

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
