package com.chinamobile.yunweizhushou.ui.functionAnalysis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.chinamobile.yunweizhushou.ui.functionAnalysis.fragments.FunctionAnalysisTotalFragment;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.fragments.FunctionPerformanceAnalysisFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.OperationTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionAnalysisActivity extends BaseActivity implements OnClickListener {

	private ViewPager mViewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	// private View bottomBar;
	private OperationTextView tab1, tab2;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function_analysis);
		initView();
		initEvent();
		initRequest();
		initFragment();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1041");
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
				JSONObject jo=new JSONObject(responseBean.getDATA());
				String charger=jo.getString("charger");
				String phone=jo.getString("phone");
				String url=jo.getString("picture");
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

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.function_analysis_viewpager);
		tab1 = (OperationTextView) findViewById(R.id.function_performance);
		tab2 = (OperationTextView) findViewById(R.id.function_total);

		tab1.setTextSize(16);
		tab2.setTextSize(16);
		tab1.setTextColor(getResources().getColor(R.color.color_lightblue));
		tab2.setTextColor(getResources().getColor(R.color.color_black));
		tab1.setSelected(true);
		tab2.setSelected(false);
		tab1.setText("实时性能检测");
		tab2.setText("恶化接口分析");
		tab1.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab2.setTextAlign(
				OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
		tab1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tab2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// bottomBar = findViewById(R.id.bottomBar);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void initEvent() {
		getTitleBar().setMiddleText("性能分析");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
	}

	private void initFragment() {
		fragmentList = new ArrayList<>();
		fragmentList.add(new FunctionPerformanceAnalysisFragment());
		fragmentList.add(new FunctionAnalysisTotalFragment());
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
		// mViewPager.setOffscreenPageLimit(fragmentList.size() - 1);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setAdapter(mAdapter);

		/*
		 * LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
		 * bottomBar.getLayoutParams(); params.leftMargin=tab1.getLeft();
		 * bottomBar.setLayoutParams(params);
		 */
	}

	private void changeSelectedTab(int position) {
		tab1.setSelected(position == 0 ? true : false);
		tab2.setSelected(position == 1 ? true : false);
	}

	private class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			changeSelectedTab(position);
			changeTabTextBG(position);
			/*
			 * LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
			 * bottomBar.getLayoutParams(); params.leftMargin =tab2.getLeft();
			 * Log.e("tag", ""+tab2.getLeft());
			 * bottomBar.setLayoutParams(params);
			 */
		}

		@Override
		public void onPageScrolled(int position, float offset, int arg2) {
			/*
			 * LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
			 * bottomBar.getLayoutParams(); params.leftMargin = (int) ((position
			 * + offset) * params.width)+tab1.getLeft();
			 * bottomBar.setLayoutParams(params);
			 */
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.function_total:
			changeTabTextBG(1);
			changeSelectedTab(1);
			mViewPager.setCurrentItem(1);
			break;
		case R.id.function_performance:
			changeTabTextBG(0);
			changeSelectedTab(0);
			mViewPager.setCurrentItem(0);
			break;
		}
	}

	private void changeTabTextBG(int position) {
		if (position == 0) {
			tab1.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab2.setTextColor(Color.BLACK);
		} else {
			tab1.setTextColor(Color.BLACK);
			tab2.setTextColor(getResources().getColor(R.color.color_lightblue));
		}
	}
}
