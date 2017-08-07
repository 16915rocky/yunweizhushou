package com.chinamobile.yunweizhushou.bean;

public class SatisfiedBean5 {

	private String yw;// 业务失败
	private String suc;// 成功量
	private String NAME;// 系统名称,
	private String ywcgl;// 业务成功率,
	private String ORG_TIME;// 系统时间
	private String xtcgl;// 系统失败
	private String xt;// 系统量

	public String getYw() {
		return yw;
	}

	public void setYw(String yw) {
		this.yw = yw;
	}

	public String getSuc() {
		return suc;
	}

	public void setSuc(String suc) {
		this.suc = suc;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getYwcgl() {
		return ywcgl;
	}

	public void setYwcgl(String ywcgl) {
		this.ywcgl = ywcgl;
	}

	public String getORG_TIME() {
		return ORG_TIME;
	}

	public void setORG_TIME(String oRG_TIME) {
		ORG_TIME = oRG_TIME;
	}

	public String getXtcgl() {
		return xtcgl;
	}

	public void setXtcgl(String xtcgl) {
		this.xtcgl = xtcgl;
	}

	public String getXt() {
		return xt;
	}

	public void setXt(String xt) {
		this.xt = xt;
	}

	public SatisfiedBean5(String yw, String suc, String nAME, String ywcgl, String oRG_TIME, String xtcgl, String xt) {
		super();
		this.yw = yw;
		this.suc = suc;
		NAME = nAME;
		this.ywcgl = ywcgl;
		ORG_TIME = oRG_TIME;
		this.xtcgl = xtcgl;
		this.xt = xt;
	}

	public SatisfiedBean5() {
		super();
	}

}
