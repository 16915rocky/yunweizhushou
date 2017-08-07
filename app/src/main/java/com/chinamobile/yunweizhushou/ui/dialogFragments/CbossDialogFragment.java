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

public class CbossDialogFragment extends BaseDialogFragment implements OnClickListener {

	private TextView tv1, tv2;
	private EditText et;
	private String noteContent;

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public CbossDialogFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.fragment_cboss_dialog, container, false);

		initView(view);
		initRequest(0);
		initEvent();
		return view;
	}

	private void initEvent() {
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
	}

	private void initRequest(int id) {

		HashMap<String, String> map = new HashMap<>();
		if (id == 0) {
			map.put("action", "findRemarkOfCboss");
			startTask(HttpRequestEnum.enum_cboss_note1, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
		} else {
			map.put("action", "operateRemarkOfCboss");
			map.put("remark", noteContent);
			startTask(HttpRequestEnum.enum_cboss_note2, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
		}

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_cboss_note1:

			if (responseBean.isSuccess()) {
				try {
					// noteContent=(String) new
					// JSONObject(responseBean.getDATA()).get("remark");
					noteContent = new JSONObject(responseBean.getDATA()).getString("remark");
					et.setText(noteContent);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_cboss_note2:

			if (responseBean.isSuccess()) {
				Toast.makeText(getActivity(), "添加或修改成功", Toast.LENGTH_LONG).show();

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}

	}

	private void initView(View view) {
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		et = (EditText) view.findViewById(R.id.diglog_editText);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv1:
			noteContent = et.getText().toString();
			initRequest(1);

			break;
		case R.id.tv2:
			dismiss();
			break;

		default:
			break;
		}

	}

}
