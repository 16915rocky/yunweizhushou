package com.chinamobile.yunweizhushou.ui.openCenter.fragments;

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
import com.chinamobile.yunweizhushou.bean.OpenCenterBacklogBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.openCenter.adapter.OpenCenterBacklogAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class OpenCenterBacklogFragment extends BaseFragment implements OnClickListener {
	private ListView mListview;
	private List<OpenCenterBacklogBean> mList;
	private OpenCenterBacklogAdapter mAdapter;
	private TextView searchBtn;
	private EditText searchEdittext;
	private String cond = "";
	private MyRefreshLayout mRefreshLayout;
	public static final int BACKLOG = 1043;
	private int tag;
	String cname;

	private TextView bottomChooce;
	private PopupWindow poWindow;
	private View popView;
	private String time = "1";

	private TextView title1, title2, title3, title4;


	public OpenCenterBacklogFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		tag=arguments.getInt("tag");
		View view = inflater.inflate(R.layout.fragment_opencenter_backlog, container, false);
		initView(view);
		initEvent();
		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", tag + "");
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", cond);
		map.put("time", time);
		startTask(HttpRequestEnum.enum_opencenter_backlog, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
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
		case enum_opencenter_backlog:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<OpenCenterBacklogBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new OpenCenterBacklogAdapter(getActivity(), mList, R.layout.item_ability_3);
				mAdapter.setTag(BACKLOG);
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

		title1.setOnClickListener(this);
		title2.setOnClickListener(this);
		title3.setOnClickListener(this);
		title4.setOnClickListener(this);

		// bottomChooce.setOnClickListener(this);
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
				intent.putExtra("fkId", "1081");
				intent.putExtra("extraKey", "pament_business");
				intent.putExtra("extraValue", mList.get(position).getName());
				intent.putExtra("extraKey2", "region_channel");
				intent.putExtra("extraValue2", mList.get(position).getCname());
				intent.setClass(getActivity(), GraphListActivity2.class);
				getActivity().startActivity(intent);
			}
		});

		mListview.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				cond = searchEdittext.getText().toString();
				initRequest();
			}
		});
	}

	private void initView(View view) {

		mListview = (ListView) view.findViewById(R.id.rule_listview);
		searchEdittext = (EditText) view.findViewById(R.id.rule_search_edittext);
		searchBtn = (TextView) view.findViewById(R.id.rule_search_btn);
		mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.rule_swipe_refresh_layout);

		title1 = (TextView) view.findViewById(R.id.ruleTitle1);
		title2 = (TextView) view.findViewById(R.id.ruleTitle2);
		title3 = (TextView) view.findViewById(R.id.ruleTitle3);
		title4 = (TextView) view.findViewById(R.id.ruleTitle4);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ability_bottom_chooce:
			initPopupWindow();
			break;
		case R.id.rule_search_btn:
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

		case R.id.ruleTitle1:
		case R.id.ruleTitle2:
		case R.id.ruleTitle3:
		case R.id.ruleTitle4:

			break;
		default:
			bottomChooce.setText(((TextView) v).getText());
			time = v.getTag() + "";
			initRequest();
			poWindow.dismiss();
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
