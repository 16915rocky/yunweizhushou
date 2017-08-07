package com.chinamobile.yunweizhushou.ui.dialogFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.produceLine.TimeSelectEvent;
import com.chinamobile.yunweizhushou.ui.produceLine.TimeSelectEvent2;
import com.chinamobile.yunweizhushou.view.PickerScrollView;
import com.chinamobile.yunweizhushou.view.Pickers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/10.
 */

public class SelectTimeDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    @BindView(R.id.psv_time_select)
    PickerScrollView   psvTimeSelect;
    Unbinder unbinder;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.tv_no)
    TextView tvNo;
    private ArrayList<String> timesList;
    private ArrayList<Pickers> list;
    private String selectTime;
    private PickerScrollView.onSelectListener  pickerListener;
    private int tab=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle arguments = getArguments();
        timesList = arguments.getStringArrayList("timesList");
        tab=arguments.getInt("tab");
        View view = inflater.inflate(R.layout.item_produceline_month_time_select, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        tvYes.setOnClickListener(this);
        tvNo.setOnClickListener(this);
        // 滚动选择器选中事件
        pickerListener = new PickerScrollView.onSelectListener() {

            @Override
            public void onSelect(Pickers pickers) {
                selectTime=pickers.getShowConetnt();
            }
        };
        psvTimeSelect.setOnSelectListener(pickerListener);

    }

    private void initData() {
        list = new ArrayList<Pickers>();
        for (int i = 0; i < timesList.size(); i++) {
            list.add(new Pickers(timesList.get(i), i + 1 + ""));
        }
        psvTimeSelect.setSelected(0);
        psvTimeSelect.setData(list);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_yes :
                TimeSelectEvent timeSelectEvent = new TimeSelectEvent();
                timeSelectEvent.setTime(selectTime);
                if(tab==1){
                    EventBus.getDefault().post(new TimeSelectEvent(selectTime));
                }
                if(tab==2){
                    EventBus.getDefault().post(new TimeSelectEvent2(selectTime));
                }
                onDestroyView();
                break;
            case R.id.tv_no :
                onDestroyView();
                break;
            default:break;
        }
    }
}
