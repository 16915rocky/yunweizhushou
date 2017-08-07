package com.chinamobile.yunweizhushou.ui.serviceChain.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceChainNextAdapter extends BaseAdapter {

	private JSONArray ja;
	private Context mcontext;

	public ServiceChainNextAdapter(Context context, JSONArray ja) {
		this.ja = ja;
		this.mcontext = context;
	}

	@Override
	public int getCount() {
		return ja.length();
	}

	@Override
	public Object getItem(int position) {
		Object object = null;
		try {
			object = ja.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_service_chain_next_item, null);
		LinearLayout lt_scn = (LinearLayout)view.findViewById(R.id.lt_scn);
		LinearLayout lt_arrow = (LinearLayout)view.findViewById(R.id.lt_arrow);
		TextView tv_item0 = (TextView) view.findViewById(R.id.tv_item0);
		JSONObject jo;
		try {
			jo = ja.getJSONObject(position);
			tv_item0.setText(jo.getString("osb_type_name"));
			JSONArray jsonArray = jo.getJSONArray("itemsList");
			for (int i = 0; i < jsonArray.length(); i++) {
				LinearLayout layout = new LinearLayout(mcontext);
				layout.setGravity(Gravity.CENTER);
				layout.setOrientation(LinearLayout.VERTICAL);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				TextView tv = new TextView(mcontext);
				LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
					    500, LinearLayout.LayoutParams.WRAP_CONTENT);
				tv_params.setMargins(0, 5, 0, 5);
				tv.setLayoutParams(tv_params);
				tv.setText(jsonArray.getJSONObject(i).getString("osb_name"));
				tv.setBackgroundResource(R.drawable.corner_rectangle_lightgray2_bg3);
				tv.setGravity(Gravity.CENTER);
				layout.addView(tv);
				layout.setLayoutParams(layoutParams);
				lt_scn.addView(layout);
			}
		if(position==(ja.length()-1)){
			lt_arrow.setVisibility(View.GONE);
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return view;
	}

	/*
	 * class ViewHolder{ public TextView tv_item0; public LinearLayout lt_scn; }
	 */

}
