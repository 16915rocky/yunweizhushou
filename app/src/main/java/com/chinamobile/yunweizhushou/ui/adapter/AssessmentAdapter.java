package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.AssessmentSubbean;

import java.util.List;

public class AssessmentAdapter extends AbsBaseAdapter<AssessmentSubbean> {

	private List<AssessmentSubbean> mList;

	public AssessmentAdapter(Context context, List<AssessmentSubbean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, AssessmentSubbean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.assessment_title);
			holder.system = (TextView) convertView.findViewById(R.id.assessment_system);
			holder.type = (TextView) convertView.findViewById(R.id.assessment_type);
			holder.content = (TextView) convertView.findViewById(R.id.assessment_content);
			convertView.setTag(holder);
		}
		holder.title.setText(mList.get(position).getThirdOrderNorm());
		holder.system.setText(mList.get(position).getSystemName());
		holder.type.setText(mList.get(position).getNormType());
		holder.content.setText(mList.get(position).getNormDesc());
		return convertView;
	}

	private static class ViewHolder {
		private TextView title, system, type, content;
	}
}
