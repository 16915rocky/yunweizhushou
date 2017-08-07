package com.chinamobile.yunweizhushou.logZone;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.logZone.adapter.LogZoneAdapter;
import com.chinamobile.yunweizhushou.logZone.bean.LogZoneBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
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

public class LogZoneActivity extends BaseActivity implements OnClickListener {
	private ListView mListview;
	private List<LogZoneBean> mList;
	private LogZoneAdapter mAdapter;
	private TextView searchBtn,title_right_text;
	private EditText searchEdittext;
	private String cond = "";
	private MyRefreshLayout mRefreshLayout;
	// public static final int BACKLOG = 1043;
	private String name = "";
	private String thirdName = "";
	private TextView bottomChooce;
	private PopupWindow poWindow;
	private View popView;
	private String time = "1";
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	public LogZoneActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_zone);
		initView();		
		initRequest();
		initEvent();

	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", "1046");
		map.put("cond", cond);
		startTask(HttpRequestEnum.enum_log_zone, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1006");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}

		if (responseBean == null) {
			Utils.ShowErrorMsg(LogZoneActivity.this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {

		case enum_log_zone:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<LogZoneBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new LogZoneAdapter(LogZoneActivity.this, mList, R.layout.activity_log_zone_item);
				// mAdapter.setTag(BACKLOG);
				mListview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(LogZoneActivity.this, responseBean.getMSG());
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
	}

	private void initEvent() {
		title_right_text.setOnClickListener(this);
		getTitleBar().setMiddleText("日志专区");
		// bottomChooce.setOnClickListener(this);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
				intent.putExtra("fkId", "1088");
				intent.putExtra("extraKey", "pament_business");
				intent.putExtra("extraValue", mList.get(position).getIndex_id());
				intent.putExtra("contentName", mList.get(position).getName());
				intent.setClass(LogZoneActivity.this, LogZoneTable3Activity.class);
				startActivity(intent);

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

	private void initView() {
		title_right_text=(TextView) findViewById(R.id.title_right_text);
		title_right_text.setVisibility(View.VISIBLE);
		title_right_text.setText("日志\n拓扑图");
		mListview = (ListView) findViewById(R.id.rule_listview);
		searchEdittext = (EditText) findViewById(R.id.rule_search_edittext);
		searchBtn = (TextView) findViewById(R.id.rule_search_btn);
		mRefreshLayout = (MyRefreshLayout) findViewById(R.id.rule_swipe_refresh_layout);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
		

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
//		case R.id.ability_bottom_chooce:
//			initPopupWindow();
//			break;
		case R.id.rule_search_btn:
			if (TextUtils.isEmpty(searchEdittext.getText().toString())) {
				Utils.ShowErrorMsg(LogZoneActivity.this, "请输入搜索内容");
			} else {
				cond = searchEdittext.getText().toString();
				if (mAdapter != null) {
					mAdapter.clearData();
				}
				initRequest();
			}
			break;

		case R.id.ruleTitle1:
//		case R.id.ruleTitle2:
		case R.id.ruleTitle3:
		case R.id.ruleTitle4:

			break;
		case R.id.title_right_text:
			Intent intent=new Intent();
			intent.setClass(this, LogZoneGplotActivity.class);
			startActivity(intent);
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
