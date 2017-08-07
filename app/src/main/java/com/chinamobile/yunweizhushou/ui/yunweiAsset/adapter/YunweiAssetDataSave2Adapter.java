package com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetDataSave2Bean;

import java.util.List;

public class YunweiAssetDataSave2Adapter extends AbsBaseAdapter<YunweiAssetDataSave2Bean> {

	private List<YunweiAssetDataSave2Bean> mList;

	public YunweiAssetDataSave2Adapter(Context context, List<YunweiAssetDataSave2Bean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, YunweiAssetDataSave2Bean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.db_name = (TextView) convertView.findViewById(R.id.list_6_item1);
			holder.owner = (TextView) convertView.findViewById(R.id.list_6_item2);
			holder.table_name = (TextView) convertView.findViewById(R.id.list_6_item3);
			holder.system = (TextView) convertView.findViewById(R.id.list_6_item4);
			holder.table_space = (TextView) convertView.findViewById(R.id.list_6_item5);
			holder.check_status = (TextView) convertView.findViewById(R.id.list_6_item6);
		
			convertView.setTag(holder);
		}
		holder.db_name.setText(mList.get(position).getDb_name());
		holder.owner.setText(mList.get(position).getOwner());
		holder.table_name.setText(mList.get(position).getTable_name());
		holder.system.setText(mList.get(position).getSystem());
		holder.table_space.setText(mList.get(position).getTable_space());
		holder.check_status.setText(mList.get(position).getCheck_status());
		return convertView;
	}

	private static class ViewHolder {
		private TextView db_name, owner, table_name, system , table_space,check_status;
	}
}
