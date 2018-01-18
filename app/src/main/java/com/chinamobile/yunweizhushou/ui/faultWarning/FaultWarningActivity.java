package com.chinamobile.yunweizhushou.ui.faultWarning;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.faultWarning.bean.FaultWarningBean;
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
 * Created by Administrator on 2017/12/13.
 */

public class FaultWarningActivity extends BaseActivity {
    @BindView(R.id.list_title_2_item1)
    TextView listTitle2Item1;
    @BindView(R.id.list_title_2_item2)
    TextView listTitle2Item2;
    @BindView(R.id.common_list_2_listview)
    ListView commonList2Listview;
    private List<FaultWarningBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_2);
        ButterKnife.bind(this);
        initEvent();
        initRequest();

    }

    private void initEvent() {
        listTitle2Item1.setText("预警内容");
        listTitle2Item2.setText("预警记录时间");
        getTitleBar().setMiddleText("故障预警");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRequest() {
        HashMap map2=new HashMap<String,String>();
        map2.put("action", "getWarningRecord");
        startTask(HttpRequestEnum.enum_fault_warning, ConstantValueUtil.URL + "TempInter?", map2, true);
    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_fault_warning:
                Type type = new TypeToken<List<FaultWarningBean>>() {
                }.getType();
                mList=new Gson().fromJson(responseBean.getDATA(),type);
                commonList2Listview.setAdapter(new CommonAdapter<FaultWarningBean>(this, R.layout.item_list_2,mList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, FaultWarningBean item, int position) {
                        viewHolder.setText(R.id.list_2_item1,item.getContent());
                        viewHolder.setText(R.id.list_2_item2,item.getCreateTime());
                        viewHolder.setOnClickListener(R.id.list_2_item1, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TextView tv1=(TextView)view;
                                AlertDialog.Builder builder = new AlertDialog.Builder(FaultWarningActivity.this);
                                builder.setMessage(tv1.getText());
                                builder.create();
                                builder.show();
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }
    }
}
