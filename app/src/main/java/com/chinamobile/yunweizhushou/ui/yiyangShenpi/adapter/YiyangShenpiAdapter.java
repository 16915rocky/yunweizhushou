package com.chinamobile.yunweizhushou.ui.yiyangShenpi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yiyangShenpi.bean.YiyangShenpiBean1;

import java.util.List;

public class YiyangShenpiAdapter extends AbsBaseAdapter<YiyangShenpiBean1> {

	private List<YiyangShenpiBean1> mList;

	public YiyangShenpiAdapter(Context context, List<YiyangShenpiBean1> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, YiyangShenpiBean1 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.approver_name = (TextView) convertView.findViewById(R.id.approver_name);
			holder.auth_type = (TextView) convertView.findViewById(R.id.auth_type);
			holder.object_name = (TextView) convertView.findViewById(R.id.object_name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
			convertView.setTag(holder);
		}
		holder.approver_name.setText(mList.get(position).getApprover_name());
		holder.auth_type.setText(mList.get(position).getAuth_type());
		holder.object_name.setText(mList.get(position).getObject_name());
		holder.time.setText(mList.get(position).getTime());
		holder.user_name.setText(mList.get(position).getUser_name());

		return convertView;
	}

	private static class ViewHolder {
		private TextView approver_name, auth_type, id, object_name, time,user_name;
	}
}
