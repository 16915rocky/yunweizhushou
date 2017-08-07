package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultRiskOrKeyNextBean;

import java.util.List;

public class FaultRiskOrKeyNextAdapter extends AbsBaseAdapter<FaultRiskOrKeyNextBean> {

	private List<FaultRiskOrKeyNextBean> mList;
	private Context mContext;

	public FaultRiskOrKeyNextAdapter(Context context, List<FaultRiskOrKeyNextBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, FaultRiskOrKeyNextBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.content = (TextView) convertView.findViewById(R.id.tv1_fault_riskwarning_item);
			holder.follow_date = (TextView) convertView.findViewById(R.id.tv2_fault_riskwarning_item);
			convertView.setTag(holder);
		}
		holder.content.setText(mList.get(position).getContent());
		holder.follow_date.setText(mList.get(position).getFollow_date());
		if(position==(mList.size()-1)){
			holder.content.setTextColor(mContext.getResources().getColor(R.color.color_lightgreen));
			holder.follow_date.setTextColor(mContext.getResources().getColor(R.color.color_lightgreen));
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView content ,follow_date;
	}
}
