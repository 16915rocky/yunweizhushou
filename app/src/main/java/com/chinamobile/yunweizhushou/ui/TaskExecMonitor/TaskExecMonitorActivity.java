package com.chinamobile.yunweizhushou.ui.TaskExecMonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.TaskExecMonitor.bean.TasExecMonitorBean;
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
 * Created by Administrator on 2017/11/13.
 */

public class TaskExecMonitorActivity extends BaseActivity {

    @BindView(R.id.search_edittext)
    EditText searchEdittext;
    @BindView(R.id.search_btn)
    TextView searchBtn;
    @BindView(R.id.lv_datas)
    ListView lvDatas;
    private List<TasExecMonitorBean> tList;
    private String searchContent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_exec_monitor);
        ButterKnife.bind(this);
        initRequest(searchContent);
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("调度平台任务查询");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchContent=searchEdittext.getText().toString();
                initRequest(searchContent);

            }
        });
        lvDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("task_code",tList.get(i).getTask_code());
                intent.putExtra("job_id",tList.get(i).getJob_id());
                intent.setClass(TaskExecMonitorActivity.this,TaskExecMonitorNextActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRequest(String task_name) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "getTaskExecMonitorList");
        maps.put("task_name", task_name);
        startTask(HttpRequestEnum.enum_taskExecMonitorList, ConstantValueUtil.URL + "PlanManagement?", maps, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_taskExecMonitorList:
                if (responseBean.isSuccess()) {
                    Type t1 = new TypeToken<List<TasExecMonitorBean>>() {
                    }.getType();
                    String str;
                    try {
                        tList = new Gson().fromJson(responseBean.getDATA(), t1);
                        lvDatas.setAdapter(new CommonAdapter<TasExecMonitorBean>(getApplicationContext(), R.layout.activity_task_exec_monitor_list_item, tList) {
                            @Override
                            protected void convert(ViewHolder viewHolder, TasExecMonitorBean item, int position) {
                                viewHolder.setText(R.id.tv_item1, item.getTask_code());
                                viewHolder.setText(R.id.tv_item2, item.getTask_name());
                                viewHolder.setText(R.id.tv_item3, item.getJob_id());
                                viewHolder.setText(R.id.tv_item4, item.getStart_time());
                                viewHolder.setText(R.id.tv_item5, item.getFinish_time());
                                viewHolder.setText(R.id.tv_item6, item.getState());
                            }
                        });
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                }
                break;
            default:
                break;
        }

    }
}
