package com.chinamobile.yunweizhushou.ui.systemTree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.systemTree.adapter.SystemTreeDetailDetailAdapter;
import com.chinamobile.yunweizhushou.ui.systemTree.adapter.SystemTreeDetailDetailAdapter2;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.SystemTreeDetailDetailBean1;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.SystemTreeDetailDetailBean2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class SystemTreeDetailDetailActivity extends BaseActivity {

	private ListView listview;
	private LinearLayout headView;
	private String title, id, busi;
	private List<SystemTreeDetailDetailBean1> list1;
	private List<SystemTreeDetailDetailBean2> list2;
	private int tag = 1;
	private static final int TAG1 = 1;
	private static final int TAG2 = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_listview);
		title = getIntent().getStringExtra("title");
		id = getIntent().getStringExtra("id");
		busi = getIntent().getStringExtra("busi");

		initView();
		initHead();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", id);
		map.put("code", "");
		map.put("busi", busi);
		map.put("cond", "");

		startTask(HttpRequestEnum.enum_system_tree_detail_detail, ConstantValueUtil.URL + "SpecialTreatment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_system_tree_detail_detail:
			if (responseBean.isSuccess()) {
				Type type = null;
				if (tag == TAG1) {
					type = new TypeToken<List<SystemTreeDetailDetailBean1>>() {
					}.getType();
					list1 = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					SystemTreeDetailDetailAdapter adapter1 = new SystemTreeDetailDetailAdapter(this, list1,
							R.layout.item_common_six_equal_part);
					listview.setAdapter(adapter1);
				} else {
					type = new TypeToken<List<SystemTreeDetailDetailBean2>>() {
					}.getType();
					list2 = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					SystemTreeDetailDetailAdapter2 adapter2 = new SystemTreeDetailDetailAdapter2(this, list2,
							R.layout.item_common_six_equal_part);
					listview.setAdapter(adapter2);
				}
				listview.addHeaderView(headView);
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

	private void initHead() {
		View view = LayoutInflater.from(this).inflate(R.layout.head_system_tree_detail_detail, null);
		if (title.contains("主机")) {
			tag = TAG1;
			headView = (LinearLayout) view.findViewById(R.id.system_tree_detail_head_1);
		} else {
			tag = TAG2;
			headView = (LinearLayout) view.findViewById(R.id.system_tree_detail_head_2);
		}
		// headLayout.removeAllViews();
		// headLayout.addView(headView);
	}

	private void initEvent() {
		getTitleBar().setMiddleText(title);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
