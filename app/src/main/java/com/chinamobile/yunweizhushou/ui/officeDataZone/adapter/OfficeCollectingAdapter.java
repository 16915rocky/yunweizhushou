package com.chinamobile.yunweizhushou.ui.officeDataZone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.officeDataZone.beans.OfficeCollectingBean;

import java.util.List;

public class OfficeCollectingAdapter extends AbsBaseAdapter<OfficeCollectingBean> {

	private List<OfficeCollectingBean> mList;

	public OfficeCollectingAdapter(Context context, List<OfficeCollectingBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, OfficeCollectingBean n, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.list_4_item1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.list_4_item2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.list_4_item3);
			convertView.setTag(holder);
		}
		holder.tv1.setText(mList.get(position).getSync_date());
		holder.tv2.setText(mList.get(position).getProc_name());
		holder.tv3.setText(mList.get(position).getStatus());
		return convertView;
	}

	private static class Viewholder {
		TextView tv1, tv2, tv3, tv4;
	}

}
