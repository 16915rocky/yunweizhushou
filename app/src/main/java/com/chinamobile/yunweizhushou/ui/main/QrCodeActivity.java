package com.chinamobile.yunweizhushou.ui.main;

import android.os.Bundle;
import android.view.View;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;

/**
 * Created by Administrator on 2017/7/12.
 */

public class QrCodeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
