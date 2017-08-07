package com.chinamobile.yunweizhushou.ui.useRank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.useRank.bean.UseRankingBean;

import java.util.List;

public class UseRankingAdapter extends BaseAdapter {

	private List<UseRankingBean.ItemsList> list;
	private Context context;
	private int state;

	public UseRankingAdapter(Context context, List<UseRankingBean.ItemsList> list, int state) {
		super();
		this.context = context;
		this.list = list;
		this.state = state;
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_use_ranking_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.id.setText("" + (position + 1));
		if (position == 0) {
			holder.id.setBackgroundResource(R.drawable.use_ranking_orange);
		} else if (position == 1) {
			holder.id.setBackgroundResource(R.drawable.use_ranking_green);
		} else if (position == 2) {
			holder.id.setBackgroundResource(R.drawable.use_ranking_violet);
		} else {
			holder.id.setBackgroundResource(R.drawable.use_ranking_gray);
		}
		if (state == 0) {
			holder.content.setText(list.get(position).getUserName());
			holder.num.setText(list.get(position).getConsumerUseNum());
		} else if (state == 1) {
			holder.content.setText(list.get(position).getMenuName());
			holder.num.setText(list.get(position).getUseMenuNum());
		}
		return convertView;
	}

	protected class ViewHolder {
		TextView id, content, num;

		public ViewHolder(View view) {
			id = (TextView) view.findViewById(R.id.use_ranking_id);
			content = (TextView) view.findViewById(R.id.use_ranking_content);
			num = (TextView) view.findViewById(R.id.use_ranking_num);
		}
	}
}
