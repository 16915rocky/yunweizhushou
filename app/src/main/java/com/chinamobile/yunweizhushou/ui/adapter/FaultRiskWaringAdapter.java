package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultRiskWarningBean;

import java.util.List;

public class FaultRiskWaringAdapter extends AbsBaseAdapter<FaultRiskWarningBean> {

	private List<FaultRiskWarningBean> mList;
	private Context mContext;
	public FaultRiskWaringAdapter(Context context, List<FaultRiskWarningBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, FaultRiskWarningBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.tv_item1);
			holder.title = (TextView) convertView.findViewById(R.id.tv_item2);
			holder.subTitle = (TextView) convertView.findViewById(R.id.tv_item3);
			holder.status = (TextView) convertView.findViewById(R.id.tv_item4);
		
			convertView.setTag(holder);
		}
		holder.date.setText(mList.get(position).getDate());
		holder.title.setText(mList.get(position).getTitle());
		holder.subTitle.setText(mList.get(position).getSubTitle());
		holder.status.setText(mList.get(position).getStatus());
		if("解除".equals(mList.get(position).getStatus())){
			holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.oval_gray));
		}else if("启动".equals(mList.get(position).getStatus())){
			holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.oval_orange));
		}else if("阶段 ".equals(mList.get(position).getStatus())){
			holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.oval_orange));
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView date , title , subTitle, status;
	}
}
