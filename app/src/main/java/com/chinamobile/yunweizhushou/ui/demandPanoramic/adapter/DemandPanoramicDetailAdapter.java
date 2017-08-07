package com.chinamobile.yunweizhushou.ui.demandPanoramic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandDetailBean;

import java.util.List;

public class DemandPanoramicDetailAdapter extends BaseAdapter {

	private List<DemandDetailBean.ProgressList> data;
	private Context context;

	public DemandPanoramicDetailAdapter(Context context, List<DemandDetailBean.ProgressList> data) {
		super();
		this.data = data;
		this.context = context;
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
			convertView = View.inflate(context, R.layout.item_importantwork_child, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.content.setText(data.get(position).getDescription());
		holder.date.setText(data.get(position).getDate());
		if (position == data.size() - 1) {
			holder.bottom.setVisibility(View.INVISIBLE);
			holder.middle.setBackgroundResource(R.drawable.oval_double_green);
			holder.content.setTextColor(context.getResources().getColor(R.color.color_lightgreen));
			holder.date.setTextColor(context.getResources().getColor(R.color.color_lightgreen));
		} else {
			holder.bottom.setVisibility(View.VISIBLE);
			holder.middle.setBackgroundResource(R.drawable.oval_gray);
			holder.content.setTextColor(context.getResources().getColor(R.color.color_dark));
			holder.date.setTextColor(context.getResources().getColor(R.color.color_dark));
		}
		return convertView;
	}

	private class ViewHolder {
		private View top, middle, bottom;
		private TextView content;
		private TextView date;

		public ViewHolder(View view) {
			content = (TextView) view.findViewById(R.id.important_work_child_content);
			date = (TextView) view.findViewById(R.id.important_work_child_date);
			top = view.findViewById(R.id.important_work_child_top);
			middle = view.findViewById(R.id.important_work_child_middle);
			bottom = view.findViewById(R.id.important_work_child_bottom);
		}
	}
}
