package com.example.bipl.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


import com.example.bipl.data.AccountBean;
import com.example.bipl.data.TrxBean;
import com.example.bipl.data.UserBean;
import com.example.bipl.data.UserLoginBean;
import com.example.bipl.mpay.R;


import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsServiceConnectionSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.ksoap2.transport.KeepAliveHttpsTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import static android.content.ContentValues.TAG;


/**
 * Created by fahad on 4/28/2017.
 */

public class ApplicationManager {
    private final static String NAMESPACE = "http://ws.bi.com/";
     private final static String _URL = "10.7.255.77";
    private final static int _PORT = 8181;
    private final static String SOAP_ACTION = "http://ws.bi.com/";
    private final static int TIMEOUT = 100000;
    private final static String UNAME = "biposws";
    private final static String UPASS = "biposws123";


        @SuppressLint("LongLogTag")
        public static UserLoginBean Login(String username, String password, Integer appId, Context ctnx) {

            String METHOD_NAME = "LOGIN";

            HttpsTransportSE httpTransportSE = null;
            UserLoginBean userLoginBean = new UserLoginBean();
            UserBean userBean = new UserBean();
            try {
                httpTransportSE = new HttpsTransportSE(_URL, _PORT, "/BIPOS_WS/BiPosWSService?wsdl", TIMEOUT);
                SslRequest.allowAllSSL();
                ((HttpsServiceConnectionSE) httpTransportSE.getServiceConnection()).setSSLSocketFactory(trustAllHosts(ctnx).getSocketFactory());
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

                httpTransportSE.call(SOAP_ACTION, envelope,list);

                Log.e("Request>>>>>", httpTransportSE.requestDump);

                SoapObject soapObject = (SoapObject) envelope.getResponse();

                Log.e("Response>>>>>", httpTransportSE.responseDump);

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
            } catch (Exception e) {
                userLoginBean.setErrorDesc("Check your internet connection");
                userLoginBean.setErrorCode("-1");
                userLoginBean.setStatus(-1);
                Log.e("IOException>>>>>>>", e.getMessage());
                e.printStackTrace();
            } finally {
                if (httpTransportSE != null) {
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
        public static Boolean Logout(String token, String loginId, Integer appId, String mId, String oId, String uId,Context ctnx) {
            Boolean status = false;
            String METHOD_NAME = "LOGOUT";
            HttpsTransportSE httpTransportSE = null;
            UserLoginBean userLoginBean = new UserLoginBean();
            UserBean userBean = new UserBean();
            try {
                httpTransportSE = new HttpsTransportSE(_URL, _PORT, "/BIPOS_WS/BiPosWSService?wsdl", TIMEOUT);
                SslRequest.allowAllSSL();
                ((HttpsServiceConnectionSE) httpTransportSE.getServiceConnection()).setSSLSocketFactory(trustAllHosts(ctnx).getSocketFactory());
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

                Log.e("Request>>>>>", httpTransportSE.requestDump);

                SoapObject soapObject = (SoapObject) envelope.getResponse();

                Log.e("Response>>>>>", httpTransportSE.responseDump);
                if (Integer.parseInt(soapObject.getPropertyAsString("errorCode")) == 0 && Integer.parseInt(soapObject.getPropertyAsString("status")) == 1) {
                    status = true;
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
                if (httpTransportSE != null) {
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
        public static AccountBean Account_TRX(TrxBean trxBean,Context ctnx) {
            String METHOD_NAME = "TRX";

            AccountBean accountBean = null;
            HttpsTransportSE httpTransportSE = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapObject object = new SoapObject();
            SoapObject soapObject = null;
            try {

                httpTransportSE = new HttpsTransportSE(_URL, _PORT, "/BIPOS_WS/BiPosWSService?wsdl", TIMEOUT);
                SslRequest.allowAllSSL();
                ((HttpsServiceConnectionSE) httpTransportSE.getServiceConnection()).setSSLSocketFactory(trustAllHosts(ctnx).getSocketFactory());

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
              //  object.addProperty("stan", trxBean.getStan());
                request.addProperty("arg0", object);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                httpTransportSE.debug = true;
                List<HeaderProperty> list = new ArrayList<>();
                list.add(new HeaderProperty("uname", UNAME));
                list.add(new HeaderProperty("pass", UPASS));
                new MarshalBase64().register(envelope);
                httpTransportSE.call(SOAP_ACTION, envelope, list);
                Log.e("Request>>>>>", httpTransportSE.requestDump);

                soapObject = (SoapObject) envelope.getResponse();

                Log.e("Response>>>>>", httpTransportSE.responseDump);


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
                        accountBean.setTrxNoImal(soapObject.getPropertyAsString("trxNoImal"));
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

                if (trxBean.getFlag().equalsIgnoreCase("LOY_TRX")) {
                    accountBean = new AccountBean();
                    if (Integer.parseInt(soapObject.getPropertyAsString("errorCode")) == 0) {
                        Log.e("TrxPoints>>>>>>>>>>>>>", soapObject.getPropertyAsString("trxNoPoints"));
                        accountBean.setAmount(soapObject.getPropertyAsString("amount"));
                        accountBean.setAppId(Integer.parseInt(soapObject.getPropertyAsString("appId")));
                        accountBean.setCnic(soapObject.getPropertyAsString("cnic"));
                        accountBean.setErrorCode(soapObject.getPropertyAsString("errorCode"));
                        accountBean.setProcessCode(soapObject.getPropertyAsString("processCode"));
                        accountBean.setProductId(Long.valueOf(soapObject.getPropertyAsString("productId")));
                        if (soapObject.getPropertyAsString("trxNoPoints").equalsIgnoreCase("0")) {
                            accountBean.setTrxNoImal(soapObject.getPropertyAsString("trxNoImal"));
                        } else {
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
            } catch (Exception e) {
                Log.e("Exception>>>>>>>", e.getMessage());
                accountBean.setErrorDesc("Connection timeout");
                accountBean.setErrorCode("-1");
                e.printStackTrace();
            } finally {
                if (httpTransportSE != null) {
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


        public static void helloWorld(String name) {
            Boolean status = false;
            String METHOD_NAME = "getHelloWorldAsString";
            String SOAP_ACTION = "http://ws.bi.com/getHelloWorldAsString";
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                PropertyInfo pi = new PropertyInfo();
                pi.setName("arg0");
                pi.setValue(name);
                pi.setType(String.class);
                request.addProperty(pi);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE = new HttpTransportSE(_URL, 20000);
                httpTransportSE.debug = true;
                List<HeaderProperty> list = new ArrayList<>();
                list.add(new HeaderProperty("uname", "adnan"));
                list.add(new HeaderProperty("pass", "adnan123"));
                httpTransportSE.call(SOAP_ACTION, envelope, list);
                Log.e("LOGIN>>>>>>>>>", envelope.getResponse().toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }

        public static boolean isInternetWorking() {
            boolean success = false;
            try {
                URL url = new URL("http://google.com");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(500);
                connection.connect();
                success = connection.getResponseCode() == 200;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;
        }


        protected static SSLContext trustAllHosts(Context context) {
            return allowAllSSL(context);
        }

        @SuppressLint("LongLogTag")
        public static SSLContext allowAllSSL(Context cntx) {
            SSLContext context = null;
            TrustManager[] trustManagers = null;
            KeyManagerFactory mgrFact;
            try {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                mgrFact = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

                KeyStore keyStore = KeyStore.getInstance("pkcs12");
                InputStream in = cntx.getResources().openRawResource(R.raw.mobileapp);
                try {
                    keyStore.load(in, "123456".toCharArray());
                    mgrFact.init(keyStore, "123456".toCharArray());
                } catch (CertificateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    in.close();
                }
                tmf.init(keyStore);


                if (trustManagers == null) {
                    trustManagers = new TrustManager[]{new FakeX509TrustManager()};
                }

                final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        System.out.println("getAcceptedIssuers");
                        return null;
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        System.out.println("Сведения о сертификате : " + chain[0].getIssuerX500Principal().getName() + "\n Тип авторизации : " + authType);
                    }

                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        System.out.println("checkClientTrusted : " + authType);
                    }
                }};
                try {
                    context = SSLContext.getInstance("TLS");
                    context.init(mgrFact.getKeyManagers(), trustAllCerts, new SecureRandom());
                } catch (NoSuchAlgorithmException e) {
                    Log.e("NoSuchAlgorithmException", e.getMessage());
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    Log.e("KeyManagementException", e.getMessage());
                    e.printStackTrace();
                }

                HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        Log.e("Hostname>>>>>>>>",hostname);
                        return true;
                    }
                });
            } catch (Exception ex) {
                Log.e("Security>>>>>>>>", "allowAllSSL failed: " + ex.toString());
            }
            return context;
        }
    }
