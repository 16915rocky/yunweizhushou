package com.chinamobile.yunweizhushou.ui.netChange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.netChange.bean.OnlineChangeBean;

import java.util.List;

public class OnlineChangeAdapter2 extends BaseAdapter {

	private List<OnlineChangeBean> mList;
	private int res;
	private LayoutInflater mInflater;

	public OnlineChangeAdapter2(Context c, List<OnlineChangeBean> mList, int res) {
		mInflater = LayoutInflater.from(c);
		this.mList = mList;
		this.res = res;
	}

	public void setData(List<OnlineChangeBean> mList) {
		this.mList = mList;
	}

	@Override
	public int getCount() {
		return mList.size();
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(res, parent, false);

			holder.category = (TextView) convertView.findViewById(R.id.category);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.operate_time = (TextView) convertView.findViewById(R.id.operate_time);
			holder.operator = (TextView) convertView.findViewById(R.id.operator);
			holder.remark = (TextView) convertView.findViewById(R.id.remark);
			holder.state = (TextView) convertView.findViewById(R.id.state);
			holder.sys = (TextView) convertView.findViewById(R.id.sys);
			holder.sysLayout = (LinearLayout) convertView.findViewById(R.id.system_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String s = "";
		if (null != mList.get(position).getSys() && mList.get(position).getSys().size() > 0) {
			for (int i = 0; i < mList.get(position).getSys().size(); i++) {
				s = s + mList.get(position).getSys().get(i) + ", ";
			}
			holder.sys.setText(s);
		} else {
			holder.sys.setText("");
		}
		if (mList.get(position).getState().equals("0")) {
			holder.state.setBackgroundResource(R.drawable.oval_gray);
			holder.state.setText("未录入");
		} else if (mList.get(position).getState().equals("1")) {
			holder.state.setBackgroundResource(R.drawable.oval_green);
			holder.state.setText("同意");
		} else if (mList.get(position).getState().equals("2")) {
			holder.state.setBackgroundResource(R.drawable.oval_red);
			holder.state.setText("不同意");
		}
		holder.category.setText(mList.get(position).getCategory());
		holder.name.setText(mList.get(position).getName());
		holder.operate_time.setText(mList.get(position).getOperate_time());
		holder.operator.setText(mList.get(position).getOperator());
		holder.remark.setText(mList.get(position).getRemark());
		if (mList.get(position).getSys().size() == 0) {
			holder.sysLayout.setVisibility(View.GONE);
		} else {
			holder.sysLayout.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	private static class ViewHolder {
		private LinearLayout sysLayout;
		private TextView category, name, operate_time, operator, remark, state, sys;
	}

}
