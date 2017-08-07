package com.chinamobile.yunweizhushou.ui.netChange;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeErrorBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.FlexibleTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeErrorActivity extends BaseActivity {

	private TextView num1, num2;
	private ListView listview;
	private String time;
	private List<NetChangeErrorBean> list;

	private LinearLayout head;
	private TextView nocontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_net_change_error);
		time = getIntent().getStringExtra("time");
		initView();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "faultDefectOfMonth");
		map.put("time", time);
		startTask(HttpRequestEnum.enum_net_change_error, ConstantValueUtil.URL + "ChangeTask?", map, true);
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
			case enum_net_change_error:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA()).getJSONObject("total");
					String exception_num = obj.getString("exception_num");
					String bug_num = obj.getString("bug_num");
					num1.setText(exception_num);
					num2.setText(bug_num);
					Type type = new TypeToken<List<NetChangeErrorBean>>() {
					}.getType();
					list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					NetChangeErrorAdapter adapter = new NetChangeErrorAdapter(this, list,
							R.layout.item_net_change_detail_3);
					listview.setAdapter(adapter);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			default:
				break;
			}
		} else {
			head.setVisibility(View.INVISIBLE);
			listview.setVisibility(View.GONE);
			nocontent.setVisibility(View.VISIBLE);
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
		getTitleBar().setMiddleText("月度故障异常");
	}

	private void initView() {
		num1 = (TextView) findViewById(R.id.net_change_error_num1);
		num2 = (TextView) findViewById(R.id.net_change_error_num2);
		listview = (ListView) findViewById(R.id.net_change_error_listview);
		head = (LinearLayout) findViewById(R.id.net_change_error_head);
		nocontent = (TextView) findViewById(R.id.nocontent_textview);
	}

	class NetChangeErrorAdapter extends AbsBaseAdapter<NetChangeErrorBean> {

		private List<NetChangeErrorBean> list;

		public NetChangeErrorAdapter(Context context, List<NetChangeErrorBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeErrorBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.net_change_detail_3_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.net_change_detail_3_item2);
				holder.item3 = (TextView) convertView.findViewById(R.id.net_change_detail_3_item3);
				holder.person = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_person);
				holder.title = (TextView) convertView.findViewById(R.id.net_change_detail_3_title);
				holder.method = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_method);
				holder.desc = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_desc);
				holder.progress = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_progress);
				convertView.setTag(holder);
			}
			holder.item1.setText(list.get(position).getType());
			if (list.get(position).getType().equals("故障")) {
				holder.item1.setBackgroundResource(R.drawable.corner_rectangle_purple_bg1);
			} else {
				holder.item1.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg);
			}
			holder.item2.setText(list.get(position).getResove());
			holder.item3.setText(list.get(position).getBug_level());
			if (list.get(position).getResove().equals("已解决")) {
				holder.item2.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg);
			} else {
				holder.item2.setBackgroundResource(R.drawable.corner_rectangle_red_bg);
			}
			holder.title.setText(list.get(position).getRequirename());
			holder.desc.setText(list.get(position).getQuestion());
			holder.progress.setText(list.get(position).getResons());
			holder.method.setText(list.get(position).getMethods());
			holder.person.setText(list.get(position).getDeal());
			return convertView;
		}
	}

	private static class ViewHolder {
		FlexibleTextView person, method, desc, progress;
		TextView item1, item2, title, item3;
	}

}
