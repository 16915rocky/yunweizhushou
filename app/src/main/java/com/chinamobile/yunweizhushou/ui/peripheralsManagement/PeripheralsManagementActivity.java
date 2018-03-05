package com.chinamobile.yunweizhushou.ui.peripheralsManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.peripheralsManagement.bean.peripheralsBean;
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
 * Created by Administrator on 2018/2/9.
 */

public class PeripheralsManagementActivity extends BaseActivity {


    @BindView(R.id.lv_list)
    ListView lvList;
    private List<peripheralsBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_management);
        ButterKnife.bind(this);
        initRequest();
        initEvent();

    }

    private void initEvent() {
        getTitleBar().setMiddleText("外设管理");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTitleBar().setRightText("添加", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PeripheralsManagementActivity.this,PeripheralsAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getAllRecord");
        startTask(HttpRequestEnum.enum_peripher_management, ConstantValueUtil.URL + "ExternalEquipment?", map);
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
                case enum_peripher_management:
                    try {
                        Type t = new TypeToken<List<peripheralsBean>>() {
                        }.getType();
                        mList = new Gson().fromJson(responseBean.getDATA(), t);
                        lvList.setAdapter(new CommonAdapter<peripheralsBean>(this,R.layout.item_list_per,mList) {
                            @Override
                            protected void convert(ViewHolder viewHolder, peripheralsBean item, int position) {
                                viewHolder.setText(R.id.tv_item1,item.getCityName());
                                viewHolder.setText(R.id.tv_item2,item.getCategory());
                                viewHolder.setText(R.id.tv_item3,item.getGroupName());
                                viewHolder.setText(R.id.tv_item4,item.getEquipName());
                                viewHolder.setText(R.id.tv_item5,item.getUseRate()+"%");
                                viewHolder.setText(R.id.tv_item6,item.getUseNum());
                                viewHolder.setText(R.id.tv_item7,item.getUnuseNum());
                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        } else {
            Utils.ShowErrorMsg(this, responseBean.getMSG());
        }
    }
}
