package com.chinamobile.yunweizhushou.ui.reconciliationSchedule.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.reconciliationSchedule.bean.ReconciliationScheduleBean;

import java.util.List;

public class ReconciliationScheduleAdapter extends BaseAdapter {

	private List<ReconciliationScheduleBean.ItemList> mList;
	private Context context;

	public ReconciliationScheduleAdapter(Context context, List<ReconciliationScheduleBean.ItemList> list) {
		mList = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_reconciliation_schedule, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ReconciliationScheduleBean.ItemList item = mList.get(position);
		holder.title.setText(item.getRegion_id());
		holder.subtitle.setText(item.getExt3());
		holder.num.setText("(" + item.getExt1() + "/29)");
		if (item.getExt1().equals("29")) {
			holder.state.setBackgroundResource(R.drawable.rect_round_green);
		} else {
			holder.state.setBackgroundResource(R.drawable.rect_round_gray);
		}
		holder.state.setText(item.getState());
		return convertView;
	}

	private class ViewHolder {
		private TextView title, subtitle, num, state;

		public ViewHolder(View view) {
			title = (TextView) view.findViewById(R.id.reconciliation_title);
			subtitle = (TextView) view.findViewById(R.id.reconciliation_subtitle);
			num = (TextView) view.findViewById(R.id.reconciliation_num);
			state = (TextView) view.findViewById(R.id.reconciliation_state);
		}
	}
}