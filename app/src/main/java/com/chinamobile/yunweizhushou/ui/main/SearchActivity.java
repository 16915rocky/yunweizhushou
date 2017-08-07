package com.chinamobile.yunweizhushou.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.SelectorModules;
import com.chinamobile.yunweizhushou.ui.adapter.SearchMoreItemAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_search_back)
    ImageView imgSearchBack;
    @BindView(R.id.lt_search)
    LinearLayout ltSearch;
    @BindView(R.id.tb_search)
    Toolbar tbSearch;
    @BindView(R.id.lv_serach_module)
    ListView lvSerachModule;
    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.tv_search_true)
    TextView tvSearchTrue;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    private List<MainPageGridBean> mList = new ArrayList<MainPageGridBean>();
    private SearchMoreItemAdapter mAdapter;
    private SelectorModules selectorModules = new SelectorModules();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_item_search);
        ButterKnife.bind(this);
        initEvent();

    }

    private void initRequest(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "searchDirectory");
        map.put("name", name);
        startTask(HttpRequestEnum.enum_more_item_search, ConstantValueUtil.URL + "DirectoryManager?", map, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_more_item_search:
                if (responseBean.isSuccess()) {
                    Type t = new TypeToken<List<MainPageGridBean>>() {
                    }.getType();
                    String data = responseBean.getDATA();
                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(data);
                        mList = new Gson().fromJson(jo.optString("dirList"), t);
                        mAdapter = new SearchMoreItemAdapter(this, mList, R.layout.item_more_item_search);
                        lvSerachModule.setAdapter(mAdapter);
                        tvPrompt.setVisibility(View.GONE);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "查无此内容", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }


    private void initEvent() {
        tvSearchTrue.setOnClickListener(this);
        imgSearchBack.setOnClickListener(this);

        lvSerachModule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectorModules.goToModules(SearchActivity.this, mList.get(i).getD_enum(),mList.get(i).getId());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_search_back:
                finish();
                break;
            case R.id.tv_search_true:
                String str = searchView.getText().toString();
                if (mAdapter != null) {
                    mAdapter.clearData();
                }
                initRequest(str);
            default:
                break;
        }
    }
}
