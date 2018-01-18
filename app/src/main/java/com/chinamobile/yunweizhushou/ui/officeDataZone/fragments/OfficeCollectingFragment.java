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
import com.chinamobile.yunweizhushou.ui.officeDataZone.adapter.OfficeCollectingAdapter;
import com.chinamobile.yunweizhushou.ui.officeDataZone.beans.OfficeCollectingBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.chinamobile.yunweizhushou.R.id.tv_desc;
import static com.chinamobile.yunweizhushou.R.id.tv_time;

/**
 * Created by Administrator on 2017/7/24.
 */

public class OfficeCollectingFragment extends BaseFragment {


    @BindView(tv_time)
    TextView tvTime;
    @BindView(tv_desc)
    TextView tvDesc;
    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    private int year;
    private int month;
    private int day;
    private String currentDate;
    private List<OfficeCollectingBean> mlist;
    private OfficeCollectingAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office_collecting, container, false);
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
        maps.put("action", "getOfficeCollecting");
        maps.put("time", time);
        startTask(HttpRequestEnum.enum_office_collecting, ConstantValueUtil.URL + "OfficeDataZone?", maps, false);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_office_collecting:
                if (responseBean.isSuccess()) {

                    try {
                        JSONObject jo = new JSONObject(responseBean.getDATA());
                        String listStr = jo.getString("list");
                        String total = jo.getString("total");
                        String exception = jo.getString("exception");
                        tvDesc.setText("共"+total+"类，异常"+exception+"类");
                        Type t = new TypeToken<List<OfficeCollectingBean>>() {
                        }.getType();
                        mlist = new Gson().fromJson(listStr, t);
                        mAdapter = new OfficeCollectingAdapter(getActivity(), mlist, R.layout.item_office_collecting);
                        if(listView!=null) {
                            listView.setAdapter(mAdapter);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
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
