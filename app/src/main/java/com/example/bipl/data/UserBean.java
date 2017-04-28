package com.example.bipl.data;

public class UserBean {
	private String mId;
	private String oId;
	private String uId;
	private String userName;
	private String userAddress;
	private String userContactNo;
	private Double locationX;
	private Double locationY;
	private Double areaAllowed;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserContactNo() {
		return userContactNo;
	}
	public void setUserContactNo(String userContactNo) {
		this.userContactNo = userContactNo;
	}
	public Double getLocationX() {
		return locationX;
	}
	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}
	public Double getLocationY() {
		return locationY;
	}
	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}
	public Double getAreaAllowed() {
		return areaAllowed;
	}
	public void setAreaAllowed(Double areaAllowed) {
		this.areaAllowed = areaAllowed;
	}
	@Override
	public String toString() {
		return "UserBean [mId=" + mId + ", oId=" + oId + ", uId=" + uId + ", userName=" + userName + ", userAddress="
				+ userAddress + ", userContactNo=" + userContactNo + ", locationX=" + locationX + ", locationY="
				+ locationY + ", areaAllowed=" + areaAllowed + "]";
	}
	
	
	
	
	
	

}
