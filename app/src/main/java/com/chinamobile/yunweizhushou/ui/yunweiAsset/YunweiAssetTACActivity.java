package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.fragments.YunweiAssetTACFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YunweiAssetTACActivity extends BaseActivity implements OnClickListener {

	private TextView item1, item2;
	private View bottomBar;
	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private TextView time1, time2, time3;
	private int dayNum = 0, monthNum = 0,date=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Calendar cal = Calendar.getInstance();
		dayNum = cal.getActualMaximum(Calendar.DATE);
		monthNum = cal.get(Calendar.MONTH) + 1;
		date=cal.get(Calendar.DATE);
		setContentView(R.layout.activity_yunwei_asset_tac);
		initView();
		initViewPager();
		initEvent();
	}

	private void initViewPager() {

		fragmentList = new ArrayList<>();
		YunweiAssetTACFragment yunweiAssetTACFragment2= new YunweiAssetTACFragment();
		Bundle bundle2 = new Bundle();
		bundle2.putString("type","2");
		yunweiAssetTACFragment2.setArguments(bundle2);
		fragmentList.add(yunweiAssetTACFragment2);

		YunweiAssetTACFragment yunweiAssetTACFragment1= new YunweiAssetTACFragment();
		Bundle bundle1 = new Bundle();
		bundle1.putString("type","1");
		yunweiAssetTACFragment1.setArguments(bundle1);
		fragmentList.add(yunweiAssetTACFragment1);

		

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

		viewPager.setOffscreenPageLimit(1);
		viewPager.setAdapter(mAdapter);
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setMiddleText("告警集中度");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		viewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetAll();
				if (arg0 == 0) {
					item1.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 1) {
					item2.setTextColor(getResources().getColor(R.color.color_lightblue));
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
		item1 = (TextView) findViewById(R.id.rule_item1);
		item2 = (TextView) findViewById(R.id.rule_item2);
		time1 = (TextView) findViewById(R.id.time1);
		time2 = (TextView) findViewById(R.id.time2);
		time3 = (TextView) findViewById(R.id.time3);
		time1.setText(monthNum + "");
		time2.setText(monthNum + "");
		time3.setText(date + "");
		viewPager = (ViewPager) findViewById(R.id.rule_viewpager);
		bottomBar = findViewById(R.id.rule_bottom_bar);
		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.rule_item1:
			viewPager.setCurrentItem(0);
			item1.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.rule_item2:
			viewPager.setCurrentItem(1);
			item2.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		default:
			break;
		}
	}

	private void resetAll() {
		item1.setTextColor(getResources().getColor(R.color.color_black));
		item2.setTextColor(getResources().getColor(R.color.color_black));
	}
}
