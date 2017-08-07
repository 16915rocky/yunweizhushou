package com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiSchema2Bean;

import java.util.List;

public class YunweiSchema2Adapter extends AbsBaseAdapter<YunweiSchema2Bean> {

	private List<YunweiSchema2Bean> mList;
	private String type;

	public YunweiSchema2Adapter(Context context, List<YunweiSchema2Bean> list, int resourceId,String type) {
		super(context, list, resourceId);
		mList = list;
		this.type=type;
	}

	@Override
	protected View newView(View convertView, YunweiSchema2Bean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			if("online_server".equals(type) || "change_server".equals(type)){
				holder.name = (TextView) convertView.findViewById(R.id.list_7_item1);
				holder.resdetail = (TextView) convertView.findViewById(R.id.list_7_item2);
				holder.macip = (TextView) convertView.findViewById(R.id.list_7_item3);
				holder.aperson = (TextView) convertView.findViewById(R.id.list_7_item4);
				holder.update_time = (TextView) convertView.findViewById(R.id.list_7_item5);
				holder.states = (TextView) convertView.findViewById(R.id.list_7_item6);
				holder.instnum = (TextView) convertView.findViewById(R.id.list_7_item7);
				holder.instnum.setVisibility(View.GONE);				
			}else{
				holder.name = (TextView) convertView.findViewById(R.id.list_7_item1);
				holder.resdetail = (TextView) convertView.findViewById(R.id.list_7_item2);
				holder.beservice  = (TextView) convertView.findViewById(R.id.list_7_item3);
				holder.instnum  = (TextView) convertView.findViewById(R.id.list_7_item4);
				holder.aperson = (TextView) convertView.findViewById(R.id.list_7_item5);
				holder.update_time = (TextView) convertView.findViewById(R.id.list_7_item6);
				holder.states = (TextView) convertView.findViewById(R.id.list_7_item7);
			}
			convertView.setTag(holder);
		}
		if("online_server".equals(type) || "change_server".equals(type)){
			holder.name.setText(mList.get(position).getName());
			holder.resdetail.setText(mList.get(position).getResdetail());
			holder.macip.setText(mList.get(position).getMacip());
			holder.aperson.setText(mList.get(position).getAperson());
			holder.update_time.setText(mList.get(position).getUpdate_time());
			holder.states.setText(mList.get(position).getStates());
		}else{			
			holder.name.setText(mList.get(position).getName());
			holder.resdetail.setText(mList.get(position).getResdetail());
			holder.beservice.setText(mList.get(position).getBeservice());
			holder.instnum.setText(mList.get(position).getInstnum());
			holder.aperson.setText(mList.get(position).getAperson());
			holder.update_time.setText(mList.get(position).getUpdate_time());
			holder.states.setText(mList.get(position).getStates());
		}	
		return convertView;
	}

	private static class ViewHolder {
		private TextView name, resdetail, aperson, update_time, states,macip,beservice,instnum;
	}
}
