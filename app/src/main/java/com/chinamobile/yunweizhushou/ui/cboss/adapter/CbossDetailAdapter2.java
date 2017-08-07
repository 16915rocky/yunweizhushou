package com.chinamobile.yunweizhushou.ui.cboss.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.cboss.bean.CbossDetailBean2;

import java.util.List;

public class CbossDetailAdapter2 extends AbsBaseAdapter<CbossDetailBean2> {

	private List<CbossDetailBean2> mList;

	public CbossDetailAdapter2(Context context, List<CbossDetailBean2> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, CbossDetailBean2 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.cboss_detail2_name);
			holder.score = (TextView) convertView.findViewById(R.id.cboss_detail2_score);
			holder.err = (TextView) convertView.findViewById(R.id.cboss_detail2_err);
			holder.time = (TextView) convertView.findViewById(R.id.cboss_detail2_time);
			holder.rate = (TextView) convertView.findViewById(R.id.cboss_detail2_rate);
			convertView.setTag(holder);
		}
		holder.name.setText(mList.get(position).getName());
		holder.score.setText(mList.get(position).getScore());
		holder.time.setText(mList.get(position).getTime());
		holder.err.setText(mList.get(position).getErr());
		holder.rate.setText(mList.get(position).getRate());
		if (mList.get(position).getState().equals("1")) {
			holder.score.setBackgroundResource(R.drawable.rect_green_bg);
		} else if (mList.get(position).getState().equals("2")) {
			holder.score.setBackgroundResource(R.drawable.rect_yellow_bg);
		} else if (mList.get(position).getState().equals("3")) {
			holder.score.setBackgroundResource(R.drawable.rect_orange_bg);
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView name, err, time, score, rate;
	}

}
