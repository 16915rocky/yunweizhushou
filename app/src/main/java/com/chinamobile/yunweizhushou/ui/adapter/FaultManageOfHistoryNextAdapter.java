package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FmohNextBean;

import java.util.List;

public class FaultManageOfHistoryNextAdapter extends AbsBaseAdapter<FmohNextBean> {

	private List<FmohNextBean> mList;

	public FaultManageOfHistoryNextAdapter(Context context, List<FmohNextBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
	}

	@Override
	protected View newView(View convertView, FmohNextBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.accept_time = (TextView) convertView.findViewById(R.id.list_3_item1);
			holder.caller_city = (TextView) convertView.findViewById(R.id.list_3_item2);
			holder.caller_no = (TextView) convertView.findViewById(R.id.list_3_item3);		
			convertView.setTag(holder);
		}
		holder.accept_time.setText(mList.get(position).getAccept_time());
		holder.caller_city.setText(mList.get(position).getCaller_city());
		holder.caller_no.setText(mList.get(position).getCaller_no());
	

		return convertView;
	}

	private static class ViewHolder {
		private TextView accept_time, caller_city, caller_no;
	}
}
