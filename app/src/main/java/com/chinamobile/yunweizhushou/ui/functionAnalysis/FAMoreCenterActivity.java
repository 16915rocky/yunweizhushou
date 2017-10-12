package com.chinamobile.yunweizhushou.ui.functionAnalysis;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FAList2Bean;
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

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;

/**
 * Created by Administrator on 2017/9/27.
 */

public class FAMoreCenterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_item1)
    TextView tvItem1;
    @BindView(R.id.tv_item2)
    TextView tvItem2;
    @BindView(R.id.tv_item3)
    TextView tvItem3;
    @BindView(R.id.tv_item4)
    TextView tvItem4;
    @BindView(R.id.tv_item5)
    TextView tvItem5;
    @BindView(R.id.tv_item6)
    TextView tvItem6;
    @BindView(R.id.tv_item7)
    TextView tvItem7;
    @BindView(R.id.tv_item8)
    TextView tvItem8;
    @BindView(R.id.tv_item9)
    TextView tvItem9;
    @BindView(R.id.tv_item10)
    TextView tvItem10;
    @BindView(R.id.search_edittext)
    EditText searchEdittext;
    @BindView(R.id.search_btn)
    TextView searchBtn;
    @BindView(R.id.list_title_4_item1)
    TextView listTitle4Item1;
    @BindView(R.id.list_title_4_item2)
    TextView listTitle4Item2;
    @BindView(R.id.list_title_4_item3)
    TextView listTitle4Item3;
    @BindView(R.id.list_title_4_item4)
    TextView listTitle4Item4;
    @BindView(R.id.common_list_4_listview)
    ListView commonList4Listview;
    @BindView(R.id.lt_common_search)
    LinearLayout ltCommonSearch;
    @BindView(R.id.title_left_button)
    ImageButton titleLeftButton;
    @BindView(R.id.title_middle_text)
    TextView titleMiddleText;
    @BindView(R.id.title_middle_search)
    EditText titleMiddleSearch;
    @BindView(R.id.title_right_button1)
    ImageButton titleRightButton1;
    @BindView(R.id.title_right_button2)
    ImageButton titleRightButton2;
    @BindView(R.id.title_right_text)
    TextView titleRightText;
    @BindView(R.id.common_list_4_extra)
    LinearLayout commonList4Extra;
    private String searchContent;
    private List<FAList2Bean> mList;
    private LinearLayout titleId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esb_more_center);
        ButterKnife.bind(this);
        initView();
        initRequest("");

    }
    public void initView(){
        getTitleBar().setMiddleText("全量接口");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listTitle4Item1.setText("接口编号");
        listTitle4Item2.setText("成功率");
        listTitle4Item3.setText("耗时");
        listTitle4Item4.setText("调用量");
        titleId= (LinearLayout) findViewById(R.id.common_list_4_head);
        titleId.setVisibility(View.GONE);
        tvItem1.setOnClickListener(this);
        tvItem2.setOnClickListener(this);
        tvItem3.setOnClickListener(this);
        tvItem4.setOnClickListener(this);
        tvItem5.setOnClickListener(this);
        tvItem6.setOnClickListener(this);
        tvItem7.setOnClickListener(this);
        tvItem8.setOnClickListener(this);
        titleMiddleSearch.setOnClickListener(this);

    }

    private void initRequest(String searchContent) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getESBInteOverTime");
        map.put("search", searchContent);
        startTask(HttpRequestEnum.enum_function_analysis_performmance_list2, ConstantValueUtil.URL + "SpecialTreatment?",
                map);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        if (responseBean.isSuccess()) {
            switch (e) {
                case enum_function_analysis_performmance_list2:
                    Type type = new TypeToken<List<FAList2Bean>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), type);
                    commonList4Listview.setAdapter(new CommonAdapter<FAList2Bean>(getActivity(), R.layout.item_list_4, mList) {
                        @Override
                        protected void convert(ViewHolder viewHolder, FAList2Bean item, int position) {
                            viewHolder.setText(R.id.list_4_item1, item.getService_key());
                            viewHolder.setText(R.id.list_4_item2, item.getSys_success() + "%");
                            viewHolder.setText(R.id.list_4_item3, item.getTime_consume());
                            viewHolder.setText(R.id.list_4_item4, item.getTotal_cnt());
                        }
                    });
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_button:
                searchContent=titleMiddleSearch.getText().toString();
                initRequest(searchContent);
        }

    }
}
