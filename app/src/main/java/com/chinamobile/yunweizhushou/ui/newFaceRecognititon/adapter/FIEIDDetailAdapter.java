package com.chinamobile.yunweizhushou.ui.newFaceRecognititon.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.newFaceRecognititon.bean.FILEIDDetailBean;

import java.util.List;

public class FIEIDDetailAdapter extends AbsBaseAdapter<FILEIDDetailBean> {

	private List<FILEIDDetailBean> mList;

	public FIEIDDetailAdapter(Context context, List<FILEIDDetailBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, FILEIDDetailBean n, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.city = (TextView) convertView.findViewById(R.id.list_3_item1);
			holder.value = (TextView) convertView.findViewById(R.id.list_3_item2);
			holder.avg = (TextView) convertView.findViewById(R.id.list_3_item3);

			convertView.setTag(holder);
		}
		holder.city.setText(mList.get(position).getCity());
		holder.value.setText(mList.get(position).getValue());
		holder.avg.setText(mList.get(position).getAvg()+"%");
		return convertView;
	}

	private static class Viewholder {
		TextView avg, city, value;
	}

}
