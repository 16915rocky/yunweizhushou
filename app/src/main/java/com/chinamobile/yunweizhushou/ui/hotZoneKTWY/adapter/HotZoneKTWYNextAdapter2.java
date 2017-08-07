package com.chinamobile.yunweizhushou.ui.hotZoneKTWY.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.bean.HotZoneKTWYCityDesBean2;

import java.util.List;

public class HotZoneKTWYNextAdapter2 extends AbsBaseAdapter<HotZoneKTWYCityDesBean2> {

	private List<HotZoneKTWYCityDesBean2> mList;

	public HotZoneKTWYNextAdapter2(Context context, List<HotZoneKTWYCityDesBean2> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, HotZoneKTWYCityDesBean2 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.item1 = (TextView) convertView.findViewById(R.id.list_2_item1);
			holder.item2 = (TextView) convertView.findViewById(R.id.list_2_item2);
			convertView.setTag(holder);
		}
		holder.item1.setText(mList.get(position).getFail_reason());
		holder.item2.setText(mList.get(position).getNum());
		return convertView;
	}

	private static class ViewHolder {
		private TextView item1, item2;
	}
}
