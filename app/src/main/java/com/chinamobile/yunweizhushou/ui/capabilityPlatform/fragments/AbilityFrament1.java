package com.chinamobile.yunweizhushou.ui.capabilityPlatform.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.backlogZone.GraphListActivity;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.adapter.Ability1Adapter;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.bean.Ability1Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class AbilityFrament1 extends BaseFragment implements OnClickListener {
	private ListView mListview;
	private int tag;
	public static final int CENTER = 1028;
	public static final int CITY = 1029;
	public static final int CHANNEL = 1030;
	private List<Ability1Bean> mList;
	private Ability1Adapter mAdapter;
	private MyRefreshLayout mRefreshLayout;
	private TextView bottomChooce;
	private PopupWindow poWindow;
	private View popView;
	private String orderType = "";
	private String time = "1";
	private TextView titleItem1, titleItem2, titleItem3, titleItem4, titleItem5;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ability_1, container, false);
		Bundle arguments = getArguments();
		tag=arguments.getInt("tag");
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
		map.put("cond", "");
		map.put("time", time);
		map.put("order", orderType);
		startTask(HttpRequestEnum.enum_ability_list1, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
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
		case enum_ability_list1:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<Ability1Bean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new Ability1Adapter(getActivity(), mList, R.layout.item_ability_1);
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

		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				if (tag == CENTER) {
					intent.putExtra("fkId", "1027");
				} else if (tag == CITY) {
					intent.putExtra("fkId", "1028");
				} else if (tag == CHANNEL) {
					intent.putExtra("fkId", "1029");
				}
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

		mListview = (ListView) view.findViewById(R.id.ability_1_listview);
		mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.dbConnectionRefreshLayout);
		titleItem1 = (TextView) view.findViewById(R.id.ability_title_item1);
		titleItem2 = (TextView) view.findViewById(R.id.ability_title_item2);
		titleItem3 = (TextView) view.findViewById(R.id.ability_title_item3);
		titleItem4 = (TextView) view.findViewById(R.id.ability_title_item4);
		titleItem5 = (TextView) view.findViewById(R.id.ability_title_item5);

		titleItem1.setTag("name");
		titleItem2.setTag("num");
		titleItem3.setTag("value");
		titleItem4.setTag("avg");
		titleItem5.setTag("err");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ability_title_item1:
		case R.id.ability_title_item2:
		case R.id.ability_title_item3:
		case R.id.ability_title_item4:
		case R.id.ability_title_item5:
			orderType = v.getTag().toString();

			initRequest();
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
