package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultTodayBean;

import java.util.List;

public class FaultTodayNewAdapter extends AbsBaseAdapter<FaultTodayBean> {

	private List<FaultTodayBean> mList;

	public FaultTodayNewAdapter(Context context, List<FaultTodayBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, FaultTodayBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.num = (TextView) convertView.findViewById(R.id.fault_today_new_num);
			holder.title = (TextView) convertView.findViewById(R.id.fault_today_new_title);
			holder.level = (TextView) convertView.findViewById(R.id.fault_today_new_level);
			holder.state = (TextView) convertView.findViewById(R.id.fault_today_new_state);
			holder.source = (TextView) convertView.findViewById(R.id.fault_today_new_source);
			holder.time = (TextView) convertView.findViewById(R.id.fault_today_new_time);
			holder.standard = (TextView) convertView.findViewById(R.id.fault_today_new_standard);
			holder.progress = (TextView) convertView.findViewById(R.id.fault_today_new_progress);
			convertView.setTag(holder);
		}
		holder.num.setText("" + (position + 1));
		holder.title.setText(mList.get(position).getAccidentTitle());
		if (mList.get(position).getPdlevel().equals("1")) {
			holder.level.setText("一级");
			holder.level.setBackgroundResource(R.drawable.oval_red);
		} else if (mList.get(position).getPdlevel().equals("2")) {
			holder.level.setText("二级");
			holder.level.setBackgroundResource(R.drawable.oval_orange);
		} else if (mList.get(position).getPdlevel().equals("3")) {
			holder.level.setText("三级");
			holder.level.setBackgroundResource(R.drawable.oval_yellow);
		} else if (mList.get(position).getPdlevel().equals("4")) {
			holder.level.setText("四级");
			holder.level.setBackgroundResource(R.drawable.oval_blue);
		} else if (mList.get(position).getPdlevel().equals("5")) {
			holder.level.setText("五级");
			holder.level.setBackgroundResource(R.drawable.oval_gray);
		} else if (mList.get(position).getPdlevel().equals("6")) {
			holder.level.setText("未达\n五级");
			holder.level.setBackgroundResource(R.drawable.oval_gray);
		} else if (mList.get(position).getPdlevel().equals("7")) {
			holder.level.setText("业务\n理解");
			holder.level.setBackgroundResource(R.drawable.oval_gray);
		} else if (mList.get(position).getPdlevel().equals("8")) {
			holder.level.setText("非支撑");
			holder.level.setBackgroundResource(R.drawable.oval_gray);
		} else if (mList.get(position).getPdlevel().equals("9")) {
			holder.level.setText("同原因");
			holder.level.setBackgroundResource(R.drawable.oval_gray);
		} else {
			holder.level.setText("其他");
			holder.level.setBackgroundResource(R.drawable.oval_gray);
		}

		holder.time.setText(mList.get(position).getDuration());
		holder.source.setText(mList.get(position).getAccidentSource());
		holder.standard.setText(mList.get(position).getContantConfig());
		holder.state.setText(mList.get(position).getDplStatus());
		int size = mList.get(position).getProgressList().size();
		if (size == 0) {
			holder.progress.setText("暂无进度");
		} else {
			StringBuffer sb2 = new StringBuffer();
			sb2.append("当前进度\n");
			for (int i = 0; i < (size <= 2 ? size : 2); i++) {
				sb2.append(mList.get(position).getProgressList().get(i).getTime() + " "
						+ mList.get(position).getProgressList().get(i).getContent() + "\n");
			}
			holder.progress.setText(sb2.toString());
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView num, title, level, state, source, time, standard, progress;
	}

}
