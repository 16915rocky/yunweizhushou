package com.chinamobile.yunweizhushou.ui.netChange;

import android.content.Intent;
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
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.netChange.fragments.NetChangeDetailFragment11;
import com.chinamobile.yunweizhushou.ui.netChange.fragments.NetChangeDetailFragment12;
import com.chinamobile.yunweizhushou.ui.netChange.fragments.NetChangeDetailFragment2;
import com.chinamobile.yunweizhushou.ui.netChange.fragments.NetChangeDetailFragment3;
import com.chinamobile.yunweizhushou.ui.netChange.fragments.NetChangeDetailFragment4;
import com.chinamobile.yunweizhushou.ui.netChange.fragments.NetChangeDetailFragment5;
import com.chinamobile.yunweizhushou.ui.netChange.fragments.NetChangeDetailFragment6;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.FlexibleTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetChangeDetailManageActivity extends BaseActivity implements OnClickListener {
	private TextView item1, item2, item3, item4, item5, item6;
	private View bottomBar;
	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private FlexibleTextView topContent;

	private String type, plan;
	private boolean isThree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_net_change_detail);
		type = getIntent().getStringExtra("type");
		isThree = (type.equals("0") || type.equals("1")) ? false : true;
		plan = getIntent().getStringExtra("plan");
		initView();
		initViewPager();
		initEvent();
		initRequest();

	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getConclusion");
		map.put("online_plan", plan);
		startTask(HttpRequestEnum.enum_net_change_0, ConstantValueUtil.URL + "ChangeTask?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_net_change_0:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					String content = obj.getString("remark");
					topContent.setText(content);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		if (!isThree) {
			getTitleBar().setLeftButton(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
			getTitleBar().setRightButton1(R.mipmap.icon_list, new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(NetChangeDetailManageActivity.this, OnlineChangeActivity.class);
					intent.putExtra("title", getIntent().getStringExtra("title"));
					intent.putExtra("type", getIntent().getStringExtra("type"));
					startActivity(intent);

				}

			});
		}
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);
		item5.setOnClickListener(this);
		item6.setOnClickListener(this);

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetAll();
				if (arg0 == 0) {
					item1.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 1) {
					if (isThree) {
						item3.setTextColor(getResources().getColor(R.color.color_lightblue));
					} else {
						item2.setTextColor(getResources().getColor(R.color.color_lightblue));
					}
				} else if (arg0 == 2) {
					if (isThree) {
						item4.setTextColor(getResources().getColor(R.color.color_lightblue));
					} else {
						item3.setTextColor(getResources().getColor(R.color.color_lightblue));
					}

				} else if (arg0 == 3) {
					item4.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 4) {
					item5.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 5) {
					item6.setTextColor(getResources().getColor(R.color.color_lightblue));
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
		item1 = (TextView) findViewById(R.id.net_change_detail_item1);
		item2 = (TextView) findViewById(R.id.net_change_detail_item2);
		item3 = (TextView) findViewById(R.id.net_change_detail_item3);
		item4 = (TextView) findViewById(R.id.net_change_detail_item4);
		item5 = (TextView) findViewById(R.id.net_change_detail_item5);
		item6 = (TextView) findViewById(R.id.net_change_detail_item6);
		viewPager = (ViewPager) findViewById(R.id.net_change_detail_viewpager);
		bottomBar = findViewById(R.id.net_change_detail_bottom_bar);
		topContent = (FlexibleTextView) findViewById(R.id.net_change_top_textview);

		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
	}

	private void initViewPager() {

		fragmentList = new ArrayList<>();
		if (type.equals("0") || type.equals("1")) {
			NetChangeDetailFragment11 netChangeDetailFragment11 = new NetChangeDetailFragment11();
			Bundle bundle = new Bundle();
			bundle.putString("plan",plan);
			netChangeDetailFragment11.setArguments(bundle);
			fragmentList.add(netChangeDetailFragment11);
			NetChangeDetailFragment4 netChangeDetailFragment4 = new NetChangeDetailFragment4();
			Bundle bundle1 = new Bundle();
			bundle1.putString("plan",plan);
			netChangeDetailFragment4.setArguments(bundle1);
			fragmentList.add(netChangeDetailFragment4);

		} else {
			item2.setVisibility(View.GONE);
			NetChangeDetailFragment12 netChangeDetailFragment12 = new NetChangeDetailFragment12();
			Bundle bundle = new Bundle();
			bundle.putString("plan",plan);
			netChangeDetailFragment12.setArguments(bundle);
			fragmentList.add(netChangeDetailFragment12);
		}
		NetChangeDetailFragment2 netChangeDetailFragment2 = new NetChangeDetailFragment2();
		Bundle bundle = new Bundle();
		bundle.putString("plan",plan);
		netChangeDetailFragment2.setArguments(bundle);
		fragmentList.add(netChangeDetailFragment2);
		NetChangeDetailFragment3 netChangeDetailFragment3 = new NetChangeDetailFragment3();
		Bundle bundle6 = new Bundle();
		bundle6.putString("plan",plan);
		bundle6.putBoolean("isThree",isThree);
		netChangeDetailFragment3.setArguments(bundle6);
		fragmentList.add(netChangeDetailFragment3);
		if (type.equals("0") || type.equals("1")) {
			NetChangeDetailFragment5 netChangeDetailFragment5 = new NetChangeDetailFragment5();
			Bundle bundle5=new Bundle();
			bundle5.putString("plan",plan);
			netChangeDetailFragment5.setArguments(bundle5);
			fragmentList.add(netChangeDetailFragment5);
			NetChangeDetailFragment6 netChangeDetailFragment6 = new NetChangeDetailFragment6();
			Bundle bundle7=new Bundle();
			bundle7.putString("plan",plan);
			netChangeDetailFragment6.setArguments(bundle7);
			fragmentList.add(netChangeDetailFragment6);
		} else {
			item5.setVisibility(View.GONE);
			item6.setVisibility(View.GONE);
		}

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

	private void resetAll() {
		item1.setTextColor(getResources().getColor(R.color.color_black));
		item2.setTextColor(getResources().getColor(R.color.color_black));
		item3.setTextColor(getResources().getColor(R.color.color_black));
		item4.setTextColor(getResources().getColor(R.color.color_black));
		item5.setTextColor(getResources().getColor(R.color.color_black));
		item6.setTextColor(getResources().getColor(R.color.color_black));
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.net_change_detail_item1:
			viewPager.setCurrentItem(0);
			item1.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.net_change_detail_item2:
			if (isThree) {
				viewPager.setCurrentItem(2);
				item3.setTextColor(getResources().getColor(R.color.color_lightblue));
			} else {
				viewPager.setCurrentItem(1);
				item2.setTextColor(getResources().getColor(R.color.color_lightblue));

			}
			break;
		case R.id.net_change_detail_item3:
			if (isThree) {
				viewPager.setCurrentItem(3);
				item4.setTextColor(getResources().getColor(R.color.color_lightblue));
			} else {

				viewPager.setCurrentItem(2);
				item3.setTextColor(getResources().getColor(R.color.color_lightblue));
			}

			break;
		case R.id.net_change_detail_item4:
			viewPager.setCurrentItem(3);
			item4.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.net_change_detail_item5:
			viewPager.setCurrentItem(4);
			item5.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.net_change_detail_item6:
			viewPager.setCurrentItem(5);
			item6.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		default:
			break;
		}
	}

}
