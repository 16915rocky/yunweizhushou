package com.chinamobile.yunweizhushou.ui.autoCheck.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.autoCheck.bean.AutoCheckUnsolveBean;

import java.util.List;

public class AutoCheckUnsolveAdapter extends AbsBaseAdapter<AutoCheckUnsolveBean> {

	private List<AutoCheckUnsolveBean> mList;

	public AutoCheckUnsolveAdapter(Context context, List<AutoCheckUnsolveBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, AutoCheckUnsolveBean t, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.num = (TextView) convertView.findViewById(R.id.auto_unsolve_num);
			holder.title = (TextView) convertView.findViewById(R.id.auto_unsolve_title);
			holder.content = (TextView) convertView.findViewById(R.id.auto_unsolve_content);
			holder.level = (TextView) convertView.findViewById(R.id.auto_unsolve_level);
			holder.state = (TextView) convertView.findViewById(R.id.auto_unsolve_state);
			holder.time = (TextView) convertView.findViewById(R.id.auto_unsolve_time);
			holder.progress = (TextView) convertView.findViewById(R.id.auto_unsolve_progress);
			holder.system = (TextView) convertView.findViewById(R.id.auto_unsolve_system);
			holder.person = (TextView) convertView.findViewById(R.id.auto_unsolve_group);
			convertView.setTag(holder);
		}

		holder.num.setText((position + 1) + "");
		holder.title.setText(mList.get(position).getTitle());
		holder.content.setText(mList.get(position).getFunction_des());
		holder.level.setText(mList.get(position).getPriority_id());
		if (mList.get(position).getPriority_id().equals("高")) {
			holder.level.setBackgroundResource(R.drawable.oval_red);
		} else if (mList.get(position).getPriority_id().equals("中")) {
			holder.level.setBackgroundResource(R.drawable.oval_orange);
		} else if (mList.get(position).getPriority_id().equals("低")) {
			holder.level.setBackgroundResource(R.drawable.oval_yellow);
		}
		if (mList.get(position).getState().length() == 4) {
			holder.state.setText(mList.get(position).getState().substring(0, 2) + "\n"
					+ mList.get(position).getState().substring(2));
		} else {
			holder.state.setText(mList.get(position).getState());
		}
		holder.time.setText(mList.get(position).getWorking_hours() + "h");
		StringBuffer sb = new StringBuffer();
		if (mList.get(position).getIsList() != null && mList.get(position).getIsList().size() > 0) {
			sb.append("当前进度:\n");
			for (int i = 0; i < mList.get(position).getIsList().size(); i++) {
				sb.append(mList.get(position).getIsList().get(i).getFollow_time() + " "
						+ mList.get(position).getIsList().get(i).getSolution_desc() + "\n");
			}
		} else {
			holder.progress.setText("暂无进度");
		}
		holder.progress.setText(sb.toString());
		holder.system.setText(mList.get(position).getIns_system());
		holder.person.setText(mList.get(position).getCharge_op_name());
		return convertView;
	}

	private static class Viewholder {
		private TextView num, title, content, level, state, time, progress, system, person;
	}

}
