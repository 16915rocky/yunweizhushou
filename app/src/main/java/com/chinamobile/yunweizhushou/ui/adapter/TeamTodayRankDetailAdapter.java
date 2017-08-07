package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.TeamTodayRankDetailBean;

import java.util.List;

public class TeamTodayRankDetailAdapter extends AbsBaseAdapter<TeamTodayRankDetailBean> {

	private List<TeamTodayRankDetailBean> mList;
	private Context mContext;

	public TeamTodayRankDetailAdapter(Context context, List<TeamTodayRankDetailBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
		this.mContext = context;
	}

	@Override
	protected View newView(View convertView, TeamTodayRankDetailBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.team_today_detail_province);
			holder.rank = (TextView) convertView.findViewById(R.id.team_today_detail_rank);
			holder.score = (TextView) convertView.findViewById(R.id.team_today_detail_score);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.team_today_detail_layout);
		}
		holder.name.setText(mList.get(position).getName());
		holder.rank.setText(mList.get(position).getSortNumber());
		holder.score.setText(mList.get(position).getScore());
		if (position % 2 == 0) {
			holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.color_lightgray2));
		} else {
			holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
		}
		if (mList.get(position).getName().equals("浙江省")) {
			holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.color_lightblue));
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView rank, name, score;
		private LinearLayout layout;
	}

}
