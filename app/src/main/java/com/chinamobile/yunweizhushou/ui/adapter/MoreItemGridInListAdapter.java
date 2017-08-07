package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.ui.main.MainEidtDelEvent;
import com.chinamobile.yunweizhushou.ui.main.MainEidtEvent;
import com.chinamobile.yunweizhushou.ui.main.MoreItemActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Administrator on 2017/5/30.
 */

public class MoreItemGridInListAdapter  extends BaseAdapter  {

    private Context mContext;
    private List<MainPageGridBean> mList;
    private int position;
    boolean isImgShow=false;
    private MainPageGridBean  compareMb;
    public MoreItemGridInListAdapter(Context context, List<MainPageGridBean> list,boolean isImgShow,MainPageGridBean compareMb) {
        this.mContext=context;
        this.mList=list;
        this.isImgShow=isImgShow;
        this.compareMb=compareMb;

    }
    //接收消息
    @Subscribe
    public void onEventMainThread(MainEidtDelEvent event) {
        //String msg = "onEventMainThread收到了消息：" + event.getMb().getD_name();
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        MainPageGridBean mb = event.getMb();
        compareMb=mb;
        notifyDataSetChanged();
    }

    public boolean isImgShow() {
        return isImgShow;
    }

    public void setImgShow(boolean imgShow) {
        isImgShow = imgShow;
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
        MyViewHolder viewHolder;
        position=i;
        if(view==null){
            viewHolder=new MyViewHolder();
            view=LayoutInflater.from(mContext).inflate(R.layout.view_common_grid_item,null);
            viewHolder.img= (ImageView) view.findViewById(R.id.img_mainpage_grid_item);
            viewHolder.tv_subtitle= (TextView) view.findViewById(R.id.tv_mainpage_grid_item);
            viewHolder.imgTag= (ImageView) view.findViewById(R.id.img_tag);
            viewHolder.rt_grid= (RelativeLayout) view.findViewById(R.id.rt_grid_item);
            viewHolder.rt_grid.setBackgroundResource(0);
            view.setTag(viewHolder);
        }else{
            viewHolder= (MyViewHolder) view.getTag();
        }
        viewHolder.tv_subtitle.setText(mList.get(i).getD_name());
        Glide.with(mContext)
             .load(mList.get(i).getIcon())
             .into(viewHolder.img);
        if(compareMb!=null){
            if(compareMb.getD_name().equals(mList.get(i).getD_name())){
                mList.get(i).setState("0");
                notifyDataSetChanged();
            }
        }
        if("1".equals(mList.get(i).getState())){
            viewHolder.imgTag.setImageResource(R.drawable.ic_selector_true);
            viewHolder.imgTag.setEnabled(false);

        }

        viewHolder.imgTag.setTag(mList.get(i));
        viewHolder.imgTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoreItemActivity mia = (MoreItemActivity) mContext;
                ImageView imgView = (ImageView) view;
                MainPageGridBean mb = (MainPageGridBean) view.getTag();
                if(mia.hpDirectory!=null){
                    if(mia.hpDirectory.size()>10){
                        Toast.makeText(mContext,"首页最多添加11个应用",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                mb.setState("1");
                imgView.setEnabled(false);
                MainEidtEvent meEvent = new MainEidtEvent();
                meEvent.setMb(mb);
                EventBus.getDefault().post(meEvent);
                imgView.setImageResource(R.drawable.ic_selector_true);


            }
        });
        if(isImgShow){
            viewHolder.imgTag.setVisibility(View.VISIBLE);
            viewHolder.imgTag.setFocusable(true);
            viewHolder.rt_grid.setBackgroundResource(R.color.lightgray2);
        }
        return view;
    }



    class MyViewHolder {
        private TextView tv_subtitle;
        private ImageView img;
        private ImageView imgTag;
        private RelativeLayout rt_grid;
    }
}
