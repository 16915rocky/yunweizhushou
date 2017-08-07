package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter.YunweiAssetWebLogicAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetBean7;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YunweiAssetWebLogicActivity extends BaseActivity {

	private LinearLayout lt, DBM_LinearLayout;
	private TextView tv1, tv2, tv3,tv4, DBM_tv1, DBM_tv2, DBM_tv3, titlecontent;
	private List<YunweiAssetBean7> list;
	private YunweiAssetWebLogicAdapter mAdapter;
	private ListView mListView;
	private String add, upd, del;
	private String type;
	private String titleContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type=getIntent().getStringExtra("type");
		titleContent=getIntent().getStringExtra("titleContent");
		setContentView(R.layout.common_list_3);
		initView();
		initRequest();
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		getTitleBar().setMiddleText(titleContent);

	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "getListOfDataCheck");
		if("3".equals(type)){
			maps.put("type", "3");
		}else{			
			maps.put("type", "2");			
		}
		startTask(HttpRequestEnum.enum_yunwei_asset_webLogic, ConstantValueUtil.URL + "AssetsUsed?", maps, true);

	}

	private void initView() {
		lt = (LinearLayout) findViewById(R.id.common_list_3_layout);
		DBM_LinearLayout = (LinearLayout) findViewById(R.id.DBM_LinearLayout);
		DBM_LinearLayout.setVisibility(View.VISIBLE);
		tv1 = (TextView) findViewById(R.id.list_title_3_item1);
		tv2 = (TextView) findViewById(R.id.list_title_3_item2);
		tv3 = (TextView) findViewById(R.id.list_title_3_item3);
		tv4 = (TextView) findViewById(R.id.list_title_3_item4);
		tv4.setVisibility(View.VISIBLE);
		titlecontent = (TextView) findViewById(R.id.titlecontent2);
		if("BMC平台".equals(titleContent)){
			titlecontent.setText("经和运维资产自动采集数据进行比对,BMC平台");
		}else{
			titlecontent.setText("经和运维资产自动采集数据进行比对,Weblogic平台");
		}
		titlecontent.setVisibility(View.VISIBLE);
		DBM_tv1 = (TextView) findViewById(R.id.DBM_tv1);
		DBM_tv2 = (TextView) findViewById(R.id.DBM_tv2);
		DBM_tv3 = (TextView) findViewById(R.id.DBM_tv3);
		mListView = (ListView) findViewById(R.id.common_list_3_listview);
		lt.setVisibility(View.GONE);
		tv1.setText("调整\n类型");
		tv2.setText("进程\n名称");
		tv3.setText("用户名");
		tv4.setText("归属\n服务器");

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {

		super.onTaskFinish(e, responseBean);
		
		switch (e) {
		case enum_yunwei_asset_webLogic:
			if (responseBean.isSuccess()) {

				try {
					JSONObject jo = new JSONObject(responseBean.getDATA());
					add = jo.getString("add");
					del = jo.getString("del");
					upd = jo.getString("upd");
					DBM_tv1.setText(add);
					DBM_tv2.setText(del);
					DBM_tv3.setText(upd);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Type t = new TypeToken<List<YunweiAssetBean7>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter = new YunweiAssetWebLogicAdapter(this, list, R.layout.item_list_4);
				mListView.setAdapter(mAdapter);
			}
			break;
		}

	}

}
