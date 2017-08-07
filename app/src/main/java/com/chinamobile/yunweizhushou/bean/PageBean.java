package com.chinamobile.yunweizhushou.bean;

public class PageBean {
	private int beginRowNum;
	private int endRowNum;
	private boolean hasNext;
	private boolean hasPrevious;
	private int itemCountInPage;
	private int itemCountPerPage;
	private int lastPageNumber;
	private int pageNumber;
	private int totalRecordCount;

	public int getBeginRowNum() {
		return beginRowNum;
	}

	public int getEndRowNum() {
		return endRowNum;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public int getItemCountInPage() {
		return itemCountInPage;
	}

	public int getItemCountPerPage() {
		return itemCountPerPage;
	}

	public int getLastPageNumber() {
		return lastPageNumber;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/*
	 * public void setBeginRowNum(int beginRowNum) { this.beginRowNum =
	 * beginRowNum; }
	 * 
	 * public void setEndRowNum(int endRowNum) { this.endRowNum = endRowNum; }
	 * 
	 * public void setHasNext(boolean hasNext) { this.hasNext = hasNext; }
	 * 
	 * public void setHasPrevious(boolean hasPrevious) { this.hasPrevious =
	 * hasPrevious; }
	 * 
	 * public void setItemCountInPage(int itemCountInPage) {
	 * this.itemCountInPage = itemCountInPage; }
	 * 
	 * public void setItemCountPerPage(int itemCountPerPage) {
	 * this.itemCountPerPage = itemCountPerPage; }
	 * 
	 * public void setLastPageNumber(int lastPageNumber) { this.lastPageNumber =
	 * lastPageNumber; }
	 * 
	 * public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber;
	 * }
	 * 
	 * public void setTotalRecordCount(int totalRecordCount) {
	 * this.totalRecordCount = totalRecordCount; }
	 */

}
