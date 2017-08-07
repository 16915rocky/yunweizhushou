package com.chinamobile.yunweizhushou.ui.userPerceptionIndex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.bean.KeiBean;

import java.util.List;

public class KeiExpanableListViewAdapter  extends BaseExpandableListAdapter {
	
	private List<KeiBean> parentList;
	private List<List<KeiBean>> childList;
	private Context mcontext;
	private ExpandableListView expandListview;
	int height=0;
	public KeiExpanableListViewAdapter(Context context,List<KeiBean> parentList, List<List<KeiBean>> childList, ExpandableListView expandListview){
		this.parentList=parentList;
		this.childList=childList;
		this.mcontext=context;
		this.expandListview=expandListview;
	}

	@Override
	public Object getChild(int parentPos, int childPos) {
		return childList.get(parentPos).get(childPos);
	}

	@Override
	public long getChildId(int parentPos, int childPos) {
		return childPos;
	}

	@Override
	public View getChildView(int parentPos, int childPos, boolean isExpanded, View convertView, ViewGroup viewGroup) {
		ViewHolderChild viewHolderChild = null;
		/*if(convertView==null){*/
			viewHolderChild=new ViewHolderChild();
			LayoutInflater  inflater =(LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_kei_chlid, null);
			viewHolderChild.tv_child_second = (TextView) convertView.findViewById(R.id.tv_child_second);
			viewHolderChild.tv_child_third = (TextView) convertView.findViewById(R.id.tv_child_third);
			viewHolderChild.tv_child_fourth = (TextView) convertView.findViewById(R.id.tv_child_fourth);
			viewHolderChild.lt_extra = (LinearLayout) convertView.findViewById(R.id.lt_extra);
		
	/*	}else{
			viewHolderChild=(ViewHolderChild) convertView.getTag();
		}*/
		String class_name=childList.get(parentPos).get(childPos).getClass_name();
		String cnt_2017=childList.get(parentPos).get(childPos).getCnt_2017();
		String cnt_2016=childList.get(parentPos).get(childPos).getCnt_2016();
		viewHolderChild.tv_child_second.setText(class_name);
		viewHolderChild.tv_child_third.setText(cnt_2017+"‰");
		viewHolderChild.tv_child_fourth.setText("(去年同期"+cnt_2016+"‰)");
		List<KeiBean> list = childList.get(parentPos).get(childPos).getItemList();
		if(list!=null){
		for(int i=0;i<list.size();i++){
			LayoutInflater  inflater2 =(LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
			View view2 = inflater2.inflate(R.layout.item_kei_child_child, null);
			TextView tv_kei_child_child_item1 = (TextView) view2.findViewById(R.id.tv_kei_child_child_item1);
			TextView tv_kei_child_child_item2 = (TextView) view2.findViewById(R.id.tv_kei_child_child_item2);
			TextView tv_kei_child_child_item3 = (TextView) view2.findViewById(R.id.tv_kei_child_child_item3);
		//	List<KeiBean> list2 = childList.get(parentPos).get(childPos).getItemList();
			tv_kei_child_child_item1.setText(list.get(i).getClass_name());
			tv_kei_child_child_item2.setText(list.get(i).getCnt_2017());
			tv_kei_child_child_item3.setText("‰(去年同期"+list.get(i).getCnt_2016()+"‰)");
			viewHolderChild.lt_extra.addView(view2);
		
		}
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int parentPos) {
		return childList.get(parentPos).size();
	}

	@Override
	public Object getGroup(int parentPos) {
		return parentList.get(parentPos);
	}

	@Override
	public int getGroupCount() {
		return parentList.size();
	}

	@Override
	public long getGroupId(int parentPos) {
		return parentPos;
	}

	@Override
	public View getGroupView(int parentPos, boolean childPos, View view, ViewGroup viewGroup) {
		ViewHolderGroup   viewHolderGroup=null;
		if(view==null){
			viewHolderGroup=new ViewHolderGroup();
			view=LayoutInflater.from(mcontext).inflate(R.layout.item_kei_next, null);
			viewHolderGroup.tv_first=(TextView) view.findViewById(R.id.tv_first);
			viewHolderGroup.tv_second=(TextView) view.findViewById(R.id.tv_second);
			viewHolderGroup.tv_thrid=(TextView) view.findViewById(R.id.tv_thrid);
			view.setTag(viewHolderGroup);
		
		}else{
			viewHolderGroup=(ViewHolderGroup) view.getTag();
		}
		viewHolderGroup.tv_first.setText(parentList.get(parentPos).getClass_name());
		viewHolderGroup.tv_second.setText(parentList.get(parentPos).getCnt_2017()+"‰");
		viewHolderGroup.tv_thrid.setText("(去年同期"+parentList.get(parentPos).getCnt_2016()+"‰)");
		return view;
	}
	

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	private static class ViewHolderGroup {
		private TextView tv_first, tv_second,tv_thrid;
	}
	private static class ViewHolderChild {
		private TextView tv_child_second, tv_child_third ,tv_child_fourth;
		private LinearLayout lt_extra;

	}

}
