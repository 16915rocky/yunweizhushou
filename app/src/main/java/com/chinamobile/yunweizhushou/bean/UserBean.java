package com.chinamobile.yunweizhushou.bean;

import java.io.Serializable;

public class UserBean implements Serializable {

	private String userName;
	private String password;
	private String sessionId;
	private String nickName;

	private String gesture;
	private String phone;

	private String deviceId ;
	private String useType ;
	private String content;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getUseType() {
		return useType;
	}

	public String getGesture() {
		return gesture;
	}

	public void setGesture(String gesture) {
		this.gesture = gesture;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "UserBean{" +
				"userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", sessionId='" + sessionId + '\'' +
				", gesture='" + gesture + '\'' +
				", phone='" + phone + '\'' +
				", deviceId='" + deviceId + '\'' +
				", useType='" + useType + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
