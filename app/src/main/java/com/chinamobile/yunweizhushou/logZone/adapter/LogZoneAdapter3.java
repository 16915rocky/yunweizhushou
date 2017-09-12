package com.chinamobile.yunweizhushou.logZone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.logZone.bean.LogZoneBean6;

import java.util.List;

public class LogZoneAdapter3 extends BaseExpandableListAdapter {

	private LayoutInflater mInflater;
	private List<LogZoneBean6> groupList;
	private List<List<LogZoneBean6>> childList;
	private Context mContext;

	public LogZoneAdapter3(Context context, List<LogZoneBean6> groupList, List<List<LogZoneBean6>> childList) {
		this.childList = childList;
		this.groupList = groupList;
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	
	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHolderGroup g = null;
		if (convertView == null) {
			g = new ViewHolderGroup();
			convertView = mInflater.inflate(R.layout.fragement_log_zone_table3_3, parent, false);
			g.tv1 = (TextView) convertView.findViewById(R.id.tv1);
			g.tv2 = (TextView) convertView.findViewById(R.id.tv2);
			g.oval = (TextView) convertView.findViewById(R.id.oval);

			convertView.setTag(g);
		} else {
			g = (ViewHolderGroup) convertView.getTag();
		}
		g.tv1.setText(groupList.get(groupPosition).getName());
		String tempStr=groupList.get(groupPosition).getDoc_count();
		if(tempStr.length()>5) {
			g.tv2.setText(tempStr.substring(0,5)+"..");
		}else{
			g.tv2.setText(tempStr);
		}
		g.oval.setText(groupPosition + 1 + "");
		if (groupPosition == 0) {
			// g.oval.setBackgroundColor(mContext.getResources().getColor(R.color.color_orange));
			g.oval.setBackgroundResource(R.drawable.oval_orange);
		} else if (groupPosition == 1) {
			g.oval.setBackgroundResource(R.drawable.oval_yellow);
		} else if (groupPosition == 2) {
			g.oval.setBackgroundResource(R.drawable.oval_blue);
		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		ViewHolderChild c = null;
		if (convertView == null) {
			c = new ViewHolderChild();
			convertView = mInflater.inflate(R.layout.fragement_log_zone_table3_3, parent, false);
			c.name = (TextView) convertView.findViewById(R.id.tv1);
			c.doc_count = (TextView) convertView.findViewById(R.id.tv2);
			c.tv11 = (TextView) convertView.findViewById(R.id.tv11);
			c.oval = (TextView) convertView.findViewById(R.id.oval);
			c.oval.setVisibility(View.GONE);
			convertView.setTag(c);
		} else {
			c = (ViewHolderChild) convertView.getTag();
		}
		c.name.setText(childList.get(groupPosition).get(childPosition).getName());
		String tempStr2=childList.get(groupPosition).get(childPosition).getDoc_count();
		if(tempStr2.length()>5) {
			c.doc_count.setText(tempStr2.substring(0,5)+"..");
		}else{
			c.doc_count.setText(tempStr2);
		}
		c.tv11.setText("实例进程");
		
		

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private static class ViewHolderChild {
		private TextView name, doc_count, tv11,oval;

	}

	private static class ViewHolderGroup {
		private TextView tv1, tv2, oval;
	}

}
