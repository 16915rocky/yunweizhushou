package com.chinamobile.yunweizhushou.ui.customerCenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.customerCenter.bean.CustomerCenterESBBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
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
 * Created by Administrator on 2017/9/1.
 */

public class CustomerCenterESBFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.titleid)
    LinearLayout titleid;
    @BindView(R.id.common_list_3_listview)
    ListView commonList3Listview;
    @BindView(R.id.common_list_3_layout)
    LinearLayout commonList3Layout;
    @BindView(R.id.list_title_3_item1)
    TextView listTitle3Item1;
    @BindView(R.id.list_title_3_item2)
    TextView listTitle3Item2;
    @BindView(R.id.list_title_3_item3)
    TextView listTitle3Item3;
    @BindView(R.id.search_edittext)
    EditText searchEdittext;
    @BindView(R.id.search_btn)
    TextView searchBtn;
    @BindView(R.id.myRefreshLayout)
    MyRefreshLayout myRefreshLayout;
    private String searchContent = "";
    private List<CustomerCenterESBBean> mList;
    private String csf_server_code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            csf_server_code = arguments.getString("csf_server_code");
        }
        View view = inflater.inflate(R.layout.fragment_esb, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRequest();
        initEvent();
        titleid.setVisibility(View.GONE);
        listTitle3Item1.setText("接口名称");
        listTitle3Item2.setText("调用量");
        listTitle3Item3.setText("成功率");

        return view;
    }


    private void initEvent() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchContent = searchEdittext.getText().toString();
                initRequest();
            }
        });
        commonList3Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("fkId", "1168");
                intent.putExtra("extraKey", "service_key");
                intent.putExtra("extraValue", mList.get(position).getGroupfield_value());
                intent.putExtra("time", "1h");
                intent.putExtra("userId", "");
                intent.setClass(getActivity(), GraphListActivity2.class);
                getActivity().startActivity(intent);
            }
        });
        myRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRequest();
            }
        });
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getEsbList");
        map.put("csf_server_code", csf_server_code);
        map.put("osb_server_code", searchContent);
        startTask(HttpRequestEnum.enum_opencenter_backlog, ConstantValueUtil.URL + "MessageQueue?", map, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if(myRefreshLayout.isShown()){
            myRefreshLayout.setRefreshing(false);
        }
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_opencenter_backlog:
                if (responseBean.isSuccess()) {
                    Type type = new TypeToken<List<CustomerCenterESBBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), type);
                    // LinearLayout linearLayout = dynamicSettingView();
                    //commonListview.addHeaderView(linearLayout);
                    commonList3Listview.setAdapter(new CommonAdapter<CustomerCenterESBBean>(getActivity(), R.layout.item_list_3, mList) {
                        @Override
                        protected void convert(ViewHolder viewHolder, CustomerCenterESBBean item, int position) {
                            viewHolder.setText(R.id.list_3_item1, item.getGroupfield_value());
                            viewHolder.setText(R.id.list_3_item2, item.getValue());
                            viewHolder.setText(R.id.list_3_item3, item.getAvg() + "%");
                        }
                    });
                } else {
                    Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
                }
                break;
            default:
                break;
        }
    }

    private LinearLayout dynamicSettingView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout view = new LinearLayout(getActivity());
        view.setLayoutParams(lp);
        view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_blue));
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv1 = new TextView(getActivity());
        TextView tv2 = new TextView(getActivity());
        TextView tv3 = new TextView(getActivity());
        tv1.setLayoutParams(vlp);
        tv2.setLayoutParams(vlp);
        tv3.setLayoutParams(vlp);
        tv1.setText("service_key");
        tv2.setText("调用量");
        tv3.setText("成功");
        view.addView(tv1);
        view.addView(tv2);
        view.addView(tv3);
        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
