package com.chinamobile.yunweizhushou.ui.backlogZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.backlogZone.adapter.BacklogZongListAdapter;
import com.chinamobile.yunweizhushou.ui.backlogZone.bean.BacklogZongBean;
import com.chinamobile.yunweizhushou.ui.backlogZone.bean.BacklogZongListBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.DisplayUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class BacklogZoneActivity extends BaseActivity implements OnClickListener {

	private LinearLayout leftChildLayout;
	private LinearLayout rightChildLayout;
	private TextView tabLeft;
	private TextView tabRight;
	private ListView mListView;
	private ImageView tableftImage;
	private ImageView tabRightImage;
	// popupWindow
	private PopupWindow popupWindow;
	private LinearLayout popupLeftMenu;
	private LinearLayout popupRightMenu;
	private int selectId;
	// 搜索
	private RelativeLayout demandSearch;
	private RelativeLayout searchChildLayout;
	private TextView searchBtn;
	private TextView searchShow;
	private EditText searchContent;
	private boolean searchState = false;

	private BacklogZongListAdapter adapter;
	private LinearLayout listViewHeadLayout;
	private boolean tabState = false;// left or right? default is left

	private BacklogZongBean bean;
	private BacklogZongListBean listBean;

	private String cityCode = "";
	private String city = "";
	private String busiCode = "";
	private String busi = "";
	List<BacklogZongBean.ItemsListBean.ItemsListChildBean> childBeans = null;
	private int position3;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	private MyRefreshLayout mRefreshLayout;
	HashMap<String, String> map = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backlog_zong);
		initView();
		setEvent();
		initRequest();
	}

	private void initView() {
		getTitleBar().setMiddleText("积压专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (searchState) {
					showNormalState();
				} else {
					finish();
				}
			}
		});
		leftChildLayout = (LinearLayout) findViewById(R.id.tabLeftChildLayout);
		rightChildLayout = (LinearLayout) findViewById(R.id.tabRightChildLayout);
		tabLeft = (TextView) findViewById(R.id.leftTab);
		tabRight = (TextView) findViewById(R.id.rightTab);
		tableftImage = (ImageView) findViewById(R.id.tableftImage);
		tabRightImage = (ImageView) findViewById(R.id.tabRightImage);

		demandSearch = (RelativeLayout) findViewById(R.id.searchLayout);
		searchContent = (EditText) findViewById(R.id.searchContent);
		searchContent.addTextChangedListener(mTextWatcher);
		searchChildLayout = (RelativeLayout) findViewById(R.id.searchChildLayout);
		searchBtn = (TextView) findViewById(R.id.searchBtn);
		searchShow = (TextView) findViewById(R.id.searchShow);

		LayoutParams params = (LayoutParams) demandSearch.getLayoutParams();
		params.width = ConstantValueUtil.WINDOW_WIDTH - ConstantValueUtil.WINDOW_WIDTH / 10;
		demandSearch.setLayoutParams(params);

		mListView = (ListView) findViewById(R.id.backlogListView);
		listViewHeadLayout = (LinearLayout) findViewById(R.id.listViewHeadLayout);
		mRefreshLayout = (MyRefreshLayout) findViewById(R.id.middleRefreshLayout);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void setEvent() {
		leftChildLayout.setOnClickListener(this);
		rightChildLayout.setOnClickListener(this);

		demandSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LayoutParams params = (LayoutParams) demandSearch.getLayoutParams();
				params.width = 0;
				demandSearch.setLayoutParams(params);

				RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) searchChildLayout.getLayoutParams();
				params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				params2.width = RelativeLayout.LayoutParams.MATCH_PARENT;
				params2.setMargins(10, 0, 0, 0);
				searchChildLayout.setLayoutParams(params2);

				searchContent.setCursorVisible(true);
				searchBtn.setVisibility(View.VISIBLE);
				searchBtn.setOnClickListener(new SearchClickListener());
				searchShow.setVisibility(View.GONE);
				searchContent.setVisibility(View.VISIBLE);

				RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) searchContent
						.getLayoutParams();
				params3.width = DisplayUtil.px2dip(BacklogZoneActivity.this, ConstantValueUtil.WINDOW_WIDTH / 3 * 2)
						+ 300;
				searchContent.setLayoutParams(params3);

				BacklogZongListAdapter adapter = new BacklogZongListAdapter(BacklogZoneActivity.this,
						new BacklogZongListBean());
				mListView.setAdapter(adapter);

				searchState = true;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				BacklogZongListBean.ItemsList item = listBean.getItemsList().get(position);

				Intent intent = new Intent(BacklogZoneActivity.this, GraphListActivity.class);
				intent.putExtra("fkId", "1024");
				intent.putExtra("name", item.getName());
				intent.putExtra("userId", item.getCity());
				startActivity(intent);
			}
		});

		mListView.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (!map.isEmpty() || map.size() != 0) {
					sendTask(map);
					popupWindow.dismiss();
				} else {
					initRequest();
				}

			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getParam");
		map.put("codeType", "jiya_module");
		startTask(HttpRequestEnum.enum_backlog_zong_menu_list, ConstantValueUtil.URL + "SpecialTreatment?", map);

		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getSpecial");
		map2.put("id", "1015");
		// map2.put("busi", "");
		// map2.put("city", "");
		// map2.put("center", "");
		// map2.put("channel", "");
		// map2.put("busi_code", "");
		// map2.put("city_code", "");
		// map2.put("center_code", "");
		// map2.put("channel_code", "");
		map2.put("cond", "");
		startTask(HttpRequestEnum.enum_backlog_zong_list, ConstantValueUtil.URL + "SpecialTreatment?", map2, true);
		
		HashMap map3=new HashMap<String,String>();
		map3.put("action", "getChargerOfModule");
		map3.put("id", "1007");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map3, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(BacklogZoneActivity.this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_backlog_zong_list:
				Type t = new TypeToken<BacklogZongListBean>() {
				}.getType();

				listBean = new Gson().fromJson(responseBean.getDATA(), t);
				if (listBean == null || listBean.getItemsList() == null) {
					return;
				}
				adapter = new BacklogZongListAdapter(BacklogZoneActivity.this, listBean);
				mListView.setAdapter(adapter);
				break;
			case enum_backlog_zong_menu_list:
				Type type = new TypeToken<BacklogZongBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), type);

				if (bean == null || bean.getItemsList() == null) {
					return;
				}
				int itemsSize = bean.getItemsList().size();

				if (itemsSize == 1) {
					tabLeft.setText(bean.getItemsList().get(0).getCode_name());
				} else if (itemsSize == 2) {
					tabLeft.setText(bean.getItemsList().get(0).getCode_name());
					tabRight.setText(bean.getItemsList().get(1).getCode_name());
				}
				break;
			case enum_charge_people:
				try {
					JSONObject jo=new JSONObject(responseBean.getDATA());
					String charger=jo.getString("charger");
					String phone=jo.getString("phone");
					String url=jo.getString("picture");
					tv_name.setText(charger);
					tv_phone.setText(phone);
					if (url != null && !TextUtils.isEmpty(url)) {
						MainApplication.mImageLoader.displayImage(url, img_charge_people);
					}
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		} else {
			Utils.ShowErrorMsg(BacklogZoneActivity.this, responseBean.getMSG());
		}
	}

	@Override
	public void onClick(View view) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.backlog_zong_tab_open);
		anim.setFillAfter(true);
		switch (view.getId()) {
		case R.id.tabLeftChildLayout:
			tableftImage.startAnimation(anim);
			tabState = false;
			showLeftPopupWindow();
			break;
		case R.id.tabRightChildLayout:
			tabRightImage.startAnimation(anim);
			tabState = true;
			showRightPopupWindow();
			break;
		}
	}

	// 业务
	private void showLeftPopupWindow() {
		View myView = View.inflate(BacklogZoneActivity.this, R.layout.item_backlog_zong_popup, null);
		popupLeftMenu = (LinearLayout) myView.findViewById(R.id.backlogPopupLeftMenu);
		popupRightMenu = (LinearLayout) myView.findViewById(R.id.backlogZongFragment);

		initLeftMenu(bean.getItemsList().get(0).getItemsList(), popupLeftMenu);

		// 默认为第一个 生成二级菜单
		initRightMenu(bean.getItemsList().get(0).getItemsList().get(0).getItemsList(), popupRightMenu);

		popupWindow = new PopupWindow(myView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.showAsDropDown(leftChildLayout, 0, 10);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				Animation animation = AnimationUtils.loadAnimation(BacklogZoneActivity.this,
						R.anim.backlog_zong_tab_close);
				animation.setFillAfter(true);
				tableftImage.startAnimation(animation);
			}
		});
	}

	// 地市菜单
	private void showRightPopupWindow() {
		View myView = View.inflate(BacklogZoneActivity.this, R.layout.item_backlog_zong_popup, null);
		popupLeftMenu = (LinearLayout) myView.findViewById(R.id.backlogPopupLeftMenu);
		popupRightMenu = (LinearLayout) myView.findViewById(R.id.backlogZongFragment);
		ScrollView scrollView = (ScrollView) myView.findViewById(R.id.leftScrollView);
		scrollView.setVisibility(View.GONE);

		initRightMenu(bean.getItemsList().get(1).getItemsList(), popupRightMenu, null);

		popupWindow = new PopupWindow(myView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.showAsDropDown(leftChildLayout, 0, 10);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				Animation animation = AnimationUtils.loadAnimation(BacklogZoneActivity.this,
						R.anim.backlog_zong_tab_close);
				animation.setFillAfter(true);
				tabRightImage.startAnimation(animation);
			}
		});
	}

	/**
	 * 生成一级菜单
	 * 
	 * @param subList
	 *
	 */
	private void initLeftMenu(List<BacklogZongBean.ItemsListBean.ItemsListChildBean> subList, LinearLayout leftMenu) {
		leftMenu.removeAllViews();
		for (int i = 0; i < subList.size(); i++) {
			TextView view = (TextView) View.inflate(BacklogZoneActivity.this, R.layout.fragment_backlog_zong_textview,
					null);
			view.setText(subList.get(i).getCode_name());
			view.setOnClickListener(new OnMenuClickListener(i));
			if (i == 0) {
				view.setBackgroundColor(Color.WHITE);
				selectId = 0;
			}
			leftMenu.addView(view);
		}
	}

	/**
	 * 业务生成二级菜单
	 * 
	 * @param subList
	 * @param rightMenu
	 */
	private void initRightMenu(List<BacklogZongBean.ItemsListBean.ItemsListChildBean.ItemsListChildSubBean> subList, LinearLayout rightMenu) {
		rightMenu.removeAllViews();
		for (int i = 0; i < subList.size(); i++) {
			View subView = View.inflate(BacklogZoneActivity.this, R.layout.activity_backlog_zong_menu_right, null);
			TextView textView = (TextView) subView.findViewById(R.id.backlogZongRightMenu);
			textView.setText(subList.get(i).getCode_name());
			textView.setOnClickListener(new OnSubMenuClickListener(i));
			textView.setBackgroundColor(Color.WHITE);
			if (i == subList.size() - 1) {
				View v = subView.findViewById(R.id.rightMenuLine);
				v.setVisibility(View.GONE);
			}
			rightMenu.addView(subView);
		}
	}

	/**
	 * 地市生成菜单
	 * 
	 * @param subList
	 * @param rightMenu
	 */
	private void initRightMenu(List<BacklogZongBean.ItemsListBean.ItemsListChildBean> subList, LinearLayout rightMenu, String a) {
		rightMenu.removeAllViews();
		for (int i = 0; i < subList.size(); i++) {
			View subView = View.inflate(BacklogZoneActivity.this, R.layout.activity_backlog_zong_menu_right, null);
			TextView textView = (TextView) subView.findViewById(R.id.backlogZongRightMenu);
			textView.setText(subList.get(i).getCode_name());
			textView.setOnClickListener(new OnSubMenuClickListener(i));
			textView.setBackgroundColor(Color.WHITE);
			if (i == subList.size() - 1) {
				View v = subView.findViewById(R.id.rightMenuLine);
				v.setVisibility(View.GONE);
			}
			rightMenu.addView(subView);
		}
	}

	private void resetMenuBG(int position) {
		int count = popupLeftMenu.getChildCount();
		for (int i = 0; i < count; i++) {
			popupLeftMenu.getChildAt(i).setBackgroundColor(position == i ? Color.WHITE : Color.parseColor("#10545454"));
		}

	}

	/**
	 * 一级菜单点击事件
	 */
	private class OnMenuClickListener implements OnClickListener {

		private int position;

		public OnMenuClickListener(int p) {
			this.position = p;
		}

		@Override
		public void onClick(View view) {
			if (position == selectId) {
				return;
			}
			resetMenuBG(position);
			selectId = position;

			initRightMenu(bean.getItemsList().get(0).getItemsList().get(position).getItemsList(), popupRightMenu);
		}
	}

	/**
	 * 菜单点击事件(左右Tab共同点击事件)
	 */
	private class OnSubMenuClickListener implements OnClickListener {

		private int position2;

		public OnSubMenuClickListener(int position) {
			position3 = position;
			this.position2 = position;

		}

		// 点击业务和地市菜单选择项
		@Override
		public void onClick(View view) {

			if (tabState) {// 为true代表右边点击
				childBeans = bean.getItemsList().get(1).getItemsList();
				tabRight.setText(childBeans.get(position2).getCode_name());

				map.put("action", "getSpecial");
				map.put("id", "1015");
				map.put("city", childBeans.get(position2).getCode_value());
				map.put("city_code", childBeans.get(position2).getCode_desc());// desc
				map.put("busi", busi);
				map.put("busi_code", busiCode);
				map.put("cond", "");

				city = childBeans.get(position2).getCode_value();
				cityCode = childBeans.get(position2).getCode_desc();
			} else {
				childBeans = bean.getItemsList().get(0).getItemsList();
				tabLeft.setText(childBeans.get(selectId).getItemsList().get(position2).getCode_name());

				map.put("action", "getSpecial");
				map.put("id", "1015");
				map.put("busi", childBeans.get(selectId).getItemsList().get(position2).getCode_value());
				map.put("busi_code", childBeans.get(selectId).getItemsList().get(position2).getCode_desc());// desc
				map.put("city", city);
				map.put("city_code", cityCode);
				map.put("cond", "");

				busi = childBeans.get(selectId).getItemsList().get(position2).getCode_value();
				busiCode = childBeans.get(selectId).getItemsList().get(position2).getCode_desc();
			}
			sendTask(map);
			popupWindow.dismiss();
		}
	}

	// tab/search 请求
	private void sendTask(HashMap<String, String> map) {
		startTask(HttpRequestEnum.enum_backlog_zong_list, ConstantValueUtil.URL + "SpecialTreatment?", map, true);

	}

	/**
	 * 搜索框监听
	 */
	private TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String searchText = s.toString();
			if ("".equals(searchText)) {
				searchBtn.setText("取消");
			} else {
				searchBtn.setText("搜索");
			}
		}
	};

	/**
	 * 搜索按钮点击事件
	 */
	class SearchClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String searchBtnText = (String) searchBtn.getText();
			// 关闭系统输入框
			InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManage.hideSoftInputFromWindow(BacklogZoneActivity.this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			if ("取消".equals(searchBtnText)) {
				showNormalState();
			} else if ("搜索".equals(searchBtnText)) {

				HashMap<String, String> map = new HashMap<>();
				map.put("action", "getSpecial");
				map.put("id", "1015");
				map.put("cond", searchContent.getText().toString());
				sendTask(map);
				searchState = true;
			}
		}
	}

	/**
	 * 搜索框改为初始状态
	 */
	@SuppressLint("NewApi")
	private void showNormalState() {
		LayoutParams params = (LayoutParams) demandSearch.getLayoutParams();
		params.width = ConstantValueUtil.WINDOW_WIDTH - ConstantValueUtil.WINDOW_WIDTH / 10;
		demandSearch.setLayoutParams(params);
		searchBtn.setVisibility(View.GONE);

		RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) searchChildLayout.getLayoutParams();
		params2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		params2.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params2.addRule(RelativeLayout.CENTER_IN_PARENT);
		params2.setMargins(10, 0, 0, 0);
		searchChildLayout.setLayoutParams(params2);

		mListView.setAdapter(adapter);// 重新设置回进入搜索状态前的列表

		searchShow.setVisibility(View.VISIBLE);
		searchContent.setVisibility(View.GONE);
		searchContent.setCursorVisible(false);
		searchState = false;

		listViewHeadLayout.setVisibility(View.VISIBLE);
	}
}
