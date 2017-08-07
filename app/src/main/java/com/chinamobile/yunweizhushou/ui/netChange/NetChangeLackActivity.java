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
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeLackBean;
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

public class NetChangeLackActivity extends BaseActivity {

	private TextView num1, num2, num3, num4;
	private ListView listview;
	private String time;
	private List<NetChangeLackBean> list;

	private LinearLayout head;
	private TextView nocontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_net_change_lack);
		time = getIntent().getStringExtra("time");
		initView();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "onlineDefectOfMonth");
		map.put("time", time);
		startTask(HttpRequestEnum.enum_net_change_lack, ConstantValueUtil.URL + "ChangeTask?", map, true);
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
			case enum_net_change_lack:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA()).getJSONObject("total");
					String remain_num = obj.getString("remain_num");
					String fault_valid_num = obj.getString("fault_valid_num");
					String fault_num = obj.getString("fault_num");
					String fault_invalid_num = obj.getString("fault_invalid_num");
					num1.setText(fault_num);
					num2.setText(fault_valid_num);
					num3.setText(fault_invalid_num);
					num4.setText(remain_num);
					Type type = new TypeToken<List<NetChangeLackBean>>() {
					}.getType();
					list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					NetChangeLackAdapter adapter = new NetChangeLackAdapter(this, list,
							R.layout.item_net_change_detail_2);
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
		getTitleBar().setMiddleText("月度上线缺陷");
	}

	private void initView() {
		num1 = (TextView) findViewById(R.id.net_change_lack_num1);
		num2 = (TextView) findViewById(R.id.net_change_lack_num2);
		num3 = (TextView) findViewById(R.id.net_change_lack_num3);
		num4 = (TextView) findViewById(R.id.net_change_lack_num4);
		listview = (ListView) findViewById(R.id.net_change_lack_listview);

		head = (LinearLayout) findViewById(R.id.net_change_lack_head);
		nocontent = (TextView) findViewById(R.id.nocontent_textview);
	}

	class NetChangeLackAdapter extends AbsBaseAdapter<NetChangeLackBean> {

		private List<NetChangeLackBean> list;

		public NetChangeLackAdapter(Context context, List<NetChangeLackBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeLackBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.bug = (TextView) convertView.findViewById(R.id.net_change_detail_2_bug);
				holder.solve = (TextView) convertView.findViewById(R.id.net_change_detail_2_solve);
				holder.person1 = (TextView) convertView.findViewById(R.id.net_change_detail_2_person1);
				holder.person2 = (TextView) convertView.findViewById(R.id.net_change_detail_2_person2);
				holder.item = (TextView) convertView.findViewById(R.id.net_change_detail_2_item);
				holder.content = (TextView) convertView.findViewById(R.id.net_change_detail_2_content);
				holder.desc = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_2_desc);
				holder.progress = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_2_progress);
				holder.require = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_2_require);
				convertView.setTag(holder);
			}
			String bug = list.get(position).getEffect_fault();
			if (bug.length() >= 4) {
				holder.bug.setText(bug.substring(0, 2) + "\n" + bug.substring(2, bug.length()));
			} else {
				holder.bug.setText(bug);
			}
			holder.solve.setText(list.get(position).getResove());
			if (list.get(position).getResove().equals("已解决")) {
				holder.solve.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg);
			} else {
				holder.solve.setBackgroundResource(R.drawable.corner_rectangle_red_bg);
			}
			holder.item.setText(list.get(position).getType());
			holder.content.setText("缺陷编号:" + list.get(position).getBug_num());
			holder.desc.setText(list.get(position).getQuestion());
			holder.progress.setText(list.get(position).getResove_level());
			holder.person1.setText(list.get(position).getRequire_manager());
			holder.person2.setText(list.get(position).getBug_dev());
			holder.require.setText(list.get(position).getRequire());

			return convertView;
		}

	}

	private static class ViewHolder {
		FlexibleTextView desc, progress, require;
		TextView bug, solve, person1, person2, item, content;
	}

}
