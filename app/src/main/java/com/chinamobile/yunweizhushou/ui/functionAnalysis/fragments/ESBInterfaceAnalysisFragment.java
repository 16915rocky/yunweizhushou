package com.chinamobile.yunweizhushou.ui.functionAnalysis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.ESBIABean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/29.
 */

public class ESBInterfaceAnalysisFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.search_edittext)
    EditText searchEdittext;
    @BindView(R.id.search_btn)
    TextView searchBtn;
    @BindView(R.id.lt_common_search)
    LinearLayout ltCommonSearch;
    @BindView(R.id.rv_grid)
    ListView rvGrid;
    @BindView(R.id.tv_button)
    TextView tvButton;
    Unbinder unbinder;
    private String osb_server_code="",csf_server_code="";
    private  List<ESBIABean> mList;
    private CommonAdapter mAdapter;
    private PopupWindow popupWindow;
    private TextView tvTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_esb_interface_analysis, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initEvent();
        initRequest(osb_server_code,csf_server_code);
        return view;
    }

    private void initEvent() {
        searchBtn.setOnClickListener(this);
        tvButton.setOnClickListener(this);
        rvGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("fkId", "1171");
                intent.putExtra("extraKey", "service_key");
                intent.putExtra("extraValue", mList.get(position).getOsb_server_code());
                intent.setClass(getActivity(), GraphListActivity2.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initView() {
      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvGrid.setLayoutManager(linearLayoutManager);*/
        initPop();
    }

    private void initRequest(String service_key,String csf_server_code) {
        HashMap<String, String> map3 = new HashMap<>();
        map3.put("action", "getEsbAndCsf");
        map3.put("service_key",service_key);
        map3.put("csf_server_code",csf_server_code);
        startTask(HttpRequestEnum.enum_function_analysis_performmance_list2, ConstantValueUtil.URL + "SpecialTreatment?", map3,true);

    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (!responseBean.isSuccess()) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_function_analysis_performmance_list2:
                Type type = new TypeToken<List<ESBIABean>>() {
                }.getType();
                mList=new Gson().fromJson(responseBean.getDATA(),type);
                mAdapter=new CommonAdapter<ESBIABean>(getActivity(),R.layout.item_list_7_esb,mList){

                    @Override
                    protected void convert(com.zhy.adapter.abslistview.ViewHolder holder, ESBIABean esbiaBean, int position) {
                        holder.setText(R.id.list_7_item1,esbiaBean.getCsf_name());
                        holder.setText(R.id.list_7_item2,esbiaBean.getCsf_server_code());
                        holder.setText(R.id.list_7_item3,esbiaBean.getName());
                        holder.setText(R.id.list_7_item4,esbiaBean.getOsb_server_code());
                        holder.setText(R.id.list_7_item5,esbiaBean.getSys_success());
                        holder.setText(R.id.list_7_item6,esbiaBean.getTime_consume());
                        holder.setText(R.id.list_7_item7,esbiaBean.getTotal_cnt());
                    }
                };
                rvGrid.setAdapter(mAdapter);
             /*   HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
                LinearLayout linearlayout=new LinearLayout(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
                linearlayout.setLayoutParams(lp);
                //实例化一个TextView
                TextView tv1 = new TextView(getActivity());
                TextView tv2 = new TextView(getActivity());
                //设置宽高以及权重
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                //设置textview垂直居中
                tvParams.gravity = Gravity.CENTER_VERTICAL;
                tv1.setLayoutParams(tvParams);
                tv1.setTextSize(14);
                tv1.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.color_blue));
                tv1.setTextColor(getResources().getColor(R.color.color_white));
                tv2.setLayoutParams(tvParams);
                tv2.setTextSize(14);
                tv2.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.color_blue));
                tv2.setTextColor(getResources().getColor(R.color.color_white));
                linearlayout.addView(tv1);
                linearlayout.addView(tv2);
                headerAndFooterWrapper.addHeaderView(linearlayout);
                rvGrid.setAdapter(headerAndFooterWrapper);
                headerAndFooterWrapper.notifyDataSetChanged();*/

                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_btn:
                osb_server_code=searchEdittext.getText().toString();
                initRequest(osb_server_code,csf_server_code);
                break;
            case R.id.tv_button:
                popupWindow.showAtLocation(tvButton, Gravity.BOTTOM,0,0);
                break;
            case R.id.tv_item1:
            case R.id.tv_item2:
            case R.id.tv_item3:
            case R.id.tv_item4:
            case R.id.tv_item5:
            case R.id.tv_item6:
            case R.id.tv_item7:
            case R.id.tv_item8:
            case R.id.tv_item9:
            case R.id.tv_item10:
            case R.id.tv_item11:
                tvTemp=(TextView)view;
                csf_server_code=tvTemp.getTag().toString();
                tvButton.setText(tvTemp.getText().toString());
                initRequest(osb_server_code,csf_server_code);
                popupWindow.dismiss();
                break;
            default:break;
        }
    }
    public void initPop(){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_esb_pop_list, null);
        popupWindow=new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        TextView tv1= (TextView) contentView.findViewById(R.id.tv_item1);
        TextView tv2= (TextView) contentView.findViewById(R.id.tv_item2);
        TextView tv3= (TextView) contentView.findViewById(R.id.tv_item3);
        TextView tv4= (TextView) contentView.findViewById(R.id.tv_item4);
        TextView tv5= (TextView) contentView.findViewById(R.id.tv_item5);
        TextView tv6= (TextView) contentView.findViewById(R.id.tv_item6);
        TextView tv7= (TextView) contentView.findViewById(R.id.tv_item7);
        TextView tv8= (TextView) contentView.findViewById(R.id.tv_item8);
        TextView tv9= (TextView) contentView.findViewById(R.id.tv_item9);
        TextView tv10= (TextView) contentView.findViewById(R.id.tv_item10);
        TextView tv11= (TextView) contentView.findViewById(R.id.tv_item11);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv10.setOnClickListener(this);
        tv11.setOnClickListener(this);
    }
}
