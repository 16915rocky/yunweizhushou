package com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBallBean;
import com.chinamobile.yunweizhushou.view.waterwaveprogress.WaterWaveProgress;

import java.util.List;

public class DBConnectionPoolBallAdapter extends BaseAdapter {

	private List<DBConnectionPoolBallBean.ConnectionPoolItem> data;
	private Context mContext;
	private String text = "";

	public DBConnectionPoolBallAdapter(Context context, List<DBConnectionPoolBallBean.ConnectionPoolItem> list) {
		this.data = list;
		this.mContext = context;
	}

	public DBConnectionPoolBallAdapter(Context context, List<DBConnectionPoolBallBean.ConnectionPoolItem> list, String text) {
		this.data = list;
		this.mContext = context;
		this.text = text;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_db_connection_ball, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if ("".equals(text)) {// 数据库容量池管理
			holder.name.setText(data.get(position).getCode());
		} else {// 其他线程管理
			holder.name.setText(data.get(position).getName());
		}

		// holder.title.setText(data.get(position).getDs());
		// holder.progress.setProgress(Float.valueOf(data.get(position).getUsAge()));
		holder.water.setShowProgress(true);
		holder.water.setWaveSpeed(0.03f);
		holder.water.setAmplitude(5f);
		holder.water.setMaxProgress(100);
		holder.water.setProgress(Float.parseFloat(data.get(position).getRate()));

		if (data.get(position).getState().equals("1")) {
			holder.water.setmWaterColor(mContext.getResources().getColor(R.color.color_lightgreen));
			holder.water.setmRingColor(mContext.getResources().getColor(R.color.color_lightgreen));
		} else if (data.get(position).getState().equals("2")) {
			holder.water.setmWaterColor(mContext.getResources().getColor(R.color.color_yellow));
			holder.water.setmRingColor(mContext.getResources().getColor(R.color.color_yellow));
		} else {
			holder.water.setmWaterColor(mContext.getResources().getColor(R.color.color_red));
			holder.water.setmRingColor(mContext.getResources().getColor(R.color.color_red));
		}
		holder.water.animateWave();
		return convertView;
	}

	private class ViewHolder {
		private TextView name, title;
		private WaterWaveProgress water;

		public ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.ball_name);
			title = (TextView) view.findViewById(R.id.ball_title);
			water = (WaterWaveProgress) view.findViewById(R.id.ball_waterprogress);
		}
	}
}
