package com.example.bipl.data;

/**
 * Created by fahad on 5/4/2017.
 */

public class AccountBean {
    private String mId;
    private String oId;
    private String uId;
    private String amount;
    private String cnic;
    private byte thumb;
    private String processCode;
    private String errorCode;
    private String errorDesc;
    private String description;
    private Long trxNoImal;
    private Long trxNoPoints;
    private int appId;
    private Long productId;
    private String token;
    private String flag;
    private String locationX;
    private String locationY;
    private String accNum;
    private String accTitle;
    private String points;
    private String pointsWorth;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public byte getThumb() {
        return thumb;
    }

    public void setThumb(byte thumb) {
        this.thumb = thumb;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTrxNoImal() {
        return trxNoImal;
    }

    public void setTrxNoImal(Long trxNoImal) {
        this.trxNoImal = trxNoImal;
    }

    public Long getTrxNoPoints() {
        return trxNoPoints;
    }

    public void setTrxNoPoints(Long trxNoPoints) {
        this.trxNoPoints = trxNoPoints;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getAccTitle() {
        return accTitle;
    }

    public void setAccTitle(String accTitle) {
        this.accTitle = accTitle;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPointsWorth() {
        return pointsWorth;
    }

    public void setPointsWorth(String pointsWorth) {
        this.pointsWorth = pointsWorth;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "mId='" + mId + '\'' +
                ", oId='" + oId + '\'' +
                ", uId='" + uId + '\'' +
                ", amount='" + amount + '\'' +
                ", cnic='" + cnic + '\'' +
                ", thumb=" + thumb +
                ", processCode='" + processCode + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                ", description='" + description + '\'' +
                ", trxNoImal=" + trxNoImal +
                ", trxNoPoints=" + trxNoPoints +
                ", appId=" + appId +
                ", productId=" + productId +
                ", token='" + token + '\'' +
                ", flag='" + flag + '\'' +
                ", locationX='" + locationX + '\'' +
                ", locationY='" + locationY + '\'' +
                ", accNum='" + accNum + '\'' +
                ", accTitle='" + accTitle + '\'' +
                ", points='" + points + '\'' +
                ", pointsWorth='" + pointsWorth + '\'' +
                '}';
    }
}
