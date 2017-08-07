package com.chinamobile.yunweizhushou.ui.esbInterface.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.GovernNewListBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class OperateAnalysisAdapter extends AbsBaseAdapter<GovernNewListBean> {

	private List<GovernNewListBean> mList;

	public OperateAnalysisAdapter(Context context, List<GovernNewListBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
	}

	@Override
	protected View newView(View convertView, GovernNewListBean t, int position) {

		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.govern_operate_title);
			holder.content = (TextView) convertView.findViewById(R.id.govern_operate_content);
			holder.percent = (TextView) convertView.findViewById(R.id.govern_operate_percent);
			holder.ball = convertView.findViewById(R.id.govern_operate_ball);
			holder.leftLine = convertView.findViewById(R.id.govern_operate_leftline);
			holder.bottomLine = convertView.findViewById(R.id.govern_operate_bottomline);
		}
		holder.title.setText(mList.get(position).getName());
		holder.content.setText(mList.get(position).getValue());
		holder.percent.setText(mList.get(position).getRate().endsWith("%") ? mList.get(position).getRate()
				: mList.get(position).getRate() + "%");
		if (mList.get(position).getState().equals("1")) {
			holder.ball.setBackgroundResource(R.drawable.oval_double_green);
			holder.leftLine.setBackgroundResource(R.color.color_deepgreen);
			holder.bottomLine.setBackgroundResource(R.color.color_deepgreen);
			holder.percent.setBackgroundResource(R.color.color_deepgreen);
		} else if (mList.get(position).getState().equals("2")) {
			holder.ball.setBackgroundResource(R.drawable.oval_double_yellow);
			holder.leftLine.setBackgroundResource(R.color.color_yellow);
			holder.bottomLine.setBackgroundResource(R.color.color_yellow);
			holder.percent.setBackgroundResource(R.color.color_yellow);
		} else if (mList.get(position).getState().equals("3")) {
			holder.ball.setBackgroundResource(R.drawable.oval_double_red);
			holder.leftLine.setBackgroundResource(R.color.color_red);
			holder.bottomLine.setBackgroundResource(R.color.color_red);
			holder.percent.setBackgroundResource(R.color.color_red);
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView title, content, percent;
		private View ball, leftLine, bottomLine;
	}
}
