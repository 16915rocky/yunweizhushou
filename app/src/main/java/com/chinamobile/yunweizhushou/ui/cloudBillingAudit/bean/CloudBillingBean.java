package com.chinamobile.yunweizhushou.ui.cloudBillingAudit.bean;

import java.util.List;

public class CloudBillingBean {
	private List<List4> list4;

	private List<List2> list2;

	private List<List3> list3;

	private List<List1> list1;

	private Hm hm;

	public void setList4(List<List4> list4) {
		this.list4 = list4;
	}

	public List<List4> getList4() {
		return this.list4;
	}

	public void setList2(List<List2> list2) {
		this.list2 = list2;
	}

	public List<List2> getList2() {
		return this.list2;
	}

	public void setList3(List<List3> list3) {
		this.list3 = list3;
	}

	public List<List3> getList3() {
		return this.list3;
	}

	public void setList1(List<List1> list1) {
		this.list1 = list1;
	}

	public List<List1> getList1() {
		return this.list1;
	}

	public void setHm(Hm hm) {
		this.hm = hm;
	}

	public Hm getHm() {
		return this.hm;
	}

	public static class List1 {
		private String fileNumber;

		private String name;

		private String processingTime;

		private String totalFileSize;

		public void setFileNumber(String fileNumber) {
			this.fileNumber = fileNumber;
		}

		public String getFileNumber() {
			return this.fileNumber;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setProcessingTime(String processingTime) {
			this.processingTime = processingTime;
		}

		public String getProcessingTime() {
			return this.processingTime;
		}

		public void setTotalFileSize(String totalFileSize) {
			this.totalFileSize = totalFileSize;
		}

		public String getTotalFileSize() {
			return this.totalFileSize;
		}
	}

	public static class List2 {
		private String fileNumber;
		private String name;
		private String processingTime;

		public String getFileNumber() {
			return fileNumber;
		}

		public void setFileNumber(String fileNumber) {
			this.fileNumber = fileNumber;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getProcessingTime() {
			return processingTime;
		}

		public void setProcessingTime(String processingTime) {
			this.processingTime = processingTime;
		}
	}

	public static class List3 {
		private String fileNumber;

		private String name;

		private String numberOfCorrect;

		private String numberOfDuplicateFiles;

		private String processingTime;

		private String totalNumber;

		public void setFileNumber(String fileNumber) {
			this.fileNumber = fileNumber;
		}

		public String getFileNumber() {
			return this.fileNumber;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setNumberOfCorrect(String numberOfCorrect) {
			this.numberOfCorrect = numberOfCorrect;
		}

		public String getNumberOfCorrect() {
			return this.numberOfCorrect;
		}

		public void setNumberOfDuplicateFiles(String numberOfDuplicateFiles) {
			this.numberOfDuplicateFiles = numberOfDuplicateFiles;
		}

		public String getNumberOfDuplicateFiles() {
			return this.numberOfDuplicateFiles;
		}

		public void setProcessingTime(String processingTime) {
			this.processingTime = processingTime;
		}

		public String getProcessingTime() {
			return this.processingTime;
		}

		public void setTotalNumber(String totalNumber) {
			this.totalNumber = totalNumber;
		}

		public String getTotalNumber() {
			return this.totalNumber;
		}
	}

	public static class List4 {
		private String fileNumber;

		private String name;

		private String numberOfCorrect;

		private String numberOfDuplicateFiles;

		private String processingTime;

		private String totalNumber;

		public void setFileNumber(String fileNumber) {
			this.fileNumber = fileNumber;
		}

		public String getFileNumber() {
			return this.fileNumber;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setNumberOfCorrect(String numberOfCorrect) {
			this.numberOfCorrect = numberOfCorrect;
		}

		public String getNumberOfCorrect() {
			return this.numberOfCorrect;
		}

		public void setNumberOfDuplicateFiles(String numberOfDuplicateFiles) {
			this.numberOfDuplicateFiles = numberOfDuplicateFiles;
		}

		public String getNumberOfDuplicateFiles() {
			return this.numberOfDuplicateFiles;
		}

		public void setProcessingTime(String processingTime) {
			this.processingTime = processingTime;
		}

		public String getProcessingTime() {
			return this.processingTime;
		}

		public void setTotalNumber(String totalNumber) {
			this.totalNumber = totalNumber;
		}

		public String getTotalNumber() {
			return this.totalNumber;
		}
	}

	public static class Hm {
		private List<String> COLUMNS;

		private String NAME;

		private String DOUBLEY;

		private String TIMENO;

		private List<List<String>> POINTS;

		private String CHARTS_TYPE;

		private String YTITLE;

		private String X;

		private String msg;

		public void setCOLUMNS(List<String> COLUMNS) {
			this.COLUMNS = COLUMNS;
		}

		public List<String> getCOLUMNS() {
			return this.COLUMNS;
		}

		public void setNAME(String NAME) {
			this.NAME = NAME;
		}

		public String getNAME() {
			return this.NAME;
		}

		public void setDOUBLEY(String DOUBLEY) {
			this.DOUBLEY = DOUBLEY;
		}

		public String getDOUBLEY() {
			return this.DOUBLEY;
		}

		public void setTIMENO(String TIMENO) {
			this.TIMENO = TIMENO;
		}

		public String getTIMENO() {
			return this.TIMENO;
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

		public void setYTITLE(String YTITLE) {
			this.YTITLE = YTITLE;
		}

		public String getYTITLE() {
			return this.YTITLE;
		}

		public void setX(String X) {
			this.X = X;
		}

		public String getX() {
			return this.X;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getMsg() {
			return this.msg;
		}
	}
}
