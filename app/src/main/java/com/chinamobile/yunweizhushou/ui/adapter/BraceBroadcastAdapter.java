package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.BraceBroadcastBean;
import com.chinamobile.yunweizhushou.ui.fault.ChangeActivity;
import com.chinamobile.yunweizhushou.ui.fault.CommentActivity;
import com.chinamobile.yunweizhushou.ui.fault.FaultTodayDetailActivity;
import com.chinamobile.yunweizhushou.view.FlexibleTextView;

import java.util.List;

public class BraceBroadcastAdapter extends AbsBaseAdapter<BraceBroadcastBean> {

	private List<BraceBroadcastBean> mList;
	private Context context;
	private Handler mHandler;
	private boolean showBottom;

	public BraceBroadcastAdapter(Context context, List<BraceBroadcastBean> list, int resourceId, Handler handler,
			boolean show) {
		super(context, list, resourceId);
		this.context = context;
		this.mList = list;
		this.mHandler = handler;
		this.showBottom = show;
	}

	@Override
	protected View newView(View convertView, BraceBroadcastBean t, final int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.brace_item_time);
			holder.type = (TextView) convertView.findViewById(R.id.brace_item_type);
			holder.content = (FlexibleTextView) convertView.findViewById(R.id.brace_item_content);
			holder.read = (TextView) convertView.findViewById(R.id.brace_item_read);
			holder.share = (TextView) convertView.findViewById(R.id.brace_item_share);
			holder.comment = (TextView) convertView.findViewById(R.id.brace_item_comment);
			holder.msg = (TextView) convertView.findViewById(R.id.brace_item_msg);
			holder.bottom = (LinearLayout) convertView.findViewById(R.id.timeasix_gone_layout);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getBomcTime());
		holder.type.setText("【" + mList.get(position).getBomcName() + "】");
		holder.content.setText(mList.get(position).getBomcTitle());
		holder.comment.setText(mList.get(position).getCommentNumber());
		holder.read.setText("阅读  " + mList.get(position).getBomcNumber());
		if (showBottom) {
			holder.bottom.setVisibility(View.VISIBLE);
		} else {
			holder.bottom.setVisibility(View.GONE);
		}
		holder.msg.setVisibility(View.INVISIBLE);
		holder.comment.setOnClickListener(null);

		if (mList.get(position).getBomcType() != null && mList.get(position).getBomcType().equals("11")) {
			holder.content.setClickable(false);
			holder.msg.setVisibility(View.VISIBLE);
			holder.msg.setText("点击阅读");
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = new Message();
					msg.obj = mList.get(position).getId();
					mHandler.sendMessage(msg);
					Intent intent = new Intent();
					intent.setClass(context, ChangeActivity.class);
					intent.putExtra("id", mList.get(position).getId());
					intent.putExtra("title", mList.get(position).getBomcTitle());
					context.startActivity(intent);
				}
			});
		} else if (mList.get(position).getBomcType() != null && mList.get(position).getBomcType().equals("1")) {
			holder.content.setClickable(false);
			holder.msg.setVisibility(View.VISIBLE);
			holder.msg.setText("点击阅读");
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = new Message();
					msg.obj = mList.get(position).getId();
					mHandler.sendMessage(msg);
					Intent intent = new Intent();
					intent.setClass(context, FaultTodayDetailActivity.class);
					intent.putExtra("id", mList.get(position).getId());
					context.startActivity(intent);
				}
			});
		} else {
			holder.content.setClickable(true);
			holder.comment.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(context, CommentActivity.class);
					intent.putExtra("item", mList.get(position));
					context.startActivity(intent);
				}
			});
			// convertView.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Message msg = new Message();
			// msg.obj = mList.get(position).getId();
			// mHandler.sendMessage(msg);
			// }
			// });
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView time, type, read, share, msg, comment;
		private FlexibleTextView content;
		private LinearLayout bottom;
	}
}
