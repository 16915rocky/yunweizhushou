package com.chinamobile.yunweizhushou.ui.functionAnalysis.bean;

import java.util.List;

public class FunctionAnalysisPerformancebean {

	private List<ItemsList> itemsList;

	public List<ItemsList> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsList> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ItemsList {
		private String name;
		private String num;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getNum() {
			return this.num;
		}
	}
}
