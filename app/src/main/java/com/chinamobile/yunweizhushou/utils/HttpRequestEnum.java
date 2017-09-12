package com.chinamobile.yunweizhushou.utils;

public enum HttpRequestEnum {

	// temp
	enum_temp,

	// 登录
	enum_login,
	// inode
	enum_login_inode,
	// 修改密码
	enum_changepassword,
	// 检查更新
	enum_check_update,
	// 登录页面忘记密码
	enum_reset_password,

	/**
	 * 主页
	 */
	// 广告
	enum_mainpage_ad,
	// 广播
	enum_mainpage_broadcast,
	// 进度条
	enum_mainpage_progress,

	/**
	 * 故障管理
	 */
	// 总览
	enum_faultmanage_total, enum_faultmanage_total_score,
	// 今日
	enum_faultmanage_today,
	// 今日head
	enum_faultmanage_today_head,
	// 今日item
	enum_faultmanage_today_detail,
	// 评论列表
	enum_faultmanage_today_comment_list,
	// 发起评论
	enum_faultmanage_today_comment_commit,
	// 未完成
	enum_faultmanage_unsolve,enum_faultmanage_unsolve_next,
	// 典型
	enum_faultmanage_typical,
	// 跟进
	enum_faultmanage_follow_total,
	// total列表
	enum_faultmanage_follow_totallist,
	// total详情
	enum_faultmanage_follow_totallist_detail,
	// 未完成
	enum_faultmanage_follow_unfinish,
	// 进度
	enum_faultmanage_follow_progress,
	// 时间轴 月
	enum_faultmanage_timeaxis_month,
	// 时间轴 日列表
	enum_faultmanage_timeaxis_day,
	// 故障播报搜索
	enum_faultmanage_search,
	// 今日head表
	enum_faultmanage_today_headchart,
	// 故障 最近
	enum_faultnamage_recent,
	// 故障 风险预警列表
	enum_fault_riskwarning,
	// 故障 风险预警列表下一层
	enum_fault_riskwarning_next,
	// 故障 风险预警列表
	enum_fault_faultkey,
	// 故障 风险预警列表下一层
	enum_fault_faultkey_next,
	//故障运营
	enum_fault_operation,
	//热点服务下一级
	enum_service_next,

	/**
	 * 故障保障
	 */
	// total
	enum_guarentee_total,
	// 下周期
	enum_guarentee_nextperiod,

	/**
	 * 支撑播报
	 */
	// total
	enum_bracebroadcast,
	// read
	enum_bracebroadcast_addread,
	// change
	enum_bracebroadcast_change,
	// comment
	enum_bracebroadcast_comment,
	// send comment
	enum_bracebroadcast_sendcomment,
	// calendar
	enum_bracebroadcast_month, enum_bracebroadcast_day,

	// 搜索
	enum_bracebroadcast_searchadvice,
	// 关键字搜索
	enum_bracebroadcast_keysearch,

	/**
	 * 充值
	 */
	// 浙江省总图
	enum_charge_zhejiang_map,
	// 各城市地图
	enum_charge_city_map,
	// 省加市波动图
	enum_charge_graph,
	// 充值总列表
	enum_charge_list,
	// 充值总子表
	enum_charge_sublist,

	/**
	 * 投诉
	 */
	// total
	enum_complain,
	// today
	enum_complain_today,
	// popup
	enum_complain_popup,
	// hot
	enum_complain_hot,
	// 表格
	enum_complain_chart,
	// 报表
	enum_complain_report_form,
	// 报表详情
	enum_complain_report_form_detail,
	// 详情图表
	enum_complain_report_form_graph,
	// 未结投诉单
	enum_complain_unfinish,
	// 未结投诉单详情
	enum_complain_unfinish_detail,
	// 未结投诉单详情搜索
	enum_complain_unfinish_detail_search,
	// 月度
	enum_complain_month,
	// 月度2
	enum_complain_month_detail, enum_complain_month_detail2,

	/**
	 * 集团考核
	 */
	// 今日总览
	enum_team_today,
	// 排行详情
	enum_team_rank_detail,
	//排名详情下一页
	enum_team_rank_detail2,
	// 右上角考核
	enum_team_detail,

	/**
	 * 业务波动更多
	 */
	// 更多总览
	enum_more_wave,
	// 列表
	enum_more_wave_list,
	// 子列表
	enum_more_wave_sublist,
	// 图表
	enum_more_wave_graph,

	/**
	 * 专项治理
	 */
	// 服务治理列表
	enum_govern_service_govern,
	// 容量分析_title
	enum_govern_analysis_title,
	// 容量分析_list
	enum_govern_analysis_list,
	// 容量分析
	enum_govern_analysis_list_next,
	// 容量分析下一级
	enum_govern_analysis,
	// 数据库连接池容量-业务总览列表
	enum_pool_analysis_business,
	// 数据库连接池容量-统计数
	enum_pool_analysis_statistics,
	// 需优化，需关注列表
	enum_pool_analysis_ball,
	// 容量分析 图标
	enum_govern_analysis_graph,
	// 容量分析 图标详情
	enum_govern_analysis_graph_detail,
	// 成功率 图表 list
	enum_govern_analysis_successrate_graph_list,

	enum_govern_analysis_successrate_graph_lists,
	// 成功率 图表 list
	enum_govern_analysis_successrate_graph_list2,

	/**
	 * 线程容量管理
	 */
	enum_thread_capacity_management, enum_thread_capacity_management_ball,

	/**
	 * 专项优化
	 */
	// 优化项总览
	enum_better,
	// 优化group表
	enum_better_group,
	// 优化child
	enum_better_child,
	// 优化 fragement总览
	enum_better_total,
	// 优化 fragement总览 详情
	enum_better_total_detail,
	// 优化 指标
	enum_better_target,

	/**
	 * 值班表
	 */
	enum_duty_chart,

	/**
	 * 运维宝 分级
	 */
	enum_takara_level,

	/**
	 * 运维工具
	 */
	enum_takara_util,
	// 日常运维
	enum_daily_operations,

	/**
	 * 近期热点
	 */
	enum_recent_hot_graph,
	// 列表 母
	enum_recent_hot_list_group,
	// 列表 子
	enum_recent_hot_list_child,

	/**
	 * 性能分析
	 */
	// 恶化接口分析
	enum_function_analysis_total,
	// 实时性能检测 --饼图
	enum_function_analysis_performance,
	// 实时性能检测 --列表
	enum_function_analysis_performance_list,
	// 列表
	enum_function_analysis_list,
	// 详情
	enum_function_analysis_detail,

	/**
	 * 重大工作
	 */
	// 列表
	enum_important_work,
	// 详情
	enum_important_work_detail,
	// 添加
	enum_important_work_add,
	// 删除
	enum_important_work_delete,

	/**
	 * 需求运营
	 */
	enum_demand_panoramic,
	// 需求排序类别
	enum_demand_type,
	// 需求搜索
	enum_demand_search,
	// 需求默认排序详细
	enum_demand_title_detail,

	/**
	 * 省内流量统付
	 */
	enum_flow_province, enum_flow_province_trend,

	/**
	 * 容灾演练
	 */
	enum_emergency_exercise,
	/**
	 * 容灾演练细节
	 */
	enum_drill_detail,

	/**
	 * 使用排名
	 */
	enum_use_ranking,

	/**
	 * 充值功能
	 */
	enum_recharge_function, enum_recharge_wave_tatal, enum_recharge_function_wave_list, enum_recharge_function_wave_detail,

	/**
	 * 系统全景
	 */
	enum_system_tree, enum_system_tree_detail, enum_system_tree_detail_detail, enum_system_tree_detail_newdetail1, enum_system_tree_detail_newdetail2, enum_system_tree_detail_5,

	/**
	 * 入网变更
	 */
	enum_net_change, enum_net_change_mainlist, enum_net_change_detail, enum_net_change_calendar_month, enum_net_change_calendar_day,

	enum_net_change_0, // 入网变更-紧急变更-详情
	enum_net_change_11, enum_net_change_12, enum_net_change_2, enum_net_change_3, enum_net_change_4, enum_net_change_5, enum_net_change_6,

	enum_net_change_error, enum_net_change_lack,

	/**
	 * 首页-营业厅开户
	 */
	enum_business_detection, enum_business_detection_list,

	/**
	 * 核心业务受理量
	 */
	enum_business_amount, enum_business_amount_wave_list, enum_business_amount_wave_detail,

	/**
	 * 云账单查询
	 */
	enum_cloud_billing_audit,

	/**
	 * 自动化巡检
	 */
	enum_auto_check_unsolve, enum_auto_check_today, enum_auto_check_month,

	/**
	 * 积压专区
	 */
	enum_backlog_zong_menu_list, enum_backlog_zong_list, enum_backlog_zong_search_list, enum_backlog_zong_graph, enum_backlog_zong_graph_detail,

	/**
	 * 积压专区
	 */
	enum_abnormal_zong_menu_list, enum_abnormal_zong_list,
	/**
	 * cboss
	 */
	enum_cboss_title, enum_cboss_list, enum_cboss_detail, enum_cboss_dialog, enum_cboss_note1, enum_cboss_note2,

	/**
	 * capes
	 */

	enum_capes_list1, enum_capes_left_list3, enum_capes_left_list2, enum_capes_2_select_list, enum_capes_2_graph, enum_capes_right_list3,
	/**
	 * 智维需求
	 */
	enum_demand_list,

	/**
	 * 入网验收
	 */
	enum_network_list,

	/**
	 * 能力平台
	 */
	enum_ability_list1, enum_ability_list2, enum_ability_list3,

	/**
	 * 每日好文
	 */
	enum_every_day_article, enum_every_day_zan, enum_every_day_yuedu,

	/**
	 * 对账进度
	 */
	enum_reconciliation_schedule_list,
	/**
	 * 规则中心
	 */
	enum_rule_center,

	/**
	 * 实体厅满意度
	 */
	enum_hall_satisfied, enum_hall_satisfied1, enum_hall_satisfied2,

	enum_pie_standable, enum_pie_cant_use, enum_pie_unsatisfied,

	/**
	 * 业务受理
	 */
	enum_busi_accept_0, enum_busi_accept_1, enum_busi_accept_2, enum_busi_accept_center,

	/**
	 * 充值1009更新
	 */
	enum_rityaji_1_total, enum_rityaji_1_all_citys, enum_rityaji_1_city,

	enum_rityaji_2_city,

	/**
	 * 在线预览
	 */
	enum_online_preview_ppt, enum_online_preview_word, enum_online_preview_excel,

	/**
	 * 新日历
	 */
	enum_calendar_itemlist, enum_calendar_sublist, enum_calendar_day, enum_calendar_day_detail, enum_calendar_day_bottom,

	/**
	 * 开通中心
	 */
	enum_opencenter_csf, enum_opencenter_handle, enum_opencenter_mq, enum_opencenter_mq_detail, enum_opencenter_backlog, enum_opencenter_failure, enum_opentheprocessbacklog,

	/**
	 * 订单中心
	 */
	enum_order_mq, enum_order_broadband,

	/**
	 * 账户中心
	 */
	enum_account_mq,

	/**
	 * 月末月初
	 */
	enum_produceline_month_time, enum_produceline_month_item, enum_produceline_month_content, enum_produceline_month_change_content, enum_produceline_month_change_star, enum_produceline_month_add_star, enum_produceline_month_change_state,

	enum_produceline_moneyout_time, enum_produceline_moneyout_item, enum_produceline_moneyout_content, enum_produceline_moneyout_add_star,

	/**
	 * 下周期
	 */
	enum_next_period,

	/**
	 * 预缴返充
	 */
	enum_paymore_list,

	/**
	 * 运维资产
	 */
	enum_yunwei_asset_sum, enum_yunwei_asset_detail1, enum_yunwei_asset_detail2, enum_yunwei_asset_detail_combined, enum_yunwei_asset_mc, enum_yunwei_asset_mc_list, enum_yunwei_asset_tac, enum_yunwei_asset_bdm,enum_yunwei_asset_webLogic,enum_yunwei_asset_onlinechange,enum_yunwei_asset_linechart,enum_yunwei_asset_dealwithExpiredData,
	enum_yunwei_asset_dataset,enum_yunwei_asset_dataset_next,enum_yunwei_businesscertificate,
	/**
	 * 扣费提醒
	 */
	enum_deduction_remind_flow_chart, enum_deduction_remind_backlog, enum_deduction_remind_abnormal,

	/**
	 * 全网流量统付
	 */
	enum_enet_traffic_sys_tsib, enum_enet_traffic_sys_ate, enum_enet_traffic_sys_otmdb,

	/**
	 * 酬金出账检查
	 */
	enum_moneyout_check, enum_moneyout_check_pie,

	/**
	 * 上线变更单
	 */
	enum_net_change_change_list, enum_net_change_change_itemlist, enum_net_change_change_change,

	/**
	 * 用户添加
	 */
	enum_add_user,

	/**
	 * 考核指标
	 */
	enum_assessment_of_the_zone, enum_assessment_of_the_zone_dialog,

	/**
	 *
	 * 日志专区
	 */
	enum_log_zone, enum_log_zone2, enum_log_zone3, enum_log_zone4,enum_log_zone5,enum_log_zone6,
	/**
	 *
	 * 信控
	 */
	enum_credit_control, enum_credit_control2,enum_new_credit_control,enum_new_credit_control5,enum_new_credit_control2,enum_new_credit_control6,//重做的信控

	/**
	 *
	 * 人脸识别
	 */
	enum_face_recognition,enum_crm_app_Detail,

	/**
	 *
	 *
	 * 实名制
	 */
	enum_real_name_system, enum_network_total_graph,

	/**
	 *
	 *
	 * 立体监控
	 */
	enum_monitoring_alarm_first,enum_monitoring_alarm_stere,enum_monitoring_alarm_stere_list,enum_monitoring_alarm_xingneng,enum_monitoring_alarm_xingneng_list,
	enum_monitoring_alarm_duixiangjuzheng,enum_monitoring_alarm_duixiangjuzheng_list,enum_monitoring_alarm_duixiangjuzheng_list2,

	/**
	 *
	 * 亿阳审批
	 */
	enum_yiyangshenpi_approved, enum_yiyangshenpi_unapproved,enum_yiyangshenpi_detail1,enum_yiyangshenpi_detail2,enum_yiyangshenpi_status,

	/**
	 *
	 * 计费流程
	 */
	enum_billFlow,
	/**
	 *
	 * 故障自愈
	 */
	enum_guzhangziyu,enum_guzhangziyu_dialog,
	/**
	 *
	 * 各模块负责人
	 *
	 */
	enum_charge_people,
	/**
	 *
	 * 用户感知
	 *
	 */
	enum_entireperception,enum_entireperception_list,enum_entireperception_list_next,
	/**
	 *
	 * 宽带专区
	 *
	 */
	enum_bandService,
	/**
	 *
	 * 业务链
	 *
	 */
	enum_serviceChain,enum_serviceChain_next,
	/**
	 *
	 * 查询
	 *
	 */
	enum_search,
	/**
	 *
	 * 查询
	 *
	 */
	enum_2017kei,enum_2017kei_barGraph,enum_2017kei_expandlistView,enum_2017kei_expandlistView_child,
	/**
	 *
	 *
	 * 虚拟化桌面
	 */
	enum_virtual_desktop_TotalAccessWave,enum_virtual_desktop_KindsHeatWave,enum_virtual_desktop_Top10,
	/**
	 *
	 *
	 * 预案管理
	 */
	enum_plan_management_pmgroup,enum_plan_management_pmtotal,
	/**
	 *
	 *
	 *统一支付失败专区
	 */
	enum_hotzone_findTYZFBar,enum_hotzone_findTYZFCityFailure,enum_hotzone_findTYZFFailureDes,
	/**
	 * 开通网元失败专区
	 *
	 */
	enum_hotzone_findKTWYBar,enum_hotzone_findKTWYFailure,enum_hotzone_findKTWYCityFailure,enum_hotzone_findKTWYFailure_next,
	/**
	 *
	 * 物联网指标专区
	 *
	 */
	enum_hotzone_findWLWZBSucc,enum_hotzone_findWLWZBState,
	/**
	 *
	 * MainPageActivity
	 */
	enum_mainPage_num,
	/*
	* SearchActivity
	* */
	enum_more_item_search,
	/**
	 *
	 * 告警专区
	 */
	enum_gj_target,
	/*
	* 客户感知指标
	* */
	enum_user_perception_common_barchart,enum_user_peception_common_list,enum_user_peception_list_bar,
	/*
	* 局数据专区
	* */
	enum_office_collecting,enum_office_release,enum_group_increment_check,enum_file_receiving,
	/*服务查询
	* */
	enum_service_log_query,
	/*
	* 新宽带专区
	* */
	enum_new_band_service_linechart1,enum_new_band_service_linechart2,
	/*
	* 统一查询
	* */
	enum_month_data_tab,enum_uq_search
}
