package com.chinamobile.yunweizhushou.bean;

public class LoginBean {
	private String sessionId;
	private String userName;
	private String content;
	private String deviceId;
	private String useType;
	private String phone;
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public String getContent() {
		return content;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getUseType() {
		return useType;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

}
