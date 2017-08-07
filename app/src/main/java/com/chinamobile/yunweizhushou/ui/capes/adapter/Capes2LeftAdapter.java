package com.chinamobile.yunweizhushou.ui.capes.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes2LeftBean;

import java.util.List;

public class Capes2LeftAdapter extends AbsBaseAdapter<Capes2LeftBean> {

	private List<Capes2LeftBean> mList;

	public Capes2LeftAdapter(Context context, List<Capes2LeftBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, Capes2LeftBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.capes_2_left_time);
			holder.name = (TextView) convertView.findViewById(R.id.capes_2_left_name1);
			holder.value = (TextView) convertView.findViewById(R.id.capes_2_left_value);
			holder.countryAvg = (TextView) convertView.findViewById(R.id.capes_2_left_countryAvg);
			holder.kpi = (TextView) convertView.findViewById(R.id.capes_2_left_name2);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		holder.name.setText(mList.get(position).getName());
		holder.value.setText(mList.get(position).getValue());
		holder.countryAvg.setText(mList.get(position).getAvg());
		holder.kpi.setText(mList.get(position).getKpi());

		return convertView;
	}

	private static class ViewHolder {
		private TextView time, name, value, countryAvg, kpi;
	}
}
