package com.chinamobile.yunweizhushou.ui.levelStandar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.levelStandar.bean.LevelStandardBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class LevelStandardFragment extends BaseFragment {

	private List<LevelStandardBean> mList;
	private LevelStandardAdapter mAdapter;
	private ListView mListview;
	private int id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id = getArguments().getInt("id");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_listview_notitle, container, false);
		mListview = (ListView) view.findViewById(R.id.common_listview);

		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "findByLevels");
		map.put("levels", id + "");
		startTask(HttpRequestEnum.enum_takara_level, ConstantValueUtil.URL + "SupportStandard?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_takara_level:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<LevelStandardBean>>() {
				}.getType();
				mList = new Gson().fromJson(responseBean.getDATA(), type);
				mAdapter = new LevelStandardAdapter(getActivity(), mList, R.layout.item_level_standard);
				mListview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	public static LevelStandardFragment newInstance(int id) {
		LevelStandardFragment mFragment = new LevelStandardFragment();
		Bundle b = new Bundle();
		b.putInt("id", id);
		mFragment.setArguments(b);
		return mFragment;
	}
}

class LevelStandardAdapter extends AbsBaseAdapter<LevelStandardBean> {

	private List<LevelStandardBean> mList;

	public LevelStandardAdapter(Context context, List<LevelStandardBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, LevelStandardBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.level_standard_item_name);
			holder.num = (TextView) convertView.findViewById(R.id.level_standard_item_num);
			holder.point = (TextView) convertView.findViewById(R.id.level_standard_item_point);
		}

		if (position < 9) {
			holder.num.setText("0" + (position + 1));
		} else {
			holder.num.setText((position + 1) + "");
		}
		if (position == 0) {
			holder.num.setBackgroundResource(R.drawable.oval_orange);
		} else if (position == 1) {
			holder.num.setBackgroundResource(R.drawable.oval_green);
		} else if (position == 2) {
			holder.num.setBackgroundResource(R.drawable.oval_violet);
		} else {
			holder.num.setBackgroundResource(R.drawable.oval_gray);
		}
		holder.name.setText(mList.get(position).getBusiName());
		holder.point.setText(mList.get(position).getScore() + "");

		return convertView;
	}

	private class ViewHolder {
		private TextView num, name, point;
	}
}
