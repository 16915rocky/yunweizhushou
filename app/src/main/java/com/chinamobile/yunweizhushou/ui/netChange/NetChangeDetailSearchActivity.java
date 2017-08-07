package com.chinamobile.yunweizhushou.ui.netChange;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail11Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeDetailSearchActivity extends BaseActivity implements OnClickListener {

	private ListView listview;
	private TextView searchBtn;
	private EditText searchContent;
	private String cond = "", plan;
	private List<NetChangeDetail11Bean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_net_change_detail_search);
		plan = getIntent().getStringExtra("plan");
		initView();
		initEvent();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setMiddleText("全部清单需求");

		searchBtn.setOnClickListener(this);

		searchContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					searchBtn.setBackgroundResource(R.drawable.button_click_selector2);
				} else {
					searchBtn.setBackgroundResource(R.drawable.corner_rectangle_lightgray_bg);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.net_change_detail_search_listview);
		searchBtn = (TextView) findViewById(R.id.net_change_search_btn);
		searchContent = (EditText) findViewById(R.id.net_change_search_edittext);
	}

	@Override
	public void onClick(View v) {
		if (TextUtils.isEmpty(searchBtn.getText().toString())) {
			Utils.ShowErrorMsg(this, "请输入搜索内容");
		} else {
			cond = searchContent.getText().toString();
			initRequest();
		}
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getOnlineAllList");
		map.put("online_plan", plan);
		map.put("requireName", cond);
		startTask(HttpRequestEnum.enum_net_change_0, ConstantValueUtil.URL + "ChangeTask?", map, true);
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
			case enum_net_change_0:
				Type t = new TypeToken<List<NetChangeDetail11Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				NetChangeDetail11Adapter adapter = new NetChangeDetail11Adapter(this, list,
						R.layout.item_net_change_detail_1_1);
				listview.setAdapter(adapter);

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	class NetChangeDetail11Adapter extends AbsBaseAdapter<NetChangeDetail11Bean> {

		private List<NetChangeDetail11Bean> list;

		public NetChangeDetail11Adapter(Context context, List<NetChangeDetail11Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail11Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_item2);
				holder.system1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_system1);
				holder.system2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_system2);
				holder.person1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_person1);
				holder.person2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_person2);
				holder.person3 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_person3);
				holder.state1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_state1);
				holder.state2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_state2);
				convertView.setTag(holder);
			}
			holder.item1.setText(list.get(position).getBig_system());
			holder.system1.setText(list.get(position).getRequire_code());
			holder.item2.setText(list.get(position).getSystem());
			holder.system2.setText(list.get(position).getRequire_name());
			holder.person1.setText(list.get(position).getRequire_man());
			holder.person2.setText(list.get(position).getDev_manager());
			holder.person3.setText(list.get(position).getTest_manager());

			String state1 = list.get(position).getState();
			String state2 = list.get(position).getIntroduced_state();
			holder.state1.setText(state1);
			holder.state2.setText(state2);
			setBackColor(state1, holder.state1);
			setBackColor(state2, holder.state2);

			return convertView;
		}

	}

	private void setBackColor(String state, TextView tv) {
		if (state.equals("成功") || state.equals("通过")) {
			tv.setBackgroundResource(R.drawable.oval_green);
		} else if (state.equals("失败") || state.equals("未通过")) {
			tv.setBackgroundResource(R.drawable.oval_red);
		} else {
			// tv.setBackgroundResource(R.drawable.oval_green);
		}
	}

	private static class ViewHolder {
		TextView system1, system2, person1, person2, person3, state1, state2, item1, item2;
	}
}
