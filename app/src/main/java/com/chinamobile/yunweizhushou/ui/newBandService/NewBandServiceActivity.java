package com.chinamobile.yunweizhushou.ui.newBandService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.businessaccept.BusinessAcceptHorizenGraphActivity;
import com.chinamobile.yunweizhushou.ui.commonView.LinearChartModules;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;
import static com.chinamobile.yunweizhushou.R.id.tv_item1;
import static com.chinamobile.yunweizhushou.R.id.tv_item2;
import static com.chinamobile.yunweizhushou.R.id.tv_item3;
import static com.chinamobile.yunweizhushou.R.id.tv_item4;

/**
 * Created by Administrator on 2017/8/9.
 */

public class NewBandServiceActivity extends BaseActivity implements View.OnClickListener {


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
    @BindView(R.id.titleid)
    LinearLayout titleid;
    @BindView(R.id.lv_item1)
    ListView lvItem1;
    @BindView(tv_item1)
    TextView tvItem1;
    @BindView(tv_item2)
    TextView tvItem2;
    @BindView(tv_item3)
    TextView tvItem3;
    @BindView(tv_item4)
    TextView tvItem4;
    @BindView(R.id.lv_item2)
    ListView lvItem2;
    @BindView(R.id.busi_city_1)
    TextView busiCity1;
    @BindView(R.id.busi_city_2)
    TextView busiCity2;
    @BindView(R.id.busi_city_3)
    TextView busiCity3;
    @BindView(R.id.busi_city_4)
    TextView busiCity4;
    @BindView(R.id.busi_city_5)
    TextView busiCity5;
    @BindView(R.id.busi_city_6)
    TextView busiCity6;
    @BindView(R.id.busi_city_7)
    TextView busiCity7;
    @BindView(R.id.busi_city_8)
    TextView busiCity8;
    @BindView(R.id.busi_city_9)
    TextView busiCity9;
    @BindView(R.id.busi_city_10)
    TextView busiCity10;
    @BindView(R.id.busi_city_11)
    TextView busiCity11;
    @BindView(R.id.busi_city_12)
    TextView busiCity12;
    private LinearChartModules linearChartModules;
    private int id;
    private String[] type1 = new String[]{"1155", "1156", "有线宽带变更", "宽带销户", "宽带开户", "有线宽带移机"};
    private String[] type2 = new String[]{"1157", "1158", "当前在途订单变更", "当前在途订单拆机", "当前在途订单开户", "当前在途订单移机"};
    private String[] type3 = new String[]{"1159", "1160", "有线宽带变更", "有线宽带拆机", "有线宽带开户", "有线宽带移机"};
    private String[] type4 = new String[]{"1161", "1162", "有线宽带平均竣工时长变更", "有线宽带平均竣工时长销户", "有线宽带平均竣工时长开户", "有线宽带平均竣工时长移机"};
    private String[] type5 = new String[]{"1163", "1164", "有线宽带变更", "有线宽带拆机", "有线宽带开户", "有线宽带移机"};
    private String[] type6 = new String[]{"1165", "1166", "有线宽带变更", "有线宽带拆机", "有线宽带开户 ", "有线宽带移机"};
    private String[] type7 = new String[]{"570", "571", "572", "573", "574", "575","576","577","578","579","580"};

    private String[] temp;
    private String fkId1,fkId2;
    private String pament_business,city;
    private String title;
    private RechargeFunctionListAdapter adapter1, adapter2;
    private RechargeFunctionGraphBean bean1, bean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_band_service);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        selectArr(id);
        initView();
        initEvent();
        initRequest1(temp[0], temp[2]);
        initRequest2(temp[1], "580");
    }

    private void initView() {
        tvItem1.setText(temp[2]);
        tvItem2.setText(temp[3]);
        tvItem3.setText(temp[4]);
        tvItem4.setText(temp[5]);
        busiCity1.setText(type7[0]);
        busiCity2.setText(type7[1]);
        busiCity3.setText(type7[2]);
        busiCity4.setText(type7[3]);
        busiCity5.setText(type7[4]);
        busiCity6.setText(type7[5]);
        busiCity7.setText(type7[6]);
        busiCity8.setText(type7[7]);
        busiCity9.setText(type7[8]);
        busiCity10.setText(type7[9]);
        busiCity11.setText(type7[10]);
        resetTvItemColor();
        tvItem1.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
        tvItem1.setTextColor(ContextCompat.getColor(this,R.color.color_white));
        busiCity1.setBackgroundResource(R.drawable.oval_blue);
        busiCity1.setTextColor(ContextCompat.getColor(this,R.color.color_white));
        fkId1=temp[0];
        fkId2=temp[1];
        pament_business=temp[2];
        city=type7[0];
    }

    private void initEvent() {
        getTitleBar().setMiddleText(title);
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvItem1.setOnClickListener(this);
        tvItem2.setOnClickListener(this);
        tvItem3.setOnClickListener(this);
        tvItem4.setOnClickListener(this);
        busiCity1.setOnClickListener(this);
        busiCity2.setOnClickListener(this);
        busiCity3.setOnClickListener(this);
        busiCity4.setOnClickListener(this);
        busiCity5.setOnClickListener(this);
        busiCity6.setOnClickListener(this);
        busiCity7.setOnClickListener(this);
        busiCity8.setOnClickListener(this);
        busiCity9.setOnClickListener(this);
        busiCity10.setOnClickListener(this);
        busiCity11.setOnClickListener(this);
        busiCity12.setOnClickListener(this);
        lvItem1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);
                intent.putExtra("extraKey", "pament_business");
                intent.putExtra("extraValue", pament_business);
                intent.putExtra("fkId",fkId1 );
                startActivity(intent);
            }
        });
        lvItem2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);
                intent.putExtra("extraKey", "city");
                intent.putExtra("extraValue", city);
                intent.putExtra("fkId",fkId2);
                startActivity(intent);
            }
        });

    }

    private void selectArr(int id) {
        switch (id) {
            case 0:
                temp = type1;
                break;
            case 1:
                temp = type2;
                break;
            case 2:
                temp = type3;
                break;
            case 3:
                temp = type4;
                break;
            case 4:
                temp = type5;
                break;
            case 5:
                temp = type6;
                break;
            default:
                break;
        }

    }

    private void initRequest1(String fkId, String pament_business) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "waveGraph");
        maps.put("fkId", fkId);
        maps.put("pament_business", pament_business);
        maps.put("time", "6h");
        startTask(HttpRequestEnum.enum_new_band_service_linechart1, ConstantValueUtil.URL + "BusiFluct?", maps, false);
    }

    private void initRequest2(String fkId, String city) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "waveGraph");
        maps.put("fkId", fkId);
        maps.put("region_code", city);
        maps.put("time", "6h");
        startTask(HttpRequestEnum.enum_new_band_service_linechart2, ConstantValueUtil.URL + "BusiFluct?", maps, false);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_new_band_service_linechart1:
                try {
                    Type t = new TypeToken<RechargeFunctionGraphBean>() {
                    }.getType();
                    bean1 = new Gson().fromJson(responseBean.getDATA(), t);
                    adapter1 = new RechargeFunctionListAdapter(getActivity(), bean1.getItemsList());
                    lvItem1.setAdapter(adapter1);
                    break;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                break;
            case enum_new_band_service_linechart2:
                try {
                    Type t = new TypeToken<RechargeFunctionGraphBean>() {
                    }.getType();
                    bean2 = new Gson().fromJson(responseBean.getDATA(), t);
                    adapter2 = new RechargeFunctionListAdapter(getActivity(), bean2.getItemsList());
                    lvItem2.setAdapter(adapter2);
                    break;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_item1:
                    resetTvItemColor();
                    tvItem1.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
                    tvItem1.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest1(temp[0], temp[2]);
                    pament_business=temp[2];
                    break;
                case R.id.tv_item2:
                    resetTvItemColor();
                    tvItem2.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
                    tvItem2.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest1(temp[0], temp[3]);
                    pament_business=temp[3];
                    break;
                case R.id.tv_item3:
                    resetTvItemColor();
                    tvItem3.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
                    tvItem3.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest1(temp[0], temp[4]);
                    pament_business=temp[4];
                    break;
                case R.id.tv_item4:
                    resetTvItemColor();
                    tvItem4.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
                    tvItem4.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest1(temp[0], temp[5]);
                    pament_business=temp[5];
                    break;
                case R.id.busi_city_1:
                    resetTvCityColor();
                    busiCity1.setBackgroundResource(R.drawable.oval_blue);
                    busiCity1.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "570");
                    city=type7[0];
                    break;
                case R.id.busi_city_2:
                    resetTvCityColor();
                    busiCity2.setBackgroundResource(R.drawable.oval_blue);
                    busiCity2.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "571");
                    city=type7[1];
                    break;
                case R.id.busi_city_3:
                    resetTvCityColor();
                    busiCity3.setBackgroundResource(R.drawable.oval_blue);
                    busiCity3.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "572");
                    city=type7[2];
                    break;
                case R.id.busi_city_4:
                    resetTvCityColor();
                    busiCity4.setBackgroundResource(R.drawable.oval_blue);
                    busiCity4.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "573");
                    city=type7[3];
                    break;
                case R.id.busi_city_5:
                    resetTvCityColor();
                    busiCity5.setBackgroundResource(R.drawable.oval_blue);
                    busiCity5.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "574");
                    city=type7[4];
                    break;
                case R.id.busi_city_6:
                    resetTvCityColor();
                    busiCity6.setBackgroundResource(R.drawable.oval_blue);
                    busiCity6.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "575");
                    city=type7[5];
                    break;
                case R.id.busi_city_7:
                    resetTvCityColor();
                    busiCity7.setBackgroundResource(R.drawable.oval_blue);
                    busiCity7.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "576");
                    city=type7[6];
                    break;
                case R.id.busi_city_8:
                    resetTvCityColor();
                    busiCity8.setBackgroundResource(R.drawable.oval_blue);
                    busiCity8.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "577");
                    city=type7[7];
                    break;
                case R.id.busi_city_9:
                    resetTvCityColor();
                    busiCity9.setBackgroundResource(R.drawable.oval_blue);
                    busiCity9.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "578");
                    city=type7[8];
                    break;
                case R.id.busi_city_10:
                    resetTvCityColor();
                    busiCity10.setBackgroundResource(R.drawable.oval_blue);
                    busiCity10.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "579");
                    city=type7[9];
                    break;
                case R.id.busi_city_11:
                    resetTvCityColor();
                    busiCity11.setBackgroundResource(R.drawable.oval_blue);
                    busiCity11.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                    initRequest2(temp[1], "580");
                    city=type7[10];
                    break;
            }
    }
    public void resetTvItemColor(){
        tvItem1.setBackgroundResource(0);
        tvItem2.setBackgroundResource(0);
        tvItem3.setBackgroundResource(0);
        tvItem4.setBackgroundResource(0);
        tvItem1.setTextColor(ContextCompat.getColor(this,R.color.color_black));
        tvItem2.setTextColor(ContextCompat.getColor(this,R.color.color_black));
        tvItem3.setTextColor(ContextCompat.getColor(this,R.color.color_black));
        tvItem4.setTextColor(ContextCompat.getColor(this,R.color.color_black));
    }
    public void resetTvCityColor(){
        busiCity1.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity2.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity3.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity4.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity5.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity6.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity7.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity8.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity9.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity10.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity11.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity12.setBackgroundResource(R.drawable.oval_blue_stroke);
        busiCity1.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity2.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity3.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity4.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity5.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity6.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity7.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity8.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity8.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity9.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity10.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity11.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        busiCity12.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
    }
}
