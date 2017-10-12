package com.chinamobile.yunweizhushou.ui.monitor;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.customerCenter.adapter.MyFragmentPagerAdapter;
import com.chinamobile.yunweizhushou.ui.monitor.fragment.GraphListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MonitorActivity extends BaseActivity {
    @BindView(R.id.tb_ccm)
    TabLayout tbCcm;
    @BindView(R.id.vp_ccm)
    ViewPager vpCcm;
    private List<Fragment> fList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        ButterKnife.bind(this);
        initVP();
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("监控");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initVP() {
        fList=new ArrayList<Fragment>();
        GraphListFragment graphListFragment = new GraphListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fkId","1169");
        bundle.putString("extraKey","pament_business");
        bundle.putString("extraValue","10.78.130.103");
        graphListFragment.setArguments(bundle);
        fList.add(graphListFragment);
        GraphListFragment graphListFragment2 = new GraphListFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("fkId","1169");
        bundle2.putString("tab","2");
        bundle2.putString("extraKey","pament_business");
        bundle2.putString("extraValue","10.78.130.103");
        graphListFragment2.setArguments(bundle2);
        fList.add(graphListFragment2);
        String[] arr=new String[]{"主机","数据库"};
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fList, arr);
        vpCcm.setAdapter(myFragmentPagerAdapter);
        tbCcm.setupWithViewPager(vpCcm);
    }
}
