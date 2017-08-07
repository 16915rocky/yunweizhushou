package com.chinamobile.yunweizhushou.ui.produceLine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.produceLine.bean.ProduceLineMonthContentBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.FlexibleTextView2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ProduceLineMonthFocusActivity extends BaseActivity {
	private ListView listview;
	private String currentTime, currentItem;
	private List<ProduceLineMonthContentBean> list;
	private String tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_listview);
		tag = getIntent().getStringExtra("tag");
		currentTime = getIntent().getStringExtra("time");
		currentItem = getIntent().getStringExtra("item");
		initView();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "focusEvent");
		map.put("event", tag);
		map.put("title", currentTime);
		map.put("category", currentItem);
		startTask(HttpRequestEnum.enum_produceline_month_content, ConstantValueUtil.URL + "CoreLine?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_produceline_month_content:
				Type t2 = new TypeToken<List<ProduceLineMonthContentBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t2);
				ProduceLineMonthAdapter adapter2 = new ProduceLineMonthAdapter(this, list,
						R.layout.item_produce_line_2);
				listview.setAdapter(adapter2);

				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		getTitleBar().setMiddleText("关注点");
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.common_listview);
	}

	class ProduceLineMonthAdapter extends AbsBaseAdapter<ProduceLineMonthContentBean> {

		private List<ProduceLineMonthContentBean> list;

		public ProduceLineMonthAdapter(Context context, List<ProduceLineMonthContentBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, ProduceLineMonthContentBean t, final int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.l1 = (TextView) convertView.findViewById(R.id.produce_line_month_l1);
				holder.l2 = (TextView) convertView.findViewById(R.id.produce_line_month_l2);
				holder.l3 = (TextView) convertView.findViewById(R.id.produce_line_month_l3);
				holder.r1 = (TextView) convertView.findViewById(R.id.produce_line_month_r1);
				holder.r2 = (FlexibleTextView2) convertView.findViewById(R.id.produce_line_month_r2);
				holder.r3 = (TextView) convertView.findViewById(R.id.produce_line_month_r3);
				holder.r4 = (TextView) convertView.findViewById(R.id.produce_line_month_r4);
				holder.ue = (TextView) convertView.findViewById(R.id.produce_line_month_ue);
				convertView.setTag(holder);
			}
			holder.l1.setText(list.get(position).getCategory());
			holder.l2.setText(list.get(position).getSubclass());
			holder.l3.setText(list.get(position).getMotion());
			holder.r1.setText(list.get(position).getContent());
			holder.r2.setText(list.get(position).getDescription());
			holder.r3.setText(list.get(position).getHandler());
			holder.r4.setText(list.get(position).getRemark());
			holder.ue.setText(list.get(position).getEvent_date() + "\n" + list.get(position).getEvent_time());
			return convertView;
		}

	}

	private static class ViewHolder {
		FlexibleTextView2 r2;
		TextView l1, l2, l3, r1, r3, r4, ue;
	}
}
