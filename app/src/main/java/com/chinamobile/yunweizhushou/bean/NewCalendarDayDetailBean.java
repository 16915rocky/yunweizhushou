package com.chinamobile.yunweizhushou.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewCalendarDayDetailBean  implements Parcelable{
	private String busi_date;
	private String busi_level;
	private String busi_name;
	private String busi_system;
	private String handler;
	private String menuVal;
	private String remark;

	protected NewCalendarDayDetailBean(Parcel in) {
		busi_date = in.readString();
		busi_level = in.readString();
		busi_name = in.readString();
		busi_system = in.readString();
		handler = in.readString();
		menuVal = in.readString();
		remark = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(busi_date);
		dest.writeString(busi_level);
		dest.writeString(busi_name);
		dest.writeString(busi_system);
		dest.writeString(handler);
		dest.writeString(menuVal);
		dest.writeString(remark);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<NewCalendarDayDetailBean> CREATOR = new Creator<NewCalendarDayDetailBean>() {
		@Override
		public NewCalendarDayDetailBean createFromParcel(Parcel in) {
			return new NewCalendarDayDetailBean(in);
		}

		@Override
		public NewCalendarDayDetailBean[] newArray(int size) {
			return new NewCalendarDayDetailBean[size];
		}
	};

	public String getBusi_date() {
		return busi_date;
	}

	public String getBusi_level() {
		return busi_level;
	}

	public String getBusi_name() {
		return busi_name;
	}

	public String getBusi_system() {
		return busi_system;
	}

	public String getHandler() {
		return handler;
	}

	public String getMenuVal() {
		return menuVal;
	}

	public String getRemark() {
		return remark;
	}

	public void setBusi_date(String busi_date) {
		this.busi_date = busi_date;
	}

	public void setBusi_level(String busi_level) {
		this.busi_level = busi_level;
	}

	public void setBusi_name(String busi_name) {
		this.busi_name = busi_name;
	}

	public void setBusi_system(String busi_system) {
		this.busi_system = busi_system;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public void setMenuVal(String menuVal) {
		this.menuVal = menuVal;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
