package com.chinamobile.yunweizhushou.ui.bandService.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.bandService.bean.BandServiceBean;

import java.util.List;

public class BandServiceAdapter extends AbsBaseAdapter<BandServiceBean> {

	private List<BandServiceBean> mList;

	public BandServiceAdapter(Context context, List<BandServiceBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, BandServiceBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.city = (TextView) convertView.findViewById(R.id.list_5_item1);
			holder.todayorder = (TextView) convertView.findViewById(R.id.list_5_item2);
			holder.ontheorder = (TextView) convertView.findViewById(R.id.list_5_item3);
			holder.proportion = (TextView) convertView.findViewById(R.id.list_5_item4);
			holder.handle = (TextView) convertView.findViewById(R.id.list_5_item5);
			convertView.setTag(holder);
		}
		holder.city.setText(mList.get(position).getCity());
		holder.todayorder.setText(mList.get(position).getTodayorder());
		holder.ontheorder.setText(mList.get(position).getOntheorder());
		holder.proportion.setText(mList.get(position).getProportion()+"%");
		holder.handle.setText(mList.get(position).getHandle()+"%");

		return convertView;
	}

	private static class ViewHolder {
		private TextView city, todayorder, ontheorder, proportion, handle;
	}
}
