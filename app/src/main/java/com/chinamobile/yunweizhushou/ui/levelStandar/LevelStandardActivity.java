package com.chinamobile.yunweizhushou.ui.levelStandar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.levelStandar.fragments.LevelStandardFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelStandardActivity extends BaseActivity implements OnClickListener {

	private LinearLayout level1, level2, level3 , level4 ;
	private View bottomBar;
	private TextView dateTextView;
	private int bottomBarWidth;
	private ViewPager levelViewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter pagerAdapter;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_level_standard);
		initView();
		initData();
		initEvent();
		initRequest();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1044");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
	
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
	private void initData() {
		dateTextView.setText("数据截止" + Utils.getCurrentTime());
		fragmentList = new ArrayList<>();
		fragmentList.add(LevelStandardFragment.newInstance(1));
		fragmentList.add(LevelStandardFragment.newInstance(2));
		fragmentList.add(LevelStandardFragment.newInstance(3));
		fragmentList.add(LevelStandardFragment.newInstance(4));
		pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragmentList.get(arg0);
			}
		};
		levelViewPager.setAdapter(pagerAdapter);
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {

		getTitleBar().setMiddleText("业务分级标准");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		level1.setOnClickListener(this);
		level2.setOnClickListener(this);
		level3.setOnClickListener(this);
		 level4.setOnClickListener(this);

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		levelViewPager.setOffscreenPageLimit(3);

		levelViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

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
		level1 = (LinearLayout) findViewById(R.id.level_standard_level1_layout);
		level2 = (LinearLayout) findViewById(R.id.level_standard_level2_layout);
		level3 = (LinearLayout) findViewById(R.id.level_standard_level3_layout);
		level4 = (LinearLayout) findViewById(R.id.level_standard_level4_layout);
		bottomBar = findViewById(R.id.level_standard_bottom_layout);
		dateTextView = (TextView) findViewById(R.id.level_standard_date);
		levelViewPager = (ViewPager) findViewById(R.id.level_standard_viewpager);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.level_standard_level1_layout:
			levelViewPager.setCurrentItem(0);
			break;
		case R.id.level_standard_level2_layout:
			levelViewPager.setCurrentItem(1);
			break;
		case R.id.level_standard_level3_layout:
			levelViewPager.setCurrentItem(2);
			break;
		 case R.id.level_standard_level4_layout:
		 levelViewPager.setCurrentItem(3);
		 break;
		default:
			break;
		}
	}
}
