package com.chinamobile.yunweizhushou.ui.fault.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NameValueBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class FaultOperationAdapter extends AbsBaseAdapter<NameValueBean> {

	private List<NameValueBean> mList;
	private Context mContext;
	private int resourceId;

	public FaultOperationAdapter(Context context, List<NameValueBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		mContext = context;
        this.resourceId=resourceId;
	}

	@Override
	protected View newView(View convertView, NameValueBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.item1 = (TextView) convertView.findViewById(R.id.list_2_item1);
			holder.item2 = (TextView) convertView.findViewById(R.id.list_2_item2);

		}
		holder.item1.setText(mList.get(position).getName());
		holder.item1.setText(mList.get(position).getValue());
		return convertView;
	}

	private class ViewHolder {
		private TextView item1,item2;

	}

}
