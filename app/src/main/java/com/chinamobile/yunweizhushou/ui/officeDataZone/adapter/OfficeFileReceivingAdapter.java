package com.chinamobile.yunweizhushou.ui.officeDataZone.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.officeDataZone.beans.GroupIncrementCheckBean;

import java.util.List;

public class OfficeFileReceivingAdapter extends AbsBaseAdapter<GroupIncrementCheckBean> {

	private List<GroupIncrementCheckBean> mList;
    private Context mContext;
	public OfficeFileReceivingAdapter(Context context, List<GroupIncrementCheckBean> list, int resourceId) {
		super(context, list, resourceId);
		this.mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, GroupIncrementCheckBean n, int position) {
		Viewholder holder = (Viewholder) convertView.getTag();
		if (holder == null) {
			holder = new Viewholder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.list_4_item1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.list_4_item2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.list_4_item3);
			holder.tv4 = (TextView) convertView.findViewById(R.id.list_4_item4);
			holder.tv3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					TextView tv1=(TextView)v;
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setTitle("三级分类");
					builder.setMessage(tv1.getText());
					builder.create();
					builder.show();
				}
			});
			convertView.setTag(holder);
		}
		holder.tv1.setText(mList.get(position).getLevel1_name());
		holder.tv2.setText(mList.get(position).getLevel2_name());
		holder.tv3.setText(mList.get(position).getLevel3_name());
		holder.tv4.setText(mList.get(position).getConsist_ratio());
		return convertView;
	}

	private static class Viewholder {
		TextView tv1, tv2, tv3, tv4;
	}

}
