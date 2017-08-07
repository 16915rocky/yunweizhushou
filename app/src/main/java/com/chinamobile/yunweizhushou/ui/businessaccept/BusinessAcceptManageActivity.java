package com.chinamobile.yunweizhushou.ui.businessaccept;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.businessaccept.fragments.BusiAcceptFragment1;
import com.chinamobile.yunweizhushou.ui.businessaccept.fragments.BusiAcceptFragment2;
import com.chinamobile.yunweizhushou.ui.businessaccept.fragments.BusiAcceptFragment3;
import com.chinamobile.yunweizhushou.ui.businessaccept.fragments.BusiCenterFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class BusinessAcceptManageActivity extends BaseActivity implements OnClickListener {

	private TextView item1, item2, item3, item4;
	private View bottomBar;
	private MyViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_business_accept_manage);
		initView();
		initViewPager();
		initEvent();
	}

	private void initViewPager() {

		fragmentList = new ArrayList<>();
		// fragmentList.add(new
		// BusinessAcceptFragment(BusinessAcceptFragment.BUSINESS_ACCEPT_1));
		fragmentList.add(new BusiAcceptFragment1());
		fragmentList.add(new BusiCenterFragment());
		// fragmentList.add(new
		// BusinessAcceptFragment(BusinessAcceptFragment.BUSINESS_ACCEPT_2));
		// fragmentList.add(new
		// BusinessAcceptFragment(BusinessAcceptFragment.BUSINESS_ACCEPT_3));
		fragmentList.add(new BusiAcceptFragment2());
		fragmentList.add(new BusiAcceptFragment3());

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
		getTitleBar().setMiddleText("业务受理");
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

		LayoutParams params = bottomBar.getLayoutParams();
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
					listener2.switch2Busi2();
				} else if (arg0 == 2) {
					item3.setTextColor(getResources().getColor(R.color.color_lightblue));
					listener3.switch2Busi3();
				} else if (arg0 == 3) {
					item4.setTextColor(getResources().getColor(R.color.color_lightblue));
					listener4.switch2Busi4();
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
		item1 = (TextView) findViewById(R.id.busi_item1);
		item2 = (TextView) findViewById(R.id.busi_item2);
		item3 = (TextView) findViewById(R.id.busi_item3);
		item4 = (TextView) findViewById(R.id.busi_item4);
		viewPager = (MyViewPager) findViewById(R.id.busi_viewpager);
		bottomBar = findViewById(R.id.busi_bottom_bar);

		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.busi_item1:
			viewPager.setCurrentItem(0);
			item1.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.busi_item2:
			viewPager.setCurrentItem(1);
			item2.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.busi_item3:
			viewPager.setCurrentItem(2);
			item3.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.busi_item4:
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

	public interface switch2Busi2Listener {
		void switch2Busi2();
	}

	public interface switch2Busi3Listener {
		void switch2Busi3();
	}

	public interface switch2Busi4Listener {
		void switch2Busi4();
	}

	private switch2Busi2Listener listener2;
	private switch2Busi3Listener listener3;
	private switch2Busi4Listener listener4;

	public void setSwitch2Busi2Listener(switch2Busi2Listener listener) {
		if (listener != null) {
			this.listener2 = listener;
		}
	}

	public void setSwitch2Busi3Listener(switch2Busi3Listener listener) {
		if (listener != null) {
			this.listener3 = listener;
		}
	}

	public void setSwitch2Busi4Listener(switch2Busi4Listener listener) {
		if (listener != null) {
			this.listener4 = listener;
		}
	}

}
