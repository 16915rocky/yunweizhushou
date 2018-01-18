package com.chinamobile.yunweizhushou.ui.TaskExecMonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.TaskExecMonitor.bean.TaskDetailListBean;
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

public class TaskExecMonitorNextActivity extends BaseActivity {


    @BindView(R.id.lv_datas)
    ListView lvDatas;
    private List<TaskDetailListBean> tList;
    private String task_code="";
    private String job_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task_code=getIntent().getStringExtra("task_code");
        job_id=getIntent().getStringExtra("job_id");
        setContentView(R.layout.activity_task_exec_monitor_next);
        ButterKnife.bind(this);
        initRequest(task_code,job_id);
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRequest(String task_code,String job_id) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "getTaskDetailList");
        maps.put("task_code", task_code);
        maps.put("job_id", job_id);
        startTask(HttpRequestEnum.enum_TaskDetailList, ConstantValueUtil.URL + "PlanManagement?", maps, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_TaskDetailList:
                if (responseBean.isSuccess()) {
                    Type t1 = new TypeToken<List<TaskDetailListBean>>() {
                    }.getType();
                    String str;
                    try {
                        tList = new Gson().fromJson(responseBean.getDATA(), t1);
                        lvDatas.setAdapter(new CommonAdapter<TaskDetailListBean>(getApplicationContext(), R.layout.activity_task_exec_monitor_next_list_item, tList) {
                            @Override
                            protected void convert(ViewHolder viewHolder, TaskDetailListBean item, int position) {
                                viewHolder.setText(R.id.tv_item1, item.getJob_id());
                                viewHolder.setText(R.id.tv_item2, item.getCreate_time());
                                viewHolder.setText(R.id.tv_item3, item.getLog_type());
                                viewHolder.setText(R.id.tv_item4, item.getOp_info());
                                viewHolder.setText(R.id.tv_item5, item.getContainer_code());
                                viewHolder.setText(R.id.tv_item6, item.getHost());
                                viewHolder.setText(R.id.tv_item7, item.getEx_msg());
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
