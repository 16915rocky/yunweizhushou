package com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetBean7;

import java.util.List;

public class YunweiAssetDBMAdapter extends AbsBaseAdapter<YunweiAssetBean7> {

	private List<YunweiAssetBean7> mList;

	public YunweiAssetDBMAdapter(Context context, List<YunweiAssetBean7> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, YunweiAssetBean7 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.resdetail = (TextView) convertView.findViewById(R.id.list_3_item1);
			holder.name = (TextView) convertView.findViewById(R.id.list_3_item2);
			holder.beservice = (TextView) convertView.findViewById(R.id.list_3_item3);

			convertView.setTag(holder);
		}
		holder.resdetail.setText(mList.get(position).getResdetail());
		holder.name.setText(mList.get(position).getName());
		holder.beservice.setText(mList.get(position).getBeservice());

		return convertView;
	}

	private static class ViewHolder {
		private TextView beservice, resdetail, name;
	}
}
