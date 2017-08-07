package com.chinamobile.yunweizhushou.ui.emergencyExercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.bean.EmergencyExerciseBean;

import java.util.List;

public class ExerciseMiddleWareAdapter extends BaseAdapter {

	private List<EmergencyExerciseBean> list;
	private Context context;

	public ExerciseMiddleWareAdapter(Context context, List<EmergencyExerciseBean> list) {
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convertView=LayoutInflater.from(context).inflate(R.layout.item_charge_list,
		// null);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_exercise_middle_ware, null);
			// convertView=LayoutInflater.from(context).inflate(R.layout.item_charge_list,
			// null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String text = list.get(position).getStartDate();
		int index = list.get(position).getStartDate().indexOf("年");
		if (index == -1) {

		} else {
			StringBuilder sb = new StringBuilder(text);
			sb.insert(index + 1, "\n");
			holder.startDate.setText(sb.toString());
		}
		holder.exerciseSystem.setText(list.get(position).getDrillSystem());
		holder.exerciseTarget.setText(list.get(position).getDrillTarget());
		if ("1".equals(list.get(position).getDrillState())) {
			holder.state.setText("完成");
		} else {
			holder.state.setText("未完成");
		}
		return convertView;
	}

	protected class ViewHolder {
		TextView startDate, exerciseSystem, exerciseTarget, state;

		public ViewHolder(View view) {
			startDate = (TextView) view.findViewById(R.id.exerciseStartDate);
			exerciseSystem = (TextView) view.findViewById(R.id.exerciseSystem);
			exerciseTarget = (TextView) view.findViewById(R.id.exerciseTarget);
			state = (TextView) view.findViewById(R.id.exerciseState);
		}
	}

	private static String FormatTime2String(String str) {
		String text = "";
		String[] strings = new String[] {};
		if (str.matches("^[0-9]{4}/[0-9]{2}/[0-9]{2}$")) {
			strings = str.split("/");
			return strings[0] + "年\n" + strings[1] + "月" + strings[2] + "日";
		} else if (str.matches("^[0-9]{4}/[0-9]{2}$")) {
			strings = str.split("/");
			return strings[0] + "年\n" + strings[1] + "月";
		} else {

		}
		return text;
	}
}
