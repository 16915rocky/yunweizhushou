package com.chinamobile.yunweizhushou.ui.PayMoreGetShit.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.PayMoreGetShit.bean.PayMoreBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class PayMoreAdapter extends AbsBaseAdapter<PayMoreBean> {

	private List<PayMoreBean> list;

	public PayMoreAdapter(Context context, List<PayMoreBean> list, int resourceId) {
		super(context, list, resourceId);
		this.list = list;
	}

	@Override
	protected View newView(View convertView, PayMoreBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.item1 = (TextView) convertView.findViewById(R.id.list_4_item1);
			holder.item2 = (TextView) convertView.findViewById(R.id.list_4_item2);
			holder.item3 = (TextView) convertView.findViewById(R.id.list_4_item3);
			holder.item4 = (TextView) convertView.findViewById(R.id.list_4_item4);
			convertView.setTag(holder);
		}
		holder.item1.setText(list.get(position).getRegion_id());
		holder.item2.setText(list.get(position).getExec_begin_date());
		holder.item3.setText(list.get(position).getExec_end_date());
		holder.item4.setText(list.get(position).getState());

		return convertView;
	}

	private static class ViewHolder {
		TextView item1, item2, item3, item4;
	}

}
