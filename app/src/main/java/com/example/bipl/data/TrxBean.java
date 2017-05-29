package com.example.bipl.data;

/**
 * Created by fahad on 5/2/2017.
 */

public class TrxBean {
    private String amount;
    private int appId;
    private String cnic;
    private String description;
    private String flag;
    private int processCode;
    private int productId;
    private byte[] thumb;
    private String token;
    private String mId;
    private String oId;
    private String uId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public byte[] getThumb() {
        return thumb;
    }

    public void setThumb(byte[] thumb) {
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

    @Override
    public String toString() {
        return "TrxBean{" +
                "amount='" + amount + '\'' +
                ", appId=" + appId +
                ", cnic='" + cnic + '\'' +
                ", description='" + description + '\'' +
                ", flag='" + flag + '\'' +
                ", processCode=" + processCode +
                ", productId=" + productId +
                ", thumb='" + thumb + '\'' +
                ", token='" + token + '\'' +
                ", mId='" + mId + '\'' +
                ", oId='" + oId + '\'' +
                ", uId='" + uId + '\'' +
                '}';
    }
}
