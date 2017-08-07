package com.chinamobile.yunweizhushou.ui.systemTree.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.systemTree.adapter.SystemTreeAdapter;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.SystemTreeBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class SystemTreeFragment extends BaseFragment {

	private int id;
	private String[] titles = new String[] { "渠道支撑应用层", "业务能力集成层", "核心能力中心层", "基础能力层", "接口子域" };
	private LinearLayout linearLayout;
	private List<SystemTreeBean> systemList;

	public static SystemTreeFragment newInstance(int id) {
		SystemTreeFragment mFragment = new SystemTreeFragment();
		Bundle b = new Bundle();
		b.putInt("id", id);
		mFragment.setArguments(b);
		return mFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id = getArguments().getInt("id");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ScrollView scrollView = new ScrollView(getActivity());
		linearLayout = new LinearLayout(getActivity());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(20, 20, 20, 0);
		scrollView.addView(linearLayout);
		initRequest();
		return scrollView;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getImportantSystem");
		map.put("id1Name", titles[id]);
		startTask(HttpRequestEnum.enum_system_tree, ConstantValueUtil.URL + "ImportantSystem?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_system_tree:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<SystemTreeBean>>() {
				}.getType();
				systemList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				addContent(systemList);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void addContent(List<SystemTreeBean> list) {
		for (int i = 0; i < list.size(); i++) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_system_tree, null);
			TextView title = (TextView) view.findViewById(R.id.system_tree_title);
			MyGridView gridView = (MyGridView) view.findViewById(R.id.system_tree_gridview);
			if (list.get(i).getItemsList().size() == 1) {
				gridView.setNumColumns(1);
			}
			title.setText(list.get(i).getItem());
			SystemTreeAdapter mAdapter = new SystemTreeAdapter(getActivity(), list.get(i).getItemsList(),
					R.layout.item_system_tree_item);
			gridView.setAdapter(mAdapter);
			linearLayout.addView(view);
		}
	}

}
