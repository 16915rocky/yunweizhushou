package com.chinamobile.yunweizhushou.ui.newFaceRecognititon;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.newFaceRecognititon.adapter.FIEIDDetailAdapter;
import com.chinamobile.yunweizhushou.ui.newFaceRecognititon.bean.FILEIDDetailBean;
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

/**
 * Created by Administrator on 2017/8/15.
 */

public class FILEIDDetailActivity extends BaseActivity {
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
    ListView commonList3Listview;
    private List<FILEIDDetailBean> mList;
    private FIEIDDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_3);
        ButterKnife.bind(this);
        initView();
        initRequest();
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("地市详情");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRequest() {
        HashMap map2=new HashMap<String,String>();
        map2.put("action", "getCRMAPPDetail");
        startTask(HttpRequestEnum.enum_crm_app_Detail, ConstantValueUtil.URL + "RealNameMonitoring?", map2, true);
    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_crm_app_Detail:
                Type t = new TypeToken<List<FILEIDDetailBean>>(){}.getType();
                mList=new Gson().fromJson(responseBean.getDATA(),t);
                mAdapter=new FIEIDDetailAdapter(this,mList,R.layout.item_list_3);
                commonList3Listview.setAdapter(mAdapter);
                break;
            default:
                break;
        }
    }

    private void initView() {
        commonList3Layout.setVisibility(View.GONE);
        listTitle3Item1.setText("地市");
        listTitle3Item2.setText("当前值");
        listTitle3Item3.setText("返回值");
    }
}
