package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.bean.MoreItemListBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.SelectorModules;
import com.chinamobile.yunweizhushou.view.CustomGridView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/30.
 */

public class MoreItemListAdapter extends BaseAdapter {

    private List<MoreItemListBean> mList;
    private Context mContext;
    boolean isImgShow=false;
    private MainPageGridBean compareMb;
    private SelectorModules  sm;

    public MoreItemListAdapter(Context context ,List<MoreItemListBean> list,boolean isImgShow) {
        this.mList=list;
        this.mContext=context;
        this.isImgShow=isImgShow;
        sm=new SelectorModules();
    }

    public void setCompareMb(MainPageGridBean compareMb) {
        this.compareMb = compareMb;
    }

    public void setImgShow(boolean imgShow) {
        isImgShow = imgShow;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        if(view==null){
            viewHolder=new MyViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.view_more_item_list_item, null);
            viewHolder.title= (TextView) view.findViewById(R.id.tv_more_item_title);
            viewHolder.customGridView= (CustomGridView) view.findViewById(R.id.gv_more_item_grid);
            view.setTag(viewHolder);
        }else{
            viewHolder= (MyViewHolder) view.getTag();
        }
        viewHolder.title.setText(mList.get(i).getD_name());
        MoreItemGridInListAdapter mIgILAdapter=new MoreItemGridInListAdapter(mContext,mList.get(i).getDirList(),isImgShow,compareMb);
        if(compareMb!=null){
            mIgILAdapter.notifyDataSetChanged();
        }
        if(isImgShow){
            mIgILAdapter.notifyDataSetChanged();

        }
        viewHolder.customGridView.setTag(mList.get(i).getDirList());
        viewHolder.customGridView.setAdapter(mIgILAdapter);
        viewHolder.customGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //Toast.makeText(mContext,"第"+i+"个item",Toast.LENGTH_SHORT).show();
                CustomGridView cgv=(CustomGridView)adapterView;
                if(cgv!=null) {
                    List<MainPageGridBean> list = (List<MainPageGridBean>) cgv.getTag();

                    sm.goToModules(mContext, list.get(i).getD_enum(),list.get(i).getId());

                }
            }
        });

        return view;
    }




    class MyViewHolder {
       private CustomGridView customGridView;
       private TextView title;
    }
}
