package com.chinamobile.yunweizhushou.ui.serviceChain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.serviceChain.bean.BusinessHealthBean;

import java.util.List;

public class BusinessHealthAdapter extends AbsBaseAdapter<BusinessHealthBean> {

	private List<BusinessHealthBean> mList;
	private Context  mContext;

	public BusinessHealthAdapter(Context context, List<BusinessHealthBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, BusinessHealthBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.create_time = (TextView) convertView.findViewById(R.id.item1);
			holder.sys_name = (TextView) convertView.findViewById(R.id.item2);
			holder.busi_name = (TextView) convertView.findViewById(R.id.item3);
			holder.final_score = (TextView) convertView.findViewById(R.id.item4);
			convertView.setTag(holder);
		}
		holder.create_time.setText(mList.get(position).getCreate_time());
		holder.sys_name.setText(mList.get(position).getSys_name());
		holder.busi_name.setText(mList.get(position).getBusi_name());
		holder.final_score.setText(mList.get(position).getFinal_score());
		if("ill".equals(mList.get(position).getType())){
			holder.final_score.setBackground(mContext.getResources().getDrawable(R.drawable.corner_rectangle_red_bg));
		}else if("little_empty".equals(mList.get(position).getType())){
			holder.final_score.setBackground(mContext.getResources().getDrawable(R.drawable.corner_rectangle_orange_bg));
		}else if("sub-health".equals(mList.get(position).getType())){
			holder.final_score.setBackground(mContext.getResources().getDrawable(R.drawable.corner_rectangle_lightgreen_bg));
		}else if("good".equals(mList.get(position).getType())){
			holder.final_score.setBackground(mContext.getResources().getDrawable(R.drawable.corner_rectangle_deepgreen_bg));
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView  create_time, sys_name, busi_name, final_score;
	}
}
