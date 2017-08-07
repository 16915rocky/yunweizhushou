package com.chinamobile.yunweizhushou.ui.systemTree.bean;

/**
 * 类似于责任人的数据结构实体类
 * 
 * @author Administrator
 */
public class ItemBean {

	private String item;
	private String itemValue;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public ItemBean() {
		super();
	}

	public ItemBean(String item) {
		super();
		this.item = item;
	}

	public ItemBean(String item, String itemValue) {
		super();
		this.item = item;
		this.itemValue = itemValue;
	}

}
