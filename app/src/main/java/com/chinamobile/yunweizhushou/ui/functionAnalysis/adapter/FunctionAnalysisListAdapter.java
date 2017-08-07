package com.chinamobile.yunweizhushou.ui.functionAnalysis.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FunctionAnalysisListSubBean;

import java.util.List;

public class FunctionAnalysisListAdapter extends AbsBaseAdapter<FunctionAnalysisListSubBean> {

	private List<FunctionAnalysisListSubBean> mList;

	public FunctionAnalysisListAdapter(Context context, List<FunctionAnalysisListSubBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, FunctionAnalysisListSubBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.tag = (TextView) convertView.findViewById(R.id.function_analysis_list_tag);
			holder.content = (TextView) convertView.findViewById(R.id.function_analysis_list_content);
			holder.state = (TextView) convertView.findViewById(R.id.function_analysis_list_state);
			convertView.setTag(holder);
		}
		holder.tag.setText(mList.get(position).getDelLevel());
		holder.content.setText(mList.get(position).getServiceName());
		holder.state.setText(mList.get(position).getAnanlysisResult());
		if (mList.get(position).getDelLevel().equals("轻微")) {
			holder.tag.setBackgroundResource(R.drawable.oval_yellow);
		} else if (mList.get(position).getDelLevel().equals("一般")) {
			holder.tag.setBackgroundResource(R.drawable.oval_orange);
		} else if (mList.get(position).getDelLevel().equals("严重")) {
			holder.tag.setBackgroundResource(R.drawable.oval_red);
		}
		if (mList.get(position).getAnanlysisResult().equals("分析中")) {
			holder.state.setBackgroundResource(R.drawable.rect_gray_bg);
		} else if (mList.get(position).getAnanlysisResult().equals("实施中")) {
			holder.state.setBackgroundResource(R.drawable.rect_blue_bg);
		} else if (mList.get(position).getAnanlysisResult().equals("已完成")) {
			holder.state.setBackgroundResource(R.drawable.rect_green_bg);
		}

		return convertView;
	}

	private class ViewHolder {
		private TextView tag, content, state;
	}

}
