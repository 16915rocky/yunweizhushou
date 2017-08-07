package com.chinamobile.yunweizhushou.ui.openCenter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.OpenCenterNetYuanBacklogBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class OpenCenterNetYuanBacklogAdapter extends AbsBaseAdapter<OpenCenterNetYuanBacklogBean> {

	private List<OpenCenterNetYuanBacklogBean> mList;

	public OpenCenterNetYuanBacklogAdapter(Context context, List<OpenCenterNetYuanBacklogBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, OpenCenterNetYuanBacklogBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.area = (TextView) convertView.findViewById(R.id.list_4_item1);
			holder.ps_service_type = (TextView) convertView.findViewById(R.id.list_4_item2);
			holder.ps_net_code_name = (TextView) convertView.findViewById(R.id.list_4_item3);
			holder.num = (TextView) convertView.findViewById(R.id.list_4_item4);
			convertView.setTag(holder);
		}
		holder.area.setText(mList.get(position).getArea());
		holder.ps_service_type.setText(mList.get(position).getPs_service_type());
		holder.ps_net_code_name.setText(mList.get(position).getPs_net_code_name());
		holder.num.setText(mList.get(position).getNum());

		return convertView;
	}

	private static class ViewHolder {
		private TextView ps_net_code_name, ps_service_type, num, area;
	}
}
