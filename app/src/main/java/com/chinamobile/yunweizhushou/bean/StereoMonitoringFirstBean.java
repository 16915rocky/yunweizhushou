package com.chinamobile.yunweizhushou.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class StereoMonitoringFirstBean  {
	

	private String app;
	private String busi;
	private String db;
	private String inter;
	private String name;
	private String value;
	
//	public StereoMonitoringFirstBean(Parcel in){
//		
//		app=in.readString();
//		busi=in.readString();
//		db=in.readString();
//		inter=in.readString();
//		name=in.readString();
//		name=in.readString();
//	
//	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getBusi() {
		return busi;
	}
	public void setBusi(String busi) {
		this.busi = busi;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getInter() {
		return inter;
	}
	public void setInter(String inter) {
		this.inter = inter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		
//		dest.writeString(app);
//		dest.writeString(busi);
//		dest.writeString(db);
//		dest.writeString(name);
//		dest.writeString(value);
//		
//	}
//	
//	public static final Parcelable.Creator<StereoMonitoringFirstBean> CREATOR = new Parcelable.Creator<StereoMonitoringFirstBean>() {  
//	        public StereoMonitoringFirstBean createFromParcel(Parcel in) {  
//	            return new StereoMonitoringFirstBean(in);  
//	        }  
//	          
//	        public StereoMonitoringFirstBean[] newArray(int size) {  
//	            return new StereoMonitoringFirstBean[size];  
//	        }  
//	  };  
	
}
