package com.chinamobile.yunweizhushou.ui.officeDataZone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.officeDataZone.adapter.OfficeReleaseAdapter;
import com.chinamobile.yunweizhushou.ui.officeDataZone.beans.OfficeReleaseBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.chinamobile.yunweizhushou.R.id.tv_time;

/**
 * Created by Administrator on 2017/7/24.
 */

public class OfficeReleaseFragment extends BaseFragment {


    @BindView(tv_time)
    TextView tvTime;
    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    private int year;
    private int month;
    private int day;
    private String currentDate;
    private List<OfficeReleaseBean> mlist;
    private OfficeReleaseAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office_release, container, false);
        unbinder = ButterKnife.bind(this, view);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);
        currentDate = Utils.getCurrentTime();
        tvTime.setText(year+"年"+month+"月"+day);
        initRequest(currentDate);
        initEvent();

        return view;
    }

    private void initEvent() {
        tvTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SelectDayDialog dialog = new SelectDayDialog(getActivity());
                dialog.show();
                dialog.setBirthdayListener(new SelectDayDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        String currentDate1 = year + "年" + month + "月"+day+"日";
                        currentDate = year + "-" + month+"-"+day;
                        tvTime.setText(currentDate1);
                        initRequest(currentDate);
                    }
                });
            }
        });
    }

    private void initRequest(String time) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "getOfficeReleaseCheck");
        maps.put("time", time);
        startTask(HttpRequestEnum.enum_office_release, ConstantValueUtil.URL + "OfficeDataZone?", maps, false);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_office_release:
                if (responseBean.isSuccess()) {
                    Type t = new TypeToken<List<OfficeReleaseBean>>() {
                    }.getType();
                    mlist = new Gson().fromJson(responseBean.getDATA(), t);
                    mAdapter = new OfficeReleaseAdapter(getActivity(), mlist, R.layout.item_office_release);
                    if(listView!=null) {
                        listView.setAdapter(mAdapter);
                    }
                }
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
