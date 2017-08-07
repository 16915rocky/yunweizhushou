package com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetBean5;

import java.util.List;

public class YunweiAssetMCAdapter2 extends AbsBaseAdapter<YunweiAssetBean5> {

	private List<YunweiAssetBean5> mList;
	private int layout;

	public YunweiAssetMCAdapter2(Context context, List<YunweiAssetBean5> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.layout = resourceId;
	}

	@Override
	protected View newView(View convertView, YunweiAssetBean5 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (layout == R.layout.item_list_5) {
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.list_5_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.list_5_item2);
				holder.item3 = (TextView) convertView.findViewById(R.id.list_5_item3);
				holder.item4 = (TextView) convertView.findViewById(R.id.list_5_item4);
				holder.item5 = (TextView) convertView.findViewById(R.id.list_5_item5);
				convertView.setTag(holder);
			}
		}else{
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.list_4_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.list_4_item2);
				holder.item3 = (TextView) convertView.findViewById(R.id.list_4_item3);
				holder.item4 = (TextView) convertView.findViewById(R.id.list_4_item4);
				convertView.setTag(holder);
			}
		}		
		holder.item1.setText(mList.get(position).getName());
		if (layout == R.layout.item_list_5) {
			holder.item2.setText(mList.get(position).getResdetail());
			holder.item3.setText(mList.get(position).getBeservice());
			holder.item4.setText(mList.get(position).getInstnum());
			holder.item5.setText(mList.get(position).getAperson());
		} else {
			holder.item2.setText(mList.get(position).getBeservice());
			holder.item3.setText(mList.get(position).getResdetail());
			holder.item4.setText(mList.get(position).getAperson());
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView item1, item2, item3, item4, item5;
	}
}
