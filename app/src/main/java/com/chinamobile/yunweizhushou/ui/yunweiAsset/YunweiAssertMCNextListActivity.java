package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter.YunweiAssetMCAdapter2;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetBean5;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YunweiAssertMCNextListActivity extends BaseActivity {

	private TextView tv1_1, tv1_2, tv1_3, tv1_4;
	private TextView tv2_1, tv2_2, tv2_3, tv2_4, tv2_5;
	private List<YunweiAssetBean5> list;
	private YunweiAssetMCAdapter2 mAdapter;
	private ListView listView;
	private String type, system;
	private int layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getIntent().getStringExtra("type");
		system = getIntent().getStringExtra("system");
		if ("2".equals(type)) {
			setContentView(R.layout.common_list_5);
		} else {
			setContentView(R.layout.common_list_4);
		}

		initView();
		initEvent();
		initRequest(type);

	}

	private void initRequest(String type) {
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getFocusListing");
		map2.put("type", type);
		map2.put("system", system);
		startTask(HttpRequestEnum.enum_yunwei_asset_mc_list, ConstantValueUtil.URL + "AssetsUsed?", map2, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_yunwei_asset_mc_list:
			if (responseBean.isSuccess()) {

				Type t = new TypeToken<List<YunweiAssetBean5>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				if ("2".equals(type)) {					
					mAdapter=new YunweiAssetMCAdapter2(this, list, R.layout.item_list_5);
				} else {
					mAdapter=new YunweiAssetMCAdapter2(this, list, R.layout.item_list_4);
				}

				//mAdapter = new YunweiAssetMCAdapter2(this, list, layout);
				listView.setAdapter(mAdapter);

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}

			break;

		default:
			break;
		}
	}

	private void initEvent() {

		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		/*
		 * gridView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long idLong) { if (canClickIntoDetail) { Intent intent
		 * = new Intent(); intent.setClass(YunweiAssertMCActivity.this,
		 * WaterBallGraphActivity.class); intent.putExtra("userId", "");
		 * intent.putExtra("fkId", "1006"); intent.putExtra("name",
		 * listBean.get(position).getName()); startActivity(intent); } else if
		 * (titleId.equals("1004")) { Intent intent = new Intent();
		 * intent.setClass(YunweiAssertMCActivity.this,
		 * GraphListActivity.class); intent.putExtra("userId", "");
		 * intent.putExtra("fkId", "1014"); intent.putExtra("name",
		 * listBean.get(position).getName()); startActivity(intent); } } });
		 */

	}

	private void initView() {
		if ("2".equals(type)) {
			listView = (ListView) findViewById(R.id.common_list_5_listview);
			tv2_1 = (TextView) findViewById(R.id.list_title_5_item1);
			tv2_2 = (TextView) findViewById(R.id.list_title_5_item2);
			tv2_3 = (TextView) findViewById(R.id.list_title_5_item3);
			tv2_4 = (TextView) findViewById(R.id.list_title_5_item4);
			tv2_5 = (TextView) findViewById(R.id.list_title_5_item5);
			tv2_1.setText("进程名称");
			tv2_2.setText("功能描述");
			tv2_3.setText("归属服务器");
			tv2_4.setText("进程实例数");
			tv2_5.setText("甲方责任人");
		}else{
			listView = (ListView) findViewById(R.id.common_list_4_listview);
			tv1_1 = (TextView) findViewById(R.id.list_title_4_item1);
			tv1_2 = (TextView) findViewById(R.id.list_title_4_item2);
			tv1_3 = (TextView) findViewById(R.id.list_title_4_item3);
			tv1_4 = (TextView) findViewById(R.id.list_title_4_item4);
			tv1_1.setText("服务器名称");
			tv1_2.setText("物理IP");
			tv1_3.setText("功能描述");
			tv1_4.setText("甲方责任人");
		}
		

	}

}
