package com.chinamobile.yunweizhushou.ui.main;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NewCalendarDayDetailBean;
import com.chinamobile.yunweizhushou.bean.NewCalendarItemBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.dialogFragments.CalendarDialogFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.calendarviewtest.ColorfulBackgroundDecorator;
import com.chinamobile.yunweizhushou.view.calendarviewtest.MyCircleDecorator;
import com.chinamobile.yunweizhushou.view.calendarviewtest.StarDecorator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MainCalendarFragment extends BaseFragment implements OnDateSelectedListener, OnMonthChangedListener, View.OnClickListener {
    private MaterialCalendarView calendar;
    private MyGridView gridview;
    private MyListView listview;
    private List<NewCalendarItemBean> itemList, subList;
    private int position1 = 0;
    private NewCalendarListAdapter adapter1, adapter2;
    private String tag1 = "";
    // currentDate到月 currentDate2到日 均用于请求
    private String currentDate, currentDate2;
    private String currentYear, currentMonth, currentDay;
    private ResponseBean calendarBean;
    private ArrayList<NewCalendarDayDetailBean> dayDetailList;

    private static final int TYPE1 = 1;
    private static final int TYPE2 = 2;

    private int[] month;

    private TextView gzItem1, gzItem2, gzItem3, fbItem1, fbItem2, ymycItem1, ymycItem2, cjczItem1, cjczItem2, rcItem1,
            rcItem2, rcItem3;
    private String gzValue, fbValue, ymycValue, cjczValue, rcValue;
    private LinearLayout gz, fb, ymyc, rc, cjcz;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_ct,container,false);
        currentDate = Utils.getRequestTime4();
        currentYear = currentDate.substring(0, 4);
        // 初始化当前月份减1以保持一致
        currentMonth = (Integer.valueOf(currentDate.substring(5, 7)) - 1) + "";
        month = new int[] { 1, 2, 3, 4, 5, 26, 27, 28, 29, 30, 31 };
        initView(view);
        initCalendar();
        initEvent();
        initDataRequest();
        // initItemListRequest("");
        initCalendarRequest("");

       return view;
    }

    public void initDataRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "totalByMonth");
        map.put("date", currentDate);
        startTask(HttpRequestEnum.enum_calendar_day_bottom, ConstantValueUtil.URL + "BusiCalendar?", map);


    }

    private void initEvent() {


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                adapter1.notifyDataSetChanged();
                tag1 = itemList.get(position).getBusiName();
                // initSubListRequest(tag1);
                initCalendarRequest(tag1.equals("全部") ? "" : tag1);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.notifyDataSetChanged();
            }
        });

        gz.setOnClickListener(this);
        fb.setOnClickListener(this);
        ymyc.setOnClickListener(this);
        rc.setOnClickListener(this);
        cjcz.setOnClickListener(this);

    }

    private void initCalendar() {
       // calendar.setCalendarDisplayMode(CalendarMode.MONTHS);
        calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        calendar.setOnDateChangedListener(this);
        calendar.setOnMonthChangedListener(this);
        calendar.setSelectionColor(getResources().getColor(R.color.color_white));
       // calendar.setTileHeightDp(10);
       // calendar.setWeekDayFormatter();
     //   calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
    }

    private void initView(View view) {
        calendar = (MaterialCalendarView) view.findViewById(R.id.cv);
        gridview = (MyGridView) view.findViewById(R.id.new_calendar_gridview);
        listview = (MyListView) view.findViewById(R.id.new_calendar_listview);
        gridview.setVisibility(View.GONE);
        listview.setVisibility(View.GONE);
        gzItem1 = (TextView) view.findViewById(R.id.new_calendar_gz_item1);
        gzItem2 = (TextView) view.findViewById(R.id.new_calendar_gz_item2);
        gzItem3 = (TextView) view.findViewById(R.id.new_calendar_gz_item3);
        fbItem1 = (TextView) view.findViewById(R.id.new_calendar_fb_item1);
        fbItem2 = (TextView) view.findViewById(R.id.new_calendar_fb_item2);
        ymycItem1 = (TextView) view.findViewById(R.id.new_calendar_ymyc_item1);
        ymycItem2 = (TextView) view.findViewById(R.id.new_calendar_ymyc_item2);
        cjczItem1 = (TextView) view.findViewById(R.id.new_calendar_cjcz_item1);
        cjczItem2 = (TextView) view.findViewById(R.id.new_calendar_cjcz_item2);
        rcItem1 = (TextView) view.findViewById(R.id.new_calendar_rc_item1);
        rcItem2 = (TextView) view.findViewById(R.id.new_calendar_rc_item2);
        rcItem3 = (TextView) view.findViewById(R.id.new_calendar_rc_item3);

        gz = (LinearLayout) view.findViewById(R.id.new_calendar_layout_gz);
        fb = (LinearLayout) view.findViewById(R.id.new_calendar_layout_fb);
        ymyc = (LinearLayout) view.findViewById(R.id.new_calendar_layout_ymyc);
        cjcz = (LinearLayout) view.findViewById(R.id.new_calendar_layout_cjcz);
        rc = (LinearLayout) view.findViewById(R.id.new_calendar_layout_rc);



    }

    private void initCalendarDayRequest(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "queryByDay");
        map.put("date", currentDate2);
        map.put("busiName", name);
        startTask(HttpRequestEnum.enum_calendar_day_detail, ConstantValueUtil.URL + "BusiCalendar?", map, true);
    }

    private void initCalendarRequest(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "queryByMonth");
        map.put("date", currentDate);
        map.put("busiName", name);
        startTask(HttpRequestEnum.enum_calendar_day, ConstantValueUtil.URL + "BusiCalendar?", map, false);
    }

    private void initSubListRequest(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "queryBusiName");
        map.put("level", "2");
        map.put("busiName", name);
        startTask(HttpRequestEnum.enum_calendar_sublist, ConstantValueUtil.URL + "BusiCalendar?", map);
    }

    private void initItemListRequest(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "queryBusiName");
        map.put("level", "1");
        map.put("busiName", name);
        startTask(HttpRequestEnum.enum_calendar_itemlist, ConstantValueUtil.URL + "BusiCalendar?", map);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        if (responseBean.isSuccess()) {
            Type t = new TypeToken<List<NewCalendarItemBean>>() {
            }.getType();
            switch (e) {
                case enum_calendar_itemlist:
                    itemList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
                    itemList.add(0, new NewCalendarItemBean("全部"));
                    adapter1 = new NewCalendarListAdapter(getActivity(), itemList, R.layout.item_simple_textview, TYPE1);
                    gridview.setAdapter(adapter1);
                    break;
                case enum_calendar_sublist:
                    subList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
                    adapter2 = new NewCalendarListAdapter(getActivity(), subList, R.layout.item_simple_textview, TYPE2);
                    listview.setAdapter(adapter2);
                    break;
                case enum_calendar_day:
                    calendarBean = responseBean;
                    drawCalendar(calendarBean);
                    break;
                case enum_calendar_day_detail:
                    Type t2 = new TypeToken<List<NewCalendarDayDetailBean>>() {
                    }.getType();
                    dayDetailList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t2);
                    CalendarDialogFragment fragment = new CalendarDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("list",dayDetailList);
                    bundle.putString("date",currentDate2);
                    fragment.setArguments(bundle);
                    fragment.show(getFragmentManager(), "fragment");
                    break;

                case enum_calendar_day_bottom:

                    try {
                        JSONObject obj = new JSONObject(responseBean.getDATA());
                        JSONObject gz = obj.getJSONObject("gz");
                        String level3 = gz.getString("level3");
                        gzValue = gz.getString("menuVal");
                        String level5 = gz.getString("level5");
                        String level4 = gz.getString("level4");
                        gzItem1.setText(level3 + "起");
                        gzItem2.setText(level4 + "起");
                        gzItem3.setText(level5 + "起");

                        JSONObject fb = obj.getJSONObject("fb");
                        String total = fb.getString("total");
                        String succ = fb.getString("succ");
                        fbValue = fb.getString("menuVal");
                        fbItem1.setText(total);
                        fbItem2.setText(succ);

                        JSONObject ymyc = obj.getJSONObject("ymyc");
                        String succ2 = ymyc.getString("succ");
                        String focus = ymyc.getString("focus");
                        ymycValue = ymyc.getString("menuVal");
                        ymycItem1.setText(succ2 + "%");
                        ymycItem2.setText(focus);

                        JSONObject cjcz = obj.getJSONObject("cjcz");
                        String succ3 = cjcz.getString("succ");
                        String focus3 = cjcz.getString("focus");
                        cjczValue = cjcz.getString("menuVal");
                        cjczItem1.setText(succ3 + "%");
                        cjczItem2.setText(focus3);

                        JSONObject rc = obj.getJSONObject("rc");
                        String high = rc.getString("high");
                            String low = rc.getString("low");
                        String middle = rc.getString("middle");
                        rcValue = rc.getString("menuVal");
                        rcItem1.setText(high + "项");
                        rcItem2.setText(middle + "项");
                        rcItem3.setText(low + "项");

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    break;

                default:
                    break;
            }
        } else {
            Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
        }
    }

    private void drawCalendar(ResponseBean responseBean) {

        calendar.removeDecorators();
        calendar.clearSelection();
        String data = responseBean.getDATA();
        try {
            JSONObject obj = new JSONObject(data);
            JSONArray nets = obj.getJSONArray("listOfNet");
            JSONArray locals = obj.getJSONArray("listOfLocal");

            //drawGrayBackground(currentMonth);
            drawTodayBackGround();

            Collection<com.prolificinteractive.materialcalendarview.CalendarDay> c = new ArrayList<>();
            for (int i = 0; i < nets.length(); i++) {
                c.add(CalendarDay.from(Integer.valueOf(currentYear), Integer.valueOf(currentMonth),
                        Integer.valueOf(nets.getString(i))));
            }
            calendar.addDecorators(new MyCircleDecorator(getActivity(), c));

            Collection<com.prolificinteractive.materialcalendarview.CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < locals.length(); i++) {
                dates.add(CalendarDay.from(Integer.valueOf(currentYear), Integer.valueOf(currentMonth),
                        Integer.valueOf(locals.getString(i))));
            }
            calendar.addDecorators(new StarDecorator(getActivity(), dates));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    private void drawTodayBackGround() {
        ArrayList<CalendarDay> green = new ArrayList<>();
        green.add(CalendarDay.today());
        calendar.addDecorator(new ColorfulBackgroundDecorator(green, "#B4FEB6"));
    }

   /* private void drawGrayBackground(String currentMonth) {
        Collection<CalendarDay> grays = new ArrayList<>();
        for (int i = 0; i < month.length; i++) {
            grays.add(CalendarDay.from(Integer.valueOf(currentYear), Integer.valueOf(currentMonth), month[i]));
        }
        calendar.addDecorators(new ColorfulBackgroundDecorator(grays, "#E0E0E0"));
    }*/

    class NewCalendarListAdapter extends AbsBaseAdapter<NewCalendarItemBean> {

        private List<NewCalendarItemBean> list;
        private int type;
        private Context context;

        public NewCalendarListAdapter(Context context, List<NewCalendarItemBean> list, int resourceId, int type) {
            super(context, list, resourceId);
            this.list = list;
            this.type = type;
            this.context = context;
        }

        @Override
        protected View newView(View convertView, NewCalendarItemBean t, int position) {

            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.busiName = (TextView) convertView.findViewById(R.id.simple_textview);
                convertView.setTag(holder);
            }
            holder.busiName.setText(list.get(position).getBusiName());
            if (type == TYPE1) {
                if (position1 != position) {
                    holder.busiName.setBackgroundResource(R.drawable.corner_rectangle_blue_stroke_bg);
                    holder.busiName.setTextColor(context.getResources().getColor(R.color.color_lightblue));
                } else {
                    holder.busiName.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
                    holder.busiName.setTextColor(context.getResources().getColor(R.color.color_white));
                }
            } else if (type == TYPE2) {

                holder.busiName.setBackgroundResource(R.drawable.button_click_selector2);
                holder.busiName.setTextColor(context.getResources().getColor(R.color.color_white));
            }
            return convertView;
        }

    }

    private static class ViewHolder {
        TextView busiName;
    }

    @Override
    public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
        currentYear = date.getYear() + "";
        currentMonth = date.getMonth() + "";
        currentDay = date.getDay() + "";
        currentDate2 = currentYear + "-" + (date.getMonth() + 1) + "-" + currentDay;
        initCalendarDayRequest(tag1);
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        currentYear = date.getYear() + "";
        currentMonth = date.getMonth() + "";
        currentDate = currentYear + "-" + (date.getMonth() + 1);
        initCalendarRequest(tag1);
        initDataRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_calendar_layout_gz:
                Utils.jump2Shit(getActivity(), gzValue);
                break;
            case R.id.new_calendar_layout_fb:
                Utils.jump2Shit(getActivity(), fbValue);
                break;
            case R.id.new_calendar_layout_ymyc:
                Utils.jump2Shit(getActivity(), ymycValue);
                break;
            case R.id.new_calendar_layout_cjcz:
                Utils.jump2Shit(getActivity(), cjczValue);
                break;
            case R.id.new_calendar_layout_rc:
                Utils.jump2Shit(getActivity(), rcValue);
                break;

            default:
                break;
        }
    }
}
