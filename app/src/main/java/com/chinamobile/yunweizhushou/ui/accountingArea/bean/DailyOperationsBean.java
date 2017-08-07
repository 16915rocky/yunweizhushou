package com.chinamobile.yunweizhushou.ui.accountingArea.bean;

public class DailyOperationsBean {

	private String MYFLAGID;
	private String TYPE;
	private String DTLURL;
	private String MYFLAG;
	private String IMAGEURL;
	private String PROCNAME;
	private int ID;

	public String getMYFLAGID() {
		return MYFLAGID;
	}

	public void setMYFLAGID(String mYFLAGID) {
		MYFLAGID = mYFLAGID;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getDTLURL() {
		return DTLURL;
	}

	public void setDTLURL(String dTLURL) {
		DTLURL = dTLURL;
	}

	public String getMYFLAG() {
		return MYFLAG;
	}

	public void setMYFLAG(String mYFLAG) {
		MYFLAG = mYFLAG;
	}

	public String getIMAGEURL() {
		return IMAGEURL;
	}

	public void setIMAGEURL(String iMAGEURL) {
		IMAGEURL = iMAGEURL;
	}

	public String getPROCNAME() {
		return PROCNAME;
	}

	public void setPROCNAME(String pROCNAME) {
		PROCNAME = pROCNAME;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public DailyOperationsBean(String mYFLAGID, String tYPE, String dTLURL, String mYFLAG, String iMAGEURL,
			String pROCNAME, int iD) {
		super();
		MYFLAGID = mYFLAGID;
		TYPE = tYPE;
		DTLURL = dTLURL;
		MYFLAG = mYFLAG;
		IMAGEURL = iMAGEURL;
		PROCNAME = pROCNAME;
		ID = iD;
	}

	public DailyOperationsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
}
