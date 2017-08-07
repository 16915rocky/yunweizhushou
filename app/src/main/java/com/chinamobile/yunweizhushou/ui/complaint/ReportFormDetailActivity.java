package com.chinamobile.yunweizhushou.ui.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.complaint.adapter.ReportFormAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ReportFormBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ReportFormDetailActivity extends BaseActivity {

	private TextView item1, item2, item3, item4;
	private ListView listview;
	private List<ReportFormBean> list;
	private String name, time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_list_4);

		name = getIntent().getStringExtra("name");
		time = getIntent().getStringExtra("time");

		initView();

		initRequest();

		initEvent();

	}

	private void initEvent() {

		getTitleBar().setMiddleText(name);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent();
				i.setClass(ReportFormDetailActivity.this, ReportFormGraphDetailActivity.class);
				i.putExtra("name", list.get(position).getName());
				startActivity(i);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "assignerList");
		map.put("time", time);
		map.put("name", name);
		startTask(HttpRequestEnum.enum_complain_report_form_detail, ConstantValueUtil.URL + "ComplaintsBulletin?", map,
				true);
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
			case enum_complain_report_form_detail:
				Type t = new TypeToken<List<ReportFormBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				ReportFormAdapter adapter = new ReportFormAdapter(this, list, R.layout.item_list_4);
				listview.setAdapter(adapter);
				break;

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void initView() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.common_list_title_4);

		item1 = (TextView) layout.findViewById(R.id.list_title_4_item1);
		item2 = (TextView) layout.findViewById(R.id.list_title_4_item2);
		item3 = (TextView) layout.findViewById(R.id.list_title_4_item3);
		item4 = (TextView) layout.findViewById(R.id.list_title_4_item4);
		listview = (ListView) findViewById(R.id.common_list_4_listview);

		item1.setText("处理人");
		item2.setText("处理量");
		item3.setText("重开率");
		item4.setText("及时率");
	}

}
