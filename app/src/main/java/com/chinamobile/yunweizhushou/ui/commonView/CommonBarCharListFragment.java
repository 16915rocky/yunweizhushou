package com.chinamobile.yunweizhushou.ui.commonView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphNewBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.commonView.adapter.CommonBarChartListAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CommonBarCharListFragment extends BaseFragment {
    @BindView(R.id.common_listview)
    ListView commonListview;
    Unbinder unbinder;
    private List<RechargeFunctionGraphNewBean.ItemsList> mList;
    private CommonBarChartListAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_listview_notitle, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRequest();

        return view;
    }
    private void initRequest() {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "findRunTargetBar");
        startTask(HttpRequestEnum.enum_user_peception_list_bar, ConstantValueUtil.URL + "KEIServlet?", maps, false);
    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_user_peception_list_bar:
                try {
                    Type t=new TypeToken<List<RechargeFunctionGraphNewBean.ItemsList>>(){}.getType();
                    mList=new Gson().fromJson(responseBean.getDATA(),t);
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
