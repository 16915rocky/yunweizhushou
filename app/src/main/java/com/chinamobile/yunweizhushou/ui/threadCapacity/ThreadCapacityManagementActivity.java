package com.chinamobile.yunweizhushou.ui.threadCapacity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.threadCapacity.fragments.OtherThreadCapacityFragment;
import com.chinamobile.yunweizhushou.ui.threadCapacity.fragments.ThreadCapacityManagementFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThreadCapacityManagementActivity extends BaseActivity implements OnClickListener {

	private ViewPager mViewPager;
	private View bottomBar;
	private List<Fragment> fragmentList;
	private TextView tabLeft, tabRight;

private TextView tv_phone,tv_name;
private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_capacity_management);
		initView();
		setEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1014");
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

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.threadViewPager);
		tabLeft = (TextView) findViewById(R.id.tab1);
		tabRight = (TextView) findViewById(R.id.tab2);
		bottomBar = findViewById(R.id.bottomBar);

		getTitleBar().setMiddleText("线程容量管理");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void setEvent() {
		fragmentList = new ArrayList<>();
		fragmentList.add(new ThreadCapacityManagementFragment());
		fragmentList.add(new OtherThreadCapacityFragment());
		tabLeft.setOnClickListener(this);
		tabRight.setOnClickListener(this);

		LayoutParams params = (LayoutParams) bottomBar.getLayoutParams();
		params.width = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		bottomBar.setLayoutParams(params);

		mViewPager.setAdapter(viewPagerAdapter);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setCurrentItem(0);
		changeTextColor(0);
	}

	private FragmentPagerAdapter viewPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}
	};

	private void changeTextColor(int position) {
		switch (position) {
		case 0:
			tabLeft.setTextColor(getResources().getColor(R.color.use_ranking_tab_color_s));
			tabRight.setTextColor(Color.BLACK);
			break;
		case 1:
			tabLeft.setTextColor(Color.BLACK);
			tabRight.setTextColor(getResources().getColor(R.color.use_ranking_tab_color_s));
			break;
		}
	}

	private class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			changeTextColor(arg0);
		}

		@Override
		public void onPageScrolled(int position, float offset, int arg2) {
			LayoutParams params = (LayoutParams) bottomBar.getLayoutParams();
			params.leftMargin = (int) ((position + offset) * params.width);
			bottomBar.setLayoutParams(params);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab1:
			mViewPager.setCurrentItem(0);
			changeTextColor(0);
			break;
		case R.id.tab2:
			mViewPager.setCurrentItem(1);
			changeTextColor(1);
			break;
		}
	}
}
