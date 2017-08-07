package com.chinamobile.yunweizhushou.ui.produceLine.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.dialogFragments.SelectTimeDialogFragment;
import com.chinamobile.yunweizhushou.ui.produceLine.PoduceLineMoneyoutDetailActivity;
import com.chinamobile.yunweizhushou.ui.produceLine.ProduceLineMonthChangeStateActivity;
import com.chinamobile.yunweizhushou.ui.produceLine.ProduceLineMonthFocusActivity;
import com.chinamobile.yunweizhushou.ui.produceLine.TimeSelectEvent2;
import com.chinamobile.yunweizhushou.ui.produceLine.bean.ProduceLineMonthContentBean;
import com.chinamobile.yunweizhushou.ui.produceLine.bean.ProduceLineMonthStarBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("NewApi")
public class ProduceLineMoneyoutFragment extends BaseFragment implements OnClickListener, ProduceLineMonthChangeContentDialogFragment.OnClickCommitListener {

	private MyListView listview1, listview2;
	private RelativeLayout itemSelect;
	private ArrayList<String> timesList, itemsList;
	private List<ProduceLineMonthContentBean> contentList;
	private List<ProduceLineMonthStarBean> starList;
	private String currentTime, currentItem = "全部";
	private TextView timeTv, itemTv;
	private PopupWindow popupWindow;
	private View popupView;
	private int tag;
	private static final int TIME_TAG = 1;
	private static final int ITEM_TAG = 2;
	private ListView popupListview;
	private TextView focus, add;
	private PopupWindow popWnd;
	private ListView common_listview;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_produceline_moneyout, container, false);
		EventBus.getDefault().register(this);
		initView(view);
		initPopup();
		initEvent();
		initTimeRequest();
		initItemRequest();
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			initContentRequest();
		}
	}

	private void initPopup() {
		popupView = LayoutInflater.from(getActivity()).inflate(R.layout.common_listview_notitle, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		popupListview = (ListView) popupView.findViewById(R.id.common_listview);
	}

	private void initEvent() {
		timeTv.setOnClickListener(this);
		itemSelect.setOnClickListener(this);
		focus.setOnClickListener(this);
		add.setOnClickListener(this);

		listview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), PoduceLineMoneyoutDetailActivity.class);
				intent.putExtra("data", contentList.get(position));
				startActivityForResult(intent, 0);
			}
		});

		popupListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				if (tag == TIME_TAG) {
					currentTime = timesList.get(position);
					timeTv.setText(currentTime);
					initContentRequest();
				} else if (tag == ITEM_TAG) {
					currentItem = itemsList.get(position);
					itemTv.setText(currentItem);
					initContentRequest();
				}
			}
		});
	}

	private void initTimeRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getParam");
		map.put("event", "酬金出账");
		map.put("type", "1");
		startTask(HttpRequestEnum.enum_produceline_moneyout_time, ConstantValueUtil.URL + "CoreLine?", map);
	}

	private void initItemRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getParam");
		map.put("event", "酬金出账");
		map.put("type", "2");
		startTask(HttpRequestEnum.enum_produceline_moneyout_item, ConstantValueUtil.URL + "CoreLine?", map);
	}

	private void initContentRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getRecord");
		map.put("event", "酬金出账");
		map.put("title", currentTime);
		map.put("category", currentItem);
		startTask(HttpRequestEnum.enum_produceline_moneyout_content, ConstantValueUtil.URL + "CoreLine?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_produceline_moneyout_time:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONArray array = obj.getJSONArray("itemsList");
					timesList = new ArrayList<>();
					for (int i = 0; i < array.length(); i++) {
						timesList.add(array.getString(i));
					}
					currentTime = timesList.get(0);
					timeTv.setText(currentTime);
					initContentRequest();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			case enum_produceline_moneyout_item:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONArray array = obj.getJSONArray("itemsList");
					itemsList = new ArrayList<>();
					for (int i = 0; i < array.length(); i++) {
						if (null != array.getString(i)) {
							itemsList.add(array.getString(i));
						}
					}
					currentItem = itemsList.get(0);
					itemTv.setText(currentItem);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			case enum_produceline_moneyout_content:
				Type t = new TypeToken<List<ProduceLineMonthStarBean>>() {
				}.getType();
				starList = new Gson().fromJson(Utils.getkeyEventsListArray(responseBean.getDATA()), t);
				ProduceLineMoneyoutStarAdapter adapter = new ProduceLineMoneyoutStarAdapter(getActivity(), starList,
						R.layout.item_produce_line_month_star);
				listview1.setAdapter(adapter);
				Type t2 = new TypeToken<List<ProduceLineMonthContentBean>>() {
				}.getType();
				contentList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t2);
				ProduceLineMoneyoutContentAdapter adapter2 = new ProduceLineMoneyoutContentAdapter(getActivity(),
						contentList, R.layout.item_produce_line_moneyout_content);
				listview2.setAdapter(adapter2);
				break;
			// case enum_produceline_month_change_content:
			// Utils.ShowErrorMsg(getActivity(), "更新成功");
			// initContentRequest();
			// break;
			case enum_produceline_month_change_star:
				Utils.ShowErrorMsg(getActivity(), "更新成功");
				initContentRequest();
				break;
			case enum_produceline_moneyout_add_star:
				Utils.ShowErrorMsg(getActivity(), "更新成功");
				initContentRequest();
				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private void initView(View view) {
		itemSelect = (RelativeLayout) view.findViewById(R.id.produce_line_moneyout_items);
		listview1 = (MyListView) view.findViewById(R.id.produce_line_moneyout_star_listview);
		listview2 = (MyListView) view.findViewById(R.id.produce_line_moneyout_content_listview);
		timeTv = (TextView) view.findViewById(R.id.produce_line_moneyout_time);
		itemTv = (TextView) view.findViewById(R.id.produce_line_moneyout_item);
		focus = (TextView) view.findViewById(R.id.produce_line_moneyout_focus);
		add = (TextView) view.findViewById(R.id.produce_line_moneyout_add);
	}

	class ProduceLineMoneyoutContentAdapter extends AbsBaseAdapter<ProduceLineMonthContentBean> {

		private List<ProduceLineMonthContentBean> list;

		public ProduceLineMoneyoutContentAdapter(Context context, List<ProduceLineMonthContentBean> list,
				int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, ProduceLineMonthContentBean t, final int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.type = (TextView) convertView.findViewById(R.id.produce_line_moneyout_type);
				holder.content = (TextView) convertView.findViewById(R.id.produce_line_moneyout_content);
				holder.date = (TextView) convertView.findViewById(R.id.produce_line_moneyout_date);
				holder.light = (ImageView) convertView.findViewById(R.id.produce_line_moneyout_lightbulb);
				holder.state = convertView.findViewById(R.id.produce_line_moneyout_state);
				convertView.setTag(holder);
			}
			holder.content.setText(list.get(position).getContent());
			holder.type.setText(list.get(position).getCategory());
			holder.date.setText(list.get(position).getEvent_date() + "\n" + list.get(position).getEvent_time());
			if (list.get(position).getState().equals("1")) {
				holder.state.setBackgroundResource(R.drawable.oval_light_green);
			} else {
				holder.state.setBackgroundResource(R.drawable.oval_gray);
			}
			if (list.get(position).getMenuval().startsWith("-")) {
				holder.light.setVisibility(View.GONE);
			} else {
				holder.light.setVisibility(View.VISIBLE);
			}
			holder.light.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Utils.jump2Shit(getActivity(), list.get(position).getMenuval());
				}
			});

			holder.state.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), ProduceLineMonthChangeStateActivity.class);
					intent.putExtra("tag", "酬金出账");
					intent.putExtra("time", currentTime);
					intent.putExtra("item", currentItem);
					intent.putStringArrayListExtra("itemsList", itemsList);
					startActivityForResult(intent, 0);
				}
			});
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView type, content, date;
		ImageView light;
		View state;
	}

	class ProduceLineMoneyoutStarAdapter extends AbsBaseAdapter<ProduceLineMonthStarBean> {

		private List<ProduceLineMonthStarBean> list;

		public ProduceLineMoneyoutStarAdapter(Context context, List<ProduceLineMonthStarBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, ProduceLineMonthStarBean t, final int position) {
			ViewHolder2 holder = (ViewHolder2) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder2();
				holder.person = (TextView) convertView.findViewById(R.id.produce_line_month_star_person);
				holder.content = (TextView) convertView.findViewById(R.id.produce_line_month_star_content);
				holder.image = (ImageView) convertView.findViewById(R.id.produce_line_month_star_change);
				convertView.setTag(holder);
			}
			holder.person.setText("责任人:" + list.get(position).getHandler());
			holder.content.setText("内容:" + list.get(position).getContent());
			holder.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ProduceLineMonthChangeContentDialogFragment fragment = new ProduceLineMonthChangeContentDialogFragment(
							);
					Bundle bundle = new Bundle();
					bundle.putSerializable("starBean", list.get(position));
					fragment.setArguments(bundle);
					fragment.setOnClickCommitListener(ProduceLineMoneyoutFragment.this);
					fragment.show(getFragmentManager(), "1");
				}
			});
			return convertView;
		}

	}

	private static class ViewHolder2 {
		TextView person, content;
		ImageView image;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.produce_line_moneyout_time:
			tag = TIME_TAG;
//			ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
//					android.R.layout.simple_expandable_list_item_1, timesList);
//			popupListview.setAdapter(adapter);
//			// popupWindow.showAtLocation(timeTv, Gravity.CENTER, 0, 0);
//			popupWindow.showAsDropDown(timeTv, 0, 0, Gravity.BOTTOM);
			SelectTimeDialogFragment dialogFragment2=new SelectTimeDialogFragment();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("timesList",timesList);
			bundle.putInt("tab",2);
			dialogFragment2.setArguments(bundle);
			dialogFragment2.show(getFragmentManager(),"selectTimeDialogFragment2");
			break;
		case R.id.produce_line_moneyout_items:
			tag = ITEM_TAG;
			ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(),
					android.R.layout.simple_expandable_list_item_1, itemsList);
			setPopWindow(adapter2);
			//popupListview.setAdapter(adapter2);
			//popupWindow.showAtLocation(itemSelect, Gravity.BOTTOM, 0, 0);
			// popupWindow.showAsDropDown(itemSelect, 0,
			// -(popupListview.getHeight()+itemSelect.getHeight()),
			// Gravity.TOP);
			break;
		case R.id.produce_line_moneyout_focus:
			Intent intent = new Intent();
			intent.setClass(getActivity(), ProduceLineMonthFocusActivity.class);
			intent.putExtra("tag", "酬金出账");
			intent.putExtra("time", currentTime);
			intent.putExtra("item", currentItem);
			startActivity(intent);
			break;
		case R.id.produce_line_moneyout_add:
			ProduceLineMonthChangeContentDialogFragment fragment = new ProduceLineMonthChangeContentDialogFragment();
			fragment.setOnClickCommitListener(ProduceLineMoneyoutFragment.this);
			fragment.show(getFragmentManager(), "1");
			break;

		default:
			break;
		}
	}
	@Subscribe
	public void onEventMainThread(TimeSelectEvent2 event){
		currentTime = event.getTime();
		initContentRequest();
		timeTv.setText(currentTime);
	}

	public void setPopWindow(ArrayAdapter<String> adapter){
		View view=LayoutInflater.from(getActivity()).inflate(R.layout.common_listview_notitle,null);
		popWnd = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,true);
		popWnd.setContentView(view);
		common_listview= (ListView) view.findViewById(R.id.common_listview);
		common_listview.setBackgroundResource(R.color.color_white);
		common_listview.setAdapter(adapter);
		View parentView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_produceline_month,null);
		popWnd.showAtLocation(parentView, Gravity.BOTTOM,0,0);
		common_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				currentItem=itemsList.get(i);
				itemTv.setText(currentItem);
				initContentRequest();
				popWnd.dismiss();
			}
		});
	}

	private void initChangeStar(String id, String person, String content) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "updateRecord");
		map.put("id", id);
		map.put("handler", person);
		map.put("content", content);
		map.put("event_date", "");
		map.put("event_time", "");
		map.put("remark", "");
		startTask(HttpRequestEnum.enum_produceline_month_change_star, ConstantValueUtil.URL + "CoreLine?", map);
	}

	private void initAddStar(String person, String content) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "addKeyEvent");
		map.put("event", "酬金出账");
		map.put("title", currentTime);
		map.put("handler", person);
		map.put("content", content);
		startTask(HttpRequestEnum.enum_produceline_moneyout_add_star, ConstantValueUtil.URL + "CoreLine?", map);
	}

	@Override
	public void onClickCommitContent(String id, String person, String remark, String date, String time) {
	}

	@Override
	public void onClickCommitStar(String id, String person, String content) {
		initChangeStar(id, person, content);
	}

	@Override
	public void onClickAddStar(String person, String content) {
		initAddStar(person, content);
	}

}
