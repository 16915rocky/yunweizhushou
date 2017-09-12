package com.chinamobile.yunweizhushou.ui.customerCenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> fList;
    private String[] arr;
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list,String[] arr) {
        super(fm);
        this.fList=list;
        this.arr=arr;
    }

    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arr[position];
    }
}
