package com.chinamobile.yunweizhushou.ui.demandPanoramic;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandDetailBean2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemandPanoramicDetail2Activity extends BaseActivity {


    @BindView(R.id.tv_busdeid)
    TextView tvBusdeid;
    @BindView(R.id.tv_basesummary)
    TextView tvBasesummary;
    @BindView(R.id.tv_major_field)
    TextView tvMajorField;
    @BindView(R.id.tv_professional_subcategories)
    TextView tvProfessionalSubcategories;
    @BindView(R.id.tv_demandes)
    TextView tvDemandes;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_priority)
    TextView tvPriority;
    @BindView(R.id.tv_important)
    TextView tvImportant;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_demander)
    TextView tvDemander;
    @BindView(R.id.tv_demandoridin)
    TextView tvDemandoridin;
    @BindView(R.id.planer)
    TextView planer;
    @BindView(R.id.tv_decommittime)
    TextView tvDecommittime;
    @BindView(R.id.realizationWay)
    TextView realizationWay;
    @BindView(R.id.tv_deadmini)
    TextView tvDeadmini;
    @BindView(R.id.workingHours)
    TextView workingHours;
    @BindView(R.id.tv_swdeid)
    TextView tvSwdeid;
    @BindView(R.id.tv_srbasesummary)
    TextView tvSrbasesummary;
    @BindView(R.id.tv_srdemandes)
    TextView tvSrdemandes;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R.id.tv_demandclassifl)
    TextView tvDemandclassifl;
    @BindView(R.id.tv_requirementsubsys)
    TextView tvRequirementsubsys;
    @BindView(R.id.tv_estimatedtime)
    TextView tvEstimatedtime;
    @BindView(R.id.tv_pri_time)
    TextView tvPriTime;
    private DemandDetailBean2 demandDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_panoramic_detail2);
        ButterKnife.bind(this);
        initView();
        initRequest();
        setEvent();
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getDemandById");
        map.put("id", getIntent().getStringExtra("id"));
        startTask(HttpRequestEnum.enum_demand_title_detail, ConstantValueUtil.URL + "Demand?", map);
    }

    private void setEvent() {
        getTitleBar().setLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(DemandPanoramicDetail2Activity.this, responseBean.getMSG());
            return;
        }
        if (responseBean.isSuccess()) {
            switch (e) {
                case enum_demand_title_detail:
                    Type type = new TypeToken<DemandDetailBean2>() {
                    }.getType();
                    demandDetail = new Gson().fromJson(responseBean.getDATA(), type);
                    tvBusdeid.setText(demandDetail.getBusdeid());
                    tvBasesummary.setText(demandDetail.getBasesummary());
                    tvMajorField.setText(demandDetail.getMajor_field());
                    tvProfessionalSubcategories.setText(demandDetail.getProfessional_subcategories());
                    tvDemandes.setText(demandDetail.getDemandes());
                    tvStatus.setText(demandDetail.getStatus());
                    tvPriority.setText(demandDetail.getPriority());
                    tvImportant.setText(demandDetail.getImportant());
                    tvAttention.setText(demandDetail.getAttention());
                    tvDemander.setText(demandDetail.getDemander());
                    tvDemandoridin.setText(demandDetail.getDemandoridin());
                    tvDecommittime.setText(demandDetail.getDecommittime());
                    tvDeadmini.setText(demandDetail.getDeadmini());
                    tvSwdeid.setText(demandDetail.getSwdeid());
                    tvSrbasesummary.setText(demandDetail.getSrbasesummary());
                    tvSrdemandes.setText(demandDetail.getSrdemandes());
                    tvCreatetime.setText(demandDetail.getCreatetime());
                    tvDemandclassifl.setText(demandDetail.getDemandclassifl());
                    tvRequirementsubsys.setText(demandDetail.getRequirementsubsys());
                    tvEstimatedtime.setText(demandDetail.getEstimatedtime());
                    tvPriTime.setText(demandDetail.getPri_time());
                    break;
                default:
                    break;
            }
        }
    }

    private void initView() {
        getTitleBar().setMiddleText(getIntent().getStringExtra("title"));

    }


}
