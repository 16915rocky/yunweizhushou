package com.chinamobile.yunweizhushou.ui.functionAnalysis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.FAMoreCenterActivity;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FAList2Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/27.
 */

public class ESBPerformanceFragment extends BaseFragment {
    @BindView(R.id.list_title_4_item1)
    TextView listTitle4Item1;
    @BindView(R.id.list_title_4_item2)
    TextView listTitle4Item2;
    @BindView(R.id.list_title_4_item3)
    TextView listTitle4Item3;
    @BindView(R.id.list_title_4_item4)
    TextView listTitle4Item4;
    @BindView(R.id.common_list_4_listview)
    ListView commonList4Listview;

    @BindView(R.id.tv_button)
    TextView tvButton;
    Unbinder unbinder;
    private List<FAList2Bean> mList;
    private LinearLayout titleId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_esb_performance, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        initRequest();
        return view;
    }

    private void initView(View view) {
        listTitle4Item1.setText("接口编号");
        listTitle4Item2.setText("成功率");
        listTitle4Item3.setText("耗时");
        listTitle4Item4.setText("调用量");
        titleId= (LinearLayout) view.findViewById(R.id.common_list_4_head);
        titleId.setVisibility(View.GONE);
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FAMoreCenterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getAppointInteOverTime");
        startTask(HttpRequestEnum.enum_function_analysis_performmance_list2, ConstantValueUtil.URL + "SpecialTreatment?",
                map);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        if (responseBean.isSuccess()) {
            switch (e) {
                case enum_function_analysis_performmance_list2:
                    Type type = new TypeToken<List<FAList2Bean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), type);
                    commonList4Listview.setAdapter(new CommonAdapter<FAList2Bean>(getActivity(), R.layout.item_list_4, mList) {
                        @Override
                        protected void convert(ViewHolder viewHolder, FAList2Bean item, int position) {
                            viewHolder.setText(R.id.list_4_item1, item.getService_key());
                            viewHolder.setText(R.id.list_4_item2, item.getSys_success() + "%");
                            viewHolder.setText(R.id.list_4_item3, item.getTime_consume());
                            viewHolder.setText(R.id.list_4_item4, item.getTotal_cnt());
                        }
                    });
                    break;
                default:break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
