package com.chinamobile.yunweizhushou.ui.emergencyExercise;

import android.graphics.Color;
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
import com.chinamobile.yunweizhushou.ui.emergencyExercise.fragments.ExerciseMiddleWareFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;

import java.util.ArrayList;
import java.util.List;

public class EmergencyExerciseActivity extends BaseActivity implements OnClickListener {

	private ViewPager mViewPager;
	private View bottomBar;
	private List<Fragment> fragmentList;
	private TextView tabLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_exercise);
		initView();
		setEvent();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.exerciseViewPager);
		tabLeft = (TextView) findViewById(R.id.tabLeft);
		bottomBar = findViewById(R.id.bottomBar);

		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void setEvent() {
		fragmentList = new ArrayList<>();
		ExerciseMiddleWareFragment fragment=new ExerciseMiddleWareFragment();
		Bundle bundle = new Bundle();
		bundle.putString("title","中间件");
		fragment.setArguments(bundle);
		fragmentList.add(fragment);
		// fragmentList.add(new
		// ExerciseMiddleWareFragment(EmergencyExerciseActivity.this,"故障总控台"));
		tabLeft.setOnClickListener(this);
		// tabRight.setOnClickListener(this);

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
			tabLeft.setTextColor(getResources().getColor(R.color.emergency_tab_color_s));
			// tabRight.setTextColor(Color.BLACK);
			break;
		case 1:
			tabLeft.setTextColor(Color.BLACK);
			// tabRight.setTextColor(getResources().getColor(R.color.emergency_tab_color_s));
			break;
		}
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
		}
	}
}
