package com.chinamobile.yunweizhushou.ui.AccountCenter.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionBean;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.capes.RechargeFunctionGraphDetailActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntegrationServiceFragment extends BaseFragment implements View.OnClickListener {

	private LinearLayout tabLeftLayout;
	private LinearLayout tabRightLayout;
	private LinearLayout tabLeftChildLayout, tabRightChildLayout;
	private TextView leftTabText, rightTabText;
	private TextView promptTextView;// 未选中时 灰色提示栏文本
	private ImageView leftImage, rightImage;
	private ListView mListView;
	private TextView queryBtn;// 查询按钮
	private PopupWindow popupWindow;

	private List<RechargeFunctionBean.ItemsChildList> cityList;
	private List<RechargeFunctionBean.ItemsChildList> pamentList;
	private RechargeFunctionBean bean;
	private RechargeFunctionListAdapter adapter;

	private ViewGroup.MarginLayoutParams lp;
	private int citySelectedId;
	private int pamentSelectedId;

	private String fkId;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		fkId=arguments.getString("fkId");
		View view = inflater.inflate(R.layout.fragment_business_detection, container, false);
		initView(view);
		initRequest();
		setEvent();
		return view;
	}

	private void initView(View rootView) {
		tabLeftLayout = (LinearLayout) rootView.findViewById(R.id.tabLeftLayout);
		tabRightLayout = (LinearLayout) rootView.findViewById(R.id.tabRightLayout);
		tabLeftChildLayout = (LinearLayout) rootView.findViewById(R.id.tabLeftChildLayout);
		tabRightChildLayout = (LinearLayout) rootView.findViewById(R.id.tabRightChildLayout);
		leftTabText = (TextView) rootView.findViewById(R.id.tabLeft);
		rightTabText = (TextView) rootView.findViewById(R.id.tabRight);
		leftImage = (ImageView) rootView.findViewById(R.id.leftImage);
		rightImage = (ImageView) rootView.findViewById(R.id.rightImage);
		mListView = (ListView) rootView.findViewById(R.id.businessListView);
		queryBtn = (TextView) rootView.findViewById(R.id.queryBtn);
		promptTextView = (TextView) rootView.findViewById(R.id.promptTextView);
	}

	private void setEvent() {
		tabLeftLayout.setOnClickListener(this);
		tabRightLayout.setOnClickListener(this);
		queryBtn.setOnClickListener(this);
		lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(5, 3, 5, 3);// 设置边距
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveParam");
		map.put("upId", fkId);
		startTask(HttpRequestEnum.enum_business_detection, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			return;
		}

		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_business_detection:
				if (pamentList == null) {
					pamentList = new ArrayList<>();
				} else {
					pamentList.clear();
				}
				if (cityList == null) {
					cityList = new ArrayList<>();
				} else {
					cityList.clear();
				}

				Type type = new TypeToken<RechargeFunctionBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), type);
				List<RechargeFunctionBean.ItemsList> list = bean.getItemsList();

				tabRightChildLayout.setVisibility(View.GONE);
				tabLeftChildLayout.setVisibility(View.GONE);

				for (int i = 0; i < list.size(); i++) {
					String item = list.get(i).getItem();
					List<RechargeFunctionBean.ItemsChildList> itemList = (list.get(i).getItemsList());
					if (i == 0) {
						pamentList.addAll(itemList);
						tabLeftChildLayout.setVisibility(View.VISIBLE);
						rightTabText.setText(list.get(i).getItemValue());
					} else if (i == 1) {
						cityList.addAll(itemList);
						tabRightChildLayout.setVisibility(View.VISIBLE);
						if (list.get(i).getItemValue().equals("按地市")) {
							leftTabText.setText("按业务");
						} else {
							leftTabText.setText(list.get(i).getItemValue());
						}
					}
				}
				break;
			case enum_business_detection_list:
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				RechargeFunctionGraphBean beans = new Gson().fromJson(responseBean.getDATA(), t);
				mListView.setVisibility(View.VISIBLE);
				adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				adapter.setFloatState(true);
				mListView.setAdapter(adapter);
				mListView.setOnItemClickListener(new GraphListItemOnClickListener(beans.getItemsList()));
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	class GraphListItemOnClickListener implements OnItemClickListener {
		private List<RechargeFunctionGraphBean.ItemsList> data;

		public GraphListItemOnClickListener(List<RechargeFunctionGraphBean.ItemsList> data) {
			this.data = data;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			int listViewMaxPosition = mListView.getHeaderViewsCount() + mListView.getCount()
					- mListView.getFooterViewsCount() - 1;
			if (position <= listViewMaxPosition) {
				RechargeFunctionGraphBean.ItemsList item = data.get(position);
				Intent intent = new Intent(getActivity(), RechargeFunctionGraphDetailActivity.class);
				intent.putExtra("fkId", fkId);
				intent.putExtra("waveId", item.getWaveId());
				intent.putExtra("time", item.getTIME());

				String type = cityList.get(citySelectedId).getType();
				String value = cityList.get(citySelectedId).getValue();
				intent.putExtra("cityType", type);// region_code 000
				intent.putExtra("cityValue", value);

				if (pamentList.isEmpty()) {

				} else {
					type = pamentList.get(pamentSelectedId).getType();
					value = pamentList.get(pamentSelectedId).getValue();
					intent.putExtra("pamentType", type);// pament_business 000
					intent.putExtra("pamentValue", value);
				}
				intent.putExtra("positionList", item);
				intent.putExtra("isFloatState", "true");
				startActivity(intent);
			} else {
				// 点击的是footerView
			}
		}
	}

	// popupWindow列表适配器
	class SpinnerListAdapter extends BaseAdapter {

		private List<RechargeFunctionBean.ItemsChildList> list;

		public SpinnerListAdapter(List<RechargeFunctionBean.ItemsChildList> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(getActivity());
			textView.setBackgroundColor(getResources().getColor(R.color.color_white));
			textView.setText(list.get(position).getName());
			textView.setTextSize(16f);
			textView.setGravity(Gravity.CENTER);
			textView.setPadding(10, 10, 10, 10);
			return textView;
		}
	}

	public PopupWindow getPopupWindow() {
		return popupWindow;
	}

	private void showPopupWindow(String str) {
		View myView = View.inflate(getActivity(), R.layout.item_recharge_function_spinner, null);
		popupWindow = new PopupWindow(myView, LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT, true);
		// 设置焦点为可点击
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);

		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		// popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.4f;
		getActivity().getWindow().setAttributes(lp);

		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lParams = getActivity().getWindow().getAttributes();
				lParams.alpha = 1f;
				getActivity().getWindow().setAttributes(lParams);
			}
		});

		ListView lv = (ListView) myView.findViewById(R.id.rechargeSpinnerListView);
		if (str.equals("left")) {
			popupWindow.showAsDropDown(tabLeftLayout);
			lv.setAdapter(new SpinnerListAdapter(cityList));
		} else if (str.equals("right")) {
			popupWindow.showAsDropDown(tabRightLayout);
			lv.setAdapter(new SpinnerListAdapter(pamentList));
		}
		lv.setOnItemClickListener(new MySpinnerListViewOnClick(str));
	}

	protected class MySpinnerListViewOnClick implements OnItemClickListener {
		private String str;

		public MySpinnerListViewOnClick(String text) {
			this.str = text;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			if ("left".equals(str)) {
				leftTabText.setText(cityList.get(position).getName());
				leftTabText.setTextColor(getResources().getColor(R.color.color_lightblue));
				tabLeftChildLayout.setBackgroundResource(R.drawable.recharge_function_tab_selected_bg2);
				leftImage.setVisibility(View.GONE);
				popupWindow.dismiss();
			} else if ("right".equals(str)) {
				rightTabText.setText(pamentList.get(position).getName());
				rightTabText.setTextColor(getResources().getColor(R.color.color_lightblue));
				tabRightChildLayout.setBackgroundResource(R.drawable.recharge_function_tab_selected_bg2);
				rightImage.setVisibility(View.GONE);
				popupWindow.dismiss();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tabLeftLayout:
			if (popupWindow == null) {
				showPopupWindow("left");
				leftImage.setImageResource(R.mipmap.icon_recharge_tab_top);
			} else {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
					leftImage.setImageResource(R.mipmap.icon_recharge_tab_bottom);
					rightImage.setImageResource(R.mipmap.icon_recharge_tab_bottom);
				} else {
					leftImage.setImageResource(R.mipmap.icon_recharge_tab_top);
					showPopupWindow("left");
				}
			}
			break;
		case R.id.tabRightLayout:
			if (popupWindow == null) {
				showPopupWindow("right");
				rightImage.setImageResource(R.mipmap.icon_recharge_tab_top);
			} else {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
					leftImage.setImageResource(R.mipmap.icon_recharge_tab_bottom);
					rightImage.setImageResource(R.mipmap.icon_recharge_tab_bottom);
				} else {
					rightImage.setImageResource(R.mipmap.icon_recharge_tab_top);
					showPopupWindow("right");
				}
			}
			break;
		case R.id.queryBtn:
			if (cityList == null || pamentList == null) {
				Toast.makeText(getActivity(), "正在加载数据", Toast.LENGTH_SHORT).show();
				return;
			}

			promptTextView.setVisibility(View.GONE);
			String leftStr = leftTabText.getText().toString();
			String rightStr = rightTabText.getText().toString();

			List<RechargeFunctionBean.ItemsList> list = bean.getItemsList();
			if (list.isEmpty()) {
				return;
			}
			if (list.size() == 1) {
				if ("".equals(leftStr)) {
					return;
				}
				if (leftStr.equals(list.get(0).getItemValue())) {
					Toast.makeText(getActivity(), "查询条件不足", Toast.LENGTH_SHORT).show();
					return;
				}
			} else if (list.size() == 2) {
				if ("".equals(leftStr) || "".equals(rightStr)) {
					return;
				}
				if (leftStr.equals(list.get(0).getItemValue()) || rightStr.equals(list.get(1).getItemValue())) {
					Toast.makeText(getActivity(), "查询条不足件", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			HashMap<String, String> map = new HashMap<>();
			map.put("action", "waveGraph");
			map.put("fkId", fkId);
			map.put("time", "");

			int cityListSize = cityList.size();
			for (int i = 0; i < cityListSize; i++) {
				String str = cityList.get(i).getName();
				if ("".equals(str)) {
					continue;
				}
				RechargeFunctionBean.ItemsChildList itemsChildList = cityList.get(i);
				if (leftStr.equals(str)) {
					map.put(itemsChildList.getType(), itemsChildList.getValue());
					citySelectedId = i;
					break;
				}
			}

			if (!pamentList.isEmpty()) {
				int pamentListSize = pamentList.size();
				for (int j = 0; j < pamentListSize; j++) {
					String str = pamentList.get(j).getName();
					if ("".equals(str)) {
						continue;
					}
					RechargeFunctionBean.ItemsChildList itemsChildList = pamentList.get(j);
					if (rightStr.equals(str)) {
						map.put(itemsChildList.getType(), itemsChildList.getValue());
						pamentSelectedId = j;
						break;
					}
				}
			}
			startTask(HttpRequestEnum.enum_business_detection_list, ConstantValueUtil.URL + "BusiFluct?", map, true);
			break;
		}
	}
}
