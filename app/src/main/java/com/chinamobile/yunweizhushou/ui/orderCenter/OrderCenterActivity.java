package com.chinamobile.yunweizhushou.ui.orderCenter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.logZone.fragments.GraphListFragment2;
import com.chinamobile.yunweizhushou.ui.orderCenter.adapter.ViewPagerAdapter;
import com.chinamobile.yunweizhushou.ui.orderCenter.fragments.OrderCenterBroadbandFragment;
import com.chinamobile.yunweizhushou.ui.orderCenter.fragments.OrderMQFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderCenterActivity extends BaseActivity implements OnClickListener {

	private MyViewPager mViewPager;
	private List<Fragment> fragments = new ArrayList<>();
	private FragmentPagerAdapter mAdapter;
	private TextView tab1, tab2, tab3, tab4;
	private View tab1BottomView;
	private View tab2BottomView;
	private View tab3BottomView;
	private View tab4BottomView;
	private List<TextView> tabs;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_center);
		initView();
		initRequest();
		setEvent();
		initFragment();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1033");
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

	private void initFragment() {
		fragments = new ArrayList<>();

		fragments.add(new OrderMQFragment());

		// for (int i = 0; i < tabs.size(); i++) {
		//RechargeFunctionFragment fragment = new RechargeFunctionFragment();
		//Bundle bundle = new Bundle();
		//bundle.putString("upId", "1032");// 失败量
		//fragment.setArguments(bundle);
		GraphListFragment2 graphListFragment2 = new GraphListFragment2();
		Bundle bundle = new Bundle();
		bundle.putString("fkId","1141");
		graphListFragment2.setArguments(bundle);
		fragments.add(graphListFragment2);
		fragments.add(new OrderCenterBroadbandFragment());
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, fragments);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOffscreenPageLimit(0);// 设置缓存个数

		getTitleBar().setMiddleText("订单中心");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		tabs = new ArrayList<>();
		mViewPager = (MyViewPager) findViewById(R.id.function_analysis_viewpager);
		tab1 = (TextView) findViewById(R.id.tab1);
		tab2 = (TextView) findViewById(R.id.tab2);
		tab3 = (TextView) findViewById(R.id.tab3);
		tab4 = (TextView) findViewById(R.id.tab4);
		tab1BottomView = findViewById(R.id.tab1BottomView);
		tab2BottomView = findViewById(R.id.tab2BottomView);
		tab3BottomView = findViewById(R.id.tab3BottomView);
		tabs.add(tab1);
		tabs.add(tab2);
		tabs.add(tab3);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void setEvent() {
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
	}

	private class PageChangeListener implements MyViewPager.OnPageChangeListener {
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
			tab1.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab2.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab1BottomView.setVisibility(View.VISIBLE);
			tab2BottomView.setVisibility(View.GONE);
			tab3BottomView.setVisibility(View.GONE);
		} else if (position == 1) {
			tab1.setTextColor(Color.BLACK);
			tab3.setTextColor(Color.BLACK);
			tab2.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab1BottomView.setVisibility(View.GONE);
			tab3BottomView.setVisibility(View.GONE);
			tab2BottomView.setVisibility(View.VISIBLE);
		} else {
			tab1.setTextColor(Color.BLACK);
			tab2.setTextColor(Color.BLACK);
			tab3.setTextColor(getResources().getColor(R.color.color_lightblue));
			tab1BottomView.setVisibility(View.GONE);
			tab2BottomView.setVisibility(View.GONE);
			tab3BottomView.setVisibility(View.VISIBLE);
		}
	}

	private void changeSelectedTab(int position) {
		tab1.setSelected(position == 0 ? true : false);
		tab2.setSelected(position == 1 ? true : false);
		tab3.setSelected(position == 2 ? true : false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab1:
			changeTabTextBG(0);
			changeSelectedTab(0);
			mViewPager.setCurrentItem(0);
			break;
		case R.id.tab2:
			changeTabTextBG(1);
			changeSelectedTab(1);
			mViewPager.setCurrentItem(1);
			break;
		case R.id.tab3:
			changeTabTextBG(2);
			changeSelectedTab(2);
			mViewPager.setCurrentItem(2);
			break;
		}
	}

}
