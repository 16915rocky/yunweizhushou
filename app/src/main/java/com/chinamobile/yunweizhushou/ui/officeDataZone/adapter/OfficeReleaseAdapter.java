package com.chinamobile.yunweizhushou.ui.officeDataZone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.officeDataZone.beans.OfficeReleaseBean;

import java.util.List;

public class OfficeReleaseAdapter extends AbsBaseAdapter<OfficeReleaseBean> {

	private List<OfficeReleaseBean> mList;

	public OfficeReleaseAdapter(Context context, List<OfficeReleaseBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, OfficeReleaseBean n, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.list_4_item1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.list_4_item2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.list_4_item3);
			holder.tv4 = (TextView) convertView.findViewById(R.id.list_4_item4);
			convertView.setTag(holder);
		}
		holder.tv1.setText(mList.get(position).getTgt_dept());
		holder.tv2.setText(mList.get(position).getSrc_table());
		holder.tv3.setText(mList.get(position).getRemarks());
		holder.tv4.setText(mList.get(position).getConsist_ratio());
		return convertView;
	}

	private static class Viewholder {
		TextView tv1, tv2, tv3, tv4;
	}

}
