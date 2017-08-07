package com.chinamobile.yunweizhushou.ui.busifluct.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.busifluct.bean.DeductionRemindBean;

import java.util.List;

public class DeductionRemind1Adapter extends AbsBaseAdapter<DeductionRemindBean> {

	private List<DeductionRemindBean> mList;

	public DeductionRemind1Adapter(Context context, List<DeductionRemindBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, DeductionRemindBean d, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.list_4_item1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.list_4_item2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.list_4_item3);
			holder.tv4 = (TextView) convertView.findViewById(R.id.list_4_item4);
			convertView.setTag(holder);
		}
		holder.tv1.setText(mList.get(position).getOrg_time());
		holder.tv2.setText(mList.get(position).getTab());
		holder.tv3.setText(mList.get(position).getSys());
		holder.tv4.setText(mList.get(position).getNum());
		return convertView;
	}

	private static class Viewholder {
		TextView tv1, tv2, tv3, tv4;
	}

}
