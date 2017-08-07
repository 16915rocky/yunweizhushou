package com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.bean.AssessmentOfTheZoneBean;

import java.util.List;

public class AssessmentOfTheZoneAdapter extends AbsBaseAdapter<AssessmentOfTheZoneBean> {

	private List<AssessmentOfTheZoneBean> mList;
	// private int tag = 0;

	public AssessmentOfTheZoneAdapter(Context context, List<AssessmentOfTheZoneBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	/*
	 * public void setTag(int tag) { this.tag = tag; }
	 */
	@Override
	protected View newView(View convertView, AssessmentOfTheZoneBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.list_5_item1);
			holder.name2 = (TextView) convertView.findViewById(R.id.list_5_item2);
			holder.name3 = (TextView) convertView.findViewById(R.id.list_5_item3);
			holder.value = (TextView) convertView.findViewById(R.id.list_5_item4);
			holder.score = (TextView) convertView.findViewById(R.id.list_5_item5);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		holder.name2.setText(mList.get(position).getName2());
		holder.name3.setText(mList.get(position).getName3());
		holder.value.setText(mList.get(position).getValue());
		holder.score.setText(mList.get(position).getScore());

		// Double s = Double.parseDouble(str);
		if (!"0".equals(holder.score.getText().toString())) {
			holder.score.setTextColor(0xffff0000);
		} else {
			holder.score.setTextColor(0xff000000);
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView time, name1, name2, name3, value, score;
	}
}
