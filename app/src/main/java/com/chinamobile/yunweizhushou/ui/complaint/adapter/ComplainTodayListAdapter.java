package com.chinamobile.yunweizhushou.ui.complaint.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplainTodayItemBean;

import java.util.List;

public class ComplainTodayListAdapter extends AbsBaseAdapter<ComplainTodayItemBean> {

	private List<ComplainTodayItemBean> mList;

	public ComplainTodayListAdapter(Context context, List<ComplainTodayItemBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
	}

	@Override
	protected View newView(View convertView, ComplainTodayItemBean t, int position) {

		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.complain_today_item_name);
			holder.index = (TextView) convertView.findViewById(R.id.complain_today_item_index);
			holder.num = (TextView) convertView.findViewById(R.id.complain_today_item_num);
		}
		if (position == 0) {
			holder.index.setBackgroundResource(R.drawable.oval_orange);
		} else if (position == 1) {
			holder.index.setBackgroundResource(R.drawable.oval_yellow);
		} else if (position == 2) {
			holder.index.setBackgroundResource(R.drawable.oval_blue);
		} else {
			holder.index.setBackgroundResource(R.drawable.oval_gray);
		}
		if (position < 9) {
			holder.index.setText("0" + (position + 1));
		} else {
			holder.index.setText("" + (position + 1));
		}
		holder.name.setText(mList.get(position).getName());
		holder.num.setText(mList.get(position).getNumber());
		if (!TextUtils.isEmpty(mList.get(position).getColor())) {
			if (mList.get(position).getColor().equals("1")) {
				holder.num.setBackgroundResource(R.drawable.rect_red_bg);
			} else {
				holder.num.setBackgroundResource(R.drawable.rect_green_bg);
			}
		}

		return convertView;
	}

	private class ViewHolder {
		private TextView index, name, num;
	}
}
