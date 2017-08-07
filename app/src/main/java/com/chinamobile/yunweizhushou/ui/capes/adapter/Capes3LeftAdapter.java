package com.chinamobile.yunweizhushou.ui.capes.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes3LeftBean;

import java.util.List;

public class Capes3LeftAdapter extends AbsBaseAdapter<Capes3LeftBean> {

	private List<Capes3LeftBean> mList;

	public Capes3LeftAdapter(Context context, List<Capes3LeftBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, Capes3LeftBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.capes_3_left_time);
			holder.name = (TextView) convertView.findViewById(R.id.capes_3_left_name);
			holder.value = (TextView) convertView.findViewById(R.id.capes_3_left_value);
			holder.countryAvg = (TextView) convertView.findViewById(R.id.capes_3_left_countryAvg);
			holder.useAvg = (TextView) convertView.findViewById(R.id.capes_3_left_useAvg);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		holder.name.setText(mList.get(position).getName());
		holder.value.setText(mList.get(position).getValue());
		holder.countryAvg.setText(mList.get(position).getAvg());
		holder.useAvg.setText(mList.get(position).getUse());

		return convertView;
	}

	private static class ViewHolder {
		private TextView time, name, value, countryAvg, useAvg;
	}
}
