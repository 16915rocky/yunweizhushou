package com.chinamobile.yunweizhushou.ui.newBandService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphNewBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListNewAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphListActivity extends BaseActivity {

    @BindView(R.id.rt_common)
    MyRefreshLayout rtCommon;
    private ListView listview;
    private String fkId;
    private RechargeFunctionListNewAdapter adapter;
    private List<RechargeFunctionGraphNewBean.ItemsList> beans;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = getIntent().getStringExtra("action");
        setContentView(R.layout.common_listview_with_refresh);
        ButterKnife.bind(this);
        initView();
        initEvent();
        initRequest();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("宽带专区");
        getTitleBar().setLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(GraphListActivity.this, NewBandServiceActivity.class);
                intent.putExtra("id", position);
                intent.putExtra("title", beans.get(position).getTitle());
                startActivity(intent);
            }
        });
        rtCommon.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRequest();
            }
        });
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", action);
        map.put("time", "6h");
        startTask(HttpRequestEnum.enum_govern_analysis_successrate_graph_list, ConstantValueUtil.URL + "BroadBand?",
                map, true);

    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if(rtCommon.isShown()){
            rtCommon.setRefreshing(false);
        }
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_govern_analysis_successrate_graph_list:
                if (responseBean.isSuccess()) {
                    Type t = new TypeToken<List<RechargeFunctionGraphNewBean.ItemsList>>() {
                    }.getType();
                    beans = new Gson().fromJson(responseBean.getDATA(), t);
                    adapter = new RechargeFunctionListNewAdapter(this, beans);
                    listview.setAdapter(adapter);

                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                }
                break;

            default:
                break;
        }
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.common_listview);
    }
}
