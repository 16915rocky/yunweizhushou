package com.chinamobile.yunweizhushou.ui.complaint.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplainHotBean;

import java.util.List;

public class ComplainHotAdapter extends AbsBaseAdapter<ComplainHotBean> {

	private List<ComplainHotBean> mList;

	public ComplainHotAdapter(Context context, List<ComplainHotBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
	}

	@Override
	protected View newView(View convertView, ComplainHotBean t, int position) {

		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.num = (TextView) convertView.findViewById(R.id.complain_hot_item_num);
			holder.title = (TextView) convertView.findViewById(R.id.complain_hot_item_title);
			holder.content = (TextView) convertView.findViewById(R.id.complain_hot_item_content);
			holder.reason = (TextView) convertView.findViewById(R.id.complain_hot_item_reason);
			holder.progress = (TextView) convertView.findViewById(R.id.complain_hot_item_progress);
		}
		holder.num.setText(mList.get(position).getCmNumber());
		holder.title.setText(mList.get(position).getTitle());
		holder.content.setText(mList.get(position).getRecord());
		holder.reason.setText(mList.get(position).getReason());
		holder.progress.setText(mList.get(position).getProgress());

		return convertView;
	}

	private class ViewHolder {
		private TextView num, title, content, reason, progress;
	}

}
