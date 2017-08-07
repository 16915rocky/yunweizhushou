package com.chinamobile.yunweizhushou.ui.cboss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.backlogZone.GraphListActivity;
import com.chinamobile.yunweizhushou.ui.cboss.adapter.CbossDetailAdapter;
import com.chinamobile.yunweizhushou.ui.cboss.adapter.CbossDetailAdapter2;
import com.chinamobile.yunweizhushou.ui.cboss.bean.CbossDetailBean;
import com.chinamobile.yunweizhushou.ui.cboss.bean.CbossDetailBean2;
import com.chinamobile.yunweizhushou.ui.cboss.fragments.CbossFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CbossDetailActivity extends BaseActivity {

	private ListView mListview;
	private String name;
	private TextView title, item1, item2, item3, item4, item5;
	private List<CbossDetailBean> mList;
	private List<CbossDetailBean2> mList2;
	private CbossDetailAdapter mAdapter;
	private CbossDetailAdapter2 mAdapter2;
	private MyRefreshLayout mRefrashLayout;
	private int tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cboss_detail);
		name = getIntent().getStringExtra("name");
		tag = getIntent().getIntExtra("tag", 0);
		initView();
		initData();
		initEvent();
		initRequest();
	}

	private void initData() {
		if (tag == CbossFragment.WARNING + 2) {// cboss积分业务 id=1037
			item1.setText("采集\n时间");
			item2.setText("指标\n名称");
			item3.setText("失败\n量");
			item4.setText("成功率\n(%)");
			item5.setText("预计\n扣分");
		}
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", tag + "");
		map.put("code", "");
		map.put("busi", name);
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_cboss_detail, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	private void initEvent() {
		getTitleBar().setMiddleText(name);
		title.setText(name);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(CbossDetailActivity.this, GraphListActivity.class);
				if (tag == CbossFragment.MONITOR + 2) {
					intent.putExtra("name", mList.get(position).getBcode());
					intent.putExtra("userId", mList.get(position).getCode());
					intent.putExtra("fkId", "1016");
				} else {
					intent.putExtra("fkId", "1031");
					intent.putExtra("name", mList2.get(position).getName());
					intent.putExtra("userId", "");
				}
				startActivity(intent);
			}
		});

		mRefrashLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

		mListview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				boolean enable = false;
				if (mListview != null && mListview.getChildCount() > 0) {
					boolean firstItemVisible = mListview.getFirstVisiblePosition() == 0;
					boolean topOfFirstItemVisible = mListview.getChildAt(0).getTop() == 0;
					enable = firstItemVisible && topOfFirstItemVisible;
				}
				mRefrashLayout.setEnabled(enable);
			}
		});

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (mRefrashLayout.isShown()) {
			mRefrashLayout.setRefreshing(false);
		}

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_cboss_detail:
			if (responseBean.isSuccess()) {

				if (tag == CbossFragment.MONITOR + 2) {
						Type type = new TypeToken<List<CbossDetailBean>>() {
					}.getType();
					mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					mAdapter = new CbossDetailAdapter(this, mList, R.layout.item_cboss);
					mListview.setAdapter(mAdapter);
				} else if (tag == CbossFragment.WARNING + 2) {
					Type type = new TypeToken<List<CbossDetailBean2>>() {
					}.getType();
					mList2 = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					mAdapter2 = new CbossDetailAdapter2(this, mList2, R.layout.item_cboss2);
					mListview.setAdapter(mAdapter2);
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView() {
		mListview = (ListView) findViewById(R.id.cboss_detail_listview);
		mRefrashLayout = (MyRefreshLayout) findViewById(R.id.cboss_detail_refresh);
		title = (TextView) findViewById(R.id.cboss_detail_title);
		item1 = (TextView) findViewById(R.id.cboss_detail_item1);
		item2 = (TextView) findViewById(R.id.cboss_detail_item2);
		item3 = (TextView) findViewById(R.id.cboss_detail_item3);
		item4 = (TextView) findViewById(R.id.cboss_detail_item4);
		item5 = (TextView) findViewById(R.id.cboss_detail_item5);
	}
}
