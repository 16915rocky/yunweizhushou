package com.chinamobile.yunweizhushou.ui.esbInterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListFragment2;
import com.chinamobile.yunweizhushou.ui.esbInterface.fragments.ESBAbnormalRankingFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class ESBNextManageActivity extends BaseActivity implements View.OnClickListener {

	private TextView item1, item2;
	private View bottomBar;
	private MyViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		name=getIntent().getStringExtra("name");
		setContentView(R.layout.activity_esb_next);
		initView();
		initViewPager();
		initEvent();
	}

	private void initViewPager() {

		fragmentList = new ArrayList<>();
		fragmentList.add(new ESBAbnormalRankingFragment());
		GraphListFragment2 graphListFragment = new GraphListFragment2();
		Bundle bundle=new Bundle();
		bundle.putString("extraKey","pament_business");
		bundle.putString("extraValue",name);
		bundle.putString("fkId","1014");
		graphListFragment.setArguments(bundle);
     	fragmentList.add(graphListFragment);
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

		//viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
		viewPager.setAdapter(mAdapter);
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setMiddleText(name);
		getTitleBar().setLeftButton(new View.OnClickListener() {

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

		viewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {

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
		viewPager = (MyViewPager) findViewById(R.id.rule_viewpager);
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
