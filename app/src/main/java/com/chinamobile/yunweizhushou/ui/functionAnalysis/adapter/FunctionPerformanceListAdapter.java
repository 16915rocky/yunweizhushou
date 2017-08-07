package com.chinamobile.yunweizhushou.ui.functionAnalysis.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FunctionAnalysisPerformancebean;

import java.util.List;

public class FunctionPerformanceListAdapter extends BaseAdapter {

	private Context context;
	private List<FunctionAnalysisPerformancebean.ItemsList> list;

	public FunctionPerformanceListAdapter(Context context, List<FunctionAnalysisPerformancebean.ItemsList> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_function_analysis_performance, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == 0) {
			holder.level.setBackgroundResource(R.drawable.oval_orange);
		} else if (position == 1) {
			holder.level.setBackgroundResource(R.drawable.oval_yellow);
		} else if (position == 2) {
			holder.level.setBackgroundResource(R.drawable.oval_blue);
		} else {
			holder.level.setBackgroundResource(R.drawable.oval_gray);
		}
		holder.level.setText("" + (position + 1));
		holder.decription.setText(list.get(position).getName());
		holder.progress.setText(list.get(position).getNum() + "%");
		return convertView;
	}

	private class ViewHolder {
		private TextView level, decription, progress;

		public ViewHolder(View view) {
			level = (TextView) view.findViewById(R.id.function_analysis_list_tag);
			decription = (TextView) view.findViewById(R.id.function_analysis_list_content);
			progress = (TextView) view.findViewById(R.id.function_analysis_list_state);
		}
	}
}
