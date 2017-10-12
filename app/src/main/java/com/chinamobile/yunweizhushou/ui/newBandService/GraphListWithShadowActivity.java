package com.chinamobile.yunweizhushou.ui.newBandService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.adapter.RechargeFunctionListShadowAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.bean.GraphBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;

/**
 * Created by Administrator on 2017/8/9.
 */

public class GraphListWithShadowActivity extends BaseActivity {

    private ListView listview;
    private String fkId;
    private RechargeFunctionListShadowAdapter adapter;
    private List<GraphBean> mList;
    private LinearLayout titleLt;
    private String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action=getIntent().getStringExtra("action");
        setContentView(R.layout.common_listview);
        initView();
        initRequest();
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("宽带专区");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("id",i);
                intent.setClass(GraphListWithShadowActivity.this,NewBandServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        listview = (ListView)findViewById(R.id.common_listview);
        titleLt = (LinearLayout) findViewById(R.id.titleid);
    }
    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", action);
        startTask(HttpRequestEnum.enum_govern_analysis_successrate_graph_list, ConstantValueUtil.URL + "BroadBand?",
                map, true);

    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_govern_analysis_successrate_graph_list:
                if (responseBean.isSuccess()) {
                    Type t = new TypeToken<List<GraphBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), t);
                    adapter = new RechargeFunctionListShadowAdapter(getActivity(), mList);
                    listview.setAdapter(adapter);
                } else {
                    Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
                }
                break;

            default:
                break;
        }
    }
}
