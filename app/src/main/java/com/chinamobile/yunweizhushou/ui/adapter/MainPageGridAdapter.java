package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.common.SelectorModules;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class MainPageGridAdapter extends RecyclerView.Adapter<MainPageGridAdapter.MyViewHolder> {

    private Context mContext;
    private List<MainPageGridBean> mList;
    public MainPageGridAdapter(Context context,List<MainPageGridBean> list) {
        this.mContext=context;
        this.mList=list;

    }

    @Override
    public MainPageGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_common_grid_item,null));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.rt_grid_item.setBackgroundResource(0);
        holder.rt_grid_item.setTag(mList.get(position));
        Glide.with(mContext)
             .load(mList.get(position).getIcon())
             .into(holder.img);
        holder.tv.setText(mList.get(position).getD_name());
        holder.rt_grid_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPageGridBean mb=(MainPageGridBean)view.getTag();
                if(mb!=null) {
                    SelectorModules sm = new SelectorModules();
                    sm.goToModules(mContext, mb.getD_enum(),mb.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView tv;
        private RelativeLayout rt_grid_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img_mainpage_grid_item);
            tv= (TextView) itemView.findViewById(R.id.tv_mainpage_grid_item);
            rt_grid_item= (RelativeLayout) itemView.findViewById(R.id.rt_grid_item);
        }
    }
}
