package com.chinamobile.yunweizhushou.ui.dialogFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CustomDialog extends BaseDialogFragment implements OnClickListener {

	private TextView tv1, tv2, tv3;
	private EditText et;
	private String content;
	private String operation;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		operation=arguments.getString("operation");
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_credit_control, container, false);

		initView(view);
		initEvent();
		return view;
	}

	private void initEvent() {
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "operate");
		maps.put("operation", operation);
		startTask(HttpRequestEnum.enum_credit_control, ConstantValueUtil.URL + "CreditControl?", maps, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_credit_control:
			try {
				content = new JSONObject(responseBean.getDATA()).getString("content");
				Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
				dismiss();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			break;

		default:
			break;
		}

	}

	private void initView(View view) {
		tv1 = (TextView) view.findViewById(R.id.positiveButton);
		tv2 = (TextView) view.findViewById(R.id.negativeButton);
		tv3 = (TextView) view.findViewById(R.id.message);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.positiveButton:
			initRequest();

			break;
		case R.id.negativeButton:
			dismiss();
			break;

		default:
			break;
		}

	}

}
