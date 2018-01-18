package com.chinamobile.yunweizhushou.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageFragmentBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.logZone.LogZoneActivity;
import com.chinamobile.yunweizhushou.ui.AccountCenter.IntegrationServiceActivity;
import com.chinamobile.yunweizhushou.ui.TaskExecMonitor.TaskExecManagerActivity;
import com.chinamobile.yunweizhushou.ui.abnormalZone.AbnormalZoneActivity;
import com.chinamobile.yunweizhushou.ui.accountingArea.AccountingAareActivity;
import com.chinamobile.yunweizhushou.ui.addUser.AddUserActivity;
import com.chinamobile.yunweizhushou.ui.backlogZone.BacklogZoneActivity;
import com.chinamobile.yunweizhushou.ui.bandService.BandServiceActivity;
import com.chinamobile.yunweizhushou.ui.billFlow.GGPRSActivity;
import com.chinamobile.yunweizhushou.ui.braceBroadcast.BraceBroadcastActivity;
import com.chinamobile.yunweizhushou.ui.busifluct.BusifluctActivity2;
import com.chinamobile.yunweizhushou.ui.businessaccept.BusinessAcceptManageActivity;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.AbilityManageActivity;
import com.chinamobile.yunweizhushou.ui.capitalrecorded.RityajiManageActivity;
import com.chinamobile.yunweizhushou.ui.cloudManagementCenter.CMCNextActivity;
import com.chinamobile.yunweizhushou.ui.creditControl.CreditControl3Activity;
import com.chinamobile.yunweizhushou.ui.customerCenter.CustomerCenterManagerActivity;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.DemandPanoramicActivity;
import com.chinamobile.yunweizhushou.ui.dutyChart.DutyChartActivity;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.EmergencyExerciseActivity;
import com.chinamobile.yunweizhushou.ui.esbInterface.GovernAnalysisActivity;
import com.chinamobile.yunweizhushou.ui.flowProvince.FlowProvinceActivity;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.FunctionAnalysisActivity;
import com.chinamobile.yunweizhushou.ui.interfaceOver.InterfaceOverActivity;
import com.chinamobile.yunweizhushou.ui.levelStandar.LevelStandardActivity;
import com.chinamobile.yunweizhushou.ui.main.MoreItemActivity;
import com.chinamobile.yunweizhushou.ui.moneyoutCheck.MoneyoutCheckActivity;
import com.chinamobile.yunweizhushou.ui.newFaceRecognititon.FaceRecognititonManageActivity;
import com.chinamobile.yunweizhushou.ui.nextCycle.NextActivity;
import com.chinamobile.yunweizhushou.ui.officeDataZone.OfficeDataZoneActivity;
import com.chinamobile.yunweizhushou.ui.onLinePreview.OnlinePreviewActivity;
import com.chinamobile.yunweizhushou.ui.openCenter.OpenCenterActivity;
import com.chinamobile.yunweizhushou.ui.orderCenter.OrderCenterActivity;
import com.chinamobile.yunweizhushou.ui.personCenter.PersonCenterManagerActivity;
import com.chinamobile.yunweizhushou.ui.planManagement.PlanManagementActivity;
import com.chinamobile.yunweizhushou.ui.platformLogin.PlatformLoginActivity;
import com.chinamobile.yunweizhushou.ui.produceLine.ProduceLineManageActivity;
import com.chinamobile.yunweizhushou.ui.realNameSystem.RealNameSystemActivity;
import com.chinamobile.yunweizhushou.ui.ruleCenter.RuleManageActivity;
import com.chinamobile.yunweizhushou.ui.selfRepair.SelfRepairActivity;
import com.chinamobile.yunweizhushou.ui.serviceChain.ServiceChainManageActivity;
import com.chinamobile.yunweizhushou.ui.serviceLogQuery.ServiceLogQueryActivity;
import com.chinamobile.yunweizhushou.ui.systemTree.SystemTreeActivity;
import com.chinamobile.yunweizhushou.ui.teamcheck.AssessmentActivity;
import com.chinamobile.yunweizhushou.ui.ubstantivehall.EntityHallIndexActivity;
import com.chinamobile.yunweizhushou.ui.unifiedQuery.UnifiedQueryActivity;
import com.chinamobile.yunweizhushou.ui.useRank.UseRankingActivity;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.UserPerceptionIndexActivity;
import com.chinamobile.yunweizhushou.ui.userperception.UserPerceptionActivity2;
import com.chinamobile.yunweizhushou.ui.virtualDesktop.VirtualDesktopActivity;
import com.chinamobile.yunweizhushou.ui.webView.WebViewActivity;
import com.chinamobile.yunweizhushou.ui.yiyangShenpi.YiyangShenpiActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetActivity2;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/6/13.
 */

public class SelectorModules {



    private UserBean userBean;
    private String sessionId;
        public void goToModules(Context context,String dname,String id){
            Intent intent=new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            setRecordDirectory(context,id);
            switch(dname){
                case "0":
                    intent.setClass(context, MoreItemActivity.class);//更多
                    context.startActivity(intent);
                    break;
                case "1":
                    intent.setClass(context, BusinessAcceptManageActivity.class);//业务受理量
                    context.startActivity(intent);
                    break;
                case "2":
                    intent.setClass(context, RityajiManageActivity.class);//资金入账
                    context.startActivity(intent);
                    break;
                case "3":
                    intent.setClass(context, EntityHallIndexActivity.class);//实体厅指标
                    context.startActivity(intent);
                    break;
                case "4":
                    intent.setClass(context, UserPerceptionActivity2.class);//用户感知
                    context.startActivity(intent);
                    break;
                case "5":
                    intent.setClass(context, GovernAnalysisActivity.class);//esb接口
                    intent.putExtra("id", "1004");// ID
                    intent.putExtra("title", "成功率分析");
                    context.startActivity(intent);
                    break;
                case "7":
                    intent.setClass(context, LogZoneActivity.class);//日志专区
                    context.startActivity(intent);
                    break;
                case "8":
                    intent.setClass(context, BacklogZoneActivity.class);//积压专区
                    context.startActivity(intent);
                    break;
                case "9":
                    intent.setClass(context, AbnormalZoneActivity.class);//积压专区
                    context.startActivity(intent);
                    break;
                case "10":
                    ArrayList<MainPageFragmentBean> jkzqList = new ArrayList<>();
                    MainPageFragmentBean jkzqBean2 = new MainPageFragmentBean("csf", "csf消息服务指标管控",
                            R.mipmap.icon_csf2, "csf");
                    MainPageFragmentBean jkzqBean3 = new MainPageFragmentBean("MQ", "中心化项目MQ消费列队监控", R.mipmap.icon_mq2,
                            "MQ");
                    jkzqList.add(jkzqBean2);
                    jkzqList.add(jkzqBean3);
                    intent.setClass(context, MainPageSubMenuActivity.class);
                    intent.putExtra("list", jkzqList);
                    intent.putExtra("name", "接口专区");
                    context.startActivity(intent);
                    break;
                case "11":
                    ArrayList<MainPageFragmentBean> list = new ArrayList<>();
                    MainPageFragmentBean bean1 = new MainPageFragmentBean("esb容量管理", "esb水位管理", R.mipmap.icon_mainpage_sub1,
                            "GovernAnalysisActivity");
                    MainPageFragmentBean bean2 = new MainPageFragmentBean("数据库连接容量", "中间件到数据库连\n接数容量管理",
                            R.mipmap.icon_mainpage_sub2, "DBConnectionPoolActivity");
                    MainPageFragmentBean bean3 = new MainPageFragmentBean("线程容量管理", "中间件线程容量管理", R.mipmap.icon_mainpage_sub3,
                            "ThreadCapacityManagementActivity");
                    list.add(bean1);
                    list.add(bean2);
                    list.add(bean3);
                    intent.setClass(context, MainPageSubMenuActivity.class);
                    intent.putExtra("list", list);
                    intent.putExtra("name", "容量管理");
                    context.startActivity(intent);
                    break;
                case "12":
                    ArrayList<MainPageFragmentBean> list3 = new ArrayList<>();
                    MainPageFragmentBean bean13 = new MainPageFragmentBean("Cboss专区", "", R.mipmap.icon_mainpage_cboss,
                            "CbossManageActivity");
                    MainPageFragmentBean bean14 = new MainPageFragmentBean("capes专区", "", R.mipmap.icon_mainpage_capes,
                            "CapesManageActivity");
                    MainPageFragmentBean bean15 = new MainPageFragmentBean("考核指标", "", R.mipmap.icon_mainpage_sub3,
                            "AssessmentOfTheZoneActivity");
                    MainPageFragmentBean bean16 = new MainPageFragmentBean("业务探测指标", "", R.mipmap.icon_mainpage_guizezhongxin,
                            "AssessmentOfTheZoneActivity1");
                    MainPageFragmentBean bean17 = new MainPageFragmentBean("月度扣分项指标", "", R.mipmap.icon_mainpage_item_check,
                            "AssessmentOfTheZoneActivity2");
                    MainPageFragmentBean bean18 = new MainPageFragmentBean("话单指标", "", R.mipmap.icon_mainpage_item_complaint,
                            "AssessmentOfTheZoneActivity3");
                    MainPageFragmentBean bean21 = new MainPageFragmentBean("转售业务指标", "", R.mipmap.icon_mainpage_checkquality,
                            "AssessmentOfTheZoneActivity6");
                    MainPageFragmentBean bean22 = new MainPageFragmentBean("一级BBOSS指标", "",
                            R.mipmap.icon_mainpage_jiyazhuanqu, "AssessmentOfTheZoneActivity7");
                    list3.add(bean13);
                    list3.add(bean14);
                    list3.add(bean15);
                    list3.add(bean16);
                    list3.add(bean17);
                    list3.add(bean18);
                    list3.add(bean21);
                    list3.add(bean22);
                    intent.setClass(context, MainPageSubMenuActivity.class);
                    intent.putExtra("list", list3);
                    intent.putExtra("name", "考核专区");
                    context.startActivity(intent);
                    break;
                case "13":
                    ArrayList<MainPageFragmentBean> zglist = new ArrayList<>();
                    MainPageFragmentBean zgBean1 = new MainPageFragmentBean("对账单进度", "对账单进度", R.mipmap.icon_more_wave6,
                            "ReconciliationScheduleActivity");
                    MainPageFragmentBean zgBean2 = new MainPageFragmentBean("预缴返充", "预缴返充",
                            R.mipmap.yujiaofanchong, "PayMoreGetShit");
                    MainPageFragmentBean zgBean3 = new MainPageFragmentBean("云详单", "详单指标查询", R.mipmap.icon_mainpage_yunxiangdan,
                            "CloudBillingAuditActivity");
                    MainPageFragmentBean zgBean4 = new MainPageFragmentBean("全网流量统付", "全网流量统付", R.mipmap.quanwangliuliangtongfu,
                            "NetworkFlowPay");
                    zglist.add(zgBean1);
                    zglist.add(zgBean2);
                    zglist.add(zgBean3);
                    zglist.add(zgBean4);
                    intent.setClass(context, AccountingAareActivity.class);
                    intent.putExtra("list", zglist);
                    intent.putExtra("name", "账管专区");
                    context.startActivity(intent);
                    break;
                case "14":
                    intent.setClass(context, RealNameSystemActivity.class);//实名制
                    context.startActivity(intent);
                    break;
                case "15":
                    intent.setClass(context, CreditControl3Activity.class);//信控
                    context.startActivity(intent);
                    break;
                 case "16":
                    intent.setClass(context, MoneyoutCheckActivity.class);//酬金出账
                    context.startActivity(intent);
                    break;
                case "17":
                    intent.setClass(context, NextActivity.class);//下周期
                    context.startActivity(intent);
                    break;
                case "18":
                    intent.setClass(context, GGPRSActivity.class);//计费立体监控
                    context.startActivity(intent);
                    break;
                case "19":
                    intent.setClass(context, BusifluctActivity2.class);//扣费提醒
                    context.startActivity(intent);
                    break;
                case "20":
                    intent.setClass(context, FlowProvinceActivity.class);//流量统付
                    context.startActivity(intent);
                    break;
                case "21":
                    intent.setClass(context, FaceRecognititonManageActivity.class);//人脸识别
                    context.startActivity(intent);
                    break;
                case "52":
                    intent.setClass(context, BandServiceActivity.class);//宽带专区
                    intent.putExtra("action","findBroadbandWave");
                    context.startActivity(intent);
                    break;
                case "22":
                    intent.setClass(context, AbilityManageActivity.class);//能力平台
                    context.startActivity(intent);
                    break;
                case "23":
                    intent.setClass(context, OpenCenterActivity.class);//开通中心
                    context.startActivity(intent);
                    break;
                case "24":
                    intent.setClass(context, RuleManageActivity.class);//规则中心
                    context.startActivity(intent);
                    break;
                case "25":
                    intent.setClass(context, OrderCenterActivity.class);//订单中心
                    context.startActivity(intent);
                    break;
                case "26":
                    intent.setClass(context, IntegrationServiceActivity.class);//账户中心
                    context.startActivity(intent);
                    break;
                case "27":
                    intent.setClass(context, ProduceLineManageActivity.class);// 核心生产线
                    context.startActivity(intent);
                    break;
                case "28":
                    intent.setClass(context, YunweiAssetActivity2.class);// 运维资产
                    context.startActivity(intent);
                    break;
                case "29":
                    ArrayList<MainPageFragmentBean> list2 = new ArrayList<>();
                    MainPageFragmentBean pbean1 = new MainPageFragmentBean("入网变更", "入网变更", R.mipmap.icon_mainpage_sub1,
                            "NetChangeActivity2");
                    MainPageFragmentBean pbean2 = new MainPageFragmentBean("自动化巡检", "自动化巡检",
                            R.mipmap.icon_mainpage_sub2, "AutoCheckActivity");
                    MainPageFragmentBean pbean3 = new MainPageFragmentBean("入网验收", "入网验收", R.mipmap.icon_mainpage_sub3,
                            "NetworkAcceptanceActivity");
                    list2.add(pbean1);
                    list2.add(pbean2);
                    list2.add(pbean3);
                    intent.setClass(context, MainPageSubMenuActivity.class);
                    intent.putExtra("list", list2);
                    intent.putExtra("name", "生产管控");
                    context.startActivity(intent);
                    break;
                case "31":
                    intent.setClass(context, DemandPanoramicActivity.class);// 需求运营
                    context.startActivity(intent);
                    break;
                case "32":
                    intent.setClass(context, FunctionAnalysisActivity.class);// 性能分析
                    context.startActivity(intent);
                    break;
                case "33":
                    intent.setClass(context, YiyangShenpiActivity.class);// 权限审批
                    context.startActivity(intent);
                    break;
                case "34":
                    intent.setClass(context, SelfRepairActivity.class);// 权限审批
                    context.startActivity(intent);
                    break;
                case "35":
                    intent.setClass(context, ServiceChainManageActivity.class);// 业务启示录
                    context.startActivity(intent);
                    break;
                case "36":
                    intent.setClass(context, BraceBroadcastActivity.class);// 支撑播报
                    context.startActivity(intent);
                    break;
                case "37":
                    intent.setClass(context, EmergencyExerciseActivity.class);//应急演练
                    context.startActivity(intent);
                    break;
                case "48":
                    intent.setClass(context, VirtualDesktopActivity.class);//虚拟桌面
                    context.startActivity(intent);
                    break;
                case "49":
                    intent.setClass(context, PlanManagementActivity.class);//自动化平台
                    context.startActivity(intent);
                    break;
                case "50":
                    intent.setClass(context, UserPerceptionIndexActivity.class);//客户感知指标
                    context.startActivity(intent);
                    break;
                case "38":
                    intent.setClass(context, WebViewActivity.class);// 故障分级标准
                    intent.putExtra("previewName", "故障分级标准");
                    intent.putExtra("URL", "http://m360.zj.chinamobile.com/ywapp/document/ppt/20161017141056.6.pptx");
                    context.startActivity(intent);
                    break;
                case "39":
                    intent.setClass(context, LevelStandardActivity.class);//核心业务分级标准
                    context.startActivity(intent);
                    break;
                case "40":
                    intent.setClass(context, WebViewActivity.class);// 业务变更管理规范
                    intent.putExtra("previewName", "业务变更管理规范");
                    intent.putExtra("URL", "http://m360.zj.chinamobile.com/ywapp/document/ppt/201704191104381.pptx");
                    context.startActivity(intent);
                    break;
                case "41":
                    intent.setClass(context, SystemTreeActivity.class);//系统架构图
                    context.startActivity(intent);
                    break;
                case "42":
                    intent.setClass(context, WebViewActivity.class);// 项目架构
                    intent.putExtra("previewName", "项目架构");
                    intent.putExtra("URL", "http://m360.zj.chinamobile.com/ywapp/document/ppt/201610171410437.pptx");
                    context.startActivity(intent);
                    break;
                case "43":
                    intent.setClass(context, AssessmentActivity.class);// 集团考核指标
                    context.startActivity(intent);
                    break;
                case "44":
                    intent.setClass(context, OnlinePreviewActivity.class);// 在线预览
                    context.startActivity(intent);
                    break;
                case "45":
                    intent.setClass(context, DutyChartActivity.class);// 值班表
                    context.startActivity(intent);
                    break;
                case "46":
                    intent.setClass(context, UseRankingActivity.class);// 智维排名
                    intent.putExtra("title", "智维热点排名");
                    context.startActivity(intent);
                    break;
                case "47":
                    intent.setClass(context, UnifiedQueryActivity.class);// 统一查询
                    context.startActivity(intent);
                    break;
                case "51":
                    intent.setClass(context, AddUserActivity.class);// 用户注册
                    context.startActivity(intent);
                    break;
                case "54":
                    ArrayList<MainPageFragmentBean> hotzoneList = new ArrayList<>();
                    MainPageFragmentBean hotzoneBean1 = new MainPageFragmentBean("开通网元失败专区", "开通网元失败专区", R.mipmap.ic_ktwy,
                            "HotZoneKTWYActivity");
                    MainPageFragmentBean hotzoneBean2 = new MainPageFragmentBean("统一支付失败", "统一支付失败专区",
                            R.mipmap.ic_tysbzq, "HotZoneActivity");
                    MainPageFragmentBean hotzoneBean3 = new MainPageFragmentBean("跨区补卡", "跨区补卡专区", R.mipmap.ic_kyzq,
                            "KQBKActivity");
                    MainPageFragmentBean hotzoneBean4 = new MainPageFragmentBean("物联网指标", "物联网指标专区", R.mipmap.ic_wlwzq,
                            "HotZoneWLWZBActivity");
                /*    MainPageFragmentBean hotzoneBean5 = new MainPageFragmentBean("宽带专区", "宽带专区", R.mipmap.ic_kdzq,
                            "GraphListActivity");*/
                    MainPageFragmentBean hotzoneBean6 = new MainPageFragmentBean("告警专区", "告警专区", R.mipmap.ic_gaojing,
                            "GJTargetActivity");
                    MainPageFragmentBean hotzoneBean7 = new MainPageFragmentBean("漫游专区", "漫游专区", R.mipmap.ic_roam,
                                "RoamActivity");
                    hotzoneList.add(hotzoneBean1);
                    hotzoneList.add(hotzoneBean2);
                    hotzoneList.add(hotzoneBean3);
                    hotzoneList.add(hotzoneBean4);
                /*    hotzoneList.add(hotzoneBean5);*/
                    hotzoneList.add(hotzoneBean6);
                    hotzoneList.add(hotzoneBean7);
                    intent.setClass(context, MainPageSubMenuActivity.class);
                    intent.putExtra("list", hotzoneList);
                    intent.putExtra("name", "热点专区");
                    context.startActivity(intent);
                    break;
                case "55":
                    intent.setClass(context, ServiceLogQueryActivity.class);// 服务查询
                    context.startActivity(intent);
                    break;
                case "56":
                    intent.setClass(context, OfficeDataZoneActivity.class);// 局数据专区
                    context.startActivity(intent);
                    break;
                case "57":
                    intent.setClass(context, CustomerCenterManagerActivity.class);// 客户中心
                    context.startActivity(intent);
                    break;
                case "53":
                    intent.setClass(context, PlatformLoginActivity.class);// 平台登录
                    context.startActivity(intent);
                    break;
                case "58":
                    intent.setClass(context, InterfaceOverActivity.class);// 超时接口
                    context.startActivity(intent);
                    break;
                case "59":
                    intent.setClass(context, WebViewActivity.class);// 项目架构
                    intent.putExtra("previewName", "对账规范");
                    intent.putExtra("URL", "http://m360.zj.chinamobile.com/ywapp/document/ppt/201710061039264.1.pptx");
                    context.startActivity(intent);
                    break;
                case "60":
                    intent.setClass(context, TaskExecManagerActivity.class);// 调度平台任务查询
                    context.startActivity(intent);
                    break;
                case "61":
                    intent.setClass(context, PersonCenterManagerActivity.class);// 个人中心
                    context.startActivity(intent);
                    break;
                case "62":
                    intent.setClass(context, WebViewActivity.class);// 运维管理中心
                    intent.putExtra("title", "运维管理中心");
                    intent.putExtra("URL", "http://omwf.zj.chinamobile.com/portal/app/");
                    context.startActivity(intent);
                    break;
                case "63":
                    intent.setClass(context, CMCNextActivity.class);// 操作管理中心
                    intent.putExtra("title", "操作管理中心");
                   /* intent.putExtra("URL", "http://10.78.129.218:8080/app_web/index.html");*/
                    context.startActivity(intent);
                    break;
                case "64":
                    intent.setClass(context, WebViewActivity.class);// 监控管理中心
                    intent.putExtra("title", "监控管理中心");
                    intent.putExtra("URL", "http://10.78.129.218:8080/app_web/monitoring.html");
                    context.startActivity(intent);
                    break;
                case "65":
                    intent.setClass(context, WebViewActivity.class);// 数据管控
                    intent.putExtra("title", "数据管控中心");
                    intent.putExtra("URL", "http://10.78.129.218:8080/app_web/data_control.html");
                    context.startActivity(intent);
                    break;
                case "66":
                    intent.setClass(context, WebViewActivity.class);// 业务管理
                    intent.putExtra("title", "业务管理中心");
                    intent.putExtra("URL", "http://10.78.129.218:8080/app_web/business_implications.html");
                    context.startActivity(intent);
                    break;
                case "67":
                    intent.setClass(context, WebViewActivity.class);// 指标管理中心
                    intent.putExtra("title", "指标管理中心");
                    intent.putExtra("URL", "http://10.78.129.218:8080/app_web/target_management.html ");
                    context.startActivity(intent);
                    break;
                case "69":
                    intent.setClass(context, CMCNextActivity.class);// 云管理中心
                    intent.putExtra("title","运营分析中心");
                    context.startActivity(intent);
                    break;
                default:break;

            }



        }
    private void setRecordDirectory(Context context,String id) {
        MainApplication application= (MainApplication) context.getApplicationContext();
        userBean=application.getUser();
        sessionId=userBean.getSessionId();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("action", "recordDirectory");
        parameters.put("deviceId", "3");
        parameters.put("directory_id", id);
        parameters.put("useType", "2");
        parameters.put("sessionId", userBean.getSessionId());
        Novate novate = new Novate.Builder(context)
                .connectTimeout(8)
                .baseUrl(Contants.BASE_URL)
                .addLog(true)
                .build();
        novate.post("DirectoryManager", parameters, new BaseSubscriber<ResponseBody>(context) {
            @Override
            public void onError(Throwable e) {
                Log.e("cuowu", "错误");
            }

            @Override
            public void onNext(ResponseBody responseBody) {

            }
        });
    }
}
