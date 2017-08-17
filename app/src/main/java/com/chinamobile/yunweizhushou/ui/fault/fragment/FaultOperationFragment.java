package com.chinamobile.yunweizhushou.ui.fault.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NameValueBean;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphNewBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.commonView.adapter.CommonBarChartListAdapter;
import com.chinamobile.yunweizhushou.ui.fault.adapters.FaultOperationAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/11.
 */

public class FaultOperationFragment extends BaseFragment {
    @BindView(R.id.common_listview)
    MyListView commonListview;
    @BindView(R.id.lv_list)
    MyListView lvList;
    Unbinder unbinder;
    private List<RechargeFunctionGraphNewBean.ItemsList> mList;
    private CommonBarChartListAdapter mAdapter;
    private List<NameValueBean> dayList;
    private  FaultOperationAdapter dayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fault_operation, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRequest();
        return view;
    }
    private void initRequest() {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "findAllLevelBar");
        startTask(HttpRequestEnum.enum_fault_operation, ConstantValueUtil.URL + "Trouble?", maps, false);
    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_fault_operation:
                try {
                    JSONObject jo = new JSONObject(responseBean.getDATA());
                    String dayStr = jo.getString("dayList");
                    String barStr = jo.getString("barList");
                    Type t=new TypeToken<List<NameValueBean>>(){}.getType();
                    dayList=new Gson().fromJson(dayStr,t);
                    dayAdapter=new FaultOperationAdapter(getActivity(),dayList,R.layout.item_list_2);
                   if(dayAdapter!=null){
                       lvList.setAdapter(dayAdapter);
                   }
                    Type t2=new TypeToken<List<RechargeFunctionGraphNewBean.ItemsList>>(){}.getType();
                    mList=new Gson().fromJson(barStr,t2);
                    mAdapter=new CommonBarChartListAdapter(getActivity(),mList);
                    if(mAdapter!=null) {
                        commonListview.setAdapter(mAdapter);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
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
