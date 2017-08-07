package com.chinamobile.yunweizhushou.ui.levelStandar.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.levelStandar.bean.DutyChildBean;

import java.util.List;

public class DutyChartAdapter extends AbsBaseAdapter<DutyChildBean> {

	private List<DutyChildBean> mList;
	private Context mContext;

	public DutyChartAdapter(Context context, List<DutyChildBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		mContext = context;
	}

	@Override
	protected View newView(View convertView, DutyChildBean t, final int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.groupLayout = (LinearLayout) convertView.findViewById(R.id.duty_group_layout);
			holder.childLayout = (LinearLayout) convertView.findViewById(R.id.duty_child_layout);

			holder.groupName = (TextView) convertView.findViewById(R.id.duty_group_name);
			holder.groupLongNum = (TextView) convertView.findViewById(R.id.duty_group_longnum);
			holder.groupShortNum = (TextView) convertView.findViewById(R.id.duty_group_shortnum);
			holder.childName = (TextView) convertView.findViewById(R.id.duty_child_name);
			holder.childLongNum = (TextView) convertView.findViewById(R.id.duty_child_longnum);
			holder.childShortNum = (TextView) convertView.findViewById(R.id.duty_child_shortnum);

			holder.childHead = (ImageView) convertView.findViewById(R.id.duty_child_head);
			holder.childHeadTag = (ImageView) convertView.findViewById(R.id.duty_child_headtag);
			holder.childSun = (ImageView) convertView.findViewById(R.id.duty_child_sun);
			holder.childMoon = (ImageView) convertView.findViewById(R.id.duty_child_moon);
		}

		holder.childLayout.setVisibility(View.GONE);
		holder.groupLayout.setVisibility(View.GONE);
		holder.childSun.setVisibility(View.INVISIBLE);
		holder.childMoon.setVisibility(View.INVISIBLE);

		if (!TextUtils.isEmpty(mList.get(position).getName())) {
			holder.childLayout.setVisibility(View.VISIBLE);
			holder.childName.setText(mList.get(position).getName());
			holder.childLongNum.setText(mList.get(position).getLongNum());
			holder.childShortNum.setText(mList.get(position).getShortNum());
			if (mList.get(position).getWorkType().equals("全天")) {
				holder.childSun.setVisibility(View.VISIBLE);
				holder.childMoon.setVisibility(View.VISIBLE);
			} else if (mList.get(position).getWorkType().equals("白班")) {
				holder.childSun.setVisibility(View.VISIBLE);
				holder.childMoon.setVisibility(View.INVISIBLE);
			} else if (mList.get(position).getWorkType().equals("晚班")) {
				holder.childSun.setVisibility(View.INVISIBLE);
				holder.childMoon.setVisibility(View.VISIBLE);
			}
			if (mList.get(position).getRoleType().equals("值班人员")) {
				holder.childHead.setImageResource(R.mipmap.icon_duty_employee);
				holder.childHeadTag.setVisibility(View.INVISIBLE);
			} else if (mList.get(position).getRoleType().equals("甲方值班经理")) {
				holder.childHead.setImageResource(R.mipmap.icon_duty_manager_jia);
				holder.childHeadTag.setVisibility(View.VISIBLE);
				holder.childHeadTag.setImageResource(R.mipmap.icon_duty_manager_jia_dot);
			} else if (mList.get(position).getRoleType().equals("乙方值班经理")) {
				holder.childHead.setImageResource(R.mipmap.icon_duty_manager_yi);
				holder.childHeadTag.setVisibility(View.VISIBLE);
				holder.childHeadTag.setImageResource(R.mipmap.icon_duty_manager_yi_dot);
			}
		} else {
			holder.groupLayout.setVisibility(View.VISIBLE);
			holder.groupName.setText(mList.get(position).getSystem());
			holder.groupLongNum.setText(mList.get(position).getLongNum());
			holder.groupShortNum.setText(mList.get(position).getShortNum());
		}

		holder.childLongNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
				ab.setTitle("确定拨打" + mList.get(position).getLongNum() + "?")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialPhone(mList.get(position).getLongNum());
							}
						}).setNegativeButton("取消", null).show();
			}
		});
		holder.childShortNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
				ab.setTitle("确定拨打" + mList.get(position).getShortNum() + "?")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialPhone(mList.get(position).getShortNum());
							}
						}).setNegativeButton("取消", null).show();
			}
		});
		holder.groupLongNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
				ab.setTitle("确定拨打" + mList.get(position).getLongNum() + "?")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialPhone(mList.get(position).getLongNum());
							}
						}).setNegativeButton("取消", null).show();
			}
		});
		holder.groupShortNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
				ab.setTitle("确定拨打" + mList.get(position).getShortNum() + "?")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialPhone(mList.get(position).getShortNum());
							}
						}).setNegativeButton("取消", null).show();
			}
		});

		return convertView;
	}

	private class ViewHolder {
		private TextView groupName, groupLongNum, groupShortNum, childName, childLongNum, childShortNum;
		private ImageView childHead, childHeadTag, childSun, childMoon;
		private LinearLayout groupLayout, childLayout;
	}

	private void dialPhone(String number) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + number));
		if (intent.resolveActivity(mContext.getPackageManager()) != null) {
			mContext.startActivity(intent);
		}
	}

}
