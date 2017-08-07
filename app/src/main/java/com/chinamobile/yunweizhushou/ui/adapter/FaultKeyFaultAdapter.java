package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultKeyBean;

import java.util.List;

public class FaultKeyFaultAdapter extends AbsBaseAdapter<FaultKeyBean> {

	private List<FaultKeyBean> mList;
	private Context mContext;

	public FaultKeyFaultAdapter(Context context, List<FaultKeyBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, FaultKeyBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.tv_item1);
			holder.key_level = (TextView) convertView.findViewById(R.id.tv_item2);
			holder.title = (TextView) convertView.findViewById(R.id.tv_item3);
			holder.status = (TextView) convertView.findViewById(R.id.tv_item4);
		
			convertView.setTag(holder);
		}
		holder.date.setText(mList.get(position).getDate());
		holder.key_level.setText(mList.get(position).getKey_level());
		holder.title.setText(mList.get(position).getTitle());
		holder.status.setText(mList.get(position).getStatus());
		if("处理中".equals(mList.get(position).getStatus())){
			holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.oval_orange));
		}else if("系统恢复".equals(mList.get(position).getStatus())){
			holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.oval_green));
		}else if("业务恢复".equals(mList.get(position).getStatus())){
			holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.oval_gray));
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView date , key_level , title, status;
	}
}
