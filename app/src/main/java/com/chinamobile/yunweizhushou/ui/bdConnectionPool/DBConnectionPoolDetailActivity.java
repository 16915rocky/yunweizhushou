package com.chinamobile.yunweizhushou.ui.bdConnectionPool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter.DBConnectionPoolBallAdapter;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBallBean;
import com.chinamobile.yunweizhushou.ui.esbInterface.WaterBallGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DBConnectionPoolDetailActivity extends BaseActivity {

	private MyGridView mGridView;
	private TextView normal, focus, optimize;
	private DBConnectionPoolBallBean ballBean;
	private DBConnectionPoolBallAdapter adapter;
	private ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db_connection_detail);

		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mGridView = (MyGridView) findViewById(R.id.dbDetailGridView);
		scrollView = (ScrollView) findViewById(R.id.detailScrollView);
		normal = (TextView) findViewById(R.id.detailNormal);
		focus = (TextView) findViewById(R.id.detailFocus);
		optimize = (TextView) findViewById(R.id.detailOptimize);
		initRequest();
		initEvent();
	}

	private void initEvent() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(DBConnectionPoolDetailActivity.this, WaterBallGraphActivity.class);
				intent.putExtra("name", ballBean.getItemsList().get(position).getName());
				intent.putExtra("userId", ballBean.getItemsList().get(position).getCode());
				String fkId = getIntent().getStringExtra("fkId");
				if (fkId.equals("1011")) {
					intent.putExtra("fkId", fkId);// 数据库容量池
				} else if (fkId.equals("1012")) {
					intent.putExtra("fkId", fkId);// 线程容量池
				}
				startActivity(intent);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", getIntent().getStringExtra("id"));
		map.put("busi", getIntent().getStringExtra("busi"));
		startTask(HttpRequestEnum.enum_pool_analysis_ball, ConstantValueUtil.URL + "SpecialTreatment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(DBConnectionPoolDetailActivity.this, responseBean.getMSG());
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_pool_analysis_ball:
				Log.e("tag", responseBean.getDATA());
				Type type2 = new TypeToken<DBConnectionPoolBallBean>() {
				}.getType();
				ballBean = new Gson().fromJson(responseBean.getDATA(), type2);

				int n = 0, f = 0, O = 0;
				for (int i = 0; i < ballBean.getItemsList().size(); i++) {
					DBConnectionPoolBallBean.ConnectionPoolItem item = ballBean.getItemsList().get(i);
					if (item.getState() == null) {
						continue;
					} else {
						if ("1".equals(item.getState())) {
							n++;
						} else if ("2".equals(item.getState())) {
							f++;
						} else if ("3".equals(item.getState())) {
							O++;
						}
					}
				}
				normal.setText("" + n);
				focus.setText("" + f);
				optimize.setText("" + O);
				adapter = new DBConnectionPoolBallAdapter(DBConnectionPoolDetailActivity.this, ballBean.getItemsList());
				mGridView.setAdapter(adapter);
				scrollView.smoothScrollTo(0, 0);
				break;
			}
		}
	}
}
