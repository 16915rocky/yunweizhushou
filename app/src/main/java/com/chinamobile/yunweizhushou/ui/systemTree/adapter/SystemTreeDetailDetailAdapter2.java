package com.chinamobile.yunweizhushou.ui.systemTree.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.SystemTreeDetailDetailBean2;

import java.util.List;

public class SystemTreeDetailDetailAdapter2 extends AbsBaseAdapter<SystemTreeDetailDetailBean2> {

	private List<SystemTreeDetailDetailBean2> list1;

	public SystemTreeDetailDetailAdapter2(Context context, List<SystemTreeDetailDetailBean2> list, int resourceId) {
		super(context, list, resourceId);
		this.list1 = list;
	}

	@Override
	protected View newView(View convertView, SystemTreeDetailDetailBean2 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.item_common_six_1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.item_common_six_2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.item_common_six_3);
			holder.tv4 = (TextView) convertView.findViewById(R.id.item_common_six_4);
			holder.tv5 = (TextView) convertView.findViewById(R.id.item_common_six_5);
			holder.tv6 = (TextView) convertView.findViewById(R.id.item_common_six_6);
		}
		holder.tv1.setText(list1.get(position).getResource_type());
		holder.tv1.setVisibility(View.GONE);
		holder.tv2.setText(list1.get(position).getResource_name());
		holder.tv3.setText(list1.get(position).getResource_desc());
		holder.tv4.setText(list1.get(position).getCase_name());
		holder.tv5.setText(list1.get(position).getUse_op_name());
		holder.tv6.setText(list1.get(position).getResource_pc());

		return convertView;
	}

	class ViewHolder {

		private TextView tv1, tv2, tv3, tv4, tv5, tv6;

	}

}