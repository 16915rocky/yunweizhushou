package com.chinamobile.yunweizhushou.ui.officeDataZone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.officeDataZone.beans.GroupIncrementCheckBean;

import java.util.List;

public class GroupIncrementCheckAdapter extends AbsBaseAdapter<GroupIncrementCheckBean> {

	private List<GroupIncrementCheckBean> mList;

	public GroupIncrementCheckAdapter(Context context, List<GroupIncrementCheckBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, GroupIncrementCheckBean n, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.list_3_item1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.list_3_item2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.list_3_item3);
			convertView.setTag(holder);
		}
		holder.tv1.setText(mList.get(position).getLevel1_name());
		holder.tv2.setText(mList.get(position).getLevel2_name());
		holder.tv3.setText(mList.get(position).getConsist_ratio());
		return convertView;
	}

	private static class Viewholder {
		TextView tv1, tv2, tv3;
	}

}
