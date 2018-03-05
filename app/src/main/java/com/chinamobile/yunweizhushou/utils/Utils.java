package com.chinamobile.yunweizhushou.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.bean.FaultLineChartDataBean;
import com.chinamobile.yunweizhushou.ui.PayMoreGetShit.PayMoreActivity;
import com.chinamobile.yunweizhushou.ui.cloudBillingAudit.CloudBillingAuditActivity;
import com.chinamobile.yunweizhushou.ui.complaint.ComplainManageActivity;
import com.chinamobile.yunweizhushou.ui.contentsOfTheFirstIssue.ProcessInspectionActivity;
import com.chinamobile.yunweizhushou.ui.extraContents.NewCalendarActivity;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.ui.flowProvince.FlowProvinceActivity;
import com.chinamobile.yunweizhushou.ui.moneyoutCheck.MoneyoutCheckActivity;
import com.chinamobile.yunweizhushou.ui.netChange.NetChangeActivity2;
import com.chinamobile.yunweizhushou.ui.nextCycle.NextActivity;
import com.chinamobile.yunweizhushou.ui.produceLine.ProduceLineManageActivity;
import com.chinamobile.yunweizhushou.ui.reconciliationSchedule.ReconciliationScheduleActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetActivity2;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class Utils {

	public static final String ERROR_MSG = "获取数据失败";

	private static SimpleDateFormat sdf;
	// private static SimpleDateFormat sdf2;

	public static String getCurrentTime() {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	public static String getYesterdayTime(){
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
		date=calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String getRequestTime() {
		sdf = new SimpleDateFormat("yyyy:MM");
		return sdf.format(new Date());
	}

	public static String getRequestTime2() {
		sdf = new SimpleDateFormat("yyyy:MM:dd");
		return sdf.format(new Date());
	}

	public static String getRequestTime3() {
		sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(new Date());
	}

	public static String getRequestTime4() {
		sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date());
	}
	public static String getRequestTime5() {
		sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	public static String getRequestTime6() {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getJsonArrayForSys(String data) {
		try {
			return new JSONObject(data).getJSONArray("sysList").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getJsonArrayX(String data, String key) {
		try {
			return new JSONObject(data).getJSONArray(key).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getJsonArray(String data) {
		try {
			return new JSONObject(data).getJSONArray("itemsList").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getJsonArray2(String data) {
		try {
			return new JSONObject(data).getJSONArray("itemList").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getkeyEventsListArray(String data) {
		try {
			return new JSONObject(data).getJSONArray("keyEventsList").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getImgArray(String data) {
		try {
			return new JSONObject(data).getJSONArray("imgsList").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getJsonArrayNoS(String data) {
		try {
			return new JSONObject(data).getJSONArray("itemList").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void ShowErrorMsg(Context c, String msg) {
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap.createBitmap(

				drawable.getIntrinsicWidth(),

				drawable.getIntrinsicHeight(),

				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

						: Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);

		// canvas.setBitmap(bitmap);

		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

		drawable.draw(canvas);

		return bitmap;

	}

	public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	public static LineData setLineChart(List<FaultLineChartDataBean> list) {
		// sdf2 = new SimpleDateFormat("HH:mm");
		ArrayList<String> xVal = new ArrayList<String>();
		List<ILineDataSet> listSet = new ArrayList<ILineDataSet>();

		for (int i = 0; i < list.size(); i++) {
			// xVal.add(sdf2.format(new
			// Date(Long.parseLong(list.get(i).getTime()))));
			xVal.add(list.get(i).getTime());
			if (i != 0) {
				List<Entry> dots = new ArrayList<Entry>();
				dots.add(new Entry(list.get(i - 1).getValue(), i - 1));
				dots.add(new Entry(list.get(i).getValue(), i));
				LineDataSet set = new LineDataSet(dots, "" + i);
				if (list.get(i - 1).getType() == 1) {
					set.setColor(Color.RED);
				} else if (list.get(i - 1).getType() == 2) {
					set.setColor(Color.parseColor("#F29A3A"));
				} else if (list.get(i - 1).getType() == 3) {
					set.setColor(Color.YELLOW);
				} else if (list.get(i - 1).getType() == 4) {
					set.setColor(Color.BLUE);
				} else if (list.get(i - 1).getType() == 5) {
					set.setColor(Color.GRAY);
				}

				if (list.get(i - 1).getLineType() == 0) {
					set.enableDashedLine(10, 8, 0);
				}
				set.setCircleColor(Color.BLACK);
				listSet.add(set);
			}
		}
		LineData ld = new LineData(xVal, listSet);
		return ld;
	}

	public static String utf82Iso(String s) {
		try {
			return new String(s.getBytes("UTF-8"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String getDateFromMonth2Min(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		return sdf.format(Long.valueOf(date));
	}

	/**
	 * 获得指定日期的前一天
	 */
	public static String getDayBefore(String specifiedDay) {// 可以用new
															// Date().toLocalString()传递参数
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 */
	public static String getDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	// 汉字返回拼音，字母原样返回，都转换为小写
	public static String getPinYin(String input) {
		ArrayList<ConvertChinese2PinYin.Token> tokens = ConvertChinese2PinYin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0) {
			for (ConvertChinese2PinYin.Token token : tokens) {
				if (ConvertChinese2PinYin.Token.PINYIN == token.type) {
					sb.append(token.target);
				} else {
					sb.append(token.source);
				}
			}
		}
		return sb.toString().toLowerCase();
	}

	public static String getDateFromString2Month(String date) {

		if (date.contains("-")) {
			return date;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
			return sdf.format(Long.valueOf(date));
		}
	}

	public static String getDateFromString2Day(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(Long.valueOf(date));
	}

	public static String addZeroForMonth(String month) {
		if (month.length() < 2) {
			return "0" + month;
		} else {
			return month;
		}
	}

   public static void jump2Shit(Context context, String type) {
		Intent intent = new Intent();

	   switch (type) {
		   case "1":
			   intent.setClass(context, NetChangeActivity2.class);
			   break;
		   case "2":

			   return;
		   // case "3":
		   // intent.setClass(context, ProcessInspectionActivity.class);
		   // intent.putExtra("DTLURL", "Routine?action=dtl&id=101");
		   // intent.putExtra("PROCNAME", "今日日切日账检查(云化)");
		   // break;
		   case "4":
			   intent.setClass(context, ProcessInspectionActivity.class);
			   intent.putExtra("DTLURL", "Routine?action=dtl&id=106");
			   intent.putExtra("PROCNAME", "月账流程进度检查(Boss)");
			   break;
		   case "5":
			   intent.setClass(context, ProcessInspectionActivity.class);
			   intent.putExtra("DTLURL", "Routine?action=dtl&id=107");
			   intent.putExtra("PROCNAME", "预存抵扣流程进度检查");
			   break;
		   case "6":
			   intent.setClass(context, ProcessInspectionActivity.class);
			   intent.putExtra("DTLURL", "Routine?action=dtl&id=105");
			   intent.putExtra("PROCNAME", "托收抵扣检查(Boss)");
			   break;
		   case "7":
			   intent.setClass(context, ReconciliationScheduleActivity.class);
			   break;
		   case "8":
			   intent.setClass(context, NextActivity.class);
			   break;
		   case "9":
			   intent.setClass(context, PayMoreActivity.class);
			   break;
		   case "10":
			   intent.setClass(context, FaultManageActivity.class);
			   break;
		   case "11":
			   intent.setClass(context, ProduceLineManageActivity.class);
			   break;
		   case "12":
			   intent.setClass(context, ComplainManageActivity.class);
			   break;
		   case "13":
			   intent.setClass(context, CloudBillingAuditActivity.class);
			   break;
		   case "14":
			   intent.setClass(context, FlowProvinceActivity.class);
			   break;
		   case "15":
			   intent.setClass(context, ProcessInspectionActivity.class);
			   intent.putExtra("DTLURL", "Routine?action=dtl&id=109");
			   intent.putExtra("PROCNAME", "支付网关对账检查(网关)");
			   break;
		   case "16":
			   intent.setClass(context, ProcessInspectionActivity.class);
			   intent.putExtra("DTLURL", "Routine?action=dtl&id=104");
			   intent.putExtra("PROCNAME", "对账调账检查(Boss)");
			   break;
		   case "17":
			   intent.setClass(context, ProcessInspectionActivity.class);
			   intent.putExtra("DTLURL", "Routine?action=dtl&id=101");
			   intent.putExtra("PROCNAME", "今日日切日账检查(云化)");
			   break;
		   case "18":
			   intent.setClass(context, NewCalendarActivity.class);
			   break;
		   case "19":
			   intent.setClass(context, YunweiAssetActivity2.class);
			   break;
		   case "20":
			   intent.setClass(context, MoneyoutCheckActivity.class);
			   break;

		   default:
			   return;
	   }
	   context.startActivity(intent);
	}

	public static String getBeforeMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date date = c.getTime();
		sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(date);
	}


}
