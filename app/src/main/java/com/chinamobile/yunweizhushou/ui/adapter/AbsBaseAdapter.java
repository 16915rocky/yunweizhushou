package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsBaseAdapter<T> extends BaseAdapter {

	private List<T> mList;
	private LayoutInflater mInflater;
	private int ResourceId;

	public AbsBaseAdapter(Context context, List<T> list, int resourceId) {
		if (list == null) {
			list = new ArrayList<T>();
		}
		mList = new ArrayList<T>();
		mList.addAll(list);
		mInflater = LayoutInflater.from(context);
		ResourceId = resourceId;
	}

	public void clearData() {
		if (mList == null)
			mList = new ArrayList<T>();
		mList.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return newView(convertView == null ? mInflater.inflate(ResourceId, null) : convertView,
				mList == null || mList.size() < 1 ? null : mList.get(position), position);
	}

	protected abstract View newView(View convertView, T t, int position);

}
