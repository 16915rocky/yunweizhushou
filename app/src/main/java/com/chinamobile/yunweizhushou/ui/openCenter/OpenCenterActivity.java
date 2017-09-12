package com.chinamobile.yunweizhushou.ui.openCenter;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterBacklogFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterCSFFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterFailureFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterGraphFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterNetYuanBacklogFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterNewMQFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenTheProcessBacklogFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyViewPager;
import com.chinamobile.yunweizhushou.view.OperationTextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenCenterActivity extends BaseActivity implements OnClickListener {

	private MyViewPager mViewPager;
	private List<Fragment> fragments = new ArrayList<>();
	private FragmentPagerAdapter mAdapter;
	// private View bottomBar;
	private OperationTextView tab1, tab2, tab, tab0, tabfq, tab3, tab4;
	private List<OperationTextView> tabs;
	private TextView tv_phone, tv_name;
	private ImageView img_charge_people;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_center);
		initView();
		setEvent();
		initRequest();
		initFragment();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private void initRequest() {
		HashMap map2 = new HashMap<String, String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1031");
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
					JSONObject jo = new JSONObject(responseBean.getDATA());
					String charger = jo.getString("charger");
					String phone = jo.getString("phone");
					String url = jo.getString("picture");
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
		OpenCenterCSFFragment openCenterCSFFragment1 = new OpenCenterCSFFragment();
		Bundle bundle1=new Bundle();
		bundle1.putInt("tag",OpenCenterCSFFragment.CSF);
		openCenterCSFFragment1.setArguments(bundle1);
		fragments.add(openCenterCSFFragment1);
		OpenCenterNewMQFragment openCenterNewMQFragment = new OpenCenterNewMQFragment();
		Bundle mqBundle=new Bundle();
		mqBundle.putString("id","3");
		openCenterNewMQFragment.setArguments(mqBundle);
		fragments.add(openCenterNewMQFragment);
		OpenCenterBacklogFragment openCenterBacklogFragment = new OpenCenterBacklogFragment();
		Bundle bundle2=new Bundle();
		bundle2.putInt("tag",OpenCenterBacklogFragment.BACKLOG);
		openCenterBacklogFragment.setArguments(bundle2);
		fragments.add(openCenterBacklogFragment);
		fragments.add(new OpenTheProcessBacklogFragment());
		fragments.add(new OpenCenterNetYuanBacklogFragment());
		OpenCenterFailureFragment openCenterFailureFragment = new OpenCenterFailureFragment();
		Bundle bundle3 = new Bundle();
		bundle3.putInt("tag",OpenCenterFailureFragment.FAILURE);
		openCenterFailureFragment.setArguments(bundle3);
		fragments.add(openCenterFailureFragment);
		fragments.add(new OpenCenterGraphFragment());

		/*
		 * for (int i = 0; i < 2; i++) { RechargeFunctionFragment fragment = new
		 * RechargeFunctionFragment(); Bundle bundle = new Bundle(); if (i == 0)
		 * { bundle.putString("upId", "1025"); } else if (i == 1) {
		 * bundle.putString("upId", "1026"); } fragment.setArguments(bundle);
		 * fragments.add(fragment); }
		 */
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		};
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setAdapter(mAdapter);
	}

	private void initView() {
		tabs = new ArrayList<>();
		mViewPager = (MyViewPager) findViewById(R.id.function_analysis_viewpager);
		tab = (OperationTextView) findViewById(R.id.tab);
		tab0 = (OperationTextView) findViewById(R.id.tab0);
		tab1 = (OperationTextView) findViewById(R.id.tab1);
		tab2 = (OperationTextView) findViewById(R.id.tab2);
		tab3 = (OperationTextView) findViewById(R.id.tab3);
		tab4 = (OperationTextView) findViewById(R.id.tab4);
		tabfq = (OperationTextView) findViewById(R.id.tabfq);
		img_charge_people = (ImageView) findViewById(R.id.img_charge_people);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_phone = (TextView) findViewById(R.id.tv_phone);

		tab.setTextSize(10);
		tabfq.setTextSize(10);
		tab0.setTextSize(10);
		tab1.setTextSize(10);
		tab2.setTextSize(10);
		tab3.setTextSize(10);
		tab4.setTextSize(10);
		tab.setTextColor(getResources().getColor(R.color.color_lightblue));
		tabfq.setTextColor(getResources().getColor(R.color.color_black));
		tab0.setTextColor(getResources().getColor(R.color.color_black));
		tab1.setTextColor(getResources().getColor(R.color.color_black));
		tab2.setTextColor(getResources().getColor(R.color.color_black));
		tab3.setTextColor(getResources().getColor(R.color.color_black));
		tab4.setTextColor(getResources().getColor(R.color.color_black));
		tab.setSelected(true);
		tabfq.setSelected(false);
		tab0.setSelected(false);
		tab1.setSelected(false);
		tab2.setSelected(false);
		tab3.setSelected(false);
		tab4.setSelected(false);
		tab.setText("CSF");
		tabfq.setText("MQ");
		tab4.setText("表积压");
		tab3.setText("进程积压");
		tab2.setText("网元积压");
		tab0.setText("开通失败");
		tab1.setText("处理量");

		tab.setTextAlign(OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tabfq.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab0.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab1.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab2.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab3.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab4.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tabfq.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tab0.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tab1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tab2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tab3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tab4.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		tabs.add(tab);
		tabs.add(tabfq);
		tabs.add(tab4);
		tabs.add(tab3);
		tabs.add(tab2);
		tabs.add(tab0);
		tabs.add(tab1);

	}

	private void setEvent() {
		tab.setOnClickListener(this);
		tabfq.setOnClickListener(this);
		tab0.setOnClickListener(this);
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);

		initFragment();

		mViewPager.setAdapter(viewPagerAdapter);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOffscreenPageLimit(viewPagerAdapter.getCount() - 1);// 设置缓存个数

		getTitleBar().setMiddleText("开通中心");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("OpenCenter Page") // TODO: Define a title for the content shown.
				// TODO: Make sure this auto-generated URL is correct.
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}

	private class PageChangeListener implements OnPageChangeListener, MyViewPager.OnPageChangeListener {
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
			tab.setTextColor(getResources().getColor(R.color.color_lightblue));
			tabfq.setTextColor(Color.BLACK);
			tab4.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab0.setTextColor(Color.BLACK);
			tab1.setTextColor(Color.BLACK);
		} else if (position == 1) {
			tab.setTextColor(Color.BLACK);
			tabfq.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab4.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab0.setTextColor(Color.BLACK);
			tab1.setTextColor(Color.BLACK);
		} else if (position == 2) {
			tab.setTextColor(Color.BLACK);
			tabfq.setTextColor(Color.BLACK);
			tab4.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab3.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab0.setTextColor(Color.BLACK);
			tab1.setTextColor(Color.BLACK);
		} else if (position == 3) {
			tab.setTextColor(Color.BLACK);
			tabfq.setTextColor(Color.BLACK);
			tab4.setTextColor(Color.BLACK);
			tab3.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab2.setTextColor(Color.BLACK);
			tab0.setTextColor(Color.BLACK);
			tab1.setTextColor(Color.BLACK);
		} else if (position == 4) {
			tab.setTextColor(Color.BLACK);
			tabfq.setTextColor(Color.BLACK);
			tab4.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab2.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab0.setTextColor(Color.BLACK);
			tab1.setTextColor(Color.BLACK);
		} else if (position == 5) {
			tab.setTextColor(Color.BLACK);
			tabfq.setTextColor(Color.BLACK);
			tab4.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab0.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab1.setTextColor(Color.BLACK);
		}
	}

	private void changeSelectedTab(int position) {
		tab.setSelected(position == 0 ? true : false);
		tabfq.setSelected(position == 1 ? true : false);
		tab4.setSelected(position == 2 ? true : false);
		tab3.setSelected(position == 3 ? true : false);
		tab2.setSelected(position == 4 ? true : false);
		tab0.setSelected(position == 5 ? true : false);
		tab1.setSelected(position == 6 ? true : false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tab:
				changeTabTextBG(0);
				changeSelectedTab(0);
				mViewPager.setCurrentItem(0);
				break;
			case R.id.tabfq:
				changeTabTextBG(1);
				changeSelectedTab(1);
				mViewPager.setCurrentItem(1);
				break;
			case R.id.tab4:
				changeTabTextBG(2);
				changeSelectedTab(2);
				mViewPager.setCurrentItem(2);
				break;
			case R.id.tab3:
				changeTabTextBG(3);
				changeSelectedTab(3);
				mViewPager.setCurrentItem(3);
				break;
			case R.id.tab2:
				changeTabTextBG(4);
				changeSelectedTab(4);
				mViewPager.setCurrentItem(4);
				break;
			case R.id.tab0:
				changeTabTextBG(5);
				changeSelectedTab(5);
				mViewPager.setCurrentItem(5);
				break;
			case R.id.tab1:
				changeTabTextBG(6);
				changeSelectedTab(6);
				mViewPager.setCurrentItem(6);
				break;
		}
	}
}
