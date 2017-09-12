package com.chinamobile.yunweizhushou.ui.newFaceRecognititon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListDoubleYFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;

import java.util.ArrayList;
import java.util.List;

public class FaceRecognititonManageActivity extends BaseActivity implements View.OnClickListener {
    private TextView item1, item2;
    private View bottomBar;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private FragmentPagerAdapter mAdapter;
    private int bottomBarWidth;
    private TextView tv_phone,tv_name;
    private  ImageView img_charge_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_recognititon_manage);
        initView();

        initViewPager();
        initEvent();
    }

    private void initViewPager() {

        fragmentList = new ArrayList<>();
        NewFaceRecognititonFragment newFaceRecognititonFragment = new NewFaceRecognititonFragment();
        fragmentList.add(newFaceRecognititonFragment);
        GraphListDoubleYFragment graphListDoubleYFragment= new GraphListDoubleYFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("fkId","1167");
        graphListDoubleYFragment.setArguments(bundle2);
        fragmentList.add(graphListDoubleYFragment);




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
        getTitleBar().setMiddleText("人脸识别");
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
        item1 = (TextView) findViewById(R.id.rule_item1);
        item2 = (TextView) findViewById(R.id.rule_item2);
        viewPager = (ViewPager) findViewById(R.id.rule_viewpager);
        bottomBar = findViewById(R.id.rule_bottom_bar);
        item1.setTextColor(getResources().getColor(R.color.color_lightblue));
        img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
        tv_name=(TextView) findViewById(R.id.tv_name);
        tv_phone=(TextView) findViewById(R.id.tv_phone);
    }

    @Override
    public void onClick(View v) {
        resetAll();
        switch (v.getId()) {
            case R.id.rule_item1:
                viewPager.setCurrentItem(0);
                item1.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            case R.id.rule_item2:
                viewPager.setCurrentItem(1);
                item2.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            default:
                break;
        }
    }

    private void resetAll() {
        item1.setTextColor(getResources().getColor(R.color.color_black));
        item2.setTextColor(getResources().getColor(R.color.color_black));
    }
}
