package com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.bean.AssessmentOfZheZoneDialogBean;

import java.util.List;

public class AssessmentOfZheZoneAdapter7 extends AbsBaseAdapter<AssessmentOfZheZoneDialogBean> {

	private List<AssessmentOfZheZoneDialogBean> list1;

	public AssessmentOfZheZoneAdapter7(Context context, List<AssessmentOfZheZoneDialogBean> list, int resourceId) {
		super(context, list, resourceId);
		this.list1 = list;
	}

	@Override
	protected View newView(View convertView, AssessmentOfZheZoneDialogBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.list_2_item1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.list_2_item2);

		}
		holder.tv1.setText(list1.get(position).getName3());
		holder.tv2.setText(list1.get(position).getName4());

		return convertView;
	}

	class ViewHolder {

		private TextView tv1, tv2;

	}

}