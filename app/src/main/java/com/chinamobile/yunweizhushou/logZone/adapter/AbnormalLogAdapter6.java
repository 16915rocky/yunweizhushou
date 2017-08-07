package com.chinamobile.yunweizhushou.logZone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.logZone.bean.LogZoneBean6;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class AbnormalLogAdapter6 extends AbsBaseAdapter<LogZoneBean6> {

	private List<LogZoneBean6> mList;

	public AbnormalLogAdapter6(Context context, List<LogZoneBean6> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, LogZoneBean6 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.doc_count = (TextView) convertView.findViewById(R.id.logzone_table3_tv2);
			holder.name = (TextView) convertView.findViewById(R.id.logzone_table3_tv3);
			// holder.insert_time = (TextView)
			// convertView.findViewById(R.id.list_3_item3);
			convertView.setTag(holder);
		}
		holder.doc_count.setText(mList.get(position).getDoc_count());
		holder.name.setText(mList.get(position).getException_key());
		// holder.insert_time.setText(mList.get(position).getInsert_time());

		return convertView;
	}

	private static class ViewHolder {
		private TextView doc_count, name, insert_time;
	}
}
