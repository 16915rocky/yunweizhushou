package com.chinamobile.yunweizhushou.ui.autoCheck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.autoCheck.fragments.AutoCheckMonthFragment;
import com.chinamobile.yunweizhushou.ui.autoCheck.fragments.AutoCheckTodayFragment;
import com.chinamobile.yunweizhushou.ui.autoCheck.fragments.AutoCheckUnsolveFragment;
import com.chinamobile.yunweizhushou.ui.autoCheck.fragments.ProcessInspectionFragment2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoCheckActivity extends BaseActivity implements OnClickListener {

	private TextView today, month, unsolve, follow;
	private View bottomBar;
	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_autocheck_manage);
		initView();
		initViewPager();
		initRequest();
		initEvent();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1038");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
	
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
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
	private void initViewPager() {

		fragmentList = new ArrayList<>();
		fragmentList.add(new AutoCheckTodayFragment());
		fragmentList.add(new AutoCheckMonthFragment());
		fragmentList.add(new AutoCheckUnsolveFragment());
		ProcessInspectionFragment2 processInspectionFragment2 = new ProcessInspectionFragment2();
		Bundle bundle = new Bundle();
		bundle.putString("DTLURL","Routine?action=dtl&id=110");
		processInspectionFragment2.setArguments(bundle);
		fragmentList.add(processInspectionFragment2);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragmentList.get(arg0);
			}
		};

		viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
		viewPager.setAdapter(mAdapter);
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setMiddleText("自动化巡检");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		today.setOnClickListener(this);
		month.setOnClickListener(this);
		unsolve.setOnClickListener(this);
		follow.setOnClickListener(this);

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetAll();
				if (arg0 == 0) {
					today.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 1) {
					month.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 2) {
					unsolve.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 3) {
					follow.setTextColor(getResources().getColor(R.color.color_lightblue));
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int offsetPx) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bottomBar.getLayoutParams();
				lp.leftMargin = (int) ((position + positionOffset) * bottomBarWidth);
				bottomBar.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initView() {
		today = (TextView) findViewById(R.id.autocheck_item_today);
		month = (TextView) findViewById(R.id.autocheck_item_month);
		unsolve = (TextView) findViewById(R.id.autocheck_item_unsolve);
		follow = (TextView) findViewById(R.id.autocheck_item_follow);
		viewPager = (ViewPager) findViewById(R.id.autocheck_viewpager);
		bottomBar = findViewById(R.id.autocheck_bottom_bar);

		today.setTextColor(getResources().getColor(R.color.color_lightblue));

img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.autocheck_item_today:
			viewPager.setCurrentItem(0);
			today.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.autocheck_item_month:
			viewPager.setCurrentItem(1);
			month.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.autocheck_item_unsolve:
			viewPager.setCurrentItem(2);
			unsolve.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.autocheck_item_follow:
			viewPager.setCurrentItem(3);
			follow.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		default:
			break;
		}
	}

	private void resetAll() {
		today.setTextColor(getResources().getColor(R.color.color_black));
		month.setTextColor(getResources().getColor(R.color.color_black));
		unsolve.setTextColor(getResources().getColor(R.color.color_black));
		follow.setTextColor(getResources().getColor(R.color.color_black));
	}
}
