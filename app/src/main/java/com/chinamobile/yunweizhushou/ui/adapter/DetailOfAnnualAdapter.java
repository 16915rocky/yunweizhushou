package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.DetailOfAnnualBean;

import java.util.List;

public class DetailOfAnnualAdapter extends AbsBaseAdapter<DetailOfAnnualBean> {

	private List<DetailOfAnnualBean> mList;
	private Context mContext;

	public DetailOfAnnualAdapter(Context context, List<DetailOfAnnualBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
		this.mContext = context;
	}

	@Override
	protected View newView(View convertView, DetailOfAnnualBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.business_in_score = (TextView) convertView.findViewById(R.id.team_tv1);
			holder.business_out_score = (TextView) convertView.findViewById(R.id.team_tv2);
			holder.consistency_score = (TextView) convertView.findViewById(R.id.team_tv3);
			holder.accuracy_score = (TextView) convertView.findViewById(R.id.team_tv4);
			holder.monthly_score = (TextView) convertView.findViewById(R.id.team_tv5);
			holder.score = (TextView) convertView.findViewById(R.id.team_tv6);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.team2_today_detail_layout);
		}
		holder.business_in_score.setText(mList.get(position).getBusiness_in_score());
		holder.business_out_score.setText(mList.get(position).getBusiness_out_score());
		holder.consistency_score.setText(mList.get(position).getConsistency_score());
		holder.accuracy_score.setText(mList.get(position).getAccuracy_score());
		holder.monthly_score.setText(mList.get(position).getMonthly_score());
		holder.score.setText(mList.get(position).getScore());
		if (position % 2 == 0) {
			holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.color_lightgray2));
		} else {
			holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
		}
	
		return convertView;
	}

	private class ViewHolder {
		private TextView business_in_score, business_out_score, consistency_score,accuracy_score,monthly_score,score;
		private LinearLayout layout;
	}

}
