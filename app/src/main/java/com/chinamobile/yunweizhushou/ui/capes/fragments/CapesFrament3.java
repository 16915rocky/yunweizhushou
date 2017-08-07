package com.chinamobile.yunweizhushou.ui.capes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.backlogZone.GraphListActivity;
import com.chinamobile.yunweizhushou.ui.capes.adapter.Capes3LeftAdapter;
import com.chinamobile.yunweizhushou.ui.capes.adapter.Capes3RightAdapter;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes3LeftBean;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes3RightBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CapesFrament3 extends BaseFragment implements OnClickListener {
	private TextView current, today;
	private ListView mListview;
	private String state = "";
	private static final String CURRENT = "current";
	private static final String TODAY = "today";
	private int tag;
	public static final int IVR = 1022;
	public static final int SHORT_HALL = 1024;
	public static final int ENTITY_HALL = 1026;
	private List<Capes3LeftBean> mListLeft;
	private List<Capes3RightBean> mListRight;
	private Capes3LeftAdapter mAdapterLeft;
	private Capes3RightAdapter mAdapterRight;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_capes_3, container, false);
		Bundle arguments = getArguments();
		tag=arguments.getInt("tag");
		initView(view);
		initEvent();
		initData();
		initLeftRequest();
		return view;
	}

	private void initLeftRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", tag + "");
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_capes_left_list3, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	private void initRightRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", (tag + 1) + "");
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_capes_right_list3, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_capes_left_list3:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<Capes3LeftBean>>() {
				}.getType();
				mListLeft = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapterLeft = new Capes3LeftAdapter(getActivity(), mListLeft, R.layout.item_capes_3_left);
				mListview.setAdapter(mAdapterLeft);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_capes_right_list3:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<Capes3RightBean>>() {
				}.getType();
				mListRight = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapterRight = new Capes3RightAdapter(getActivity(), mListRight, R.layout.item_capes_3_right);
				mListview.setAdapter(mAdapterRight);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initData() {
		current.performClick();
		current.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
		current.setTextColor(getActivity().getResources().getColor(R.color.color_white));
		state = CURRENT;
	}

	private void initEvent() {
		current.setOnClickListener(this);
		today.setOnClickListener(this);

		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (state.equals(CURRENT)) {
					Intent intent = new Intent();
					if (tag == IVR) {
						intent.putExtra("fkId", "1021");
					} else if (tag == SHORT_HALL) {
						intent.putExtra("fkId", "1022");
					} else if (tag == ENTITY_HALL) {
						intent.putExtra("fkId", "1023");
					}
					intent.putExtra("name", mListLeft.get(position).getName());
					intent.putExtra("userId", "");
					intent.setClass(getActivity(), GraphListActivity.class);
					getActivity().startActivity(intent);
				}
			}
		});
	}

	private void initView(View view) {
		current = (TextView) view.findViewById(R.id.capes_3_current);
		today = (TextView) view.findViewById(R.id.capes_3_today);
		mListview = (ListView) view.findViewById(R.id.capes_3_listview);
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.capes_3_current:
			if (state.equals(TODAY)) {
				state = CURRENT;
				current.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
				current.setTextColor(getActivity().getResources().getColor(R.color.color_white));
				initLeftRequest();
			}
			break;
		case R.id.capes_3_today:
			if (state.equals(CURRENT)) {
				state = TODAY;
				today.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
				today.setTextColor(getActivity().getResources().getColor(R.color.color_white));
				initRightRequest();
			}
			break;

		default:
			break;
		}
	}

	private void resetAll() {
		current.setTextColor(getActivity().getResources().getColor(R.color.color_lightblue));
		today.setTextColor(getActivity().getResources().getColor(R.color.color_lightblue));
		current.setBackgroundResource(R.drawable.corner_rectangle_white_bg);
		today.setBackgroundResource(R.drawable.corner_rectangle_white_bg);
	}

}
