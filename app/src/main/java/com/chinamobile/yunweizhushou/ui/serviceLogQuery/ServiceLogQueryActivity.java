package com.chinamobile.yunweizhushou.ui.serviceLogQuery;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.serviceLogQuery.adapter.ServiceLogQueryAdapter;
import com.chinamobile.yunweizhushou.ui.serviceLogQuery.bean.ServiceLogQueryBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/27.
 */

public class ServiceLogQueryActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_item1)
    EditText etItem1;
    @BindView(R.id.et_item2)
    TextView etItem2;
    @BindView(R.id.tv_query)
    TextView tvQuery;
    @BindView(R.id.lv_content)
    ListView lvContent;
    private TimeSelector timeSelector;
    private String selectTime;
    private List<ServiceLogQueryBean> mList;
    private ServiceLogQueryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_log_query);
        ButterKnife.bind(this);
        initEvent();

    }

    private void initEvent() {
        getTitleBar().setMiddleText("服务日志查询");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                selectTime = time;
                etItem2.setText(time);
            }
        }, "2015-11-22 17:34", "2030-12-1 15:20");
        tvQuery.setOnClickListener(this);
        etItem2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_query:
                toQuery();
                break;
            case R.id.et_item2:
                timeSelector.show();
                break;
            default:
                break;
        }
    }

    private void toQuery() {
        if ("".equals(etItem1.getText().toString())) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
        } else {
            String phoneNum = etItem1.getText().toString();
            getDataFromApi(phoneNum, selectTime);
        }
    }

    private void getDataFromApi(String phoneNum, String time) {
        HashMap map2 = new HashMap<String, String>();
        map2.put("action", "queryLogByTel");
        map2.put("bill_id", phoneNum);
        map2.put("start_time", time);
        map2.put("pageNum", "1");
        map2.put("pageSize", "20");
        startTask(HttpRequestEnum.enum_service_log_query, ConstantValueUtil.URL + "ServiceLogQuery?", map2, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_service_log_query:
                try {
                    JSONObject jo = new JSONObject(responseBean.getDATA());
                    String rows = jo.getString("rows");
                    Type t = new TypeToken<List<ServiceLogQueryBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(rows, t);
                    mAdapter = new ServiceLogQueryAdapter(this, mList, R.layout.item__lv_service_log_query);
                    lvContent.setAdapter(mAdapter);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
