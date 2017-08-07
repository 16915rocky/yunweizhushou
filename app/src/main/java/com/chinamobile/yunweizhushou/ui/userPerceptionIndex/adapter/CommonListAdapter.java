package com.chinamobile.yunweizhushou.ui.userPerceptionIndex.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.bean.TmValueBean;

import java.util.List;

public class CommonListAdapter extends AbsBaseAdapter<TmValueBean> {

	private List<TmValueBean> mList;

	public CommonListAdapter(Context context, List<TmValueBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, TmValueBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.tm = (TextView) convertView.findViewById(R.id.list_2_item1);
			holder.value = (TextView) convertView.findViewById(R.id.list_2_item2);
			convertView.setTag(holder);
		}
		holder.tm.setText(mList.get(position).getTm());
		holder.value.setText(mList.get(position).getValue());
		return convertView;
	}

	private static class ViewHolder {
		private TextView tm, value;
	}
}
