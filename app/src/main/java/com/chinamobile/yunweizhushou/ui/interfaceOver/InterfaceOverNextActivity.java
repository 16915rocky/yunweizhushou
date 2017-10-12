package com.chinamobile.yunweizhushou.ui.interfaceOver;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.interfaceOver.bean.InterfaceOverNextBean;
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

/**
 * Created by Administrator on 2017/9/21.
 */

public class InterfaceOverNextActivity extends BaseActivity {


    @BindView(R.id.lv_ion)
    ListView lvIon;
    private String state,title;
    private List<InterfaceOverNextBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state=getIntent().getStringExtra("state");
        title=getIntent().getStringExtra("title");
        setContentView(R.layout.activity_interface_over_next);
        ButterKnife.bind(this);
        initEvent();
        initRequest(state);

    }

    private void initEvent() {
        getTitleBar().setMiddleText(title);
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initRequest(String state) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getEventOptimRecord");
        map.put("state", state);
        startTask(HttpRequestEnum.enum_moneyout_check, ConstantValueUtil.URL + "SpecialTreatment?", map, true);

    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        if (responseBean.isSuccess()) {
            switch (e) {
                case enum_moneyout_check:
                    Type t = new TypeToken<List<InterfaceOverNextBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), t);
                    lvIon.setAdapter(new CommonAdapter<InterfaceOverNextBean>(this, R.layout.item_interfaceover_next_list, mList) {

                        @Override
                        protected void convert(ViewHolder viewHolder, InterfaceOverNextBean item, int position) {
                            viewHolder.setText(R.id.tv_item1, item.getEvent_description());
                            viewHolder.setText(R.id.tv_item2, item.getEvent_state());
                            viewHolder.setText(R.id.tv_item3, item.getOp_date());
                        }
                    });
                    break;
                default:
                    break;
            }
        } else {
            Utils.ShowErrorMsg(this, responseBean.getMSG());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
