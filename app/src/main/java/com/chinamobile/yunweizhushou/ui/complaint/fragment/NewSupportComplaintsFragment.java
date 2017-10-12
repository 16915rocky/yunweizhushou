package com.chinamobile.yunweizhushou.ui.complaint.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.complaint.bean.NewSupportComplaintsBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/28.
 */

public class NewSupportComplaintsFragment extends BaseFragment {
    @BindView(R.id.tv_item1)
    TextView tvItem1;
    @BindView(R.id.tv_item2)
    TextView tvItem2;

    Unbinder unbinder;
    @BindView(R.id.rv_grid)
    RecyclerView rvGrid;
    private List<NewSupportComplaintsBean> mGrid;
    private String[] arr=new String[]{"01","02","03","04","05","06","07","08","09","10"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_support_complaints, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initRequest();
        return view;
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "sptoday");
        startTask(HttpRequestEnum.enum_complain_today, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_complain_today:
                if (responseBean.isSuccess()) {
                    Type type = new TypeToken<List<NewSupportComplaintsBean>>() {
                    }.getType();
                    String data = responseBean.getDATA();
                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(data);
                        String todNum = jo.getString("todNum");
                        String yesNum = jo.getString("yesNum");
                        tvItem1.setText(todNum);
                        tvItem2.setText(yesNum);
                        mGrid = new Gson().fromJson(jo.getString("top10"), type);
                        rvGrid.setAdapter(new CommonAdapter<NewSupportComplaintsBean>(getActivity(), R.layout.item_complain_today, mGrid) {
                            @Override
                            protected void convert(ViewHolder holder, NewSupportComplaintsBean newSupportComplaintsBean, int position) {
                                holder.setText(R.id.complain_today_item_name,newSupportComplaintsBean.getName());
                                holder.setText(R.id.complain_today_item_num,newSupportComplaintsBean.getNumber());
                                holder.setText(R.id.complain_today_item_index,arr[position]);
                                if(position==0){
                                    holder.setBackgroundRes(R.id.complain_today_item_index,R.drawable.oval_orange);
                                }else if(position==1){
                                    holder.setBackgroundRes(R.id.complain_today_item_index,R.drawable.oval_yellow);
                                }else if(position==2){
                                    holder.setBackgroundRes(R.id.complain_today_item_index,R.drawable.oval_blue);
                                }else{
                                    holder.setBackgroundRes(R.id.complain_today_item_index,R.drawable.oval_gray);
                                }
                            }
                        });
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
                }
                break;

            default:
                break;
        }
    }

    private void initView() {
        rvGrid.setLayoutManager(new GridLayoutManager(getActivity(),2));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
