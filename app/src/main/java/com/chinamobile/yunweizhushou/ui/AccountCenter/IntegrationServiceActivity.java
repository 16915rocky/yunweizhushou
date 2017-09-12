package com.chinamobile.yunweizhushou.ui.AccountCenter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.chinamobile.yunweizhushou.ui.AccountCenter.fragments.AccountMQFragment;
import com.chinamobile.yunweizhushou.ui.AccountCenter.fragments.IntegrationServiceFragment;
import com.chinamobile.yunweizhushou.ui.customerCenter.fragment.CustomerCenterESBFragment;
import com.chinamobile.yunweizhushou.ui.ruleCenter.fragments.RuleFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntegrationServiceActivity extends BaseActivity implements OnClickListener {

	private MyViewPager mViewPager;
	private View bottomBar;
	private TextView tab1, tab2, tab3, tab4, tabmq;
	private List<Fragment> fragments = new ArrayList<>();
	private int bottomBarWidth;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integration_service);
		initView();
		initRequest();
		setEvent();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1034");
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

	private void initView() {
		mViewPager = (MyViewPager) findViewById(R.id.business_detection_pager);
		tab1 = (TextView) findViewById(R.id.jifen_tab1);
		tabmq = (TextView) findViewById(R.id.jifen_tabmq);
		tab2 = (TextView) findViewById(R.id.jifen_tab2);
		tab3 = (TextView) findViewById(R.id.jifen_tab3);
		tab4 = (TextView) findViewById(R.id.jifen_tab4);
		bottomBar = findViewById(R.id.jifen_bottomBar);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);

		getTitleBar().setMiddleText("账户中心");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setEvent() {
		tab1.setOnClickListener(this);
		tabmq.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);

		CustomerCenterESBFragment customerCenterESBFragment = new CustomerCenterESBFragment();
		Bundle esbBundle = new Bundle();
		esbBundle.putString("csf_server_code","ams");
		customerCenterESBFragment.setArguments(esbBundle);
		fragments.add(customerCenterESBFragment);

		RuleFragment fragment = new RuleFragment();// 账户中心 CSF
		Bundle bundle = new Bundle();
		bundle.putInt("fkId",1041);
		fragment.setArguments(bundle);
		fragments.add(fragment);
		// add
		fragments.add(new AccountMQFragment());
		IntegrationServiceFragment integrationServiceFragment=new IntegrationServiceFragment();
		Bundle bundle1 = new Bundle();
		bundle1.putString("fkId","1015");
		integrationServiceFragment.setArguments(bundle1);
		fragments.add(integrationServiceFragment);// 积分业务量

		//fragments.add(new BusinessDetectionFragment("1042"));// 积分兑换平衡性检查

//		BusinessDetectionFragment2 fragment2 = new BusinessDetectionFragment2();
//		Bundle bundle = new Bundle();
//		bundle.putInt("tag", 1037);
//		fragment2.setArguments(bundle);
//		fragments.add(fragment2);// cboss积分业务

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragments.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		};
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setAdapter(mAdapter);

		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(0);
		changeTextColor(0);
	}

	private void changeTextColor(int position) {
		switch (position) {
		case 0:
			tab1.setTextColor(getResources().getColor(R.color.use_ranking_tab_color_s));
			tabmq.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab4.setTextColor(Color.BLACK);
			break;
		case 1:
			tab1.setTextColor(Color.BLACK);
			tabmq.setTextColor(getResources().getColor(R.color.use_ranking_tab_color_s));
			tab2.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab4.setTextColor(Color.BLACK);
			break;
		case 2:
			tab1.setTextColor(Color.BLACK);
			tabmq.setTextColor(Color.BLACK);
			tab2.setTextColor(getResources().getColor(R.color.use_ranking_tab_color_s));
			tab3.setTextColor(Color.BLACK);
			tab4.setTextColor(Color.BLACK);
			break;
		case 3:
			tab1.setTextColor(Color.BLACK);
			tabmq.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab3.setTextColor(getResources().getColor(R.color.use_ranking_tab_color_s));
			tab4.setTextColor(Color.BLACK);
			break;
		case 4:
			tab1.setTextColor(Color.BLACK);
			tabmq.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab4.setTextColor(getResources().getColor(R.color.use_ranking_tab_color_s));
			break;
		}
	}

	private class PageChangeListener implements MyViewPager.OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			changeTextColor(arg0);
		}

		@Override
		public void onPageScrolled(int position, float offset, int arg2) {
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bottomBar.getLayoutParams();
			lp.leftMargin = (int) ((position + offset) * bottomBarWidth);
			bottomBar.setLayoutParams(lp);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.jifen_tab1:
			mViewPager.setCurrentItem(0);
			changeTextColor(0);
			break;
		case R.id.jifen_tabmq:
			mViewPager.setCurrentItem(1);
			changeTextColor(1);
			break;
		case R.id.jifen_tab2:
			mViewPager.setCurrentItem(2);
			changeTextColor(2);
			break;
		case R.id.jifen_tab3:
			mViewPager.setCurrentItem(3);
			changeTextColor(3);
			break;
		case R.id.jifen_tab4:
			mViewPager.setCurrentItem(4);
			changeTextColor(4);
			break;
		}
	}
}
