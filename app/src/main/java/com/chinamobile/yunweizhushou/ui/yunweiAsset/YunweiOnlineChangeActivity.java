package com.chinamobile.yunweizhushou.ui.yunweiAsset;

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
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.fragments.YunweiSchema2Fragment3;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YunweiOnlineChangeActivity extends BaseActivity implements OnClickListener {

	

	private TextView item1, item2;
	private View bottomBar;
	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	
	private TextView time1, time2, time3;
	private int dayNum = 0, monthNum = 0,date=0;
	private String content;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		content=getIntent().getStringExtra("content");
		Calendar cal = Calendar.getInstance();
		dayNum = cal.getActualMaximum(Calendar.DATE);
		monthNum = cal.get(Calendar.MONTH) + 1;
		date=cal.get(Calendar.DATE);
		setContentView(R.layout.activity_yunwei_asset_tac);
		initView();
		initViewPager();
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(content);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		LayoutParams params = bottomBar.getLayoutParams();
//		DisplayMetrics metric = new DisplayMetrics();  
//		getWindowManager().getDefaultDisplay().getMetrics(metric);  
//		bottomBarWidth = metric.widthPixels; 
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				resetAll();
				if(0==arg0){
					item1.setTextColor(getResources().getColor(R.color.color_blue));
				}else{
					item2.setTextColor(getResources().getColor(R.color.color_blue));
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int offsetPx) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bottomBar.getLayoutParams();
				lp.leftMargin=(int) ((position+positionOffset)*bottomBarWidth);
				bottomBar.setLayoutParams(lp);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initViewPager() {
		fragmentList=new ArrayList<Fragment>();
		if("新业务上线".equals(content)){
			YunweiSchema2Fragment3 fragment1= new YunweiSchema2Fragment3();
			Bundle bundle1 = new Bundle();
			bundle1.putString("type","online_server");
			fragment1.setArguments(bundle1);
			fragmentList.add(fragment1);

			YunweiSchema2Fragment3 fragment2= new YunweiSchema2Fragment3();
			Bundle bundle2 = new Bundle();
			bundle2.putString("type","online_process");
			fragment2.setArguments(bundle2);
			fragmentList.add(fragment2);

		}else{
			YunweiSchema2Fragment3 fragment3= new YunweiSchema2Fragment3();
			Bundle bundle3 = new Bundle();
			bundle3.putString("type","change_server");
			fragment3.setArguments(bundle3);
			fragmentList.add(fragment3);

			YunweiSchema2Fragment3 fragment4= new YunweiSchema2Fragment3();
			Bundle bundle4 = new Bundle();
			bundle4.putString("type","change_process");
			fragment4.setArguments(bundle4);
			fragmentList.add(fragment4);

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

		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(mAdapter);
		
		
	}

	private void initView() {
		item1 = (TextView) findViewById(R.id.rule_item1);
		item1.setText("服务器");
		item2 = (TextView) findViewById(R.id.rule_item2);
		item2.setText("进程");
		time1 = (TextView) findViewById(R.id.time1);
		time2 = (TextView) findViewById(R.id.time2);
		time3 = (TextView) findViewById(R.id.time3);
		time1.setText(monthNum + "");
		time2.setText(monthNum + "");
		time3.setText(date + "");
		viewPager = (ViewPager) findViewById(R.id.rule_viewpager);
	
		bottomBar = findViewById(R.id.rule_bottom_bar);
		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
		
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.rule_item1:
			viewPager.setCurrentItem(0);
			item1.setTextColor(getResources().getColor(R.color.color_blue));			
			break;
		case R.id.rule_item2:
			viewPager.setCurrentItem(1);
			item2.setTextColor(getResources().getColor(R.color.color_blue));			
			break;

		default:
			break;
		}
		
		
	}
	
	public  void resetAll() {
		item1.setTextColor(getResources().getColor(R.color.color_black));
		item2.setTextColor(getResources().getColor(R.color.color_black));
		
	}
	
	

}
