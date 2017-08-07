package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;

import java.util.List;

public class SearchMoreItemAdapter extends AbsBaseAdapter<MainPageGridBean> {

	private List<MainPageGridBean> mList;
	private Context mContext;

	public SearchMoreItemAdapter(Context context, List<MainPageGridBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, MainPageGridBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.img_search_item = (ImageView) convertView.findViewById(R.id.img_search_item);
			holder.tv_search_item = (TextView) convertView.findViewById(R.id.tv_search_item);
			convertView.setTag(holder);
		}else{
			holder= (ViewHolder) convertView.getTag();
		}
		Glide.with(mContext).load(mList.get(position).getIcon()).into(holder.img_search_item);
		holder.tv_search_item.setText(mList.get(position).getD_name());
		return convertView;
	}

	private static class ViewHolder {
		private ImageView img_search_item;
		private TextView tv_search_item;
	}
}
