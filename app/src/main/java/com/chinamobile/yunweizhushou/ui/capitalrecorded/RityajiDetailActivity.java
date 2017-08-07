package com.chinamobile.yunweizhushou.ui.capitalrecorded;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.capitalrecorded.fragments.RityajiFragment2;


public class RityajiDetailActivity extends BaseActivity {

	private RityajiFragment2 fragment;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_frame_layout);

		initEvent();

		ft = getSupportFragmentManager().beginTransaction();
		String fkId = getIntent().getStringExtra("fkId");
		Bundle bundle=new Bundle();
		bundle.putInt("fkId",Integer.valueOf(fkId));
		fragment = new RityajiFragment2();
		fragment.setArguments(bundle);
		ft.add(R.id.common_frame_layout, fragment);
		ft.commit();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
