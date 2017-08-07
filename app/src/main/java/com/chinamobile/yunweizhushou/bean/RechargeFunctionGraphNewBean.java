package com.chinamobile.yunweizhushou.bean;

import java.io.Serializable;
import java.util.List;

public class RechargeFunctionGraphNewBean {
	private List<ItemsList> itemsList;

	public void setItemsList(List<ItemsList> itemsList) {
		this.itemsList = itemsList;
	}

	public List<ItemsList> getItemsList() {
		return this.itemsList;
	}

	public static class ItemsList implements Serializable {
		private List<String> columns;
		private List<List<String>> points;
		private String charts_type;
		private String msg;
		private String title;
		private String ytitle;

		public List<String> getColumns() {
			return columns;
		}

		public void setColumns(List<String> columns) {
			this.columns = columns;
		}

		public List<List<String>> getPoints() {
			return points;
		}

		public void setPoints(List<List<String>> points) {
			this.points = points;
		}

		public String getCharts_type() {
			return charts_type;
		}

		public void setCharts_type(String charts_type) {
			this.charts_type = charts_type;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getYtitle() {
			return ytitle;
		}

		public void setYtitle(String ytitle) {
			this.ytitle = ytitle;
		}
	}
}
