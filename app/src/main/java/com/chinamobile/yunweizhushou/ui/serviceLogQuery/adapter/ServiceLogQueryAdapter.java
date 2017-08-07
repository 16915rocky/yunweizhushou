package com.chinamobile.yunweizhushou.ui.serviceLogQuery.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.serviceLogQuery.bean.ServiceLogQueryBean;

import java.util.List;

public class ServiceLogQueryAdapter extends AbsBaseAdapter<ServiceLogQueryBean> {

	private List<ServiceLogQueryBean> mList;

	public ServiceLogQueryAdapter(Context context, List<ServiceLogQueryBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, ServiceLogQueryBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.op_date = (TextView) convertView.findViewById(R.id.q_item1);
			holder.op_code = (TextView) convertView.findViewById(R.id.q_item2);
			holder.return_result = (TextView) convertView.findViewById(R.id.q_item3);
			holder.return_desc = (TextView) convertView.findViewById(R.id.q_item4);
			holder.service_key = (TextView) convertView.findViewById(R.id.q_item5);
			holder.sys_op_id = (TextView) convertView.findViewById(R.id.q_item6);
			holder.error_stack_trace = (TextView) convertView.findViewById(R.id.q_item7);
			convertView.setTag(holder);
		}
		holder.op_date.setText(mList.get(position).getOp_date());
		holder.op_code.setText(mList.get(position).getOp_code());
		holder.return_result.setText(mList.get(position).getReturn_result());
		holder.return_desc.setText(mList.get(position).getReturn_desc());
		holder.service_key.setText(mList.get(position).getService_key());
		holder.sys_op_id.setText(mList.get(position).getSys_op_id());
		holder.error_stack_trace.setText(mList.get(position).getError_stack_trace());
		return convertView;
	}

	private static class ViewHolder {
		private TextView op_code, return_result, return_desc,service_key,sys_op_id,op_date,error_stack_trace;
	}
}
