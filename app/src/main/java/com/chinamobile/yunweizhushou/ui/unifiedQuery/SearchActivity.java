package com.chinamobile.yunweizhushou.ui.unifiedQuery;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.KeyValueBean2;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.unifiedQuery.adapters.UQSearchAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;

/**
 * Created by Administrator on 2017/8/25.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_edittext)
    EditText searchEdittext;
    @BindView(R.id.search_btn)
    TextView searchBtn;
    @BindView(R.id.lv_serach_content)
    MyListView lvSerachContent;
    private String type;
    private List<KeyValueBean2> mList;
    private UQSearchAdapter mAdapter;
    private String serachContent,titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getStringExtra("type");
        titleName=getIntent().getStringExtra("titleName");
        setContentView(R.layout.activity_uq_search);
        ButterKnife.bind(this);
        initEvent();


    }

    private void initEvent() {
        getTitleBar().setMiddleText(titleName);
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serachContent=searchEdittext.getText().toString();
                initRequest(serachContent);
            }
        });
    }

    private void initRequest(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "search");
        map.put("type", type);
        map.put("name", name);
        startTask(HttpRequestEnum.enum_uq_search, ConstantValueUtil.URL + "Search?", map,
                true);

    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
            return;
        }
        if (responseBean.isSuccess()) {
            switch (e) {
                case enum_uq_search:
                    Type type = new TypeToken<List<KeyValueBean2>>() {
                    }.getType();
                    mList = new Gson().fromJson(responseBean.getDATA(), type);
                    mAdapter=new UQSearchAdapter(this,mList,R.layout.item_uq_search);
                    lvSerachContent.setAdapter(mAdapter);
                    break;
            }
        }

    }
}
