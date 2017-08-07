package com.chinamobile.yunweizhushou.ui.systemTree.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.systemTree.HallFocusManageActivity;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.SystemTreeBean;

import java.util.List;

public class SystemTreeAdapter extends AbsBaseAdapter<SystemTreeBean.SystemTreeSubBean> {

	private List<SystemTreeBean.SystemTreeSubBean> mList;
	private Context mContext;

	public SystemTreeAdapter(Context context, List<SystemTreeBean.SystemTreeSubBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		mContext = context;
	}

	@Override
	protected View newView(View convertView, SystemTreeBean.SystemTreeSubBean t, final int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.system_tree_item_type);
			holder.title = (TextView) convertView.findViewById(R.id.system_tree_item_title);
			convertView.setTag(holder);
		}
		if (mList.get(position).getSystem_level().equals("核心系统")) {
			holder.image.setVisibility(View.VISIBLE);
			holder.image.setImageResource(R.mipmap.icon_star_red_m);
		} else if (mList.get(position).getSystem_level().equals("重要系统")) {
			holder.image.setImageResource(R.mipmap.icon_star_yellow_m);
			holder.image.setVisibility(View.VISIBLE);
		} else if (mList.get(position).getSystem_level().equals("一般系统")) {
			holder.image.setImageResource(R.mipmap.icon_star_gray_m);
			holder.image.setVisibility(View.VISIBLE);
		} else {
			holder.image.setVisibility(View.INVISIBLE);
		}
		holder.title.setText(mList.get(position).getId_3_name());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, HallFocusManageActivity.class);
				intent.putExtra("id", mList.get(position).getId_3_name());
				mContext.startActivity(intent);
			}
		});

		return convertView;
	}

	private static class ViewHolder {
		private ImageView image;
		private TextView title;
	}

}
