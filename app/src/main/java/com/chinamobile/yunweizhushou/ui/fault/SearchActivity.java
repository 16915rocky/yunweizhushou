package com.chinamobile.yunweizhushou.ui.fault;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.BraceBroadcastBean;
import com.chinamobile.yunweizhushou.bean.PageBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.SearchHotWordBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.SearchAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends BaseActivity implements OnClickListener {

	private TextView tip1, tip2, tip3, tip4;
	private ListView listview;
	private LinearLayout adviceLayout;
	private List<SearchHotWordBean> hotWordList;
	private List<BraceBroadcastBean> searchList = new ArrayList<BraceBroadcastBean>();

	private boolean isRefresh;
	private PageBean page;

	private SearchAdapter mAdapter;

	private boolean isNewContent = true;
	private String currentKeyWord;
	private View bottomLayout;

	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getIntent().getStringExtra("type");
		setContentView(R.layout.activity_search);

		initView();
		initEvent();
		if (type.equals(CalendarActivity.TYPE_BROADCAST)) {
			initRequest();
		}
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "hotKeyword");
		startTask(HttpRequestEnum.enum_bracebroadcast_searchadvice, ConstantValueUtil.URL + "Bomc?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_bracebroadcast_searchadvice:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<SearchHotWordBean>>() {
				}.getType();
				hotWordList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				tip1.setText(hotWordList.get(0).getBomcName());
				tip2.setText(hotWordList.get(1).getBomcName());
				tip3.setText(hotWordList.get(2).getBomcName());
				tip4.setText(hotWordList.get(3).getBomcName());

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_bracebroadcast_keysearch:
		case enum_faultmanage_search:
			if (responseBean.isSuccess()) {
				isRefresh = false;
				if (listview.getFooterViewsCount() > 0) {
					listview.removeFooterView(bottomLayout);
				}
				page = new Gson().fromJson(responseBean.getDATA(), PageBean.class);
				Type type = new TypeToken<List<BraceBroadcastBean>>() {
				}.getType();
				List<BraceBroadcastBean> l = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				if (l != null && l.size() > 0) {
					if (adviceLayout.getVisibility() == View.VISIBLE) {
						adviceLayout.setVisibility(View.GONE);
					}
					if (isNewContent) {
						searchList.clear();
					}
					searchList.addAll(l);

					if (mAdapter == null) {
						mAdapter = new SearchAdapter(this, searchList);
						listview.setAdapter(mAdapter);
					} else {
						mAdapter.notifyDataSetChanged();
					}
				} else {
					Utils.ShowErrorMsg(this, "已经是最后一页了");
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
		tip1 = (TextView) findViewById(R.id.search_tip1);
		tip2 = (TextView) findViewById(R.id.search_tip2);
		tip3 = (TextView) findViewById(R.id.search_tip3);
		tip4 = (TextView) findViewById(R.id.search_tip4);
		listview = (ListView) findViewById(R.id.search_listview);
		adviceLayout = (LinearLayout) findViewById(R.id.search_advice_layout);
		if (type.equals(CalendarActivity.TYPE_FAULT)) {
			adviceLayout.setVisibility(View.GONE);
		}
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().showSearch("请输入搜索内容");
		getTitleBar().setRightButton1(R.mipmap.icon_search_correct, new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = getTitleBar().getSearchContent();
				if (TextUtils.isEmpty(content)) {
					Utils.ShowErrorMsg(SearchActivity.this, "请输入搜索内容");
				} else {
					isNewContent = true;
					currentKeyWord = content;
					initSearchRequest(currentKeyWord, 1);
				}
			}
		});

		tip1.setOnClickListener(this);
		tip2.setOnClickListener(this);
		tip3.setOnClickListener(this);
		tip4.setOnClickListener(this);

		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (listview != null && listview.getAdapter() != null && page != null) {
					if ((listview.getLastVisiblePosition() == listview.getAdapter().getCount() - 1 && page.isHasNext())
							&& !isRefresh) {
						isRefresh = true;
						isNewContent = false;
						bottomLayout = getLayoutInflater().inflate(R.layout.page_bottom_layout, null);
						listview.addFooterView(bottomLayout);
						initSearchRequest(currentKeyWord, page.getPageNumber() + 1);
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		isNewContent = true;
		switch (v.getId()) {
		case R.id.search_tip1:
			currentKeyWord = tip1.getText().toString();
			initSearchRequest(currentKeyWord, 1);
			break;
		case R.id.search_tip2:
			currentKeyWord = tip2.getText().toString();
			initSearchRequest(currentKeyWord, 1);
			break;
		case R.id.search_tip3:
			currentKeyWord = tip3.getText().toString();
			initSearchRequest(currentKeyWord, 1);
			break;
		case R.id.search_tip4:
			currentKeyWord = tip4.getText().toString();
			initSearchRequest(currentKeyWord, 1);
			break;
		default:
			break;
		}
	}

	private void initSearchRequest(String string, int pageNum) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "search");
		map.put("keyword", Utils.utf82Iso(string));
		map.put("pageNum", pageNum + "");
		if (type.equals(CalendarActivity.TYPE_BROADCAST)) {
			startTask(HttpRequestEnum.enum_bracebroadcast_keysearch, ConstantValueUtil.URL + "Bomc?", map);
		} else if (type.equals(CalendarActivity.TYPE_FAULT)) {
			startTask(HttpRequestEnum.enum_faultmanage_search, ConstantValueUtil.URL + "Broadcast?", map);
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			HashMap<String, String> map = new HashMap<>();
			map.put("action", "bomcNumber");
			map.put("id", (String) msg.obj);
			startTask(HttpRequestEnum.enum_bracebroadcast_addread, ConstantValueUtil.URL + "Broadcast?", map);
		};
	};
}
