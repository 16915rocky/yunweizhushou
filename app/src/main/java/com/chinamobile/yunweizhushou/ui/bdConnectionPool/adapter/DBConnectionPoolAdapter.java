package com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBean;

import java.util.List;

public class DBConnectionPoolAdapter extends BaseAdapter {

	private List<DBConnectionPoolBean.ConnectionPoolChildItem> data;
	private Context context;

	public DBConnectionPoolAdapter(Context context, List<DBConnectionPoolBean.ConnectionPoolChildItem> list) {
		this.data = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_db_connection_business, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(data.get(position).getName());
		holder.normal.setText(data.get(position).getNormal());
		holder.focus.setText(data.get(position).getWarning());
		holder.optimize.setText(data.get(position).getOptimize());
		return convertView;
	}

	private class ViewHolder {
		private TextView name, normal, focus, optimize;

		public ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.name);
			normal = (TextView) view.findViewById(R.id.itemNormal);
			focus = (TextView) view.findViewById(R.id.itemFocus);
			optimize = (TextView) view.findViewById(R.id.itemOptimize);
		}
	}
}
