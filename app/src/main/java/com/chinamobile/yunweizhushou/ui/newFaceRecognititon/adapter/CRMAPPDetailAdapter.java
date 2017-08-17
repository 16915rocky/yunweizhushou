package com.chinamobile.yunweizhushou.ui.newFaceRecognititon.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.newFaceRecognititon.bean.CRMAPPDetailBean;

import java.util.List;

public class CRMAPPDetailAdapter extends AbsBaseAdapter<CRMAPPDetailBean> {

	private List<CRMAPPDetailBean> mList;

	public CRMAPPDetailAdapter(Context context, List<CRMAPPDetailBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, CRMAPPDetailBean n, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.city = (TextView) convertView.findViewById(R.id.item1);
			holder.value = (TextView) convertView.findViewById(R.id.item2);
			holder.avg = (TextView) convertView.findViewById(R.id.item3);
			holder.consistent = (TextView) convertView.findViewById(R.id.item4);
			holder.inconformity = (TextView) convertView.findViewById(R.id.item5);
			holder.non_exist = (TextView) convertView.findViewById(R.id.item6);
			holder.non_picture = (TextView) convertView.findViewById(R.id.item7);
			holder.back_error = (TextView) convertView.findViewById(R.id.item8);
			convertView.setTag(holder);
		}
		holder.city.setText(mList.get(position).getCity());
		holder.value.setText(mList.get(position).getValue());
		holder.avg.setText(mList.get(position).getAvg()+"%");
		holder.consistent.setText(mList.get(position).getConsistent());
		holder.inconformity.setText(mList.get(position).getInconformity());
		holder.non_exist.setText(mList.get(position).getNon_exist());
		holder.non_picture.setText(mList.get(position).getNon_picture());
		holder.back_error.setText(mList.get(position).getBack_error());
		return convertView;
	}

	private static class Viewholder {
		TextView avg, city, value,consistent,inconformity,non_exist,non_picture,back_error;
	}

}
