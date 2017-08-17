package com.chinamobile.yunweizhushou.ui.unifiedQuery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.unifiedQuery.adapters.MonthDataTabAdapter;
import com.chinamobile.yunweizhushou.ui.unifiedQuery.beans.MonthDataBean;
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

import static com.chinamobile.yunweizhushou.R.id.tv_commit;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MonthDataTabFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.lv_common)
    ListView lvCommon;
    @BindView(tv_commit)
    TextView tvCommit;
    Unbinder unbinder;
    private List<MonthDataBean> mList;
    private MonthDataTabAdapter mAdapter;
    private String category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if(arguments!=null){
            category=arguments.getString("category");
        }
        View view = inflater.inflate(R.layout.fragment_month_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRequest(category);
        initEvent();
        return view;
    }

    private void initEvent() {
        tvCommit.setOnClickListener(this);
    }

    private void initRequest(String category) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "findMonthData");
        map.put("category", category);
        startTask(HttpRequestEnum.enum_month_data_tab, ConstantValueUtil.URL + "KEIServlet?", map);

    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_month_data_tab:
                Type t2 = new TypeToken<List<MonthDataBean>>() {
                }.getType();
                mList = new Gson().fromJson(responseBean.getDATA(), t2);
                mAdapter = new MonthDataTabAdapter(getActivity(),mList,R.layout.item_month_data );
                lvCommon.setAdapter(mAdapter);
            default:
                break;

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_commit:

        }

    }
}
