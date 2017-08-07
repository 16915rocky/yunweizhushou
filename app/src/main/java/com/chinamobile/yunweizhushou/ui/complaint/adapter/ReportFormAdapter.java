package com.chinamobile.yunweizhushou.ui.complaint.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ReportFormBean;

import java.util.List;

public class ReportFormAdapter extends AbsBaseAdapter<ReportFormBean> {

	private List<ReportFormBean> list;

	public ReportFormAdapter(Context context, List<ReportFormBean> list, int resourceId) {
		super(context, list, resourceId);
		this.list = list;
	}

	@Override
	protected View newView(View convertView, ReportFormBean t, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.name = (TextView) convertView.findViewById(R.id.list_4_item1);
			holder.num = (TextView) convertView.findViewById(R.id.list_4_item2);
			holder.newRate = (TextView) convertView.findViewById(R.id.list_4_item3);
			holder.inRate = (TextView) convertView.findViewById(R.id.list_4_item4);
			convertView.setTag(holder);
		}
		holder.name.setText(list.get(position).getName());
		holder.num.setText(list.get(position).getNum());
		holder.newRate.setText(list.get(position).getNewRate());
		holder.inRate.setText(list.get(position).getInRate());
		return convertView;
	}

	private static class Viewholder {
		TextView name, num, newRate, inRate;
	}
}
