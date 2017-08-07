package com.chinamobile.yunweizhushou.ui.userPerceptionIndex;

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
import com.chinamobile.yunweizhushou.ui.commonView.CommonBarCharListFragment;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.fragments.UserPerceptionCommonTabFragment;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.fragments.UserPerceptionIndexTab1Fragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;

public class UserPerceptionIndexNext2Activity extends BaseActivity implements OnClickListener {
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
		KEIName=getActivity().getIntent().getStringExtra("KEIName");
		position=getActivity().getIntent().getStringExtra("position");
		classId=getActivity().getIntent().getStringExtra("classId");
		setContentView(R.layout.activity_user_perception_index_next2);
		initView();
		initViewPager();
		initEvent();
	}




	private void initViewPager() {

		fragmentList = new ArrayList<>();
		UserPerceptionIndexTab1Fragment ruleFragment1= new UserPerceptionIndexTab1Fragment();
		Bundle bundle = new Bundle();
		bundle.putString("KEIName",KEIName);
		bundle.putString("position",position);
		bundle.putString("classId",classId);
		ruleFragment1.setArguments(bundle);
		fragmentList.add(ruleFragment1);
		UserPerceptionCommonTabFragment userPerceptionCommonTabFragment2 = new UserPerceptionCommonTabFragment();
		Bundle bundle2=new Bundle();
		bundle2.putString("methodBarName","findNarrowMonthBar");
		bundle2.putString("methodListName","findNarrowDayData");
		userPerceptionCommonTabFragment2.setArguments(bundle2);
		fragmentList.add(userPerceptionCommonTabFragment2);

		UserPerceptionCommonTabFragment userPerceptionCommonTabFragment3 = new UserPerceptionCommonTabFragment();
		Bundle bundle3=new Bundle();
		bundle3.putString("methodBarName","findProvinceMonthBar");
		bundle3.putString("methodListName","findProvinceDayData");
		userPerceptionCommonTabFragment3.setArguments(bundle3);
		fragmentList.add(userPerceptionCommonTabFragment3);

		UserPerceptionCommonTabFragment userPerceptionCommonTabFragment4 = new UserPerceptionCommonTabFragment();
		Bundle bundle4=new Bundle();
		bundle4.putString("methodBarName","findUpgradeMonthBar");
		bundle4.putString("methodListName","findUpgradeDayData");
		userPerceptionCommonTabFragment4.setArguments(bundle4);
		fragmentList.add(userPerceptionCommonTabFragment4);

		UserPerceptionCommonTabFragment userPerceptionCommonTabFragment5 = new UserPerceptionCommonTabFragment();
		Bundle bundle5=new Bundle();
		bundle5.putString("methodBarName","findReorderMonthBar");
		bundle5.putString("methodListName","findReorderDayData");
		userPerceptionCommonTabFragment5.setArguments(bundle5);
		fragmentList.add(userPerceptionCommonTabFragment5);
		CommonBarCharListFragment commonBarCharListFragment = new CommonBarCharListFragment();
		fragmentList.add(commonBarCharListFragment);
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
		getTitleBar().setMiddleText("业务受理");
		getTitleBar().setLeftButton(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);
		item5.setOnClickListener(this);
		item6.setOnClickListener(this);

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
				}else if (arg0 == 4) {
					item5.setTextColor(getResources().getColor(R.color.color_lightblue));
				}else if (arg0 == 5) {
					item6.setTextColor(getResources().getColor(R.color.color_lightblue));
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int offsetPx) {
				LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) bottomBar.getLayoutParams();
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
		item5 = (TextView) findViewById(R.id.rule_item5);
		item6= (TextView) findViewById(R.id.rule_item6);
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
			case R.id.rule_item5:
				viewPager.setCurrentItem(4);
				item5.setTextColor(getResources().getColor(R.color.color_lightblue));
				break;
			case R.id.rule_item6:
				viewPager.setCurrentItem(5);
				item6.setTextColor(getResources().getColor(R.color.color_lightblue));
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
		item5.setTextColor(getResources().getColor(R.color.color_black));
		item6.setTextColor(getResources().getColor(R.color.color_black));
	}

}
