package com.chinamobile.yunweizhushou.ui.hotZoneKTWY.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.HotZoneKTWYNextActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.bean.HotZoneKTWYBean;

import java.util.List;

public class HotZoneKTWYAdapter extends  BaseExpandableListAdapter{

	private List<HotZoneKTWYBean> groupList;
	private List<List<HotZoneKTWYBean>> childList;
	private Context mContext;
	private String currentDate;
	
	
	public HotZoneKTWYAdapter(List<HotZoneKTWYBean> groupList, List<List<HotZoneKTWYBean>> childList,
			Context mContext,String currentDate) {
		super();
		this.groupList = groupList;
		this.childList = childList;
		this.mContext = mContext;
		this.currentDate=currentDate;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHodlerChild viewHodlerChild=null;
		if(convertView==null){
			viewHodlerChild=new ViewHodlerChild();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_child_hotzone_ktwy, null);
			viewHodlerChild.lt_child= (LinearLayout) convertView.findViewById(R.id.lt_child);
			viewHodlerChild.tv_child_item1=(TextView) convertView.findViewById(R.id.tv_item1);
			viewHodlerChild.tv_child_item2=(TextView) convertView.findViewById(R.id.tv_item2);
			int[] items=new int[]{groupPosition,childPosition};
			viewHodlerChild.lt_child.setTag(items);
			viewHodlerChild.lt_child.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int[] arr= (int[]) view.getTag();
					Intent intent = new Intent();
					intent.putExtra("description",groupList.get(arr[0]).getDescription());
					intent.putExtra("city",childList.get(arr[0]).get(arr[1]).getCity());
					intent.putExtra("time",currentDate);
					intent.setClass(mContext, HotZoneKTWYNextActivity.class);
					mContext.startActivity(intent);
				}
			});
			convertView.setTag(viewHodlerChild);
		}else{
			viewHodlerChild=(ViewHodlerChild) convertView.getTag();
		}
		viewHodlerChild.tv_child_item1.setText(childList.get(groupPosition).get(childPosition).getCity());
		viewHodlerChild.tv_child_item2.setText(childList.get(groupPosition).get(childPosition).getNum());
		return convertView ;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHodlerGroup viewHodlerGroup=null;
		if(convertView==null){
			viewHodlerGroup=new ViewHodlerGroup();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_parent_hotzone_ktwy, null);
			viewHodlerGroup.tv_group_item1=(TextView) convertView.findViewById(R.id.tv_item1);
			viewHodlerGroup.tv_group_item2=(TextView) convertView.findViewById(R.id.tv_item2);
			viewHodlerGroup.tv_group_item3=(TextView) convertView.findViewById(R.id.tv_item3);
			convertView.setTag(viewHodlerGroup);
		}else{
			viewHodlerGroup=(ViewHodlerGroup) convertView.getTag();
		}
		viewHodlerGroup.tv_group_item1.setText(groupList.get(groupPosition).getDescription());
		viewHodlerGroup.tv_group_item2.setText(groupList.get(groupPosition).getOrder_desc());
		viewHodlerGroup.tv_group_item3.setText(groupList.get(groupPosition).getNum());
		return convertView ;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return false;
	}
	
	public class ViewHodlerGroup{
		private TextView tv_group_item1,tv_group_item2,tv_group_item3;
		
	}
	public class ViewHodlerChild{
		private TextView tv_child_item1,tv_child_item2;
		private LinearLayout lt_child;
		
	}

}
