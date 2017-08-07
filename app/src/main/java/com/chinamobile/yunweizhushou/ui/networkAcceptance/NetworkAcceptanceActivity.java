package com.chinamobile.yunweizhushou.ui.networkAcceptance;

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
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.networkAcceptance.fragments.NetworkAcceptanceFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkAcceptanceActivity extends BaseActivity implements OnClickListener {

	private ViewPager mViewPager;
	private List<Fragment> fragments = new ArrayList<>();
	private FragmentPagerAdapter mAdapter;
	private TextView tab1, tab2;
	private View tab1BottomView, tab2BottomView;
	private List<TextView> tabs;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_acceptance);
		initView();
		setEvent();
		initRequest();
		initFragment();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1039");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
	
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
	private void initFragment() {
		fragments = new ArrayList<>();
		for (int i = 0; i < tabs.size(); i++) {
			NetworkAcceptanceFragment fragment = new NetworkAcceptanceFragment();
			Bundle bundle = new Bundle();
			bundle.putString("id", i == 0 ? "1" : "2");
			fragment.setArguments(bundle);
			fragments.add(fragment);
		}

		mViewPager.setAdapter(viewPagerAdapter);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOffscreenPageLimit(viewPagerAdapter.getCount() - 1);// 设置缓存个数

		getTitleBar().setMiddleText("入网验收");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		tabs = new ArrayList<>();
		mViewPager = (ViewPager) findViewById(R.id.function_analysis_viewpager);
		tab1 = (TextView) findViewById(R.id.tab1);
		tab2 = (TextView) findViewById(R.id.tab2);
		tab1BottomView = findViewById(R.id.tab1BottomView);
		tab2BottomView = findViewById(R.id.tab2BottomView);
		tabs.add(tab1);
		tabs.add(tab2);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void setEvent() {
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
	}

	private FragmentPagerAdapter viewPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}
	};

	private class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			changeSelectedTab(position);
			changeTabTextBG(position);
		}

		@Override
		public void onPageScrolled(int position, float offset, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	private void changeTabTextBG(int position) {
		if (position == 0) {
			tab1.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab2.setTextColor(Color.BLACK);
			tab1BottomView.setVisibility(View.VISIBLE);
			tab2BottomView.setVisibility(View.GONE);
		} else {
			tab1.setTextColor(Color.BLACK);
			tab2.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab1BottomView.setVisibility(View.GONE);
			tab2BottomView.setVisibility(View.VISIBLE);
		}
	}

	private void changeSelectedTab(int position) {
		tab1.setSelected(position == 0 ? true : false);
		tab2.setSelected(position == 1 ? true : false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab1:
			changeTabTextBG(0);
			changeSelectedTab(0);
			mViewPager.setCurrentItem(0);
			break;
		case R.id.tab2:
			changeTabTextBG(1);
			changeSelectedTab(1);
			mViewPager.setCurrentItem(1);
			break;
		}
	}
}
