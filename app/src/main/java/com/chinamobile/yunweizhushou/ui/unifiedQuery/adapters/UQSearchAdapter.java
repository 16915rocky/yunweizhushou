package com.chinamobile.yunweizhushou.ui.unifiedQuery.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.KeyValueBean2;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class UQSearchAdapter extends AbsBaseAdapter<KeyValueBean2> {

	private List<KeyValueBean2> mList;
	private Context mContext;

	public UQSearchAdapter(Context context, List<KeyValueBean2> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		mContext = context;
	}

	@Override
	protected View newView(View convertView, KeyValueBean2 t, final int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.item1 = (TextView) convertView.findViewById(R.id.tv_item1);
			holder.item2 = (TextView) convertView.findViewById(R.id.tv_item2);
			convertView.setTag(holder);
		}

		holder.item1.setText(mList.get(position).getKey());
		holder.item2.setText(mList.get(position).getValue());
		return convertView;
	}

	private static class ViewHolder {
		private TextView item1;
		private TextView item2;
	}

}
