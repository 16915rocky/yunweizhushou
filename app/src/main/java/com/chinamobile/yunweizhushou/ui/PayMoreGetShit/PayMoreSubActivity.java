package com.chinamobile.yunweizhushou.ui.PayMoreGetShit;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.PayMoreGetShit.bean.PayMoreBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PayMoreSubActivity extends BaseActivity {

	private List<PayMoreBean> list;
	private String data;
	private ListView listview;
	private TextView tv1, tv2, tv3, tv4, tv5;
	private MyRefreshLayout refreshLayout;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_list_5);
		initView();
		name = getIntent().getStringExtra("name");
		initEvent();
		data = getIntent().getStringExtra("data");
		Type t = new TypeToken<List<PayMoreBean>>() {
		}.getType();
		list = new Gson().fromJson(data, t);
		PayMoreSubAdapter adapter = new PayMoreSubAdapter(this, list, R.layout.item_list_5);
		listview.setAdapter(adapter);

	}

	private void initEvent() {
		getTitleBar().setMiddleText(name);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		refreshLayout = (MyRefreshLayout) findViewById(R.id.common_list_5_refreshlayout);
		refreshLayout.setEnabled(false);
		listview = (ListView) findViewById(R.id.common_list_5_listview);
		LinearLayout layout = (LinearLayout) findViewById(R.id.common_list_title_5);
		tv1 = (TextView) layout.findViewById(R.id.list_title_5_item1);
		tv2 = (TextView) layout.findViewById(R.id.list_title_5_item2);
		tv3 = (TextView) layout.findViewById(R.id.list_title_5_item3);
		tv4 = (TextView) layout.findViewById(R.id.list_title_5_item4);
		tv5 = (TextView) layout.findViewById(R.id.list_title_5_item5);

		tv1.setText("地市");
		tv2.setText("开始时间");
		tv3.setText("结束时间");
		tv4.setText("处理次数");
		tv5.setText("状态");
	}

	class PayMoreSubAdapter extends AbsBaseAdapter<PayMoreBean> {

		private List<PayMoreBean> list;

		public PayMoreSubAdapter(Context context, List<PayMoreBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, PayMoreBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.list_5_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.list_5_item2);
				holder.item3 = (TextView) convertView.findViewById(R.id.list_5_item3);
				holder.item4 = (TextView) convertView.findViewById(R.id.list_5_item4);
				holder.item5 = (TextView) convertView.findViewById(R.id.list_5_item5);
				convertView.setTag(holder);
			}
			holder.item1.setText(list.get(position).getRegion_id());
			holder.item2.setText(list.get(position).getExec_begin_date());
			holder.item3.setText(list.get(position).getExec_end_date());
			holder.item4.setText(list.get(position).getCompleteNumber());
			holder.item5.setText(list.get(position).getState());

			return convertView;
		}

	}

	private static class ViewHolder {
		TextView item1, item2, item3, item4, item5;
	}

}
