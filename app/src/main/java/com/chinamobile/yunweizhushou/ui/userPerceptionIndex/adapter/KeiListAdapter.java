package com.chinamobile.yunweizhushou.ui.userPerceptionIndex.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.bean.KeiItemBean;

import java.util.List;

public class KeiListAdapter extends AbsBaseAdapter<KeiItemBean> {

	private List<KeiItemBean> mList;

	public KeiListAdapter(Context context, List<KeiItemBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, KeiItemBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.tv_class_name = (TextView) convertView.findViewById(R.id.tv_class_name);
			holder.tv_class_desc = (TextView) convertView.findViewById(R.id.tv_class_desc);
			holder.tv_class_standard = (TextView) convertView.findViewById(R.id.tv_class_standard);
			holder.tv_cnt_rate = (TextView) convertView.findViewById(R.id.tv_cnt_rate);
			convertView.setTag(holder);
		}
		holder.tv_class_name.setText(mList.get(position).getClass_name());
		holder.tv_class_desc.setText(mList.get(position).getClass_desc());
	
		holder.tv_class_standard.setText(mList.get(position).getClass_standard());
		if("PRFSN-4".equals(mList.get(position).getClass_id())){
			holder.tv_cnt_rate.setText(mList.get(position).getCnt_rate()+"\n(‰)");
		}else{
			holder.tv_cnt_rate.setText(mList.get(position).getCnt_rate()+"\n(%)");
		}
		

		return convertView;
	}

	private static class ViewHolder {
		private TextView tv_class_name, tv_class_desc, tv_class_standard, tv_cnt_rate;
	}
}
