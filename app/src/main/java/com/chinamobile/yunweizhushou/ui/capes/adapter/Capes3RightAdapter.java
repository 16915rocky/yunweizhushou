package com.chinamobile.yunweizhushou.ui.capes.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes3RightBean;

import java.util.List;

public class Capes3RightAdapter extends AbsBaseAdapter<Capes3RightBean> {

	private List<Capes3RightBean> mList;

	public Capes3RightAdapter(Context context, List<Capes3RightBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, Capes3RightBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.capes_3_right_name);
			holder.num1 = (TextView) convertView.findViewById(R.id.capes_3_right_num1);
			holder.num2 = (TextView) convertView.findViewById(R.id.capes_3_right_num2);
			holder.num3 = (TextView) convertView.findViewById(R.id.capes_3_right_num3);
			convertView.setTag(holder);
		}
		holder.name.setText(mList.get(position).getName());
		holder.num1.setText(mList.get(position).getSucc());
		holder.num2.setText(mList.get(position).getFail());
		holder.num3.setText(mList.get(position).getTimeout());
		return convertView;
	}

	private static class ViewHolder {
		private TextView name, num1, num2, num3;
	}

}
