package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultUnsolveBean;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageOfHistoryNextActivity;

import java.util.List;

public class FaultUnsolveAdapter extends AbsBaseAdapter<FaultUnsolveBean> {

	private List<FaultUnsolveBean> mList;
	private Context mContext;

	public FaultUnsolveAdapter(Context context, List<FaultUnsolveBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, FaultUnsolveBean t, final int position) {
		HolderView holder = (HolderView) convertView.getTag();
		if (holder == null) {
			holder = new HolderView();
			holder.title = (TextView) convertView.findViewById(R.id.fault_unsolve_title);
			holder.level = (TextView) convertView.findViewById(R.id.fault_unsolve_level);
			holder.group = (TextView) convertView.findViewById(R.id.fault_unsolve_group);
			holder.time = (TextView) convertView.findViewById(R.id.fault_unsolve_time);
			holder.progress = (TextView) convertView.findViewById(R.id.fault_unsolve_progress);
			holder.num = (TextView) convertView.findViewById(R.id.fault_unsolve_num);
			holder.state = (TextView) convertView.findViewById(R.id.fault_unsolve_state);

			convertView.setTag(holder);
		}
		holder.title.setText(mList.get(position).getAccidentTitle());
		holder.level.setText(mList.get(position).getPdlevel() + "级");
		holder.group.setText(mList.get(position).getHandleGroup());
		holder.time.setText(mList.get(position).getTime());
		holder.num.setText("" + (position + 1));
		holder.title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("key", mList.get(position).getChr_kfnottitle());
				intent.putExtra("gzNumber", mList.get(position).getAccidentId());
				intent.setClass(mContext, FaultManageOfHistoryNextActivity.class);
				mContext.startActivity(intent);
				
			}
		});
		holder.state.setText(mList.get(position).getDplStatus());
		// holder.state.setText(mList.get(position).getAccidentId());
		if (mList.get(position).getProgressList() != null && mList.get(position).getProgressList().size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mList.get(position).getProgressList().size(); i++) {
				sb.append(mList.get(position).getProgressList().get(i).getTime() + " "
						+ mList.get(position).getProgressList().get(i).getContent() + "\n");
			}
			holder.progress.setText(sb.toString());
		} else {
			holder.progress.setText("当前没有进度");
		}
		return convertView;
	}

	private class HolderView {
		private TextView title, level, group, time, progress, num, state;
	}

}
