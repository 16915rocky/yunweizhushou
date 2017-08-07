package com.chinamobile.yunweizhushou.ui.useRank;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.useRank.fragments.UseRankingFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class UseRankingActivity extends BaseActivity implements OnClickListener {
	private ViewPager mViewPager;
	private View bottomBar;
	private List<Fragment> fragmentList;
	private TextView tabLeft, tabRight;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_ranking);
		initView();
		setEvent();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.exerciseViewPager);
		tabLeft = (TextView) findViewById(R.id.tabLeft);
		tabRight = (TextView) findViewById(R.id.tabRight);
		bottomBar = findViewById(R.id.bottomBar);

		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setEvent() {
		fragmentList = new ArrayList<>();
		UseRankingFragment useRankingFragment1 = new UseRankingFragment();
		Bundle bundle1 = new Bundle();
		bundle1.putString("action","getTheMenuUseRankings");
		bundle1.putInt("id",1);
		useRankingFragment1.setArguments(bundle1);
		fragmentList.add(useRankingFragment1);

		UseRankingFragment useRankingFragment2 = new UseRankingFragment();
		Bundle bundle2 = new Bundle();
		bundle2.putString("action","getTheUserUseRankings");
		bundle2.putInt("id",0);
		useRankingFragment2.setArguments(bundle2);
		fragmentList.add(useRankingFragment2);
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

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("UseRanking Page") // TODO: Define a title for the content shown.
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tabLeft:
				mViewPager.setCurrentItem(0);
				changeTextColor(0);
				break;
			case R.id.tabRight:
				mViewPager.setCurrentItem(1);
				changeTextColor(1);
				break;
		}
	}
}
