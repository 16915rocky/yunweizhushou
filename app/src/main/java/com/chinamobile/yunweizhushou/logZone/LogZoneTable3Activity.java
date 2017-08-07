package com.chinamobile.yunweizhushou.logZone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListFragment2;
import com.chinamobile.yunweizhushou.logZone.fragments.LogZoneFragmentOfExpandable;
import com.chinamobile.yunweizhushou.logZone.fragments.LogZoneFrament2;

import java.util.ArrayList;
import java.util.List;

public class LogZoneTable3Activity extends BaseActivity implements OnClickListener {

	private TextView item1, item2, item3;

	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private String extraKey = "", extraValue = "";
	private String extraKey2 = "", extraValue2 = "";
	private String fkId;
	private String contentName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentName=getIntent().getStringExtra("contentName");		
		setContentView(R.layout.activity_logzone_table3);
		fkId = getIntent().getStringExtra("fkId");
		extraKey = getIntent().getStringExtra("extraKey");
		extraValue = getIntent().getStringExtra("extraValue");
		extraKey2 = getIntent().getStringExtra("extraKey2");
		extraValue2 = getIntent().getStringExtra("extraValue2");
	
		initView();
		resetAll();
		initViewPager();
		initEvent();
	}

	private void initViewPager() {

		fragmentList = new ArrayList<>();
		GraphListFragment2 graphListFragment2 = new GraphListFragment2();
		Bundle bundle2=new Bundle();
		bundle2.putString("fkId","1088");
		bundle2.putString("extraKey","pament_business");
		bundle2.putString("extraValue",extraValue);
		graphListFragment2.setArguments(bundle2);
		fragmentList.add(graphListFragment2);
		LogZoneFrament2 logZoneFrament2 = new LogZoneFrament2();
		Bundle bundle = new Bundle();
		bundle.putString("index_id",extraValue);
		logZoneFrament2.setArguments(bundle);
		fragmentList.add(logZoneFrament2);
		LogZoneFragmentOfExpandable  logZoneFragmentOfExpandable=new LogZoneFragmentOfExpandable();
		logZoneFragmentOfExpandable.setArguments(bundle);
		fragmentList.add(logZoneFragmentOfExpandable);

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
		getTitleBar().setMiddleText(contentName);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetAll();
				if (arg0 == 0) {
					item1.setTextColor(getResources().getColor(R.color.color_lightblue));
					item2.setTextColor(getResources().getColor(R.color.color_black));
					item3.setTextColor(getResources().getColor(R.color.color_black));
				} else if (arg0 == 1) {
					item1.setTextColor(getResources().getColor(R.color.color_black));
					item2.setTextColor(getResources().getColor(R.color.color_lightblue));
					item3.setTextColor(getResources().getColor(R.color.color_black));
				} else if (arg0 == 2) {
					item1.setTextColor(getResources().getColor(R.color.color_black));
					item2.setTextColor(getResources().getColor(R.color.color_black));
					item3.setTextColor(getResources().getColor(R.color.color_lightblue));
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int offsetPx) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initView() {
		item1 = (TextView) findViewById(R.id.rule_item1);
		item2 = (TextView) findViewById(R.id.rule_item2);
		item3 = (TextView) findViewById(R.id.rule_item3);
		viewPager = (ViewPager) findViewById(R.id.rule_viewpager);

		/*
		 * item1.setTextColor(getResources().getColor(R.color.color_lightblue));
		 * item2.setTextColor(getResources().getColor(R.color.color_black));
		 * item3.setTextColor(getResources().getColor(R.color.color_black));
		 */
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.rule_item1:
			viewPager.setCurrentItem(0);
			item1.setTextColor(getResources().getColor(R.color.color_lightblue));
			item2.setTextColor(getResources().getColor(R.color.color_black));
			item3.setTextColor(getResources().getColor(R.color.color_black));
			break;
		case R.id.rule_item2:
			viewPager.setCurrentItem(1);
			item1.setTextColor(getResources().getColor(R.color.color_black));
			item2.setTextColor(getResources().getColor(R.color.color_lightblue));
			item3.setTextColor(getResources().getColor(R.color.color_black));
			break;
		case R.id.rule_item3:
			viewPager.setCurrentItem(2);
			item1.setTextColor(getResources().getColor(R.color.color_black));
			item2.setTextColor(getResources().getColor(R.color.color_black));
			item3.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		default:
			break;
		}
	}

	private void resetAll() {
		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
		item2.setTextColor(getResources().getColor(R.color.color_black));
		item3.setTextColor(getResources().getColor(R.color.color_black));
	}
}
