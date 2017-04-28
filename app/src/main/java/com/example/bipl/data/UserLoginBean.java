package com.example.bipl.data;

public class UserLoginBean {
	
	private String token;
	private String loginId;
	private String password;
	private int status;
	private String errorCode;
	private String errorDesc;
	private int appId;

	private UserBean user;

	

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserLoginBean [token=" + token + ", loginId=" + loginId + ", password=" + password + ", status=" + status
				+ ", errorCode=" + errorCode + ", errorDesc=" + errorDesc + ", appId=" + appId + ", user=" + user + "]";
	}

	

	


	
	
	
	
	
}
