package com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetDataSaveBean;

import java.util.List;

public class YunweiAssetDataSaveAdapter extends AbsBaseAdapter<YunweiAssetDataSaveBean> {

	private List<YunweiAssetDataSaveBean> mList;

	public YunweiAssetDataSaveAdapter(Context context, List<YunweiAssetDataSaveBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, YunweiAssetDataSaveBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.first_type = (TextView) convertView.findViewById(R.id.list_8_item1);
			holder.sec_type = (TextView) convertView.findViewById(R.id.list_8_item2);
			holder.online_storage = (TextView) convertView.findViewById(R.id.list_8_item3);
			holder.his_storage = (TextView) convertView.findViewById(R.id.list_8_item4);
			holder.nearline_storage = (TextView) convertView.findViewById(R.id.list_8_item5);
			holder.off_storage = (TextView) convertView.findViewById(R.id.list_8_item6);
			holder.table_num = (TextView) convertView.findViewById(R.id.list_8_item7);
			holder.compliance_rate = (TextView) convertView.findViewById(R.id.list_8_item8);
			convertView.setTag(holder);
		}
		holder.first_type.setText(mList.get(position).getFirst_type());
		holder.sec_type.setText(mList.get(position).getSec_type());
		holder.online_storage.setText(mList.get(position).getOnline_storage());
		holder.his_storage.setText(mList.get(position).getHis_storage());
		holder.nearline_storage.setText(mList.get(position).getNearline_storage());
		holder.off_storage.setText(mList.get(position).getOff_storage());
		holder.table_num.setText(mList.get(position).getTable_num());
		holder.compliance_rate.setText(mList.get(position).getCompliance_rate());
		return convertView;
	}

	private static class ViewHolder {
		private TextView first_type, sec_type, online_storage, his_storage, nearline_storage,off_storage,table_num,compliance_rate;
	}
}
