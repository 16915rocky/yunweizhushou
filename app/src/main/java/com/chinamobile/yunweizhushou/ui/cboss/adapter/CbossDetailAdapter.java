package com.chinamobile.yunweizhushou.ui.cboss.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.cboss.bean.CbossDetailBean;

import java.util.List;

public class CbossDetailAdapter extends AbsBaseAdapter<CbossDetailBean> {

	private List<CbossDetailBean> mList;

	public CbossDetailAdapter(Context context, List<CbossDetailBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, CbossDetailBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.cboss_detail_name);
			holder.code = (TextView) convertView.findViewById(R.id.cboss_detail_code);
			holder.num = (TextView) convertView.findViewById(R.id.cboss_detail_num);
			holder.time1 = (TextView) convertView.findViewById(R.id.cboss_detail_time1);
			holder.time2 = (TextView) convertView.findViewById(R.id.cboss_detail_time2);
			holder.rate = (TextView) convertView.findViewById(R.id.cboss_detail_rate);
			convertView.setTag(holder);
		}
		holder.name.setText(mList.get(position).getBusi_name());
		holder.code.setText(mList.get(position).getCode() + " " + mList.get(position).getBcode());
		holder.num.setText(mList.get(position).getValue());
		holder.time1.setText(mList.get(position).getStime());
		holder.time2.setText(mList.get(position).getBtime());
		holder.rate.setText(mList.get(position).getRate());
		if (mList.get(position).getState().equals("1")) {
			holder.rate.setBackgroundResource(R.drawable.rect_green_bg);
		} else if (mList.get(position).getState().equals("2")) {
			holder.rate.setBackgroundResource(R.drawable.rect_yellow_bg);
		} else if (mList.get(position).getState().equals("3")) {
			holder.rate.setBackgroundResource(R.drawable.rect_orange_bg);
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView name, code, num, time1, time2, rate;
	}

}
