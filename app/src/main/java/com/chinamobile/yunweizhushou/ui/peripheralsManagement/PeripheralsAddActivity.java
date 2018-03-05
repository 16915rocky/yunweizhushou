package com.chinamobile.yunweizhushou.ui.peripheralsManagement;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/9.
 */

public class PeripheralsAddActivity extends BaseActivity {
    @BindView(R.id.sp_item1)
    AppCompatSpinner spItem1;
    @BindView(R.id.sp_item2)
    AppCompatSpinner spItem2;
    @BindView(R.id.et_item3)
    EditText etItem3;
    @BindView(R.id.et_item4)
    EditText etItem4;
    @BindView(R.id.et_item5)
    EditText etItem5;
    @BindView(R.id.et_item6)
    EditText etItem6;
    @BindView(R.id.et_item7)
    EditText etItem7;
    @BindView(R.id.et_item8)
    EditText etItem8;
    @BindView(R.id.et_item9)
    EditText etItem9;
    @BindView(R.id.et_item10)
    EditText etItem10;
    @BindView(R.id.et_item11)
    EditText etItem11;
    @BindView(R.id.tv_button)
    TextView tvButton;
    private String[] strArray1=new String[]{"570","571","572","573","574","575","576","577","578","579","580"};
    private String[] strArray2=new String[]{"1","2"};
    private String city="571",category="1",groupName="0",equipANum="0",equipANo="0",equipBNum="0",equipBNo="0",equipCNum="0",equipCNo="0",equipDNum="0",equipDNo="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripherals_add);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "addRecord");
        map.put("city",city);
        map.put("category",category);
        map.put("groupName",groupName);
        map.put("equipANum",equipANum);
        map.put("equipANo",equipANo);
        map.put("equipBNum",equipBNum);
        map.put("equipBNo",equipBNo);
        map.put("equipCNum",equipCNum);
        map.put("equipCNo",equipCNo);
        map.put("equipDNum",equipDNum);
        map.put("equipDNo",equipDNo);
        startTask(HttpRequestEnum.enum_peripher_add, ConstantValueUtil.URL + "ExternalEquipment?", map);
    }

    private void initEvent() {
        getTitleBar().setMiddleText("新增录入");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddContent();
                initRequest();
            }
        });
    }

    private void getAddContent() {
        spItem1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                city=strArray1[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spItem2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                category=strArray2[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        groupName=etItem3.getText().toString();
        equipANum=etItem4.getText().toString();
        equipANo=etItem5.getText().toString();
        equipBNum=etItem6.getText().toString();
        equipBNo=etItem7.getText().toString();
        equipCNum=etItem8.getText().toString();
        equipCNo=etItem9.getText().toString();
        equipDNum=etItem10.getText().toString();
        equipDNo=etItem11.getText().toString();
    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        Toast.makeText(this,responseBean.getMSG(),Toast.LENGTH_SHORT).show();
    }
}
