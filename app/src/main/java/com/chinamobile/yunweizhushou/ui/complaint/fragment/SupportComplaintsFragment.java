package com.chinamobile.yunweizhushou.ui.complaint.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.complaint.adapter.ComplainTodayListAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplainTodayBean;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplaintTodayPopupRankBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class SupportComplaintsFragment extends BaseFragment {
    private TextView supportComplain, serviceComplain, todayNum, monthNum, todayPer, monthPer, yearPer, yearText,
            yearNum, hotComplain;
    private ImageView todayState, monthState, yearState;
    private ComplainTodayBean todayBean;
    private MyGridView topGridView, increaseGridView;
    private ComplainTodayListAdapter mAdapter;
    private LinearLayout nochartLayout, chartLayout;
    private LineChart mLineChart;
    private TextView chartBtn1, chartBtn2, chartTitle;
    private LinearLayout addHotLayout;
    private String doubleY;
    private boolean isGuangyi;
    private ListView popupListView;
    private List<ComplaintTodayPopupRankBean> popupList;
    private TextView popupTitle;
    private PopupWindow popupWindow;
    private View popupView;
    private ImageView cancel;
    private LinearLayout totalLayout;
    private String name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if(arguments!=null){
            isGuangyi=arguments.getBoolean("isGuangyi");
        }
        View view=inflater.inflate(R.layout.fragment_support_complaints,container,false);
        initView(view);
        transformLayout(1);
        initSupportRequest();
        return view;
    }
    private void transformLayout(int type) {
        switch (type) {
            case 1:
                if (nochartLayout.getVisibility() == View.GONE) {
                    nochartLayout.setVisibility(View.VISIBLE);
                    chartLayout.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (chartLayout.getVisibility() == View.GONE) {
                    chartLayout.setVisibility(View.VISIBLE);
                    nochartLayout.setVisibility(View.GONE);
                }
                break;

            default:
                break;
        }
    }

    private void initView(View view) {
        supportComplain = (TextView) view.findViewById(R.id.complain_btn_support);
        serviceComplain = (TextView) view.findViewById(R.id.complain_btn_service);
        hotComplain = (TextView) view.findViewById(R.id.complain_btn_hot);
        todayNum = (TextView) view.findViewById(R.id.complain_num_today);
        monthNum = (TextView) view.findViewById(R.id.complain_num_month);
        yearNum = (TextView) view.findViewById(R.id.complain_num_year);
        todayPer = (TextView) view.findViewById(R.id.complain_percent_today);
        monthPer = (TextView) view.findViewById(R.id.complain_percent_month);
        yearPer = (TextView) view.findViewById(R.id.complain_percent_year);
        todayState = (ImageView) view.findViewById(R.id.complain_today_state);
        monthState = (ImageView) view.findViewById(R.id.complain_month_state);
        yearState = (ImageView) view.findViewById(R.id.complain_year_state);
        yearText = (TextView) view.findViewById(R.id.complain_year_huan);

        mLineChart = (LineChart) view.findViewById(R.id.complain_today_chart);
        chartBtn1 = (TextView) view.findViewById(R.id.complain_today_chart_btn1);
        chartBtn2 = (TextView) view.findViewById(R.id.complain_today_chart_btn2);
        chartTitle = (TextView) view.findViewById(R.id.complain_today_chart_title);

        nochartLayout = (LinearLayout) view.findViewById(R.id.complain_today_nochart_layout);
        chartLayout = (LinearLayout) view.findViewById(R.id.complain_today_chart_layout);

        addHotLayout = (LinearLayout) view.findViewById(R.id.complain_hot_tips_layout);

        topGridView = (MyGridView) view.findViewById(R.id.complain_preday_num_gridview);
        increaseGridView = (MyGridView) view.findViewById(R.id.complain_preday_increase_num_gridview);

        totalLayout = (LinearLayout) view.findViewById(R.id.complaint_today_total_layout);
    }
    private void initSupportRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "kftoday");
        startTask(HttpRequestEnum.enum_complain_today, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
    }
    @SuppressLint("NewApi")
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_complain_today:
                if (responseBean.isSuccess()) {
                    todayBean = new Gson().fromJson(responseBean.getDATA(), ComplainTodayBean.class);
                    todayNum.setText(todayBean.getToday());
                    monthNum.setText(todayBean.getMonth());
                    yearNum.setText(todayBean.getYear());
                    todayPer.setText(todayBean.getTodayIncrease());
                    monthPer.setText(todayBean.getMonthIncrease());
                    // yearPer.setText(todayBean.getYearIncrease());

                    if (isGuangyi) {
                        yearPer.setVisibility(View.VISIBLE);
                        yearText.setVisibility(View.VISIBLE);
                        yearState.setVisibility(View.VISIBLE);
                        yearState.setImageResource(todayBean.getYearCode().equals("1") ? R.mipmap.icon_arrow_up
                                : R.mipmap.icon_arrow_down);
                        yearPer.setText(todayBean.getYearIncrease());
                    } else {
                        yearState.setVisibility(View.GONE);
                        yearPer.setVisibility(View.GONE);
                        yearText.setVisibility(View.GONE);
                    }

                    todayState.setImageResource(
                            todayBean.getTodayCode().equals("1") ? R.mipmap.icon_arrow_up : R.mipmap.icon_arrow_down);
                    monthState.setImageResource(
                            todayBean.getMonthCode().equals("1") ? R.mipmap.icon_arrow_up : R.mipmap.icon_arrow_down);

                    mAdapter = new ComplainTodayListAdapter(getActivity(), todayBean.getTop10List(),
                            R.layout.item_complain_today);
                    topGridView.setAdapter(mAdapter);
                    mAdapter = new ComplainTodayListAdapter(getActivity(), todayBean.getTop10IncreaseList(),
                            R.layout.item_complain_today);
                    increaseGridView.setAdapter(mAdapter);

                    if (!TextUtils.isEmpty(todayBean.getHots())) {
                        addHotLayout.setVisibility(View.VISIBLE);
                        addHotLayout.removeAllViews();
                        addHotLayout.setPadding(30, 0, 30, 0);
                        String[] tips = todayBean.getHots().split(";");
                        for (int i = 0; i < tips.length; i++) {
                            TextView tv = new TextView(getActivity());
                            tv.setTextSize(12);
                            tv.setText(tips[i]);
                            Drawable drawable = getActivity().getResources().getDrawable(R.mipmap.icon_hot);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tv.setCompoundDrawables(drawable, null, null, null);
                            addHotLayout.addView(tv);
                        }
                    } else {
                        addHotLayout.setVisibility(View.GONE);
                    }
                } else {
                    Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
                }
                break;
            default:
                break;
        }
    }
}
