package com.chinamobile.yunweizhushou.ui.capabilityPlatform.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.bean.Ability2Bean;
import com.chinamobile.yunweizhushou.ui.ruleCenter.fragments.RuleFragment;

import java.util.List;

public class Ability2Adapter extends AbsBaseAdapter<Ability2Bean> {

	private List<Ability2Bean> mList;
	private int tag = 0;

	public Ability2Adapter(Context context, List<Ability2Bean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	@Override
	protected View newView(View convertView, Ability2Bean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.ability_2_time);
			holder.name = (TextView) convertView.findViewById(R.id.ability_2_name);
			holder.times = (TextView) convertView.findViewById(R.id.ability_2_times);
			holder.center = (TextView) convertView.findViewById(R.id.ability_2_center);
			holder.failNum = (TextView) convertView.findViewById(R.id.ability_2_failNum);
			convertView.setTag(holder);
		}
		holder.center.setText(mList.get(position).getCname());
		holder.name.setText(mList.get(position).getName());
		holder.times.setText(mList.get(position).getValue());
		holder.time.setText(mList.get(position).getAvg());

		if (tag == 0) {
			holder.failNum.setText(mList.get(position).getErr());
		} else {// 规则中心时 tag不为0 time 和times字段更换
			if (tag == RuleFragment.RULE_ESB) {// ESB
				holder.failNum.setText(mList.get(position).getErr() + "%");
			} else {// CSF
				holder.failNum.setText(mList.get(position).getErr());
			}
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView name, center, times, time, failNum;
	}
}
