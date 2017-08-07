package com.chinamobile.yunweizhushou.ui.officeDataZone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.officeDataZone.fragments.GroupIncrementCheckFragment;
import com.chinamobile.yunweizhushou.ui.officeDataZone.fragments.OfficeCollectingFragment;
import com.chinamobile.yunweizhushou.ui.officeDataZone.fragments.OfficeFileReceivingFragment;
import com.chinamobile.yunweizhushou.ui.officeDataZone.fragments.OfficeReleaseFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class OfficeDataZoneActivity extends BaseActivity implements OnClickListener {
	private TextView item1, item2,item3,item4,item5,item6;
	private View bottomBar;
	private MyViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private TextView tv_phone,tv_name;
	private ImageView img_charge_people;
	private String KEIName;
	private View view_line;
	private String position,classId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_office_daa_zone);
		initView();
		initViewPager();
		initEvent();
	}




	private void initViewPager() {

		fragmentList = new ArrayList<>();

		OfficeCollectingFragment officeCollectingFragment = new OfficeCollectingFragment();
		fragmentList.add(officeCollectingFragment);
		OfficeReleaseFragment officeReleaseFragment = new OfficeReleaseFragment();
		fragmentList.add(officeReleaseFragment);
		GroupIncrementCheckFragment groupIncrementCheckFragment = new GroupIncrementCheckFragment();
		fragmentList.add(groupIncrementCheckFragment);
		OfficeFileReceivingFragment officeFileReceivingFragment = new OfficeFileReceivingFragment();
		fragmentList.add(officeFileReceivingFragment);
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

		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(mAdapter);
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setMiddleText("局数据专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);


		ViewGroup.LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		viewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetAll();
				if (arg0 == 0) {
					item1.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 1) {
					item2.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 2) {
					item3.setTextColor(getResources().getColor(R.color.color_lightblue));
				}else if (arg0 == 3) {
					item4.setTextColor(getResources().getColor(R.color.color_lightblue));
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
		item3 = (TextView) findViewById(R.id.rule_item3);
		item4 = (TextView) findViewById(R.id.rule_item4);
		viewPager = (MyViewPager) findViewById(R.id.rule_viewpager);
		bottomBar = findViewById(R.id.rule_bottom_bar);
		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
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
			case R.id.rule_item3:
				viewPager.setCurrentItem(2);
				item3.setTextColor(getResources().getColor(R.color.color_lightblue));
				break;
			case R.id.rule_item4:
				viewPager.setCurrentItem(3);
				item4.setTextColor(getResources().getColor(R.color.color_lightblue));
				break;
			default:
				break;
		}
	}

	private void resetAll() {
		item1.setTextColor(getResources().getColor(R.color.color_black));
		item2.setTextColor(getResources().getColor(R.color.color_black));
		item3.setTextColor(getResources().getColor(R.color.color_black));
		item4.setTextColor(getResources().getColor(R.color.color_black));
	}

}
