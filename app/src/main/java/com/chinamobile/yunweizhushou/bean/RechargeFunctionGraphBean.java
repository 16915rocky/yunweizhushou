package com.chinamobile.yunweizhushou.bean;

import java.io.Serializable;
import java.util.List;

public class 	RechargeFunctionGraphBean {
	private List<ItemsList> itemsList;

	public void setItemsList(List<ItemsList> itemsList) {
		this.itemsList = itemsList;
	}

	public List<ItemsList> getItemsList() {
		return this.itemsList;
	}



	public static class ItemsList implements Serializable {
		private List<String> COLUMNS;
		private String TIME;
		private String DOUBLEY;
		private String NAME;
		private List<List<String>> POINTS;
		private String CHARTS_TYPE;
		private String msg;
		private String waveId;
		private String X;
		private String YTITLE;

		public void setCOLUMNS(List<String> COLUMNS) {
			this.COLUMNS = COLUMNS;
		}

		public List<String> getCOLUMNS() {
			return this.COLUMNS;
		}

		public void setTIME(String TIME) {
			this.TIME = TIME;
		}

		public String getTIME() {
			return this.TIME;
		}

		public void setDOUBLEY(String DOUBLEY) {
			this.DOUBLEY = DOUBLEY;
		}

		public String getDOUBLEY() {
			return this.DOUBLEY;
		}

		public void setNAME(String NAME) {
			this.NAME = NAME;
		}

		public String getNAME() {
			return this.NAME;
		}

		public void setPOINTS(List<List<String>> POINTS) {
			this.POINTS = POINTS;
		}

		public List<List<String>> getPOINTS() {
			return this.POINTS;
		}

		public void setCHARTS_TYPE(String CHARTS_TYPE) {
			this.CHARTS_TYPE = CHARTS_TYPE;
		}

		public String getCHARTS_TYPE() {
			return this.CHARTS_TYPE;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getMsg() {
			return this.msg;
		}

		public void setX(String X) {
			this.X = X;
		}

		public String getX() {
			return this.X;
		}

		public String getWaveId() {
			return waveId;
		}

		public void setWaveId(String waveId) {
			this.waveId = waveId;
		}

		public void setYTITLE(String YTITLE) {
			this.YTITLE = YTITLE;
		}

		public String getYTITLE() {
			return this.YTITLE;
		}
	}
}
