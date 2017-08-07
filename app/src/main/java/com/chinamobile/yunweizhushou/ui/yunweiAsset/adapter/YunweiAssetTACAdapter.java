package com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetBean6;

import java.util.List;

public class YunweiAssetTACAdapter extends AbsBaseAdapter<YunweiAssetBean6> {

	private List<YunweiAssetBean6> mList;
	private String type;

	public YunweiAssetTACAdapter(Context context, List<YunweiAssetBean6> list, int resourceId, String type) {
		super(context, list, resourceId);
		mList = list;
		this.type = type;
	}

	@Override
	protected View newView(View convertView, YunweiAssetBean6 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.ip = (TextView) convertView.findViewById(R.id.list_6_item1);
			holder.name = (TextView) convertView.findViewById(R.id.list_6_item2);
			holder.system = (TextView) convertView.findViewById(R.id.list_6_item3);
			holder.num = (TextView) convertView.findViewById(R.id.list_6_item4);
			holder.avg = (TextView) convertView.findViewById(R.id.list_6_item5);
			holder.aperson = (TextView) convertView.findViewById(R.id.list_6_item6);
			if ("2".equals(type)) {
				holder.ip.setVisibility(View.GONE);
				holder.name.setVisibility(View.GONE);
			}
			convertView.setTag(holder);
		}
		if (!"2".equals(type)) {
			holder.ip.setText(mList.get(position).getIp());
			holder.name.setText(mList.get(position).getName());
		}
		holder.aperson.setText(mList.get(position).getAperson());
		holder.avg.setText(mList.get(position).getAvg()+"%");
		holder.num.setText(mList.get(position).getNum());
		holder.system.setText(mList.get(position).getSystem());

		return convertView;
	}

	private static class ViewHolder {
		private TextView ip, name, aperson, avg, num, system;
	}
}
