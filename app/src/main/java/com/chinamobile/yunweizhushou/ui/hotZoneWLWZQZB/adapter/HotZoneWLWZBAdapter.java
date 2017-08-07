package com.chinamobile.yunweizhushou.ui.hotZoneWLWZQZB.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneWLWZQZB.bean.HotZoneWLWZBBean;

import java.util.List;

public class HotZoneWLWZBAdapter extends AbsBaseAdapter<HotZoneWLWZBBean> {

	private List<HotZoneWLWZBBean> mList;

	public HotZoneWLWZBAdapter(Context context, List<HotZoneWLWZBBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, HotZoneWLWZBBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.busi_name = (TextView) convertView.findViewById(R.id.tv_item1);
			holder.totalNum = (TextView) convertView.findViewById(R.id.tv_item2);
			holder.failNum = (TextView) convertView.findViewById(R.id.tv_item3);
			holder.succNum = (TextView) convertView.findViewById(R.id.tv_item4);
			convertView.setTag(holder);
		}
		holder.busi_name.setText(mList.get(position).getBusi_name());
		holder.totalNum.setText(mList.get(position).getTotalNum());
		holder.failNum.setText(mList.get(position).getFailNum());
		holder.succNum.setText(mList.get(position).getSuccNum());
		return convertView;
	}

	private static class ViewHolder {
		private TextView busi_name, failNum,succNum,totalNum;
	}
}
