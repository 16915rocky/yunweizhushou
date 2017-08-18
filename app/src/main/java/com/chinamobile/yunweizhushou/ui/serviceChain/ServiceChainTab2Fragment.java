package com.chinamobile.yunweizhushou.ui.serviceChain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.serviceChain.adapter.ServiceChainAdapter;
import com.chinamobile.yunweizhushou.ui.serviceChain.bean.ServcieChainBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ServiceChainTab2Fragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_title1,tv_title2,tv_title3,tv_search;
    private ListView lv_common3;
    private EditText et_search;
    private ServiceChainAdapter mAdapter;
    private List<ServcieChainBean> mList;
    private LinearLayout ltTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.common_list_3_nodivider,container,false);
        initView(view);
        initRequest("");
        initEvent();
        return view;
    }
    private void initEvent() {

        tv_search.setOnClickListener(this);
        lv_common3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("sys_op_id", mList.get(position).getSys_op_id());
                intent.putExtra("business_id", mList.get(position).getBusiness_id());
                intent.putExtra("busi_name", mList.get(position).getBusi_name());
                intent.setClass(getActivity(), ServiceChainNextActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initRequest(String busi_name) {
        HashMap map=new HashMap<String,String>();
        map.put("action", "getListOfBusi");
        map.put("busi_name", busi_name);
        startTask(HttpRequestEnum.enum_serviceChain, ConstantValueUtil.URL + "BusiRadar?", map,false);

    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }

        switch (e) {
            case enum_serviceChain:
                if (responseBean == null) {
                    Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
                    return;
                }
                if (responseBean.isSuccess()) {
                    Type type = new TypeToken<List<ServcieChainBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
                    mAdapter = new ServiceChainAdapter(getActivity(), mList, R.layout.item_list_3_arrow);
                    lv_common3.setAdapter(mAdapter);
                } else {
                    Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
                }
                break;
            default:
                break;
        }
    }

    private void initView(View view) {
        ltTitle= (LinearLayout) view.findViewById(R.id.lt_title);
        ltTitle.setVisibility(View.GONE);
        tv_title1=(TextView)view.findViewById(R.id.list_title_4_item1);
        tv_title2=(TextView)view.findViewById(R.id.list_title_4_item2);
        tv_title3=(TextView)view.findViewById(R.id.list_title_4_item3);
        tv_title1.setText("业务分组");
        tv_title2.setText("渠道");
        tv_title3.setText("业务名称");
        lv_common3=(ListView) view.findViewById(R.id.common_list_4_listview);
        et_search=(EditText) view.findViewById(R.id.search_edittext);
        et_search.setHint("请按业务名称搜索");
        tv_search=(TextView) view.findViewById(R.id.search_btn);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                initRequest(et_search.getText().toString());
                break;

            default:
                break;
        }

    }
}
