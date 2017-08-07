package com.chinamobile.yunweizhushou.ui.produceLine.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.produceLine.bean.ProduceLineMonthContentBean;
import com.chinamobile.yunweizhushou.ui.produceLine.bean.ProduceLineMonthStarBean;

public class ProduceLineMonthChangeContentDialogFragment extends DialogFragment {

	private TextView tv1, tv2, tv3, tv4, cancel, commit, title;
	private EditText et1, et2, et3, et4;
	private LinearLayout layout3, layout4;
	private int tag;
	private ProduceLineMonthContentBean contentBean;
	private ProduceLineMonthStarBean starBean;
	private OnClickCommitListener listener;

	public interface OnClickCommitListener {
		void onClickCommitContent(String id, String person, String remark, String date, String time);

		void onClickCommitStar(String id, String person, String content);

		void onClickAddStar(String person, String content);
	}

	public void setOnClickCommitListener(OnClickCommitListener listener) {
		this.listener = listener;
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		if(arguments!=null) {
			tag=arguments.getInt("tag");
			starBean = (ProduceLineMonthStarBean) arguments.getSerializable("starBean");
			contentBean = (ProduceLineMonthContentBean) arguments.getSerializable("contentBean");
		}
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_fragment_produceline_change_content, container, false);
		initView(view);
		initEvent();

		return view;
	}

	private void initEvent() {
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tag == 1) {
					listener.onClickCommitContent(contentBean.getId(), et1.getText().toString(),
							et2.getText().toString(), et3.getText().toString(), et4.getText().toString());
				} else if (tag == 2) {
					listener.onClickCommitStar(starBean.getId(), et1.getText().toString(), et2.getText().toString());
				} else if (tag == 3) {
					listener.onClickAddStar(et1.getText().toString(), et2.getText().toString());
				}
				dismiss();
			}

		});
	}

	private void initView(View view) {
		cancel = (TextView) view.findViewById(R.id.produce_line_month_change_cancel);
		commit = (TextView) view.findViewById(R.id.produce_line_month_change_commit);
		title = (TextView) view.findViewById(R.id.produce_line_month_change_title);

		tv1 = (TextView) view.findViewById(R.id.produce_line_month_change_tv1);
		tv2 = (TextView) view.findViewById(R.id.produce_line_month_change_tv2);
		tv3 = (TextView) view.findViewById(R.id.produce_line_month_change_tv3);
		tv4 = (TextView) view.findViewById(R.id.produce_line_month_change_tv4);
		et1 = (EditText) view.findViewById(R.id.produce_line_month_change_et1);
		et2 = (EditText) view.findViewById(R.id.produce_line_month_change_et2);
		et3 = (EditText) view.findViewById(R.id.produce_line_month_change_et3);
		et4 = (EditText) view.findViewById(R.id.produce_line_month_change_et4);
		layout3 = (LinearLayout) view.findViewById(R.id.produce_line_month_change_layout3);
		layout4 = (LinearLayout) view.findViewById(R.id.produce_line_month_change_layout4);

		if (tag == 1) {
			title.setText(contentBean.getId());
			// tv1.setText(contentBean.getMotion());
			tv1.setText("责任人");
			tv2.setText("关注");
			tv3.setText("日期");
			tv4.setText("时间");
			et1.setText(contentBean.getHandler());
			et2.setText(contentBean.getRemark());
			et3.setText(contentBean.getEvent_date());
			et4.setText(contentBean.getEvent_time());
		} else if (tag == 2) {
			title.setText(starBean.getId());
			tv1.setText("责任人");
			tv2.setText("内容");
			layout3.setVisibility(View.GONE);
			layout4.setVisibility(View.GONE);
			et1.setText(starBean.getHandler());
			et2.setText(starBean.getContent());
		} else if (tag == 3) {
			title.setText("新增事件");
			tv1.setText("责任人");
			tv2.setText("内容");
		}
	}

}
