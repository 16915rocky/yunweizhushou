package com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.BusinessCertificateBean;

import java.util.List;

public class YunweiAssetBusinessCertificateAdapter extends AbsBaseAdapter<BusinessCertificateBean> {

	private List<BusinessCertificateBean> mList;

	public YunweiAssetBusinessCertificateAdapter(Context context, List<BusinessCertificateBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, BusinessCertificateBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.first_type = (TextView) convertView.findViewById(R.id.list_5_item1);
			holder.second_type = (TextView) convertView.findViewById(R.id.list_5_item2);
			holder.third_type = (TextView) convertView.findViewById(R.id.list_5_item3);
			holder.tab_number = (TextView) convertView.findViewById(R.id.list_5_item4);
			holder.online_storage = (TextView) convertView.findViewById(R.id.list_5_item5);
		
			convertView.setTag(holder);
		}
		holder.first_type.setText(mList.get(position).getFirst_type());
		holder.second_type.setText(mList.get(position).getSecond_type());
		holder.third_type.setText(mList.get(position).getThird_type());
		holder.tab_number.setText(mList.get(position).getTab_number());
		holder.online_storage.setText(mList.get(position).getOnline_storage());

		return convertView;
	}

	private static class ViewHolder {
		private TextView first_type, second_type, third_type, tab_number, online_storage;
	}
}


