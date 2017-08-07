package com.chinamobile.yunweizhushou.ui.capitalrecorded.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RityajiImageBean;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class ImageBlockAdapter extends AbsBaseAdapter<RityajiImageBean> {

	private List<RityajiImageBean> mList;

	public ImageBlockAdapter(Context context, List<RityajiImageBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
	}

	@Override
	protected View newView(View convertView, RityajiImageBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.image_block_title);
			holder.img = (ImageView) convertView.findViewById(R.id.image_block_image);
			convertView.setTag(holder);
		}
		holder.title.setText(mList.get(position).getTitle());
		if (mList.get(position).getImageUrl() != null) {
			MainApplication.mImageLoader.displayImage(mList.get(position).getImageUrl(), holder.img);
		} else {
			holder.img.setImageResource(R.mipmap.icon_default);
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView title;
		ImageView img;
	}

}
