package com.chinamobile.yunweizhushou.ui.PayMoreGetShit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class GraphListActivityFinal extends BaseActivity {

	private ListView listview;
	private RechargeFunctionListAdapter adapter;
	private RechargeFunctionGraphBean beans;

	private String chartName = "";
	private String actionValue = "";
	private String extraKey = "", extraValue = "";
	private String extraKey2 = "", extraValue2 = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_listview);
		chartName = getIntent().getStringExtra("chartName");
		actionValue = getIntent().getStringExtra("actionValue");
		extraKey = getIntent().getStringExtra("extraKey");
		extraValue = getIntent().getStringExtra("extraValue");
		extraKey2 = getIntent().getStringExtra("extraKey2");
		extraValue2 = getIntent().getStringExtra("extraValue2");

		initView();
		initEvent();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(extraValue);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// Intent intent = new Intent();
		// intent.setClass(GraphListActivityFinal.this,
		// BusinessAcceptHorizenGraphActivity.class);
		// intent.putExtra("extraKey", extraKey);
		// intent.putExtra("extraValue", extraValue);
		// intent.putExtra("extraKey2", extraKey2);
		// intent.putExtra("extraValue2", extraValue2);
		// intent.putExtra("action", actionValue);
		// startActivity(intent);
		// }
		// });
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", actionValue);
		if (!TextUtils.isEmpty(extraKey)) {
			map.put(extraKey, extraValue);
		}
		if (!TextUtils.isEmpty(extraKey2)) {
			map.put(extraKey2, extraValue2);
		}
		map.put("time", "6h");
		startTask(HttpRequestEnum.enum_govern_analysis_successrate_graph_list, ConstantValueUtil.URL + chartName + "?",
				map, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_govern_analysis_successrate_graph_list:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListAdapter(this, beans.getItemsList());
				listview.setAdapter(adapter);

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.common_listview);
	}
}
