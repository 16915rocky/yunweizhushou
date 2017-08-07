package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.ui.main.MainEidtDelEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class MoreItemEditGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<MainPageGridBean> mList;
    public MoreItemEditGridAdapter(Context context, List<MainPageGridBean> list) {
        this.mContext=context;
        this.mList=list;

    }

    @Override
    public int getCount() {
        return mList.size() ;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MoreItemEditGridAdapter.MyViewHolder viewHolder;
        if(view==null){
            viewHolder=new MoreItemEditGridAdapter.MyViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.view_edit_grid_item,null);
            viewHolder.img= (ImageView) view.findViewById(R.id.img_mainpage_grid_item);
            viewHolder.tv_subtitle= (TextView) view.findViewById(R.id.tv_mainpage_grid_item);
            viewHolder.img_tag= (ImageView) view.findViewById(R.id.img_tag);
            view.setTag(viewHolder);
        }else{
            viewHolder= (MoreItemEditGridAdapter.MyViewHolder) view.getTag();
        }
        viewHolder.tv_subtitle.setText(mList.get(i).getD_name());
        Glide.with(mContext)
                .load(mList.get(i).getIcon())
                .into(viewHolder.img);
        viewHolder.img_tag.setTag(mList.get(i));
        viewHolder.img_tag.setFocusable(true);
        viewHolder.img_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imgView=(ImageView)view;
                MainPageGridBean mb= (MainPageGridBean) imgView.getTag();
                mList.remove(mb);
                notifyDataSetChanged();
                MainEidtDelEvent medEvent=new MainEidtDelEvent();
                medEvent.setMb(mb);
                EventBus.getDefault().post(medEvent);
            }
        });
        return view;
    }
    class MyViewHolder {
        private TextView tv_subtitle;
        private ImageView img,img_tag;
    }
}
