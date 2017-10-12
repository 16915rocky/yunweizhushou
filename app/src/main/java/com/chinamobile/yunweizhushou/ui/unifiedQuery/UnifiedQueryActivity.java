package com.chinamobile.yunweizhushou.ui.unifiedQuery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chinamobile.yunweizhushou.R.id.lt_item1;

/**
 * Created by Administrator on 2017/8/9.
 */

public class UnifiedQueryActivity extends BaseActivity implements View.OnClickListener {
    @BindView(lt_item1)
    LinearLayout ltItem1;
    @BindView(R.id.lt_item2)
    LinearLayout ltItem2;
    @BindView(R.id.lt_item3)
    LinearLayout ltItem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified_query);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("统一查询");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ltItem1.setOnClickListener(this);
        ltItem2.setOnClickListener(this);
        ltItem3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.lt_item1:
                intent.putExtra("type","1");
                intent.putExtra("titleName","主机查询");
                intent.setClass(UnifiedQueryActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.lt_item2:
                intent.putExtra("type","2");
                intent.putExtra("titleName","用户查询");
                intent.setClass(UnifiedQueryActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            default:break;
        }

    }
}
