package com.chinamobile.yunweizhushou.ui.produceLine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.produceLine.bean.ProduceLineMonthContentBean;
import com.chinamobile.yunweizhushou.ui.produceLine.fragments.ProduceLineMonthChangeContentDialogFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import java.util.HashMap;

public class PoduceLineMoneyoutDetailActivity extends BaseActivity implements OnClickListener, ProduceLineMonthChangeContentDialogFragment.OnClickCommitListener {

	private TextView l1, l2, l3, r1, r2, r3, r4, date;
	private ImageView change, lightBulb;
	private ProduceLineMonthContentBean bean;
	private String person, remark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_produce_line_moneyout_detail);
		bean = (ProduceLineMonthContentBean) getIntent().getSerializableExtra("data");
		initView();
		initEvent();
	}

	private void initEvent() {

		getTitleBar().setMiddleText(bean.getCategory());
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(110);
				finish();
			}
		});

		change.setOnClickListener(this);
		lightBulb.setOnClickListener(this);
	}

	private void initView() {
		l1 = (TextView) findViewById(R.id.produce_line_moneyout_l1);
		l2 = (TextView) findViewById(R.id.produce_line_moneyout_l2);
		l3 = (TextView) findViewById(R.id.produce_line_moneyout_l3);
		r1 = (TextView) findViewById(R.id.produce_line_moneyout_r1);
		r2 = (TextView) findViewById(R.id.produce_line_moneyout_r2);
		r3 = (TextView) findViewById(R.id.produce_line_moneyout_r3);
		r4 = (TextView) findViewById(R.id.produce_line_moneyout_r4);
		date = (TextView) findViewById(R.id.produce_line_moneyout_ue);
		change = (ImageView) findViewById(R.id.produce_line_moneyout_shita);
		lightBulb = (ImageView) findViewById(R.id.produce_line_moneyout_lightbulb);

		l1.setText(bean.getCategory());
		l2.setText(bean.getSubclass());
		l3.setText(bean.getMotion());
		r1.setText(bean.getContent());
		r2.setText(bean.getDescription());
		r3.setText(bean.getHandler());
		r4.setText(bean.getRemark());
		date.setText(bean.getEvent_date() + "\n" + bean.getEvent_time());
		if (bean.getMenuval().contains("-")) {
			lightBulb.setVisibility(View.GONE);
		} else {
			lightBulb.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.produce_line_moneyout_shita:
			ProduceLineMonthChangeContentDialogFragment dialog = new ProduceLineMonthChangeContentDialogFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("contentBean",bean);
			dialog.setArguments(bundle);
			dialog.setOnClickCommitListener(this);
			dialog.show(getSupportFragmentManager(), "");
			break;
		case R.id.produce_line_moneyout_lightbulb:
			Utils.jump2Shit(this, bean.getMenuval());
			break;
		default:
			break;
		}
	}

	private void initChangeContent(String id, String person, String remark, String date, String time) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "updateRecord");
		map.put("id", id);
		map.put("event_date", date);
		map.put("event_time", time);
		map.put("handler", person);
		map.put("remark", remark);
		map.put("content", "");
		startTask(HttpRequestEnum.enum_produceline_month_change_content, ConstantValueUtil.URL + "CoreLine?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_produceline_month_change_content:
				Utils.ShowErrorMsg(this, "更新成功");
				r3.setText(person);
				r4.setText(remark);
				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	@Override
	public void onClickCommitContent(String id, String person, String remark, String date, String time) {
		this.person = person;
		this.remark = remark;
		initChangeContent(id, person, remark, date, time);
	}

	@Override
	public void onClickCommitStar(String id, String person, String content) {

	}

	@Override
	public void onClickAddStar(String person, String content) {

	}

}
