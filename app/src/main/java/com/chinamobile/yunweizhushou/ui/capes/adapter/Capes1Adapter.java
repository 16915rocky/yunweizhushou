package com.chinamobile.yunweizhushou.ui.capes.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes1Bean;

import java.util.List;

public class Capes1Adapter extends AbsBaseAdapter<Capes1Bean> {

	private List<Capes1Bean> mList;

	public Capes1Adapter(Context context, List<Capes1Bean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, Capes1Bean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.capes_1_time);
			holder.name = (TextView) convertView.findViewById(R.id.capes_1_name);
			holder.value1 = (TextView) convertView.findViewById(R.id.capes_1_value1);
			holder.value2 = (TextView) convertView.findViewById(R.id.capes_1_value2);
			holder.rank = (TextView) convertView.findViewById(R.id.capes_1_rank);
			holder.unit = (TextView) convertView.findViewById(R.id.capes_1_unit);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		holder.name.setText(mList.get(position).getName());
		holder.value1.setText(mList.get(position).getValue());
		holder.value2.setText(mList.get(position).getAvg());
		holder.rank.setText(mList.get(position).getTop());
		holder.unit.setText(mList.get(position).getUnit());

		return convertView;
	}

	private static class ViewHolder {
		private TextView unit, time, name, value1, value2, rank;
	}
}
