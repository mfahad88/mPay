package com.example.bipl.data;

/**
 * Created by fahad on 5/4/2017.
 */

public class PaymentBean {
    private int appId;
    private String cnic;
    private String flag;
    private String thumb;
    private String token;
    private String mId;
    private String oId;
    private String uId;
    private String description;
    private int processCode;
    private int productId;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProcessCode() {
        return processCode;
    }

    public void setProcessCode(int processCode) {
        this.processCode = processCode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "PaymentBean{" +
                "appId=" + appId +
                ", cnic='" + cnic + '\'' +
                ", flag='" + flag + '\'' +
                ", thumb='" + thumb + '\'' +
                ", token='" + token + '\'' +
                ", mId='" + mId + '\'' +
                ", oId='" + oId + '\'' +
                ", uId='" + uId + '\'' +
                ", description='" + description + '\'' +
                ", processCode=" + processCode +
                ", productId=" + productId +
                ", amount='" + amount + '\'' +
                '}';
    }
}
