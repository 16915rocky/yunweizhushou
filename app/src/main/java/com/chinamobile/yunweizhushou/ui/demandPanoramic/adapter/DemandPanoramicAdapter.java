package com.chinamobile.yunweizhushou.ui.demandPanoramic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandOperateBean;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandPanoramicBean;

import java.util.List;

public class DemandPanoramicAdapter extends BaseAdapter {

	private List<DemandPanoramicBean> data;
	private Context context;

	public DemandPanoramicAdapter(Context context, List<DemandPanoramicBean> data) {
		super();
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_demand_panoramic_list, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String title = data.get(position).getItem();
		int pos = getPositionByItem(title);// 获取字母出现的位置
		DemandOperateBean.ItemsListChild child = data.get(position).getChildItem();
		if (position == pos) {
			holder.letter.setVisibility(View.VISIBLE);
			holder.letter.setText(title);
		} else {
			holder.letter.setVisibility(View.GONE);
		}
		holder.demand.setText(child.getTitle());
		return convertView;
	}

	// 获取title出现的第一次position
	private int getPositionByItem(String item) {
		int index = 0;
		for (int i = 0; i < data.size(); i++) {
			if (item.equals(data.get(i).getItem())) {
				// 字母一样
				index = i;
				// Log.e("tag", item+"第一次位置"+(index+1));
				break;
			}
		}
		return index;
	}

	/*
	 * 通过首字母获取显示该首字母的姓名的人，如：C,成龙
	 *
	 */
	public int getPositionBySelection(int selection) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = data.get(i).getItem();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == selection) {
				return i;
			}
		}
		return -1;
	}

	private class ViewHolder {
		TextView letter;
		TextView demand;

		public ViewHolder(View view) {
			letter = (TextView) view.findViewById(R.id.letter);
			demand = (TextView) view.findViewById(R.id.demand);
		}
	}
}
