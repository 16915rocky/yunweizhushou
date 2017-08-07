package com.chinamobile.yunweizhushou.bean;

public class ResponseBean {
	private String MSG = "";
	private String DATA = "";

	private String TOTAL = "";

	public String getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(String tOTAL) {
		TOTAL = tOTAL;
	}

	public boolean isSuccess() {
		return getMSG().equals("true");
	}

	public String getMSG() {
		return MSG;
	}

	public String getDATA() {
		return DATA;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	public void setDATA(String dATA) {
		DATA = dATA;
	}

}
