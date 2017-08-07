package com.chinamobile.yunweizhushou.ui.accountingArea.fragments;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.accountingArea.bean.DailyOperationsBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class DailyOperationsAdapter extends AbsBaseAdapter<DailyOperationsBean> {

	private List<DailyOperationsBean> mList;
	private int[] images = new int[] { R.mipmap.icon_more_wave1, R.mipmap.icon_more_wave2,
			R.mipmap.icon_more_wave3, R.mipmap.icon_more_wave4, R.mipmap.icon_more_wave5,
			R.mipmap.icon_more_wave6, R.mipmap.icon_more_wave7, R.mipmap.icon_more_wave8,
			R.mipmap.icon_more_wave9 };

	public void initList(List<DailyOperationsBean> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public DailyOperationsAdapter(Context context, List<DailyOperationsBean> list, int resourceId) {
		super(context, list, resourceId);
		// TODO Auto-generated constructor stub
		mList = list;
	}

	@Override
	protected View newView(View convertView, DailyOperationsBean t, int position) {
		// TODO Auto-generated method stub
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.more_wave_title);
			holder.subtitle = (TextView) convertView.findViewById(R.id.more_wave_subtitle);
			holder.image = (ImageView) convertView.findViewById(R.id.more_wave_image);
			convertView.setTag(holder);
		}
		holder.title.setText(mList.get(position).getPROCNAME());
		holder.subtitle.setText(mList.get(position).getPROCNAME());

		if ("".equals(mList.get(position).getIMAGEURL())) {
			holder.image.setImageResource(images[position]);
		} else {
			// imageloder
		}
		return convertView;
	}

	private class ViewHolder {
		TextView title, subtitle;
		ImageView image;
	}
}
