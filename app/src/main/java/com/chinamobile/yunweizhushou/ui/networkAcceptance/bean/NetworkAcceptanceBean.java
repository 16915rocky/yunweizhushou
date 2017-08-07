package com.chinamobile.yunweizhushou.ui.networkAcceptance.bean;

import java.util.List;

public class NetworkAcceptanceBean {
	/**
	 * networkCoverage : 34.25% totalAcceptance : 327 ProductionVer : 210
	 * verificationCoverage : 2.38%
	 */

	private TotalBean total;
	/**
	 * caseTotalIncrement : 5 coverageRate : 0.4 name : 实时费用查询
	 * numOfVerificationCases : 3 proVerAutoCov : 0.3333 prototalIncrement : 3
	 * totalNum : 5
	 */

	private List<ItemsListBean> itemsList;

	public TotalBean getTotal() {
		return total;
	}

	public void setTotal(TotalBean total) {
		this.total = total;
	}

	public List<ItemsListBean> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsListBean> itemsList) {
		this.itemsList = itemsList;
	}

	public static class TotalBean {
		private String networkCoverage;// 入网验收覆盖率
		private String totalAcceptance;// 入网验收合计
		private String ProductionVer;// 生产验证合计
		private String verificationCoverage;// 生产验证覆盖率

		public String getNetworkCoverage() {
			return networkCoverage;
		}

		public void setNetworkCoverage(String networkCoverage) {
			this.networkCoverage = networkCoverage;
		}

		public String getTotalAcceptance() {
			return totalAcceptance;
		}

		public void setTotalAcceptance(String totalAcceptance) {
			this.totalAcceptance = totalAcceptance;
		}

		public String getProductionVer() {
			return ProductionVer;
		}

		public void setProductionVer(String ProductionVer) {
			this.ProductionVer = ProductionVer;
		}

		public String getVerificationCoverage() {
			return verificationCoverage;
		}

		public void setVerificationCoverage(String verificationCoverage) {
			this.verificationCoverage = verificationCoverage;
		}
	}

	public static class ItemsListBean {
		private String caseTotalIncrement;// 入网验收用例增量
		private String coverageRate;// 入网验收自动化覆盖率
		private String name;// 名称
		private String level;
		private String numOfVerificationCases;// 生产验证例数
		private String proVerAutoCov;// 生产验证自动化覆盖率
		private String prototalIncrement;// 生产验证用例增量
		private String totalNum;// 入网验收用例数

		public String getCaseTotalIncrement() {
			return caseTotalIncrement;
		}

		public void setCaseTotalIncrement(String caseTotalIncrement) {
			this.caseTotalIncrement = caseTotalIncrement;
		}

		public String getCoverageRate() {
			return coverageRate;
		}

		public void setCoverageRate(String coverageRate) {
			this.coverageRate = coverageRate;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNumOfVerificationCases() {
			return numOfVerificationCases;
		}

		public void setNumOfVerificationCases(String numOfVerificationCases) {
			this.numOfVerificationCases = numOfVerificationCases;
		}

		public String getProVerAutoCov() {
			return proVerAutoCov;
		}

		public void setProVerAutoCov(String proVerAutoCov) {
			this.proVerAutoCov = proVerAutoCov;
		}

		public String getPrototalIncrement() {
			return prototalIncrement;
		}

		public void setPrototalIncrement(String prototalIncrement) {
			this.prototalIncrement = prototalIncrement;
		}

		public String getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(String totalNum) {
			this.totalNum = totalNum;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}
	}
}
