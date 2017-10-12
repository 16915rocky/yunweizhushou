package com.chinamobile.yunweizhushou.ui.roam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.roam.bean.RoamBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
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

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;

/**
 * Created by Administrator on 2017/9/12.
 */

public class RoamActivity extends BaseActivity {
    @BindView(R.id.DBM_LinearLayout)
    LinearLayout DBMLinearLayout;
    @BindView(R.id.common_list_3_layout)
    LinearLayout commonList3Layout;
    @BindView(R.id.list_title_3_item1)
    TextView listTitle3Item1;
    @BindView(R.id.list_title_3_item2)
    TextView listTitle3Item2;
    @BindView(R.id.list_title_3_item3)
    TextView listTitle3Item3;
    @BindView(R.id.common_list_3_listview)
    MyListView commonList3Listview;
    @BindView(R.id.myRefreshLayout)
    MyRefreshLayout myRefreshLayout;
    private List<RoamBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roam);
        ButterKnife.bind(this);
        ininView();
        initRequest();
    }

    private void ininView() {
        listTitle3Item1.setText("时间");
        listTitle3Item2.setText("名称");
        listTitle3Item3.setText("数值");
        commonList3Layout.setVisibility(View.GONE);
        getTitleBar().setMiddleText("漫游专区");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRequest();
            }
        });
        commonList3Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("fkId", "1170");
                intent.putExtra("extraKey", "pament_business");
                intent.putExtra("extraValue", mList.get(i).getName());
                intent.setClass(getActivity(), GraphListActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "findRoaming");
        startTask(HttpRequestEnum.enum_roam, ConstantValueUtil.URL + "HotZone?", map, true);
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
            case enum_roam:
                if (responseBean.isSuccess()) {
                    Type type = new TypeToken<List<RoamBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), type);
                    commonList3Listview.setAdapter(new CommonAdapter<RoamBean>(this, R.layout.item_list_3, mList) {
                        @Override
                        protected void convert(ViewHolder viewHolder, RoamBean item, int position) {
                            viewHolder.setText(R.id.list_3_item1, item.getOrg_time());
                            viewHolder.setText(R.id.list_3_item2, item.getName());
                            viewHolder.setText(R.id.list_3_item3, item.getValue());
                        }
                    });
                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                }
                break;
            default:
                break;
        }
    }
}
