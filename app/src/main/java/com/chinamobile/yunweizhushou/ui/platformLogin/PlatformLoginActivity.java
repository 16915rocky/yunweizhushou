package com.chinamobile.yunweizhushou.ui.platformLogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.platformLogin.fragments.PLGraphListFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class PlatformLoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView item1, item2,item3,item4,item5,item6;
    private View bottomBar;
    private MyViewPager viewPager;
    private List<Fragment> fragmentList;
    private FragmentPagerAdapter mAdapter;
    private int bottomBarWidth;
    private TextView tv_phone,tv_name;
    private  ImageView img_charge_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_platform_login);
        initView();
        initViewPager();
        initEvent();
    }
  

    private void initViewPager() {

        fragmentList = new ArrayList<>();
        PLGraphListFragment plGraphListFragment1 = new PLGraphListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id","11180");
        plGraphListFragment1.setArguments(bundle);
        fragmentList.add(plGraphListFragment1);

        PLGraphListFragment plGraphListFragment2 = new PLGraphListFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("id","50000");
        plGraphListFragment2.setArguments(bundle2);
        fragmentList.add(plGraphListFragment2);

        PLGraphListFragment plGraphListFragment3 = new PLGraphListFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("id","50001");
        plGraphListFragment3.setArguments(bundle3);
        fragmentList.add(plGraphListFragment3);

        PLGraphListFragment plGraphListFragment4 = new PLGraphListFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("id","50002");
        plGraphListFragment4.setArguments(bundle4);
        fragmentList.add(plGraphListFragment4);

        PLGraphListFragment plGraphListFragment5 = new PLGraphListFragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("id","50003");
        plGraphListFragment5.setArguments(bundle5);
        fragmentList.add(plGraphListFragment5);

        PLGraphListFragment plGraphListFragment6 = new PLGraphListFragment();
        Bundle bundle6 = new Bundle();
        bundle6.putString("id","50004");
        plGraphListFragment6.setArguments(bundle6);
        fragmentList.add(plGraphListFragment6);






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
        getTitleBar().setMiddleText("平台登录量监控");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

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
        item1 = (TextView) findViewById(R.id.item1);
        item2 = (TextView) findViewById(R.id.item2);
        item3 = (TextView) findViewById(R.id.item3);
        item4 = (TextView) findViewById(R.id.item4);
        item5 = (TextView) findViewById(R.id.item5);
        item6 = (TextView) findViewById(R.id.item6);
        viewPager = (MyViewPager) findViewById(R.id.rule_viewpager);
        bottomBar = findViewById(R.id.rule_bottom_bar);
        item1.setTextColor(getResources().getColor(R.color.color_lightblue));


    }

    @Override
    public void onClick(View v) {
        resetAll();
        switch (v.getId()) {
            case R.id.item1:
                viewPager.setCurrentItem(0);
                item1.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            case R.id.item2:
                viewPager.setCurrentItem(1);
                item2.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            case R.id.item3:
                viewPager.setCurrentItem(2);
                item3.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            case R.id.item4:
                viewPager.setCurrentItem(3);
                item4.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            case R.id.item5:
                viewPager.setCurrentItem(4);
                item5.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            case R.id.item6:
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
