package com.chinamobile.yunweizhushou.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/5/26.
 */

public class MainPageGridBean {
    private String d_name;
    private Bitmap bitmap;
    private String d_enum;
    private String id;
    private String icon;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getD_enum() {
        return d_enum;
    }

    public void setD_enum(String d_enum) {
        this.d_enum = d_enum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MainPageGridBean other = (MainPageGridBean) obj;
        if(this.getD_enum()!=other.getD_enum())
            return false;
        return true;
    }
}
