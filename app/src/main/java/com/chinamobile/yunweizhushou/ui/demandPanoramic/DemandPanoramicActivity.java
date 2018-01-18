package com.chinamobile.yunweizhushou.ui.demandPanoramic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.adapter.DemandPanoramicAdapter;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandChildBean;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandOperateBean;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandPanoramicBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.DisplayUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.SideBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DemandPanoramicActivity extends BaseActivity implements OnClickListener {

	private View headerView;
	private RelativeLayout demandSearch;
	private RelativeLayout searchChildLayout;
	private ListView listView;
	private SideBar sideBar;
	private TextView dialog;
	private TextView searchBtn;
	private TextView searchShow;
	private TextView direction, state, duty, priority;
	private TextView popupwindow_tv1,popupwindow_tv2;

	private EditText searchContent;
	private List<DemandPanoramicBean> data;
	private DemandPanoramicAdapter adapter;
	private DemandOperateBean list;
	private TextView title_right_text;
	private List<DemandChildBean> searchData = new ArrayList<>();
	private boolean searchState = false;
	private boolean ishistory=false;
	private PopupWindow mPopupWindow;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demand_panoramic);
		initView();
		setEvent();
		initRequest();
		
	}

	private void initPopupWindow() {
		View popupView  = LayoutInflater.from(this).inflate(R.layout.activity_demandpanoramic_popupwindow, null);
		popupwindow_tv1=(TextView) popupView.findViewById(R.id.popupwindow_tv1);
		popupwindow_tv2=(TextView) popupView.findViewById(R.id.popupwindow_tv2);
		popupwindow_tv1.setOnClickListener(new OnSubMenuClickListener());
		popupwindow_tv2.setOnClickListener(new OnSubMenuClickListener());
		 mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		 mPopupWindow.setTouchable(true);
	        mPopupWindow.setOutsideTouchable(true);
	        ColorDrawable dw = new ColorDrawable(0xb0000000);
	        mPopupWindow.setBackgroundDrawable(dw);

	        mPopupWindow.getContentView().setFocusableInTouchMode(true);
	        mPopupWindow.getContentView().setFocusable(true);
	        mPopupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
	  }
	/**
	 * 菜单点击事件mPopupWindow
	 */
	private class OnSubMenuClickListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {					
			switch (v.getId()) {
			case R.id.popupwindow_tv1:
				title_right_text.setText("新版");
				ishistory=true;
				initRequest();
				mPopupWindow.dismiss();

				break;
			case R.id.popupwindow_tv2:
				title_right_text.setText("2017版");
				ishistory=false;
				initRequest();
				mPopupWindow.dismiss();
				break;
			default:
				break;
			}
		}
	}

	private void initView() {
		getTitleBar().setMiddleText("需求运营");
		demandSearch = (RelativeLayout) findViewById(R.id.demandSearch);
		searchContent = (EditText) findViewById(R.id.searchContent);
		searchContent.addTextChangedListener(mTextWatcher);
		searchChildLayout = (RelativeLayout) findViewById(R.id.searchChildLayout);
		searchBtn = (TextView) findViewById(R.id.searchBtn);
		searchShow = (TextView) findViewById(R.id.searchShow);
		listView = (ListView) findViewById(R.id.demandListView);
		headerView = View.inflate(DemandPanoramicActivity.this, R.layout.item_demand_panoramic_header, null);
		direction = (TextView) headerView.findViewById(R.id.direction);// 按专业方向
		duty = (TextView) headerView.findViewById(R.id.duty);// 按责任人
		priority = (TextView) headerView.findViewById(R.id.priority);// 按优先级
		state = (TextView) headerView.findViewById(R.id.state);// 按状态

		sideBar = (SideBar) findViewById(R.id.side_bar);
		dialog = (TextView) findViewById(R.id.dialog);// 显示选中时的selection
		title_right_text = (TextView) findViewById(R.id.title_right_text);
		title_right_text.setVisibility(View.VISIBLE);
		title_right_text.setText("2017版");
		LayoutParams params = (LayoutParams) demandSearch.getLayoutParams();
		params.width = ConstantValueUtil.WINDOW_WIDTH - ConstantValueUtil.WINDOW_WIDTH / 10;
		demandSearch.setLayoutParams(params);

		FrameLayout.LayoutParams sideParams = (FrameLayout.LayoutParams) sideBar.getLayoutParams();
		sideParams.height = ConstantValueUtil.WINDOW_HEIGHT / 3 * 2;
		sideBar.setLayoutParams(sideParams);

		listView.addHeaderView(headerView);

		sideBar.setTextView(dialog);
		sideBar.setLetterSelectedListener(letterSelectedListener);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void setEvent() {
		direction.setOnClickListener(this);
		duty.setOnClickListener(this);
		priority.setOnClickListener(this);
		state.setOnClickListener(this);
		title_right_text.setOnClickListener(this);
		listView.setOnItemClickListener(new ListViewItemClickListener());
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (searchState) {
					showNormalState();
				} else {
					// showNormalState();
					finish();
				}
			}
		});

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
				params3.width = DisplayUtil.px2dip(DemandPanoramicActivity.this, ConstantValueUtil.WINDOW_WIDTH / 3 * 2)
						+ 300;
				searchContent.setLayoutParams(params3);

				listView.removeHeaderView(headerView);

				searchState = true;

				SearchAdapter adapter = new SearchAdapter();
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();

				// 开启系统输入框
				InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(
						Context.INPUT_METHOD_SERVICE);
				inputMethodManage.showSoftInputFromInputMethod(
						DemandPanoramicActivity.this.getCurrentFocus().getWindowToken(),
						InputMethodManager.SHOW_FORCED);
			}
		});
		
	}

	private void showNormalState() {
		LayoutParams params = (LayoutParams) demandSearch.getLayoutParams();
		params.width = ConstantValueUtil.WINDOW_WIDTH - ConstantValueUtil.WINDOW_WIDTH / 10;
		demandSearch.setLayoutParams(params);
		searchBtn.setVisibility(View.GONE);
		sideBar.setVisibility(View.GONE);

		RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) searchChildLayout.getLayoutParams();
		params2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		params2.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params2.addRule(RelativeLayout.CENTER_IN_PARENT);
		params2.setMargins(10, 0, 0, 0);
		searchChildLayout.setLayoutParams(params2);

		listView.addHeaderView(headerView);

		adapter = new DemandPanoramicAdapter(DemandPanoramicActivity.this, data);
		listView.setAdapter(adapter);
		sideBar.setVisibility(View.VISIBLE);

		searchShow.setVisibility(View.VISIBLE);
		searchContent.setVisibility(View.GONE);
		searchContent.setCursorVisible(false);
		searchState = false;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		if(ishistory){		
			map.put("action", "getNewDemandList");
			map.put("state", "basesummary");
		}else{
			map.put("action", "newfindAll");
			map.put("state", "title");
		}
		startTask(HttpRequestEnum.enum_demand_panoramic, ConstantValueUtil.URL + "Demand?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1040");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		// TODO Auto-generated method stub
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_demand_panoramic:
				data = new ArrayList<>();
				Type type = new TypeToken<DemandOperateBean>() {
				}.getType();
				list = new Gson().fromJson(responseBean.getDATA(), type);
				List<DemandOperateBean.ItemsList> itemsLists = list.getItemsList();
				List<DemandPanoramicBean> dataList = new ArrayList<>();

				for (int i = 0; i < itemsLists.size(); i++) {
					List<DemandOperateBean.ItemsListChild> itemChild = itemsLists.get(i).getItemsList();
					for (int j = 0; j < itemChild.size(); j++) {
						dataList.add(new DemandPanoramicBean(itemChild.get(j), itemsLists.get(i).getItem()));
					}
				}

				data.addAll(dataList);
				adapter = new DemandPanoramicAdapter(this, dataList);
				if(ishistory){
					direction.setText("按SR专业方向");
					duty.setText("按SR状态");
					priority.setText("按BR需求管理员");
					state.setText("按SR优先级");
				}else{
					direction.setText("按专业方向");
					duty.setText("按责任人");
					priority.setText("按优先级");
					state.setText("按状态");
				}
				listView.setAdapter(adapter);

				break;
			case enum_demand_search:
				searchData.clear();
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONArray jsonArray = obj.getJSONArray("itemsList");

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jObject = jsonArray.getJSONObject(i);
						DemandChildBean demandChildBean = new DemandChildBean();
						demandChildBean.setId(jObject.getString("id"));
						demandChildBean.setTitle(jObject.getString("param"));
						searchData.add(demandChildBean);
					}

					SearchAdapter adapter = new SearchAdapter();
					listView.setAdapter(adapter);
					// listView.setOnItemClickListener(new
					// ListViewItemClickListener());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
			default:
				break;
			}
		} else {
			Toast.makeText(DemandPanoramicActivity.this, responseBean.getMSG(), Toast.LENGTH_SHORT).show();
		}
	}

	private SideBar.OnLetterSelectedListener letterSelectedListener = new SideBar.OnLetterSelectedListener() {

		@Override
		public void onLetterSelected(String s) {
			int position = adapter.getPositionBySelection(s.charAt(0));
			// headerView为0 list光标定位的title加一位
			if (position == -1) {

			} else {
				listView.setSelection(position + 1);
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.direction:
			HashMap<String, String> map = new HashMap<>();
			if(ishistory){
				map.put("action", "getNewDemandList");
				map.put("state", "major_field");
			}else{
				map.put("action", "newfindAll");
				map.put("state", "pro_direction");
			}
			startTask(HttpRequestEnum.enum_demand_panoramic, ConstantValueUtil.URL + "Demand?", map);
			break;
		case R.id.duty:
			HashMap<String, String> map2 = new HashMap<>();
			if(ishistory){
				map2.put("action", "getNewDemandList");
				map2.put("state", "deadmini");
			}else{
				map2.put("action", "newfindAll");
				map2.put("state", "charge_op_name");
			}
			startTask(HttpRequestEnum.enum_demand_panoramic, ConstantValueUtil.URL + "Demand?", map2);
			break;
		case R.id.state:
			HashMap<String, String> map3 = new HashMap<>();
			if(ishistory){
				map3.put("action", "getNewDemandList");
				map3.put("state", "status");
			}else{
				map3.put("action", "newfindAll");
				map3.put("state", "state");
			}

			startTask(HttpRequestEnum.enum_demand_panoramic, ConstantValueUtil.URL + "Demand?", map3);
			break;
		case R.id.priority:
			HashMap<String, String> map4 = new HashMap<>();
			if(ishistory){
				map4.put("action", "getNewDemandList");
				map4.put("state", "priority");
			}else{
				map4.put("action", "newfindAll");
				map4.put("state", "state");
			}
			startTask(HttpRequestEnum.enum_demand_panoramic, ConstantValueUtil.URL + "Demand?", map4);
			break;
		case R.id.title_right_text:
			initPopupWindow();	
			break;
		default:
			break;
		}
	}

	private class ListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (searchState) {
				Intent intent = new Intent();
				if(ishistory){
					intent.putExtra("action", "getDemandBySearch");
					intent.setClass(DemandPanoramicActivity.this, DemandPanoramicDetail2Activity.class);
				}else{
					intent.putExtra("action", "newfindById");
					intent.setClass(DemandPanoramicActivity.this, DemandPanoramicDetailActivity.class);
				}
				intent.putExtra("title", searchData.get(position).getTitle());

				intent.putExtra("id", searchData.get(position).getId());
				startActivity(intent);
			} else {
				// headerView占位置0 所以点击第一个时减一
				if (position == 0) {
					// 点击headerView不做处理
				} else {
					if (data == null) {
						// 如果数据为空，重新请求
						initRequest();
						Toast.makeText(DemandPanoramicActivity.this, "正在请求数据",Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent();
						intent.putExtra("title", data.get(position - 1).getChildItem().getTitle());
						if(ishistory){
							intent.putExtra("action", "getDemandBySearch");
							intent.setClass(DemandPanoramicActivity.this, DemandPanoramicDetail2Activity.class);
							//intent.putExtra("action", "getDemandById");
						}else{
							intent.putExtra("action", "newfindById");
							intent.setClass(DemandPanoramicActivity.this, DemandPanoramicDetailActivity.class);
						}
						intent.putExtra("id", data.get(position - 1).getChildItem().getId());
						startActivity(intent);
					}
				}
			}
		}
	}

	TextWatcher mTextWatcher = new TextWatcher() {

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
			searchContent.setVisibility(View.VISIBLE);
		}
	};

	class SearchAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return searchData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(DemandPanoramicActivity.this, R.layout.item_demand_search_list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText("" + searchData.get(position).getTitle());
			return convertView;
		}

		private class ViewHolder {
			TextView textView;

			public ViewHolder(View view) {
				textView = (TextView) view.findViewById(R.id.searchText);
			}
		}
	}

	class SearchClickListener implements OnClickListener {

		public SearchClickListener() {

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String searchBtnText = (String) searchBtn.getText();
			// 关闭系统输入框
			InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManage.hideSoftInputFromWindow(DemandPanoramicActivity.this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			if ("取消".equals(searchBtnText)) {
				showNormalState();
			} else if ("搜索".equals(searchBtnText)) {
				// 请求数据
				searchState = true;
				HashMap<String, String> map = new HashMap<>();
				if(ishistory){
					map.put("action", "getDemandBySearch");
				}else{
					map.put("action", "newfind");
				}
				map.put("title", searchContent.getText().toString());
				startTask(HttpRequestEnum.enum_demand_search, ConstantValueUtil.URL + "Demand?", map, true);
			}
		}
	}
	
}
