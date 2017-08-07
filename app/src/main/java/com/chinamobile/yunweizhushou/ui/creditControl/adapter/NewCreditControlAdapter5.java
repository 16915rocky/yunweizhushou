package com.chinamobile.yunweizhushou.ui.creditControl.adapter;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.creditControl.bean.NewCreditControlBean5;

import java.util.List;

public class NewCreditControlAdapter5 extends AbsBaseAdapter<NewCreditControlBean5> {
	private List<NewCreditControlBean5> mlist;
	private Context context;
	private int pos;

	public NewCreditControlAdapter5(Context context, List<NewCreditControlBean5> list, int resourceId) {
		super(context, list, resourceId);
		mlist = list;
		this.context=context;
	}

	@Override
	protected View newView(View convertView, NewCreditControlBean5 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.scenario_name = (TextView) convertView.findViewById(R.id.cnn5_item1);
			holder.success = (TextView) convertView.findViewById(R.id.cnn5_item2);
			holder.run_time = (TextView) convertView.findViewById(R.id.cnn5_item3);
			holder.exe_user_id = (TextView) convertView.findViewById(R.id.cnn5_item4);
			holder.run_detail = (TextView) convertView.findViewById(R.id.cnn5_item5);
			holder.ovalofblue = (TextView) convertView.findViewById(R.id.ovalofblue);
			holder.run_detail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TextView tv1=(TextView)v;
					Builder builder = new Builder(context);
					builder.setTitle("执行结果"); 
					builder.setMessage(tv1.getText());
					builder.create();
					builder.show();
				}
			});
			convertView.setTag(holder);
		}
		
		holder.scenario_name.setText(mlist.get(position).getScenario_name());
		holder.success.setText(mlist.get(position).getSuccess());
		holder.run_time.setText(mlist.get(position).getRun_time());
		holder.exe_user_id.setText(mlist.get(position).getExe_user_id());
		holder.run_detail.setText(mlist.get(position).getRun_detail());
		holder.ovalofblue.setText(position+"");
		
		return convertView;
	}

	private static class ViewHolder {
		private TextView success, run_time, exe_user_id, run_detail, scenario_name,ovalofblue;
	}
}
