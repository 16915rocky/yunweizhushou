package com.chinamobile.yunweizhushou.ui.teamcheck.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.AssessmentBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AssessmentAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class AssessmentFragment extends BaseFragment {

	private String type;
	private List<AssessmentBean> mList;
	private AssessmentAdapter mAdapter;
	private ScrollView scrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getArguments().getString("type");
	}

	public static AssessmentFragment newInstance(String type) {
		AssessmentFragment fragment = new AssessmentFragment();
		Bundle b = new Bundle();
		b.putString("type", type);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		scrollView = new ScrollView(getActivity());
		scrollView.setVerticalScrollBarEnabled(false);
		initRequest();
		return scrollView;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "findService");
		map.put("year", "2016");
		map.put("firstOrderNorm", type);
		startTask(HttpRequestEnum.enum_team_detail, ConstantValueUtil.URL + "Assessment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_team_detail:
			Type type = new TypeToken<List<AssessmentBean>>() {
			}.getType();
			mList = new Gson().fromJson(responseBean.getDATA(), type);
			LinearLayout linearLayout = new LinearLayout(getActivity());
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			for (int i = 0; i < mList.size(); i++) {
				MyListView mListview = new MyListView(getActivity());
				mListview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				View head = LayoutInflater.from(getActivity()).inflate(R.layout.head_assessment, null);
				TextView tv = (TextView) head.findViewById(R.id.assessment_head);
				tv.setText(mList.get(i).getItem());
				mListview.addHeaderView(head);
				mAdapter = new AssessmentAdapter(getActivity(), mList.get(i).getItemsList(), R.layout.item_assessment);
				mListview.setAdapter(mAdapter);
				linearLayout.addView(mListview);
			}
			scrollView.addView(linearLayout);
			break;

		default:
			break;
		}
	}

}
