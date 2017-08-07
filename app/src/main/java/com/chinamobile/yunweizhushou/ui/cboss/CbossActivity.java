package com.chinamobile.yunweizhushou.ui.cboss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.GovernNewListBean;
import com.chinamobile.yunweizhushou.bean.GovernNewTitleBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.esbInterface.adapters.OperateAnalysisAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CbossActivity extends BaseActivity {

	private TextView normal, warning, error;
	private GridView mGridView;
	private GovernNewTitleBean titleBean;
	private List<GovernNewListBean> listBean;
	private OperateAnalysisAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_cboss);
		initView();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", "1016");
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_cboss_title, ConstantValueUtil.URL + "SpecialTreatment?", map);
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getSpecial");
		map2.put("id", "1017");
		map2.put("code", "");
		map2.put("busi", "");
		map2.put("cond", "");
		startTask(HttpRequestEnum.enum_cboss_list, ConstantValueUtil.URL + "SpecialTreatment?", map2);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_cboss_title:
			if (responseBean.isSuccess()) {
				titleBean = new Gson().fromJson(responseBean.getDATA(), GovernNewTitleBean.class);
				normal.setText(titleBean.getNormal());
				warning.setText(titleBean.getWarning());
				error.setText(titleBean.getOptimize());
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_cboss_list:

			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<GovernNewListBean>>() {
				}.getType();
				listBean = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new OperateAnalysisAdapter(this, listBean, R.layout.item_operate_analysis);
				mGridView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}

			break;
		default:
			break;
		}
	}

	private void initEvent() {
		getTitleBar().setMiddleText("cBoss");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(CbossActivity.this, CbossDetailActivity.class);
				intent.putExtra("name", listBean.get(position).getName());
				startActivity(intent);
			}
		});
	}

	private void initView() {
		normal = (TextView) findViewById(R.id.cboss_normal);
		warning = (TextView) findViewById(R.id.cboss_warning);
		error = (TextView) findViewById(R.id.cboss_error);
		mGridView = (GridView) findViewById(R.id.cboss_gridview);
	}

}
