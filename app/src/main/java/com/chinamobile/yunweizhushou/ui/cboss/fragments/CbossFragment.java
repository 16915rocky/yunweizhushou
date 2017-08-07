package com.chinamobile.yunweizhushou.ui.cboss.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.GovernNewListBean;
import com.chinamobile.yunweizhushou.bean.GovernNewTitleBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.cboss.CbossDetailActivity;
import com.chinamobile.yunweizhushou.ui.dialogFragments.CbossDialogFragment;
import com.chinamobile.yunweizhushou.ui.dialogFragments.CbossWaringDialogFragment;
import com.chinamobile.yunweizhushou.ui.esbInterface.adapters.OperateAnalysisAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CbossFragment extends BaseFragment implements OnClickListener {

	private int tag;
	public static final int WARNING = 1032;
	public static final int MONITOR = 1016;
	private LinearLayout leftLayout, rightLayout;
	private TextView point, timeTicker, normal, warning, error, noteButton;
	private GridView mGridView;
	private GovernNewTitleBean titleRightBean;
	private List<GovernNewListBean> listBean;
	private OperateAnalysisAdapter mAdapter;
	private MyRefreshLayout mRefreshLayout;
	private String noteContent;
	private TextView note;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_cboss, container, false);
		Bundle arguments = getArguments();
		tag=arguments.getInt("tag");
		initView(view);
		initData();
		initRequest();
		initEvent();
		return view;

	}

	private void initEvent() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), CbossDetailActivity.class);
				intent.putExtra("name", listBean.get(position).getName());
				intent.putExtra("tag", tag + 2);
				startActivity(intent);
			}

		});

		note.setOnClickListener(this);

		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

		leftLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CbossWaringDialogFragment dialog = new CbossWaringDialogFragment();
				dialog.show(getChildFragmentManager(), "");
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", tag + "");
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_cboss_title, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getSpecial");
		map2.put("id", (tag + 1) + "");
		map2.put("code", "");
		map2.put("busi", "");
		map2.put("cond", "");
		startTask(HttpRequestEnum.enum_cboss_list, ConstantValueUtil.URL + "SpecialTreatment?", map2, true);
		HashMap<String, String> map3 = new HashMap<>();
		map3.put("action", "findRemarkOfCboss");
		startTask(HttpRequestEnum.enum_cboss_note1, ConstantValueUtil.URL + "SpecialTreatment?", map3, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_cboss_title:
			if (responseBean.isSuccess()) {
				if (tag == WARNING) {
					try {
						String p = new JSONObject(responseBean.getDATA()).getString("score");
						point.setText(p);
						String time = new JSONObject(responseBean.getDATA()).getString("time");
						timeTicker.setText("截止" + time);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				} else if (tag == MONITOR) {
					titleRightBean = new Gson().fromJson(responseBean.getDATA(), GovernNewTitleBean.class);
					normal.setText(titleRightBean.getNormal());
					warning.setText(titleRightBean.getWarning());
					error.setText(titleRightBean.getOptimize());
				}
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_cboss_list:

			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<GovernNewListBean>>() {
				}.getType();
				listBean = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new OperateAnalysisAdapter(getActivity(), listBean, R.layout.item_operate_analysis);
				mGridView.setAdapter(mAdapter);

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}

			break;
		case enum_cboss_note1:

			if (responseBean.isSuccess()) {
				try {
					// noteContent=(String) new
					// JSONObject(responseBean.getDATA()).get("remark");
					noteContent = new JSONObject(responseBean.getDATA()).getString("remark");
					note.setText(noteContent);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initData() {
		if (tag == WARNING) {
			leftLayout.setVisibility(View.VISIBLE);
			rightLayout.setVisibility(View.GONE);
		} else if (tag == MONITOR) {
			leftLayout.setVisibility(View.GONE);
			rightLayout.setVisibility(View.VISIBLE);
		}
	}

	private void initView(View view) {
		leftLayout = (LinearLayout) view.findViewById(R.id.cboss_left_layout);
		rightLayout = (LinearLayout) view.findViewById(R.id.cboss_right_layout);
		point = (TextView) view.findViewById(R.id.cboss_minus_point);
		noteButton = (TextView) view.findViewById(R.id.tv_note_button);
		note = (TextView) view.findViewById(R.id.tv_note);
		timeTicker = (TextView) view.findViewById(R.id.cboss_time_ticker);
		normal = (TextView) view.findViewById(R.id.cboss_normal);
		warning = (TextView) view.findViewById(R.id.cboss_warning);
		error = (TextView) view.findViewById(R.id.cboss_error);
		mGridView = (GridView) view.findViewById(R.id.cboss_gridview);
		mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.cboss_fragment_refrashLayout);
	}

	@Override
	public void onClick(View v) {
		CbossDialogFragment dialog2 = new CbossDialogFragment();
		dialog2.show(getFragmentManager(), "dialog");
		// noteContent=dialog2.getNoteContent();
		// note.setText(noteContent);
		// initRequest();

	}

}
