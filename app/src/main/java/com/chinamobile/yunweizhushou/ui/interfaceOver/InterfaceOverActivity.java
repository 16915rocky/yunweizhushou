package com.chinamobile.yunweizhushou.ui.interfaceOver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.interfaceOver.bean.InterfaceOverBean;
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

public class InterfaceOverActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.search_edittext)
    EditText searchEdittext;
    @BindView(R.id.search_btn)
    TextView searchBtn;
    @BindView(R.id.lv_common)
    ListView lvCommon;
    @BindView(R.id.lt_common_search)
    LinearLayout ltCommonSearch;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_analysis)
    TextView tvAnalysis;
    private List<InterfaceOverBean> mList;
    private String time;
    private String searchContent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_over);
        ButterKnife.bind(this);
        ininView();
        initRequest(searchContent);
        initEvent();

    }

    private void ininView() {
        time = Utils.getYesterdayTime();
        tvTime.setText("截止时间" + time);
    }

    private void initEvent() {
        getTitleBar().setMiddleText("超时专区");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvFinish.setOnClickListener(this);
        tvAnalysis.setOnClickListener(this);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchContent=searchEdittext.getText().toString();
                initRequest(searchContent);
            }
        });
    }

    private void initRequest(String searchContent) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getIntefaceOverTime");
        map.put("search", searchContent);
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
                    Type t = new TypeToken<List<InterfaceOverBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), t);
                    lvCommon.setAdapter(new CommonAdapter<InterfaceOverBean>(this, R.layout.item_lv_interface_over, mList) {

                        @Override
                        protected void convert(ViewHolder viewHolder, InterfaceOverBean item, int position) {
                            viewHolder.setText(R.id.tv_item1, item.getService_key());
                            viewHolder.setText(R.id.tv_item2, item.getService_desc());
                            viewHolder.setText(R.id.tv_item3, item.getSys_success());
                            viewHolder.setText(R.id.tv_item4, item.getTime_consume());
                            viewHolder.setText(R.id.tv_item5, item.getNum());
                            int num = Integer.parseInt(item.getNum());
                            if (num >= 2000) {
                                viewHolder.setTextColor(R.id.tv_item5, ContextCompat.getColor(InterfaceOverActivity.this, R.color.color_red));
                            } else {
                                viewHolder.setTextColor(R.id.tv_item5, ContextCompat.getColor(InterfaceOverActivity.this, R.color.color_gray));
                            }
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_finish:
                intent.putExtra("state","1");
                intent.putExtra("title","已完成");
                break;
            case R.id.tv_analysis:
                intent.putExtra("state","2");
                intent.putExtra("title","分析中");
                break;

            default:
                break;
        }
        intent.setClass(this, InterfaceOverNextActivity.class);
        startActivity(intent);
    }
}
