package com.chinamobile.yunweizhushou.ui.capabilityPlatform.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.backlogZone.GraphListActivity;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.adapter.Ability2Adapter;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.bean.Ability2Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class AbilityFrament2 extends BaseFragment implements OnClickListener {
	private ListView mListview;
	private List<Ability2Bean> mList;
	private Ability2Adapter mAdapter;
	private TextView searchBtn;
	private EditText searchEdittext;
	private String cond = "";
	private MyRefreshLayout mRefreshLayout;
	private TextView bottomChooce;
	private PopupWindow poWindow;
	private View popView;
	private String time = "1";
	private TextView titleItem1, titleItem2, titleItem3, titleItem4, titleItem5;
	private String orderType = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ability_2, container, false);
		initView(view);
		initEvent();
		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", "1031");// Cboss 1031
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", cond);
		map.put("time", time);
		map.put("order", orderType);
		startTask(HttpRequestEnum.enum_ability_list2, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_ability_list2:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<Ability2Bean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new Ability2Adapter(getActivity(), mList, R.layout.item_ability_2);
				mListview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	private void initEvent() {

		titleItem1.setOnClickListener(this);
		titleItem2.setOnClickListener(this);
		titleItem3.setOnClickListener(this);
		titleItem4.setOnClickListener(this);
		titleItem5.setOnClickListener(this);

		bottomChooce.setOnClickListener(this);
		searchBtn.setOnClickListener(this);

		searchEdittext.addTextChangedListener(new TextWatcher() {

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

		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("fkId", "1030");
				intent.putExtra("name", mList.get(position).getName());
				intent.putExtra("userId", "");
				intent.setClass(getActivity(), GraphListActivity.class);
				getActivity().startActivity(intent);
			}
		});

		mListview.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
	}

	private void initView(View view) {
		popView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_bottom_timer, null);
		popView.findViewById(R.id.bottom_timer_1min).setOnClickListener(this);
		popView.findViewById(R.id.bottom_timer_5min).setOnClickListener(this);
		popView.findViewById(R.id.bottom_timer_10min).setOnClickListener(this);
		popView.findViewById(R.id.bottom_timer_1h).setOnClickListener(this);
		popView.findViewById(R.id.bottom_timer_6h).setOnClickListener(this);
		popView.findViewById(R.id.bottom_timer_1d).setOnClickListener(this);
		popView.findViewById(R.id.bottom_timer_1week).setOnClickListener(this);
		popView.findViewById(R.id.bottom_timer_1month).setOnClickListener(this);
		bottomChooce = (TextView) view.findViewById(R.id.ability_bottom_chooce);
		bottomChooce.setText("1分钟");

		mListview = (ListView) view.findViewById(R.id.ability_2_listview);
		searchEdittext = (EditText) view.findViewById(R.id.ability_search_edittext);
		searchBtn = (TextView) view.findViewById(R.id.ability_search_btn);
		mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.dbConnectionRefreshLayout);

		titleItem1 = (TextView) view.findViewById(R.id.ability_title2_item1);
		titleItem2 = (TextView) view.findViewById(R.id.ability_title2_item2);
		titleItem3 = (TextView) view.findViewById(R.id.ability_title2_item3);
		titleItem4 = (TextView) view.findViewById(R.id.ability_title2_item4);
		titleItem5 = (TextView) view.findViewById(R.id.ability_title2_item5);

		titleItem1.setTag("cname");
		titleItem2.setTag("name");
		titleItem3.setTag("value");
		titleItem4.setTag("avg");
		titleItem5.setTag("err");
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.ability_title2_item1:
		case R.id.ability_title2_item2:
		case R.id.ability_title2_item3:
		case R.id.ability_title2_item4:
		case R.id.ability_title2_item5:
			orderType = v.getTag().toString();
			initRequest();
			break;

		case R.id.ability_search_btn:
			if (TextUtils.isEmpty(searchEdittext.getText().toString())) {
				Utils.ShowErrorMsg(getActivity(), "请输入搜索内容");
			} else {
				cond = searchEdittext.getText().toString();
				if (mAdapter != null) {
					mAdapter.clearData();
				}
				initRequest();
			}
			break;
		case R.id.ability_bottom_chooce:
			initPopupWindow();
			break;
		case R.id.bottom_timer_1min:
		case R.id.bottom_timer_5min:
		case R.id.bottom_timer_10min:
		case R.id.bottom_timer_1h:
		case R.id.bottom_timer_6h:
		case R.id.bottom_timer_1d:
		case R.id.bottom_timer_1week:
		case R.id.bottom_timer_1month:
			bottomChooce.setText(((TextView) v).getText());
			time = v.getTag() + "";
			initRequest();
			poWindow.dismiss();
			break;
		default:
			break;
		}

	}

	private void initPopupWindow() {
		poWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		poWindow.setOutsideTouchable(true);
		poWindow.setBackgroundDrawable(new ColorDrawable(0x000000000));
		poWindow.showAtLocation(bottomChooce, Gravity.BOTTOM, 0, 0);
	}

}
